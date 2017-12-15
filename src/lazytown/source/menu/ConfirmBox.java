package lazytown.source.menu;

import javafx.scene.image.ImageView;
import lazytown.source.AssetManager;
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
 * This is a class that is used for calling in new windows with buttons.
 */
public class ConfirmBox {

    private static Stage primaryStage;
    private static Scene scene;
    private static VBox root;
    private static Label label;
    private static Button yesButton;
    private static Button noButton;
    private static ImageView controls;
    private static boolean answer;
    private static final int SMALL_WINDOW_WIDTH = 500;
    private static final int SMALL_WINDOW_HEIGHT = 200;
    private static final int BIG_WINDOW_WIDTH = 800;
    private static final int BIG_WINDOW_HEIGHT = 500;

    /**
     * This method calls in a window with two buttons (Yes/No).
     * @param title the title of called in window.
     * @param message the label text inside the called in window.
     * @return returns a boolean value based on the button pressed.
     */
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
        // Tells all nodes in scene to use the MenuTheme.css style
        scene = new Scene(root, SMALL_WINDOW_WIDTH, SMALL_WINDOW_HEIGHT);
        scene.getStylesheets().add(AssetManager.getTheme("LoadingScreenTheme.css"));


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

        // Sets the scene of our stage to scene
        primaryStage.setScene(scene);

        // Returns true if the Enter key or Y is pressed, simulating the yes button being pressed.
        // Returns false if the Escape key or N is pressed, simulating the no button being pressed.
        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:  case Y: answer = true;  primaryStage.close(); break;
                case ESCAPE: case N: answer = false; primaryStage.close(); break;
            }
        });

        // Works together with Modality - shows the window and doesn't allow for other windows to be interacted with
        primaryStage.showAndWait();

        // Returns a true/false value based on which button was pressed
        return answer;

    }

    /**
     * This method calls in a window which shows controls and allows to exit the game.
     * @param title the title of called in window.
     * @return returns a boolean value based on the button pressed.
     */
    public static boolean displayMenu(String title) {
        // Instantiates a new Stage
        // Sets the title, width, resizability
        // Block input events on other windows until this is one is closed (this property is called Modality)
        primaryStage = new Stage();
        primaryStage.setTitle(title);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        // Creates a root (VBox) to store our message and our buttons
        // Adds padding, sets the alignment of all components to center
        root = new VBox(20);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setAlignment(Pos.CENTER);

        // Instantiates sceneMainMenu which will contain our root and sets the window size
        // Tells all nodes in scene to use the MenuTheme.css style
        scene = new Scene(root, BIG_WINDOW_WIDTH, BIG_WINDOW_HEIGHT);
        scene.getStylesheets().add(AssetManager.getTheme("MenuWindowTheme.css"));

        // Image displaying all controls
        controls = new ImageView(AssetManager.getUI("e_key.png"));

        // Creates the yes and no buttons
        yesButton = new Button("Exit Game");
        noButton = new Button("Resume");
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
        root.getChildren().addAll(controls, noButton, yesButton);

        // Sets the scene of our stage to scene
        primaryStage.setScene(scene);

        // Returns true if the Enter key or Y is pressed, simulating the yes button being pressed.
        // Returns false if the Escape key or N is pressed, simulating the no button being pressed.
        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:  case E: answer = true;  primaryStage.close(); break;
                case ESCAPE: case R: answer = false; primaryStage.close(); break;
            }
        });

        // Works together with Modality - shows the window and doesn't allow for other windows to be interacted with
        primaryStage.showAndWait();

        // Returns a true/false value based on which button was pressed
        return answer;

    }

}
