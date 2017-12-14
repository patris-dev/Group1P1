package lazytown.source.game;


import javafx.scene.Scene;


import com.sun.javafx.perf.PerformanceTracker;
import java.security.AccessControlException;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class FPS {

    private static PerformanceTracker tracker;

    public void start() {
        VBox root = new VBox(20);
        Label label1 = new Label();
        Label label2 = new Label();
        root.getChildren().addAll(label1, label2);

        Scene scene = new Scene(root, 200, 100);

        try {
            System.setProperty("prism.verbose", "true");
            System.setProperty("prism.dirtyopts", "false");
            //System.setProperty("javafx.animation.fullspeed", "true");
            System.setProperty("javafx.animation.pulse", "10");
        } catch (AccessControlException e) {}

        scene.setOnKeyPressed((e)->{
            label2.setText(label1.getText());
        });



        tracker = PerformanceTracker.getSceneTracker(scene);
        AnimationTimer frameRateMeter = new AnimationTimer() {

            @Override
            public void handle(long now) {
                label1.setText(String.format("Current frame rate: %.3f fps", getFPS()));
            }
        };

        frameRateMeter.start();
    }

     public float getFPS () {
        float fps = tracker.getAverageFPS();
        tracker.resetAverageFPS();
        return fps;
    }

}