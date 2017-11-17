package LazyTown;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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

        Label musicLabel = new Label("Music");
        musicLabel.setPadding(new Insets(20, 0, 0, 0));
        Label sfxLabel = new Label("Sound Effects");
        sfxLabel.setPadding(new Insets(20, 0, 0, 0));

        // A checkbox which mutes the music everywhere. Also stops/resumes the current music playing.
        CheckBox muteMusic = new CheckBox("Mute");
        muteMusic.setOnAction(e -> {
            if (muteMusic.isSelected()) {
                SoundEngine.setMuteMusic(true);
                Main.getMenuMusic().pause();
            }
            else {
                SoundEngine.setMuteMusic(false);
                Main.getMenuMusic().resume();
            }
        });

        // A checkbox which mutes the SFX (sound effects) everywhere.
        CheckBox muteSFX = new CheckBox("Mute");
        muteSFX.setOnAction(e -> {
            if (muteSFX.isSelected()) {
                SoundEngine.setMuteSFX(true);
            }
            else {
                SoundEngine.setMuteSFX(false);
            }
        });

        // A slider for controlling the volume of Music.
        Slider volumeMusic = new Slider(0.0, 1.0, 0.1);
        volumeMusic.setMaxWidth(Main.getWindowWidth() / 3);
        // When the slider is moved, sets the volume accordingly. Resumes the track to update the current volume.
        volumeMusic.valueProperty().addListener((observable, oldValue, newValue) -> {
            SoundEngine.setMusicVolume((double) newValue);
            Main.getMenuMusic().resume();
        });

        // A slider for controlling the volume of SFX.
        Slider volumeSFX = new Slider(0.0, 1.0, 0.3);
        volumeSFX.setMaxWidth(Main.getWindowWidth() / 3);
        // When the slider is moved, sets the volume accordingly. Makes a button click sound.
        volumeSFX.valueProperty().addListener((observable, oldValue, newValue) -> {
            SoundEngine.setSFXVolume((double) newValue);
            Main.getButtonClicks().play();
        });


        // Create our layout in the form of a VBox
        VBox optionsMenu = new VBox(5);
        optionsMenu.setPadding(new Insets(10));

        // Adds our button to the layout, positions it to the center
        optionsMenu.getChildren().addAll(backButton, musicLabel, volumeMusic, muteMusic, sfxLabel, volumeSFX, muteSFX);
        optionsMenu.setAlignment(Pos.CENTER);

        // Creates a scene which contains our layout
        Scene sceneOptions = new Scene(optionsMenu, Main.getWindowWidth(), Main.getWindowHeight());

        // Uses the MenuTheme.css style
        sceneOptions.getStylesheets().add("LazyTown/assets/MenuTheme.css");

        // Sets the scene of our stage to sceneOptions
        primaryStage.setScene(sceneOptions);

    }


}