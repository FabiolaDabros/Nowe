package app;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class AddNewTask {

    public static Stage primaryStage;
    public static ObservableList<Resource> toDo;
    public static ListView<Resource> toDoID;

    public AddNewTask(ObservableList <Resource> toDo, ListView <Resource> toDoID) {
        primaryStage = new Stage();
        this.toDo = toDo;
        this.toDoID = toDoID;
    }

    public static void close() {
        primaryStage.close();
    }

    public static void add(Resource item) {
        AppController.toDoItems.add(item);
    }
    public void load() {
        try {
             // zaladowanie fxml do dodania nowego zadania
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getClassLoader().getResource("app/addNewTask.fxml"));
            Parent rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle("Add new task");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
