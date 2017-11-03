import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static boolean answer;

    //ConfirmBox will create a window and return a boolean based on what the user has clicked
    public static boolean display(String title, String message) {

        //creates our new window
        Stage window = new Stage();
        window.setTitle(title);
        window.setMinWidth(250);

        //block input events on other windows until this is one is closed
        window.initModality(Modality.APPLICATION_MODAL);

        //creates a label for our message (for example: "Are you sure you want to quit?")
        Label label = new Label(message);

        //creates the yes and no buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setMinWidth(100);
        noButton.setMinWidth(100);

        //if the user presses a button, return true/false and close the window
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        //creates a layout (VBox) to store our message and our buttons
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(label, yesButton, noButton);

        //aligns all contents to the center
        layout.setAlignment(Pos.CENTER);

        //creates a scene which contains our layout
        Scene scene = new Scene(layout);

        //sets the scene of our window to scene
        window.setScene(scene);

        //works together with Modality - shows the window and doesn't allow for other windows to be interacted with
        window.showAndWait();

        //returns a true/false value based on which button was pressed
        return answer;

    }

}
