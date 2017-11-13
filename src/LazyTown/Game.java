package LazyTown;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This is the class that represents the Game scene.
 */
public class Game {
    static StackPane root;
    static Scene sceneGame;
    // Here we declare four booleans which will be the foundation of the player controls, we do not initialize them as
    // they default to false, whenever they are changed to true, logic will happen in another class. Later on, there
    // will be more variables as we give the player character more controls, like interaction, and using items.
    static boolean up, down, left, right;

   public static void show(Stage primaryStage) {
       // Variables
       root = new StackPane();
       sceneGame = new Scene(root, Main.getWindowWidth(), Main.getWindowHeight());



       // Sets the scene of our stage to sceneOptions
       primaryStage.setScene(sceneGame);

       // Methods we need to call to make our game work
       eventHandling();
       assetLoading();
       spawnActors();
       renderActors();
       actorHandler();
       startGameLoop();
    }

    // This method does all of our event handling, when the user presses or releases a key, act accordingly.
    private static void eventHandling() {
        // First we need to listen for key presses, and set the appropriate boolean values accordingly, afterwards, we
        // will do the same for when the key is no longer pressed. We use switch statements for this as that is by far
        // the most compact and elegant way of programming this logic.
        sceneGame.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:     up      = true; break;
                case A:     left    = true; break;
                case S:     down    = true; break;
                case D:     right   = true; break;
            }
        });

        sceneGame.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:     up      = false; break;
                case A:     left    = false; break;
                case S:     down    = false; break;
                case D:     right   = false; break;
            }
        });

    }

    // This method is used for loading in our art assets, audio and visual.
    private static void assetLoading() {

    }

    // This method takes care of rendering our actors to the stackPane object that we have set up
    private static void renderActors() {

    }

    // This method takes care of spawning in our various actors, among those are the player, the guards, the pickups,
    // and whatever the player can interact with, later it could be extended to be more things, like props.
    private static void spawnActors() {

    }

    // This method is in charge of handling our actors. For now this is an empty method, but it will play a role when we
    // need to do collision detection and clean up actors in our scene that are no longer valid.
    private static void actorHandler() {

    }

    // This method starts our game loop, so what we have here is actually a dynamic game.
    private static void startGameLoop() {

    }


}