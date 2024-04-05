package simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        GridPane gridPane = new GridPane();

        // Create Labels
        Label label1 = new Label("Label 1");
        Label label2 = new Label("Label 2");

        // Add Labels to GridPane
        gridPane.add(label1, 0, 0);
        gridPane.add(label2, 1, 0);

        // Setting the padding around the grid
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Create a Scene with GridPane as its root node
        Scene scene = new Scene(gridPane, 300, 200);

        // Set the scene on the primary stage
        primaryStage.setTitle("GridPane");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
