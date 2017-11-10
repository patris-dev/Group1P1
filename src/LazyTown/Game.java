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

    // Parameters for the window and buttons
    private static int windowWidth = 800;
    private static int windowHeight = 600;
    private static int buttonWidth = windowWidth/4;
    private static int buttonHeight = windowHeight/10;

    public static void show(Stage window) {

        // Create our layout in the form of a VBox
        VBox vBox = new VBox(10);

        // Creates a scene which contains our layout
        Scene sceneGame = new Scene(vBox, windowWidth, windowHeight);

        // Sets the scene of our stage to sceneOptions
        window.setScene(sceneGame);

    }


}