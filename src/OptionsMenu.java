import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OptionsMenu {

    //parameters for the window and buttons
    private static int windowWidth = 800;
    private static int windowHeight = 600;
    private static int buttonWidth = windowWidth/4;
    private static int buttonHeight = windowHeight/10;

    public static void show(Stage window) {

        //creates a button to get back
        Button backButton = new Button("Back");
        backButton.setMinSize(buttonWidth, buttonHeight);

        //when the button is clicked, set the scene of our window to MainMenu
        backButton.setOnAction(e -> MainMenu.show(window));

        //create our layout in the form of a VBox
        VBox optionsMenu = new VBox(10);
        optionsMenu.setPadding(new Insets(10, 10, 10, 10));

        //adds our button to the layout, positions it to the center
        optionsMenu.getChildren().addAll(backButton);
        optionsMenu.setAlignment(Pos.CENTER);

        //creates a scene which contains our layout
        Scene sceneOptions = new Scene(optionsMenu, windowWidth, windowHeight);

        //sets the scene of our stage to sceneOptions
        window.setScene(sceneOptions);

    }


}