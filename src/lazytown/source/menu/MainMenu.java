package lazytown.source.menu;


import lazytown.assets.AssetManager;
import lazytown.source.game.Game;
import lazytown.source.Main;
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

    private static BorderPane root;
    private static Scene sceneMainMenu;
    private static VBox buttonContainer;
    private static Game newGame;
    private static Label gameTitle;
    private static Button buttonNewGame;
    private static Button buttonOptions;
    private static Button buttonExitGame;

    public static void show(Stage primaryStage) {
        // Instantiates root, adds padding around it
        // BorderPane can lay out the components in the top, bottom, left, right or center
        // Adds padding around the root, extra padding on top
        root = new BorderPane();
        root.setPadding(new Insets(50,10,10,10));

        // Instantiates sceneMainMenu which will contain our root and sets the window size
        // Tells all nodes in sceneMainMenu to use the MenuTheme.css style
        sceneMainMenu = new Scene(root, Main.getWindowWidth(), Main.getWindowHeight());
        sceneMainMenu.getStylesheets().add(AssetManager.getTheme("MenuTheme.css"));


        // VBox lays out the components in a vertical line, spacing is the distance between components inside the VBox
        // Places buttonContainer in the center of BorderPane root
        // Aligns components of vBox to the center
        buttonContainer = new VBox(10);
        root.setCenter(buttonContainer);
        buttonContainer.setAlignment(Pos.CENTER);

        // Instantiates newGame
        newGame = new Game();


        // Title of the game in the main menu, could later on be changed into an image.
        // Sets the ID of our label to label-title to differentiate it from other labels in CSS.
        // Places gameTitle to the top of BorderPane root
        // Aligns it to the center
        gameTitle = new Label("Lazy Town");
        gameTitle.setId("label-title");
        root.setTop(gameTitle);
        BorderPane.setAlignment(gameTitle, Pos.CENTER);

        // Instantiates buttons
        buttonNewGame = new Button("New Game");
        buttonOptions = new Button("Options");
        buttonExitGame = new Button("Exit Game");

        // Sets button sizes
        buttonNewGame.setMinSize(Main.getButtonWidth(),Main.getButtonHeight());
        buttonOptions.setMinSize(Main.getButtonWidth(),Main.getButtonHeight());
        buttonExitGame.setMinSize(Main.getButtonWidth(),Main.getButtonHeight());


        // Shorter version of handling events using a lambda expression, 'e' represents the event.
        // Usage: Button.setOnAction(e -> YourCommand);

        // Sets the actions of a clicked New Game button
        buttonNewGame.setOnAction(e->{
            // Plays the buttonClick sound.
            Main.getButtonClicks().play();
            // Stops the menu music.
            Main.getMenuMusic().stop();
            // Sets the scene for our window to the Game
            newGame.show(primaryStage);
        });

        // Sets the actions of a clicked Options button
        buttonOptions.setOnAction(e->{
            // Plays the buttonClick sound.
            Main.getButtonClicks().play();
            // Sets the scene for our window to the OptionsMenu
            OptionsMenu.show(primaryStage);
        });

        // Sets the actions of a clicked Exit Game button
        buttonExitGame.setOnAction(e->{
            // Plays the buttonClick sound.
            Main.getButtonClicks().play();
            // Opens up a ConfirmBox that asks if we want to close the program. If the user presses the "yes" button,
            // ConfirmBox.display returns true
            if(ConfirmBox.display("Exit Game","Are you sure you want to exit?")){
                primaryStage.close();
            }
        });

        // Adds the buttons to our vBox, and puts the vBox in to the center of root
        buttonContainer.getChildren().addAll(buttonNewGame, buttonOptions, buttonExitGame);

        // Sets the scene of our stage to sceneMainMenu
        primaryStage.setScene(sceneMainMenu);

    }

}