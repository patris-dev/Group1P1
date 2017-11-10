import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * This is the main application class which creates our Stage and sets the scene to MainMenu.
 */
public class Main extends Application {

        private Stage window;

        public static void main(String[] args) {
            // Goes into Application, sets up main as a JavaFX application, launches start
            launch(args);
        }


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
            window = primaryStage;
            window.setTitle("LazyTown");

            // Prevents the window from being resized
            window.setResizable(false);

            // Sets the default scene as MainMenu
            MainMenu.show(window);

            // Shows the Stage on our screen
            window.show();
        }

    }

