package lazytown.source.game;


import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import lazytown.assets.AssetManager;
import lazytown.source.Main;
import lazytown.source.game.actor.*;
import lazytown.source.game.level.Level;
import lazytown.source.sound.SoundEngine;
import lazytown.source.menu.ConfirmBox;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * This is the class that represents the Game scene.
 * It is responsible for loading in everything and starting the game loop.
 */
public class Game {
    public static StackPane root;
    private static Group background;
    private static Scene sceneGame;
    private static GamePlayLoop gamePlayLoop;
    private static Image[] pSprites;
    public static MovedActor playerOne;
    private static SoundEngine backgroundMusic = new SoundEngine("music");
    public static Level level;
    public static Director director;


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


       // Methods we need to call to make our game work
       showLoadingScreen(primaryStage);
       loadLevel();
       eventHandling();
       assetLoading();
       playMusic();
       spawnActors();
       renderActors();
       actorHandler();
       startGameLoop();
       renderUI();

       // Sets the scene of our stage to sceneGame
       primaryStage.setScene(sceneGame);
    }

    // This method renders a loading screen.
    private void showLoadingScreen(Stage primaryStage) {

        BorderPane loadingScreen = new BorderPane();
        loadingScreen.setPadding(new Insets(10));

        Label loadingLabel = new Label("Loading...");
        loadingScreen.setBottom(loadingLabel);

        Scene loadingScene = new Scene(loadingScreen, Main.getWindowWidth(), Main.getWindowHeight());
        loadingScene.getStylesheets().add(AssetManager.getTheme("loadingScreenTheme.css"));
        primaryStage.setScene(loadingScene);
    }

    // This method is used to render the level from a map.
    private void loadLevel() {
        // Instantiates a level object, renders the level
        level = new Level(1);
        level.renderMap(background);
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

       pSprites = AssetManager.getPlayerSprites();

       // Loads and plays the background music.
       backgroundMusic.load("menuMusic2.mp3");
    }

    private static void playMusic() {
        backgroundMusic.play();
    }

    // This method takes care of spawning in our various actors, among those are the player, the guards, the pickups,
    // and whatever the player can interact with, later it could be extended to be more things, like props.
    private void spawnActors() {
       playerOne = new MainCharacter(this,"", (level.getImageWidth()/2), (level.getImageHeight()/2), pSprites);
    }

    // This method takes care of rendering our actors to the stackPane object that we have set up
    private static void renderActors() {
       root.getChildren().add(playerOne.spriteFrame);

        Actor[][] actors = level.getActors();
       for (int y = 0; y < level.getImageHeight(); y++) {
           for (int x = 0; x < level.getImageWidth(); x++) {
               if (actors[x][y] != null) background.getChildren().add(actors[x][y].spriteFrame);
           }
       }
    }

    // This method is in charge of handling our actors. For now this is an empty method, but it will load a role when we
    // need to do collision detection and clean up actors in our scene that are no longer valid.
    private static void actorHandler() {
        director = new Director();
        // Here we add the objects we want to check collision with
        director.addCurrentActors();
    }

    // This method starts our game loop, so what we have here is actually a dynamic game.
    private static void startGameLoop() {
        gamePlayLoop = new GamePlayLoop();
        gamePlayLoop.start();

    }

    // This method renders the in-game user interface.
    private void renderUI() {
        root.getChildren().add(UI.getUI());
    }

    // This method renders a new window to exit the game.
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