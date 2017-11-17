package LazyTown;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

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

    // isMuted shows whether the audio should not be played.
    // volume specifies the volume of the played sound. The range of volume is from 0.0 to 1.0.
    private SoundProperties soundProperties = new SoundProperties(true, 0.0);

    // Static variables for muting and volume. These affect all the sounds that are played.
    private static SoundProperties propertiesMusic = new SoundProperties(false, 0.1);
    private static SoundProperties propertiesSFX   = new SoundProperties(false, 0.5);

    // Constructor for our Sound Engine, requires to put in type.
    // type - a string specifying the type of sound files the engine should play (either "music" or "sfx").
    // If type is invalid, default values will remain (audio will be muted, and volume will be set to 0.0).
    public SoundEngine(String type) {
        // Checks the type of sound file, sets the volume and isMuted accordingly.
        switch (type) {
            case "music": soundProperties = propertiesMusic; break;
            case "sfx":   soundProperties = propertiesSFX;   break;
        }
    }

    // Loads in a sound file.
    // mp3FileName - a string specifying the name of the file, located in the package specified in the path variable.
    public void load(String mp3FileName) {
        // Sets the filename to a new value - mp3FileName. If it is empty, leaves the previously set filename.
        filename = mp3FileName;
        // Appends the filename to our path, sets the path of the media file to the whole path.
        String fullPath = path + filename;
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
    // Loads it again first, uses the previously loaded file.
    // Sets the volume of audio to our volume variable.
    // Checks if the audio is muted. If not, plays the sound.
    public void play() {
        mediaPlayer.setVolume(soundProperties.getVolume());
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

    // Getters and setters.
    public static void setMuteMusic(boolean muteMusic) {
        SoundEngine.propertiesMusic.setMuted(muteMusic);
    }

    public static void setMuteSFX(boolean muteSFX) {
        SoundEngine.propertiesSFX.setMuted(muteSFX);
    }

    public static void setMusicVolume(double musicVolume) {
        SoundEngine.propertiesMusic.setVolume(musicVolume);
    }

    public static void setSFXVolume(double SFXVolume) {
        SoundEngine.propertiesSFX.setVolume(SFXVolume);
    }
}
