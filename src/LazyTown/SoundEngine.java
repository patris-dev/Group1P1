package LazyTown;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is the class that allows to play sound files.
 * Although the SoundEngine can play multiple sound files at once, this is not recommended for music, as stopping the
 * audio will only stop the most recently played sound.
 */
public class SoundEngine {

    // path specifies the path of our sound files. Later on, a file name is appended.
    private String path = "src/LazyTown/assets/sounds/";
    // filename specifies the name of our file which will be appended to the end of the path.
    private String filename = "";
    // MediaPlayer is required to play the audio to our user.
    private MediaPlayer mediaPlayer;
    // Media is our audio object.
    private Media media;

    static final String SETTINGS_PATH = "src/LazyTown/savedData/settings.txt";

    // A dynamic SoundProperties object for determining properties of a created SoundEngine object.
    // This object will be set to one of our static SoundProperties objects, depending on the type.
    // isMuted shows whether the audio should not be played.
    // volume specifies the volume of the played sound. The range of volume is from 0.0 to 1.0.
    private SoundProperties soundProperties = new SoundProperties(true, 0.0);

    // Static SoundProperties objects for determining properties for all music/sfx sounds.
    private static final SoundProperties PROPERTIES_MUSIC = new SoundProperties(false, 0.1);
    private static final SoundProperties PROPERTIES_SFX = new SoundProperties(false, 0.3);

    // Constructor for our Sound Engine, requires to put in type.
    // type - a string specifying the type of sound files the engine should play (either "music" or "sfx").
    // If type is invalid, default values will remain (audio will be muted, and volume will be set to 0.0).
    public SoundEngine(String type) {
        // Checks the type of sound file, sets the dynamic soundProperties to one of the static ones.
        switch (type) {
            case "music": soundProperties = PROPERTIES_MUSIC; break;
            case "sfx":   soundProperties = PROPERTIES_SFX;   break;
        }
    }

    // Loads in a sound file.
    // mp3FileName - a string specifying the name of the file, located in the package specified in the path variable.
    public void load(String mp3FileName) {
        // Sets the filename to mp3FileName.
        filename = mp3FileName;
        // Creates a fullPath string which is composed of our path and filename strings.
        String fullPath = path + filename;
        // Sets the location of our media file to fullPath.
        media = new Media(new File(fullPath).toURI().toString());
        // Initializes the mediaPlayer to load the media file.
        mediaPlayer = new MediaPlayer(media);
    }

    // Stops the sound file.
    // If there are several sounds playing at once, stops the most recently played one.
    public void stop() {
        mediaPlayer.stop();
    }

    // Plays the loaded sound file.
    // Sets the volume of audio to our volume variable.
    // Checks if the audio is muted. If not, plays the sound from the beginning.
    public void play() {
        updateVolume();
        if (!soundProperties.isMuted()) {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        }
    }

    // Overloaded play method that loads and plays an audio track, in one method call.
    public void play(String mp3FileName) {
        load(mp3FileName);
        play();
    }

    // Pauses the track so it can be played again (resumed) afterwards.
    public void pause() {
        mediaPlayer.pause();
    }

    // Resumes playing a track from the time it was paused.
    public void resume() {
        updateVolume();
        if (!soundProperties.isMuted()) {
            mediaPlayer.play();
        }
    }

    public static void loadSettings() {
        String line;
        try {
            // FileReader reads text files. Sets the location of file to settingsPath.
            FileReader fileReader = new FileReader(SETTINGS_PATH);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Reads the settings on each line: music volume, music isMuted, SFX volume, SFX isMuted.
            // Sets the values of sliders and checkboxes accordingly.
            line = bufferedReader.readLine();
            SoundEngine.setMusicVolume(Double.parseDouble(line));

            line = bufferedReader.readLine();
            if (line.equals("true")) {
                SoundEngine.setMuteMusic(true); }
            else SoundEngine.setMuteMusic(false);

            line = bufferedReader.readLine();
            SoundEngine.setSFXVolume(Double.parseDouble(line));

            line = bufferedReader.readLine();
            if (line.equals("true")) {
                SoundEngine.setMuteSFX(true); }
            else SoundEngine.setMuteSFX(false);

            // Closes the file.
            bufferedReader.close();
        }
        // We must catch an exception when reading/writing text files, but for now it is ignored.
        catch(IOException ignored) { }
    }

    // Updates the volume.
    public void updateVolume() {
        mediaPlayer.setVolume(soundProperties.getVolume());
    }

    // Getters and setters.
    public static void setMuteMusic(boolean muteMusic) {
        SoundEngine.PROPERTIES_MUSIC.setMuted(muteMusic);
    }

    public static void setMuteSFX(boolean muteSFX) {
        SoundEngine.PROPERTIES_SFX.setMuted(muteSFX);
    }

    public static void setMusicVolume(double musicVolume) {
        SoundEngine.PROPERTIES_MUSIC.setVolume(musicVolume);
    }

    public static void setSFXVolume(double SFXVolume) {
        SoundEngine.PROPERTIES_SFX.setVolume(SFXVolume);
    }

    public static double getMusicVolume() {
        return SoundEngine.PROPERTIES_MUSIC.getVolume();
    }
    public static boolean getMusicMute() {
        return SoundEngine.PROPERTIES_MUSIC.isMuted();
    }
    public static double getSFXVolume() {
        return SoundEngine.PROPERTIES_SFX.getVolume();
    }
    public static boolean getSFXMute() {
        return SoundEngine.PROPERTIES_SFX.isMuted();
    }



}
