package LazyTown;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * This is the main application class which creates our Stage and sets the scene to MainMenu.
 */
public class Main extends Application {
    // Parameters for the window and buttons
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    private static final int BUTTON_WIDTH = WINDOW_WIDTH / 4;
    private static final int BUTTON_HEIGHT = WINDOW_HEIGHT / 10;
    // A SoundEngine object to load the menu music.
    // ! Important - do not convert into a local variable, as the media will be stopped by the garbage collector.
    private static SoundEngine menuMusic = new SoundEngine();


    // The main function of a JavaFX application
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Stage is the window
        // Sets the title of the window
        primaryStage.setTitle("LazyTown");
        // Prevents the window from being resized
        primaryStage.setResizable(false);
        // Sets the default scene as MainMenu
        MainMenu.show(primaryStage);

        // Tells the menuMusic to load the menuMusic file, specifies that it's a music file.
        menuMusic.load("menuMusic.mp3", "music");

        // Shows the Stage on our screen
        primaryStage.show();

        // Tells the SoundEngine menuMusic to play the loaded file.
        menuMusic.play();

    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getButtonWidth() {
        return BUTTON_WIDTH;
    }

    public static int getButtonHeight() {
        return BUTTON_HEIGHT;
    }

    public static SoundEngine getMenuMusic() {
        return menuMusic;
    }
}

