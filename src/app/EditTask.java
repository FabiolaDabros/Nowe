package app;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class EditTask {

    public static ObservableList <Resource> toDo;
    public static ObservableList <Resource> inProgress;
    public static ObservableList <Resource> done;
    public static ListView <Resource> toDoID;
    public static ListView <Resource> inProgressID;
    public static ListView <Resource> doneID;

    public static Stage primaryStage;
    public static int whichList;
    static Resource item;
    static int index;

    public EditTask(ObservableList <Resource> toDo, ListView <Resource> toDoID, ObservableList <Resource> inProgress,
                    ListView <Resource> inProgressID, ObservableList <Resource> done, ListView <Resource> doneID) {
        primaryStage = new Stage();
        this.toDoID = toDoID;
        this.toDo = toDo;
        this.inProgress = inProgress;
        this.inProgressID = inProgressID;
        this.done = done;
        this.doneID = doneID;
    }

    public void load(Resource item, int index) {
        this.item = item;
        this.index = index;
        try { // zaladowanie fxml do edycji
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getClassLoader().getResource("app/editTask.fxml"));
            Parent rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle("Edit task");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Resource getItem() {
        return item;
    }

    public static int getIndex() {
        return index;
    }

    public static void add(Resource item, int index) {
        if (whichList == 1) {
            AppController.toDoItems.remove(index); //usuwamy stare zadanie
            AppController.toDoItems.add(item);     // dodajemy nowe zadanie
        } else if (whichList == 2) {
            AppController.inProgressItems.remove(index);
            AppController.inProgressItems.add(item);
        } else if (whichList == 3) {
            AppController.doneItems.remove(index);
            AppController.doneItems.add(item);
        }
    }

    public static void close() {
        primaryStage.close();
    }
}
