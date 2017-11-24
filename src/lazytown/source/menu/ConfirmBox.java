package lazytown.source.menu;

import lazytown.source.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is a class that calls in a new window with two buttons (Yes/No).
 * The method display will return a boolean based on the button that you click.
 */
public class ConfirmBox {

    private static Stage primaryStage;
    private static Scene scene;
    private static VBox root;
    private static Label label;
    private static Button yesButton;
    private static Button noButton;
    private static boolean answer;
    private static final int SMALL_WINDOW_WIDTH = 250;
    private static final int SMALL_WINDOW_HEIGHT = 200;

    // ConfirmBox will create a window and return a boolean based on what the user has clicked
    public static boolean display(String title, String message) {
        // Instantiates a new Stage
        // Sets the title, width, resizability
        // Block input events on other windows until this is one is closed (this property is called Modality)
        primaryStage = new Stage();
        primaryStage.setTitle(title);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        // Creates a root (VBox) to store our message and our buttons
        // Adds padding, sets the alignment of all components to center
        root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setAlignment(Pos.CENTER);

        // Instantiates sceneMainMenu which will contain our root and sets the window size
        // Sets the scene of our stage to scene
        // Tells all nodes in scene to use the MenuTheme.css style
        scene = new Scene(root, SMALL_WINDOW_WIDTH, SMALL_WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("lazytown/assets/uiassets/MenuTheme.css");


        // Creates a label for our message (for example: "Are you sure you want to quit?")
        label = new Label(message);

        // Creates the yes and no buttons
        yesButton = new Button("Yes");
        noButton = new Button("No");
        yesButton.setMinWidth(100);
        noButton.setMinWidth(100);

        // If the user presses a button, return true/false and close the window
        yesButton.setOnAction(e -> {
            // Plays the buttonClick sound.
            Main.getButtonClicks().play();

            answer = true;
            primaryStage.close();
        });
        noButton.setOnAction(e -> {
            // Plays the buttonClick sound.
            Main.getButtonClicks().play();

            answer = false;
            primaryStage.close();
        });

        // Adds all nodes to root
        root.getChildren().addAll(label, yesButton, noButton);

        // Works together with Modality - shows the window and doesn't allow for other windows to be interacted with
        primaryStage.showAndWait();

        // Returns a true/false value based on which button was pressed
        return answer;

    }

}
