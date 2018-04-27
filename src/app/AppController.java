package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AppController implements Initializable, Serializable{

    private EditTask editTask;
    private AddNewTask addsNewTask;
    public MenuItem closeID;
    public Menu aboutID;
    public AnchorPane anchorPaneID;

    public static ObservableList<Resource> toDoItems = FXCollections.observableArrayList();
    public static ObservableList<Resource> inProgressItems = FXCollections.observableArrayList();
    public static ObservableList<Resource> doneItems = FXCollections.observableArrayList();

    public ListView<Resource> toDoID = new ListView<>(toDoItems);
    public ListView<Resource> inProgressID = new ListView<>(inProgressItems);
    public ListView<Resource> doneID = new ListView<>(doneItems);

    public MenuItem saveID;
    public MenuItem loadID;
    public MenuItem importID;
    public MenuItem exportID;

    Serialize serialize =new Serialize();

    public AppController(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       // toDoItems.add(new Resource("Send CV to Sabre", "MEDIUM", "Application via website", 2018, 4, 1));
       // toDoItems.add(new Resource("Send CV to Relativity", "HIGH", "Send an email", 2018, 4, 25));
      // toDoItems.add(new Resource("Send CV to PEGA", "HIGH", "Send an email", 2018, 4, 11));

        loadFromFile();
        saveID.setOnAction(event -> saveToFile());
        loadID.setOnAction(event -> loadFromFile());
        exportID.setOnAction(event -> exportToFile());
        importID.setOnAction(event -> importFromFile());


                                              // About Author
        Label about = new Label("About");
        about.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information about author");
                alert.setHeaderText("Author : Fabiola Dąbroś");
                alert.showAndWait();
            }
        });

        aboutID.setGraphic(about);
        // dodanie list
        toDoID.setItems(toDoItems);
        inProgressID.setItems(inProgressItems);
        doneID.setItems(doneItems);


        // przenoszenie miedzy listami przy pomocy strzalki
        toDoID.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case RIGHT:
                            if (!toDoID.getItems().isEmpty()) {
                                inProgressID.getItems().add(toDoID.getItems().get(toDoID.getFocusModel().getFocusedIndex()));
                                toDoID.getItems().remove(toDoID.getItems().get(toDoID.getFocusModel().getFocusedIndex()));
                            }
                            break;
                        case E:
                            if (toDoID.getSelectionModel().getSelectedIndex() >= 0) {
                                int index = toDoID.getSelectionModel().getSelectedIndex();
                                editTask.load(toDoID.getItems().get(index), index);
                                editTask.whichList = 1;    // ustawienie listy na 1
                            }

                    }
                }
        );
        inProgressID.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT:
                    if (!inProgressID.getItems().isEmpty()) {
                        doneID.getItems().add(inProgressID.getItems().get(inProgressID.getFocusModel().getFocusedIndex()));
                        inProgressID.getItems().remove(inProgressID.getItems().get(inProgressID.getFocusModel().getFocusedIndex()));
                    }
                    break;
                case LEFT:
                    if (!inProgressID.getItems().isEmpty()) {
                        toDoID.getItems().add(inProgressID.getItems().get(inProgressID.getFocusModel().getFocusedIndex()));
                        inProgressID.getItems().remove(inProgressID.getItems().get(inProgressID.getFocusModel().getFocusedIndex()));
                    }
                    break;
            }
        });
        doneID.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    if (!doneID.getItems().isEmpty()) {
                        inProgressID.getItems().add(doneID.getItems().get(doneID.getFocusModel().getFocusedIndex()));
                        doneID.getItems().remove(doneID.getItems().get(doneID.getFocusModel().getFocusedIndex()));
                    }
                    break;
            }
        });

        // ustawienie kolorow priorytetow
        toDoID.setCellFactory(new Callback<ListView<Resource>, ListCell<Resource>>() {
            @Override public ListCell<Resource> call (ListView<Resource> list) {
                return new ColorRectCell();
            }
        });
        inProgressID.setCellFactory(new Callback<ListView<Resource>,ListCell<Resource>>() {
            @Override
            public ListCell<Resource> call(ListView<Resource> list) {
                return new ColorRectCell();
            }}
        );
        doneID.setCellFactory(new Callback<ListView<Resource>,ListCell<Resource>>() {
            @Override
            public ListCell<Resource> call(ListView<Resource> list) {
                return new ColorRectCell();
            }}
        );

    }
