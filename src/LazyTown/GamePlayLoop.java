package LazyTown;

import javafx.animation.AnimationTimer;

public class GamePlayLoop extends AnimationTimer{

    // This class takes care of our gameplay loop, it extends from Animation timer, which basically just means that we
    // use JavaFX' pulse engine to cycle through our handle method as if it is a loop. this process needs to be
    // started and stopped via the two methods start() and stop() which calls the methods of the same name in the
    // AnimationTimer class.

    @Override
    public void handle(long now) {

    }

    @Override
    public void start(){
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
