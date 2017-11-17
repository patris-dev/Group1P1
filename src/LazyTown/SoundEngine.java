package LazyTown;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * This is the class that allows to play sound files.
 * Although the SoundEngine can play multiple sound files at once, this is not recommended for music, as stopping the
 * audio will only stop the most recently played sound.
 */
public class SoundEngine {

    // path specifies the path of our sound files. Later on, a file name is appended.
    private String path = "src/LazyTown/assets/sounds/";
    // MediaPlayer is required to play the audio to our user.
    private MediaPlayer mediaPlayer;

    // isMuted shows whether the audio should not be played.
    // volume specifies the volume of the played sound. The range of volume is from 0.0 to 1.0.
    private boolean isMuted = true;
    private double volume = 0.0;

    // Static variables for muting and volume. These affect all the sounds that are played.
    private static boolean muteMusic;
    private static boolean muteSFX;
    private static double musicVolume = 0.1;
    private static double SFXVolume = 0.1;

    // Loads in a sound file.
    // mp3FileName - a string specifying the name of the file, located in the package specified in the path variable.
    // type - a string specifying the type of sound file (either "music" or "sfx").
    // If type is invalid, default values will remain (audio will be muted, and volume will be set to 0.0).
    public void load(String mp3FileName, String type) {

        // Checks the type of sound file, sets the volume and isMuted accordingly.
        switch (type) {
            case "music": this.volume = musicVolume; this.isMuted = muteMusic; break;
            case "sfx":   this.volume = SFXVolume;   this.isMuted = muteSFX;   break;
        }

        // Appends the filename to our path, sets the path of the media file to the whole path.
        path += mp3FileName;
        Media media = new Media(new File(path).toURI().toString());

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
    // Checks if the audio is muted. If not, plays the sound.
    public void play() {
        mediaPlayer.setVolume(volume);
        if (!isMuted) mediaPlayer.play();
    }
    // Overloaded play method that loads and plays an audio track, in one method call
    public void play(String mp3FileName, String path) {
        load(mp3FileName, path);
        play();
    }

    // Getters and setters.
    public static void setMuteMusic(boolean muteMusic) {
        SoundEngine.muteMusic = muteMusic;
    }

    public static void setMuteSFX(boolean muteSFX) {
        SoundEngine.muteSFX = muteSFX;
    }

    public static void setMusicVolume(double musicVolume) {
        SoundEngine.musicVolume = musicVolume;
    }

    public static void setSFXVolume(double SFXVolume) {
        SoundEngine.SFXVolume = SFXVolume;
    }
}
