package lazytown.source.menu;

import lazytown.source.Main;
import lazytown.source.sound.SoundEngine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

/**
 * This is the class that represents the OptionsMenu scene.
 */
public class OptionsMenu {

    private static Scene sceneOptions;
    private static VBox root;
    private static Button backButton;
    private static Label musicLabel;
    private static Label sfxLabel;
    private static Slider volumeMusic;
    private static Slider volumeSFX;
    private static CheckBox muteMusic;
    private static CheckBox muteSFX;

    public static void show(Stage primaryStage) {
        // Create our layout in the form of a VBox
        // Adds padding and sets alignment of all components to center
        root = new VBox(5);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        // Instantiates sceneOptions which will contain our root and sets the window size
        // Sets the scene of our stage to sceneOptions
        // Tells all nodes in sceneOptions to use the MenuTheme.css style
        sceneOptions = new Scene(root, Main.getWindowWidth(), Main.getWindowHeight());
        primaryStage.setScene(sceneOptions);
        sceneOptions.getStylesheets().add("lazytown/assets/uiassets/MenuTheme.css");


        // Creates a button to get back
        backButton = new Button("Back");
        backButton.setMinSize(Main.getButtonWidth(), Main.getButtonHeight());

        // When the button is clicked, set the scene of our window to MainMenu
        backButton.setOnAction(e -> {
            // Plays the buttonClick sound.
            Main.getButtonClicks().play();
            // Saves the settings to a txt file.
            saveSettings();
            // Sets the scene back to our MainMenu scene.
            MainMenu.show(primaryStage);
        });

        // Instantiates labels, adds padding
        musicLabel = new Label("Music");
        musicLabel.setPadding(new Insets(20, 0, 0, 0));
        sfxLabel = new Label("Sound Effects");
        sfxLabel.setPadding(new Insets(20, 0, 0, 0));

        // A checkbox which mutes the music everywhere. Also stops/resumes the current music playing.
        muteMusic = new CheckBox("Mute");
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
        muteSFX = new CheckBox("Mute");
        muteSFX.setOnAction(e -> {
            if (muteSFX.isSelected()) {
                SoundEngine.setMuteSFX(true);
            }
            else {
                SoundEngine.setMuteSFX(false);
            }
        });

        // A slider for controlling the volume of Music.
        volumeMusic = new Slider(0.0, 1.0, 0.1);
        volumeMusic.setMaxWidth(Main.getWindowWidth() / 3);
        // When the slider is moved, sets the volume accordingly. Resumes the track to update the current volume.
        volumeMusic.valueProperty().addListener((observable, oldValue, newValue) -> {
            SoundEngine.setMusicVolume((double) newValue);
            Main.getMenuMusic().updateVolume();
        });

        // A slider for controlling the volume of SFX.
        volumeSFX = new Slider(0.0, 1.0, 0.3);
        volumeSFX.setMaxWidth(Main.getWindowWidth() / 3);
        // When the slider is moved, sets the volume accordingly. Makes a button click sound.
        volumeSFX.valueProperty().addListener((observable, oldValue, newValue) -> {
            SoundEngine.setSFXVolume((double) newValue);
            Main.getButtonClicks().play();
        });

        // Sets the slider and checkbox visual values to our current settings.
        // Adds all nodes to the layout.
        showSettings();
        root.getChildren().addAll(backButton, musicLabel, volumeMusic, muteMusic, sfxLabel, volumeSFX, muteSFX);


    }

    // This method takes care of showing a graphical representation of our settings so that they can be adjusted
    private static void showSettings() {
        volumeMusic.setValue(SoundEngine.getMusicVolume());
        muteMusic.setSelected(SoundEngine.getMusicMute());
        volumeSFX.setValue(SoundEngine.getSFXVolume());
        muteSFX.setSelected(SoundEngine.getSFXMute());
    }

    private static void saveSettings() {
        try {
            // FileWriter writes txt files. Sets the location of file to settingsPath.
            FileWriter fileWriter = new FileWriter(SoundEngine.SETTINGS_PATH);
            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Writes the music volume, music isMuted, SFX volume, SFX isMuted on separate lines.
            bufferedWriter.write(Double.toString(volumeMusic.getValue())  + "\n");
            bufferedWriter.write(Boolean.toString(muteMusic.isSelected()) + "\n");
            bufferedWriter.write(Double.toString(volumeSFX.getValue())  + "\n");
            bufferedWriter.write(Boolean.toString(muteSFX.isSelected()) + "\n");

            // Closes the file.
            bufferedWriter.close();
        }
        // We must catch an exception when reading/writing text files, but for now it is ignored.
        catch(IOException ignored) { }
    }


}