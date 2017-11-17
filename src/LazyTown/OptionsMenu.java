package LazyTown;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is the class that represents the OptionsMenu scene.
 */
public class OptionsMenu {
    public static void show(Stage primaryStage) {

        // Creates a button to get back
        Button backButton = new Button("Back");
        backButton.setMinSize(Main.getButtonWidth(), Main.getButtonHeight());

        // When the button is clicked, set the scene of our window to MainMenu
        backButton.setOnAction(e -> {
            // Plays the buttonClick sound.
            Main.getButtonClicks().play();

            MainMenu.show(primaryStage);
        });

        // A checkbox which mutes the music everywhere. Also stops/resumes the current music playing.
        CheckBox muteMusic = new CheckBox("Mute Music");
        muteMusic.setOnAction(e -> {
            if (muteMusic.isSelected()) {
                SoundEngine.setMuteMusic(true);
                Main.getMenuMusic().stop();
            }
            else {
                SoundEngine.setMuteMusic(false);
                Main.getMenuMusic().play();
            }
        });

        // A checkbox which mutes the SFX (sound effects) everywhere.
        CheckBox muteSFX = new CheckBox("Mute Sound Effects");
        muteSFX.setOnAction(e -> {
            if (muteSFX.isSelected()) {
                SoundEngine.setMuteSFX(true);
            }
            else {
                SoundEngine.setMuteSFX(false);
            }
        });

        // Create our layout in the form of a VBox
        VBox optionsMenu = new VBox(10);
        optionsMenu.setPadding(new Insets(10, 10, 10, 10));

        // Adds our button to the layout, positions it to the center
        optionsMenu.getChildren().addAll(backButton, muteMusic, muteSFX);
        optionsMenu.setAlignment(Pos.CENTER);

        // Creates a scene which contains our layout
        Scene sceneOptions = new Scene(optionsMenu, Main.getWindowWidth(), Main.getWindowHeight());

        // Uses the MenuTheme.css style
        sceneOptions.getStylesheets().add("LazyTown/assets/MenuTheme.css");

        // Sets the scene of our stage to sceneOptions
        primaryStage.setScene(sceneOptions);

    }


}