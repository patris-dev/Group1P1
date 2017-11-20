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

import java.io.*;
import java.util.Objects;

/**
 * This is the class that represents the OptionsMenu scene.
 */
public class OptionsMenu {

    private static Slider volumeMusic;
    private static Slider volumeSFX;
    private static CheckBox muteMusic;
    private static CheckBox muteSFX;
    private static String settingsPath = "src/LazyTown/savedData/settings.txt";

    public static void show(Stage primaryStage) {

        // Creates a button to get back
        Button backButton = new Button("Back");
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

        Label musicLabel = new Label("Music");
        musicLabel.setPadding(new Insets(20, 0, 0, 0));
        Label sfxLabel = new Label("Sound Effects");
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

        // Gets settings from a txt file.
        loadSettings();

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

    private static void loadSettings() {
        String line;
        try {
            // FileReader reads text files. Sets the location of file to settingsPath.
            FileReader fileReader = new FileReader(settingsPath);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Reads the settings on each line: music volume, music isMuted, SFX volume, SFX isMuted.
            // Sets the values of sliders and checkboxes accordingly.
            line = bufferedReader.readLine();
            volumeMusic.setValue(Double.parseDouble(line));

            line = bufferedReader.readLine();
            if (line.equals("true")) muteMusic.setSelected(true);
            else muteMusic.setSelected(false);

            line = bufferedReader.readLine();
            volumeSFX.setValue(Double.parseDouble(line));

            line = bufferedReader.readLine();
            if (line.equals("true")) muteSFX.setSelected(true);
            else muteSFX.setSelected(false);

            // Closes the file.
            bufferedReader.close();
        }
        // We must catch an exception when reading/writing text files, but for now it is ignored.
        catch(IOException ignored) { }
    }

    private static void saveSettings() {
        try {
            // FileWriter writes txt files. Sets the location of file to settingsPath.
            FileWriter fileWriter = new FileWriter(settingsPath);
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