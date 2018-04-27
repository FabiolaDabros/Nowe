package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddNewTaskController  {
    public TextArea textID;
    public TextField titleID;
    public ComboBox priorityID;
    public DatePicker dateID = new DatePicker(null);
    //ComboBOX
    ObservableList<String> priorityItems = FXCollections.observableArrayList
            ("LOW PRIORITY", "MEDIUM PRIORITY", "HIGH PRIORITY");

    public AddNewTaskController(){}

    @FXML
    public void initialize() {       // dodanie rzeczy do ComboBox
        priorityID.setItems(priorityItems);
    }

    @FXML
    public void addButton() {
        try {
            if (titleID.getText().isEmpty() || priorityID.getSelectionModel().isEmpty()
                    || dateID.getValue().equals(null)) {
                throw new NullPointerException(); }
            else {
                String title = titleID.getText();
                String priority = (String) priorityID.getValue();
                String text = textID.getText();
                int day = (dateID.getValue().getDayOfMonth());
                int month = (dateID.getValue().getMonthValue());
                int year = (dateID.getValue().getYear());
                                                         // dodanie nowej rzeczy do listy
                AddNewTask.add(new Resource(title, priority, text , year, month, day));
                AddNewTask.close();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Not enough data!");
            alert.show();
        }
    }
}
