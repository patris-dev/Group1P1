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


    // The main function of a JavaFX application
    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
           The main way of how a JavaFX application works:
           Stage is the window;
           Scene is the background;
           Layout contains objects you can store;
           Objects are buttons, labels, etc.

            Stage -> Scene -> Layout -> Objects

           A Stage can contain one scene at a given time.
           A Scene can contain one layout at a given time.
           A Layout can contain either several objects, or other layouts.
         */

        // Stage is the window
        // Sets the title of the window
        primaryStage.setTitle("LazyTown");
        // Prevents the window from being resized
        primaryStage.setResizable(false);
        // Sets the default scene as MainMenu
        MainMenu.show(primaryStage);
        // Shows the Stage on our screen
        primaryStage.show();

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
}

