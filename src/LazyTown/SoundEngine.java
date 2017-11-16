package LazyTown;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundEngine {

    private String bip;
    private Media hit;
    private MediaPlayer mediaPlayer;

    public void play(String mp3File) {
        bip = mp3File;
        hit = new Media(new File(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
}
