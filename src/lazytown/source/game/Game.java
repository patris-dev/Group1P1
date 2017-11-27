package lazytown.source.game;


import javafx.scene.Group;
import lazytown.source.Main;
import lazytown.source.game.actor.MainCharacter;
import lazytown.source.game.actor.MovedActor;
import lazytown.source.game.level.Level;
import lazytown.source.sound.SoundEngine;
import lazytown.source.menu.ConfirmBox;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



/**
 * This is the class that represents the Game scene.
 */
public class Game {
    private static StackPane root;
    private static Group background;
    private static Scene sceneGame;
    private static GamePlayLoop gamePlayLoop;
    private static Image playerSprite ,p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
    static MovedActor playerOne;
    private static final int SPRITE_WIDTH = 75;
    private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
    private static SoundEngine backgroundMusic = new SoundEngine("music");
    private static Level level;

    // Here we declare four booleans which will be the foundation of the player controls, we do not initialize them as
    // they default to false, whenever they are changed to true, logic will happen in another class. Later on, there
    // will be more variables as we give the player character more controls, like interaction, and using items.
    private boolean up, down, left, right;

   public void show(Stage primaryStage) {
       // Variables
       root = new StackPane();
       background = new Group();
       root.getChildren().add(background);
       sceneGame = new Scene(root, Main.getWindowWidth(), Main.getWindowHeight());



       // Sets the scene of our stage to sceneGame
       primaryStage.setScene(sceneGame);

       // Instantiates a level object, renders the level
       level = new Level("map1.png");
       level.renderMap(background);

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
       playerSprite = new Image("lazytown/assets/images/PC.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
               false, true);

        p0 = new Image("lazytown/assets/images/P0.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p1 = new Image("lazytown/assets/images/P1.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p2 = new Image("lazytown/assets/images/P2.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p3 = new Image("lazytown/assets/images/P3.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p4 = new Image("lazytown/assets/images/P4.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p5 = new Image("lazytown/assets/images/P5.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p6 = new Image("lazytown/assets/images/P6.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p7 = new Image("lazytown/assets/images/P7.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p8 = new Image("lazytown/assets/images/P8.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p9 = new Image("lazytown/assets/images/P9.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p10 = new Image("lazytown/assets/images/P10.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
                false, true);
        p11 = new Image("lazytown/assets/images/P11.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
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
       playerOne = new MainCharacter(this,"", (Main.getWindowWidth()/2), (Main.getWindowHeight()/2), p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11);
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

    public static Group getBackground() {
        return background;
    }

    public static Level getLevel() {
        return level;
    }
}