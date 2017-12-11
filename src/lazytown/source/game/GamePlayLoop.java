package lazytown.source.game;

import javafx.animation.AnimationTimer;
import lazytown.source.game.actor.Actor;

import static lazytown.source.game.Game.playerOne;

/**
 * This class takes care of our gameplay loop, it extends from Animation timer, which basically just means that we
 * use JavaFX' pulse engine to cycle through our handle method as if it is a loop. this process needs to be
 * started and stopped via the two methods start() and stop() which calls the methods of the same name in the
 * AnimationTimer class.
 */
public class GamePlayLoop extends AnimationTimer{

    @Override
    public void handle(long now) {
        // Applying updates to the player character
        playerOne.update();

        // Applying updates to all level-rendered actors (items, guards, etc.)
        Actor[][] actors = Game.level.getActors();
        for (int y = 0; y < Game.getLevel().getImageHeight(); y++) {
            for (int x = 0; x < Game.getLevel().getImageWidth(); x++) {
                if (actors[x][y] != null) actors[x][y].update();
            }
        }
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
