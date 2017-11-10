package LazyTown;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is the class that represents the MainMenu scene.
 */
public class MainMenu {

    // Parameters for the window and buttons
    private static int windowWidth = 800;
    private static int windowHeight = 600;
    private static int buttonWidth = windowWidth/4;
    private static int buttonHeight = windowHeight/10;

    public static void show(Stage window) {

        // Title of the game in the main menu, could later on be changed into an image
        Label gameTitle=new Label("Lazy Town");
        gameTitle.setId("label-title");

        // Adds a bit of padding to the top so the title would be lower
        gameTitle.setPadding(new Insets(40, 0, 0, 0));

        // Creates buttons
        Button buttonNewGame=new Button("New Game");
        Button buttonOptions=new Button("Options");
        Button buttonExitGame=new Button("Exit Game");

        // Sets button sizes
        buttonNewGame.setMinSize(buttonWidth,buttonHeight);
        buttonOptions.setMinSize(buttonWidth,buttonHeight);
        buttonExitGame.setMinSize(buttonWidth,buttonHeight);

        // Shorter version of handling events using a lambda expression, 'e' represents the event
        // Button.setOnAction(e -> YourCommand);
        buttonNewGame.setOnAction(e->{
            // Sets the scene for our window to the Game
            Game.show(window);
        });

        buttonOptions.setOnAction(e->{
            // Sets the scene for our window to the OptionsMenu
            OptionsMenu.show(window);
        });

        buttonExitGame.setOnAction(e->{
            // Opens up a ConfirmBox that asks if we want to close the program. If the user presses the "yes" button,
            // ConfirmBox.display returns true
            if(ConfirmBox.display("Exit Game","Are you sure you want to exit?")){
                window.close();
            }
        });

        // The layout of our contents in the app
        // BorderPane lays out the components in the top, bottom, left, right or center
        // VBox lays out the components in a vertical line, spacing is the distance between components inside the VBox
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox(10);
        // Aligns components of vBox to the center
        vBox.setAlignment(Pos.CENTER);

        // Adds the buttons to our vBox, and puts the vBox in to the center of borderPane
        vBox.getChildren().addAll(buttonNewGame, buttonOptions, buttonExitGame);
        borderPane.setCenter(vBox);

        // Adds the game title to the top of borderPane, aligns it to the center
        borderPane.setTop(gameTitle);
        BorderPane.setAlignment(gameTitle, Pos.CENTER);
        // Adds padding around the borderPane
        borderPane.setPadding(new Insets(10,10,10,10));

        // Creates a Scene which contains our layout (which, in this case, is borderPane), sets the window size
        Scene sceneMainMenu = new Scene(borderPane, windowWidth, windowHeight);

        // Uses the MenuTheme.css style
        sceneMainMenu.getStylesheets().add("LazyTown/assets/MenuTheme.css");

        // Sets the scene of our stage to sceneMainMenu
        window.setScene(sceneMainMenu);

    }

}