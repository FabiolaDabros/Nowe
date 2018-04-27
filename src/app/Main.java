package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            primaryStage.setTitle("Kanban");
            FXMLLoader loader = new FXMLLoader();  // zaladowanie glownego fxml
            loader.setLocation(this.getClass().getClassLoader().getResource("app/app.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);

            AppController appController = loader.getController(); // stworzenie sceny do nowego zadania oraz edycji istniejacego
            appController.setEditTask(new EditTask(appController.toDoItems, appController.toDoID, appController.inProgressItems,
                    appController.inProgressID, appController.doneItems, appController.doneID));
            appController.setAddNewTask(new AddNewTask(appController.toDoItems, appController.toDoID));


            primaryStage.setScene(scene);
            primaryStage.setResizable(false);  // uniemozliwienie powiekszenia sceny
            primaryStage.show();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