//Serializacja

    protected void saveToFile(){
        serialize.first=new ArrayList<>(toDoItems); // konwersja observable list na arraylist
        serialize.second=new ArrayList<>(inProgressItems);
        serialize.third=new ArrayList<>(doneItems);

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream // tworzymy strumień do zapisu i w konstruktorze przekazujemy adres do pliku ;
                (new FileOutputStream("Objects.bin"))) {      // obiekt FileOutputStream łączy się z plikiem, a w razie potrzeby tworzy go
            objectOutputStream.writeObject(serialize);    //zapis do strumienia
            objectOutputStream.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Serialization");
            alert.setHeaderText("Serialized data is saved in 'Objects.bin'");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Serialization");
            alert.setHeaderText("Serialized data are not saved in 'Objects.bin'");
            alert.showAndWait();
        }
    }

    //deserializacja

    private void loadFromFile(){
        try(ObjectInputStream objectInputStream = new ObjectInputStream
                (new FileInputStream("Objects.bin"))){
            serialize = (Serialize) objectInputStream.readObject();           // odczyt obiektu ze strumienia
            objectInputStream.close();
        } catch (FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Serialization");
            alert.setHeaderText("There is not any file like that!   \n "+e);
            alert.showAndWait();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        toDoItems= FXCollections.observableArrayList(serialize.first);  // konwersja arraylist na observable list
        toDoID.setItems(toDoItems);

        inProgressItems= FXCollections.observableArrayList(serialize.second);
        inProgressID.setItems(inProgressItems);

        doneItems= FXCollections.observableArrayList(serialize.third);
        doneID.setItems(doneItems);
    }

    //export

    private void exportToFile(){
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) anchorPaneID.getScene().getWindow();  //ustalenie sceny
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(workingDirectory);  //okreslenie poczatkowego katalogu
        fileChooser.setTitle("Choose file where you want export data ");
        fileChooser.getExtensionFilters().addAll(           // obsluga filtracji rozszerzen
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
        File file = fileChooser.showSaveDialog(stage); // wyswietlenie w okreslonej pozycji
        // File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            serialize.first=new ArrayList<>(toDoItems); // konwersja observable list na arraylist
            serialize.second=new ArrayList<>(inProgressItems);
            serialize.third=new ArrayList<>(doneItems);

            try(ObjectOutputStream objectOutputStream = new ObjectOutputStream // tworzymy strumień do zapisu i w konstruktorze przekazujemy adres do pliku ;
                    (new FileOutputStream(file.getAbsolutePath()))) {      // obiekt FileOutputStream łączy się z plikiem, a w razie potrzeby tworzy go
                objectOutputStream.writeObject(serialize);    //zapis do strumienia
                objectOutputStream.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export");
                alert.setHeaderText("Data exported to: " + file.getAbsolutePath() );
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export");
                alert.setHeaderText("Data nor exported to: " + file.getAbsolutePath() );
                alert.showAndWait();
            }
        }
    }
    //import
    private void importFromFile(){
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) anchorPaneID.getScene().getWindow();  //ustalenie sceny
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(workingDirectory);  //okreslenie poczatkowego katalogu
        fileChooser.setTitle("Choose file you want to import");
        fileChooser.getExtensionFilters().addAll(           // obsluga filtracji rozszerzen
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
        File file = fileChooser.showOpenDialog(stage); // wyswietlenie w okreslonej pozycji
        // File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            //System.out.println(file.getAbsolutePath());
            try(ObjectInputStream objectInputStream = new ObjectInputStream
                    (new FileInputStream(file.getAbsolutePath()))){
                serialize = (Serialize) objectInputStream.readObject();           // odczyt obiektu ze strumienia
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Import");
                alert.setHeaderText("Opened from   \n "+file.getAbsolutePath());
                alert.showAndWait();
                objectInputStream.close();
            } catch (FileNotFoundException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Import");
                alert.setHeaderText("There is not any file like that!   \n "+e);
                alert.showAndWait();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            toDoItems= FXCollections.observableArrayList(serialize.first);  // konwersja arraylist na observable list
            toDoID.setItems(toDoItems);

            inProgressItems= FXCollections.observableArrayList(serialize.second);
            inProgressID.setItems(inProgressItems);

            doneItems= FXCollections.observableArrayList(serialize.third);
            doneID.setItems(doneItems);
        }
    }


    public void setAddNewTask(AddNewTask addNewTask) {
        this.addsNewTask = addNewTask; }

    public void setEditTask(EditTask editTask){
        this.editTask = editTask;
    }

    @FXML            // File -> Close
    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML           // zaladowanie fxml do dodania nowego zadania
    public void addNewTaskButton(){
         addsNewTask.load();
   }
          // usuwanie lub edycja zadan
    @FXML
    public void editTODO(ActionEvent actionEvent) {
        if (toDoID.getSelectionModel().getSelectedIndex() >= 0) {
            int index = toDoID.getSelectionModel().getSelectedIndex();
             editTask.load(toDoID.getItems().get(index), index);
              editTask.whichList = 1;    // ustawienie listy na 1
        }
    }
    public void deleteTODO(ActionEvent actionEvent) {
        if (toDoID.getSelectionModel().getSelectedIndex() >= 0) {
            int index = toDoID.getSelectionModel().getSelectedIndex();
            toDoID.getItems().remove(index);
        }
    }
    public void editInProgress(ActionEvent actionEvent) {
        if (inProgressID.getSelectionModel().getSelectedIndex() >= 0) {
            int index = inProgressID.getSelectionModel().getSelectedIndex();
            editTask.load(inProgressID.getItems().get(index), index);
            editTask.whichList = 2;   // ustawienie listy na 2
        }
    }
    public void deleteInProgress(ActionEvent actionEvent) {
        if (inProgressID.getSelectionModel().getSelectedIndex() >= 0) {
            int index = inProgressID.getSelectionModel().getSelectedIndex();
            inProgressID.getItems().remove(index);
        }
    }
    public void editDone(ActionEvent actionEvent) {
        if (doneID.getSelectionModel().getSelectedIndex() >= 0) {
            int index = doneID.getSelectionModel().getSelectedIndex();
            editTask.load(doneID.getItems().get(index), index);
            editTask.whichList = 3;   // ustawienie listy na 3
        }
    }
    public void deleteDone(ActionEvent actionEvent) {
        if (doneID.getSelectionModel().getSelectedIndex() >= 0) {
            int index = doneID.getSelectionModel().getSelectedIndex();
            doneID.getItems().remove(index);
        }
    }

