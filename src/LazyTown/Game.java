package LazyTown;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is the class that represents the Game scene.
 */
public class Game {

   public static void show(Stage primaryStage) {

        // Create our layout in the form of a VBox
        VBox vBox = new VBox(10);

        // Creates a scene which contains our layout
        Scene sceneGame = new Scene(vBox, Main.getWindowWidth(), Main.getWindowHeight());

        // Sets the scene of our stage to sceneOptions
        primaryStage.setScene(sceneGame);

    }


}