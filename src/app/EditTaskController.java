package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditTaskController {
    public TextArea textID;
    public TextField titleID;
    public ComboBox priorityID;
    public DatePicker dateID;
    ObservableList< String > priorityItems = FXCollections.observableArrayList
            ("LOW PRIORITY", "MEDIUM PRIORITY", "HIGH PRIORITY");

    public EditTaskController() {}

    @FXML
    public void initialize() {   // wczytanie zadania
        titleID.setText(EditTask.getItem().getTitle());
        priorityID.setItems(priorityItems);
        dateID.setValue(EditTask.getItem().getDate());
        textID.setText(EditTask.getItem().getText());
        priorityID.setValue(EditTask.getItem().getPriority());
    }

    @FXML
    public void editButton() {
        try {    // sprawdzenie czy podano wszytskie informacje
            if (titleID.getText().isEmpty() || priorityID.getSelectionModel().isEmpty() ||
                    dateID.getValue().equals(null)) {
                throw  new NullPointerException();
            }
            else {
                String title = titleID.getText();
                String priority = (String) priorityID.getValue();
                String text = textID.getText();
                int d = (dateID.getValue().getDayOfMonth());
                int m = (dateID.getValue().getMonthValue());
                int y = (dateID.getValue().getYear());
                                                      // zmiana rzeczy na liscie
                EditTask.add(new Resource(title,  priority,text, y, m, d), EditTask.getIndex());
                EditTask.close();
            }

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Not enough data!");
            alert.show();
        }
    }
}
