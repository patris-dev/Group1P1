package LazyTown;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



/**
 * This is the class that represents the Game scene.
 */
public class Game {
    private static StackPane root;
    private static Scene sceneGame;
    private static GamePlayLoop gamePlayLoop;
    private static Image playerSprite;
    static MovedActor playerOne;
    private static final int SPRITE_WIDTH = 75;
    private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
    private static SoundEngine backgroundMusic = new SoundEngine("music");

    // Here we declare four booleans which will be the foundation of the player controls, we do not initialize them as
    // they default to false, whenever they are changed to true, logic will happen in another class. Later on, there
    // will be more variables as we give the player character more controls, like interaction, and using items.
    private boolean up, down, left, right;

   public void show(Stage primaryStage) {
       // Variables
       root = new StackPane();
       sceneGame = new Scene(root, Main.getWindowWidth(), Main.getWindowHeight());



       // Sets the scene of our stage to sceneGame
       primaryStage.setScene(sceneGame);

       // Methods we need to call to make our game work
       eventHandling();
       assetLoading();
       playMusic();
       spawnActors();
       renderActors();
       actorHandler();
       startGameLoop();
    }

    // This method does all of our event handling, when the user presses or releases a key, act accordingly.
    private void eventHandling() {
        // First we need to listen for key presses, and set the appropriate boolean values accordingly, afterwards, we
        // will do the same for when the key is no longer pressed. We use switch statements for this as that is by far
        // the most compact and elegant way of programming this logic.
        sceneGame.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:         up      = true; break;
                case A:         left    = true; break;
                case S:         down    = true; break;
                case D:         right   = true; break;
                case ESCAPE:    exitGame();
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
       playerSprite = new Image("LazyTown/assets/PC.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
               false, true);
       // Loads and plays the background music.
       backgroundMusic.load("menuMusic2.mp3");
    }

    private static void playMusic() {
        backgroundMusic.play();
    }

    // This method takes care of spawning in our various actors, among those are the player, the guards, the pickups,
    // and whatever the player can interact with, later it could be extended to be more things, like props.
    private void spawnActors() {
       playerOne = new MainCharacter(this,"", 0, 0, playerSprite);
    }
    // This method takes care of rendering our actors to the stackPane object that we have set up
    private static void renderActors() {
       root.getChildren().add(playerOne.spriteFrame);
    }

    // This method is in charge of handling our actors. For now this is an empty method, but it will load a role when we
    // need to do collision detection and clean up actors in our scene that are no longer valid.
    private static void actorHandler() {
       // This is a bit more advanced functionality, and will be implemented after the actor and its subclasses
    }

    // This method starts our game loop, so what we have here is actually a dynamic game.
    private static void startGameLoop() {
        gamePlayLoop = new GamePlayLoop();
        gamePlayLoop.start();

    }

    private static void exitGame(){
        if(ConfirmBox.display("Exit Game","Are you sure you want to exit?"))
            System.exit(0);
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }
}