// przenoszenie miedzy listami

    public void dragDetectedTODO(DragEvent dragEvent) {

       // Dragboard db= st ;
//        Dragboard db = toDoID.startDragAndDrop(TransferMode.ANY);
////        ClipboardContent cb = new ClipboardContent();
////        cb.putString(toDoID.getAccessibleText());
////        db.setContent(cb);
////        mouseEvent.consume();
//        ObservableList<String> items = getListView().getItems();
//        Dragboard dragboard = toDoID.startDragAndDrop(TransferMode.MOVE);
//        ClipboardContent content = new ClipboardContent();
//        content.putString(getItem());


    }

    public void dragDroppedInProgress(DragEvent dragEvent) {

        //ObservableList str = dragEvent.getDragboard().getDragView();
       //inProgressID.setItems(str);
       // inProgressID.getItems().add(toDoID.getItems().get(toDoID.getFocusModel().getFocusedIndex()));
    }

    public void dragOverInProgress(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasString()){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    // dodanie elipsy przed tytulem zadania
    static class ColorRectCell extends ListCell<Resource> {
        @Override
        public void updateItem(Resource item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null) {
                setText(getItem().getTitle());
                Ellipse rect = new Ellipse(5,10,5,10);
                rect.setFill(Color.web(getItem().getPriorityColor()));
                setGraphic(rect);
                Tooltip tooltip = new Tooltip();     // stworzenie tooltipa do wyswietlania tekstu
                tooltip.setText(getItem().getText());
                setTooltip(tooltip);

            } else {
                setText(null);
                setGraphic(null);
                setTooltip(null);
            }
        }}
}
