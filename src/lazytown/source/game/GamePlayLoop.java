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
public class GamePlayLoop extends AnimationTimer {

    /**
     * The mandatory handle class for any AnimationTimer subclass. It takes care of executing the playerOne's update method
     * as well as update all of our actors in the game. This method is executed at 60Hz.
     */
    @Override
    public void handle(long now) {
        // Methods required to display FPS on the screen.
        float fps = Game.getFPS();
        System.out.println(fps);
        UI.updateFPS(fps);

        // Applying updates to the player character.
        playerOne.update();

        // Applying updates to all level-rendered actors (items, guards, etc.)
        Actor[][] actors = Game.level.getActors();
        for (int y = 0; y < Game.getLevel().getImageHeight(); y++) {
            for (int x = 0; x < Game.getLevel().getImageWidth(); x++) {
                if (actors[x][y] != null) actors[x][y].update();
            }
        }
    }

    /**
     * Any AnimationTimer subclass also needs start() and stop() methods to either begin or end the updating of our game.
     */
    @Override
    public void start(){
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
