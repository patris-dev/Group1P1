package LazyTown;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This is a class that calls in a new window with two buttons (Yes/No).
 * The method display will return a boolean based on the button that you click.
 */
public class ConfirmBox {

    static boolean answer;

    // ConfirmBox will create a window and return a boolean based on what the user has clicked
    public static boolean display(String title, String message) {

        // Creates our new window
        Stage primaryStage = new Stage();
        primaryStage.setTitle(title);
        primaryStage.setMinWidth(250);
        primaryStage.setResizable(false);
        // primaryStage.initStyle(StageStyle.TRANSPARENT);

        // Block input events on other windows until this is one is closed
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        // Creates a label for our message (for example: "Are you sure you want to quit?")
        Label label = new Label(message);

        // Creates the yes and no buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
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

        // Creates a layout (VBox) to store our message and our buttons
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(label, yesButton, noButton);

        // Aligns all contents to the center
        layout.setAlignment(Pos.CENTER);

        // Creates a scene which contains our layout
        Scene scene = new Scene(layout);

        // Uses the MenuTheme.css style
        scene.getStylesheets().add("LazyTown/assets/MenuTheme.css");

        // Sets the scene of our window to scene
        primaryStage.setScene(scene);

        // Works together with Modality - shows the window and doesn't allow for other windows to be interacted with
        primaryStage.showAndWait();

        // Returns a true/false value based on which button was pressed
        return answer;

    }

}
