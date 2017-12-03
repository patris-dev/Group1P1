package lazytown.source.game.actor;

import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import lazytown.source.Main;
import lazytown.source.game.Game;

/**
 * A class used for the guards, extends MovedActor class.
 */
public class Guard extends MovedActor {

    boolean animation = false;
    boolean facingUp = false;
    boolean facingDown = true;
    boolean facingLeft = false;
    boolean facingRight = false;

    int framecounter = 0;
    int runningspeed = 12;

    // Our constructor again calling up to the superclass
    public Guard(String SVGdata, double xLoc, double yLoc, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
    }

    @Override
    public void update() {
        setXYLocation();
        setImageState();
        moveCharacter();
    }

    // The guard currently moves side to side horizontally.
    private void setXYLocation() {
        if (facingLeft) {
            iX--;
            if (iX < 500) facingLeft = false;
        }
        else {
            iX++;
            if (iX > 1000) facingLeft = true;
        }

    }

    private void setImageState() {
        if (facingLeft) {
            spriteFrame.setImage(imageStates.get(11));
            spriteFrame.setScaleX(1);
        }
        else {
            spriteFrame.setImage(imageStates.get(9));
            spriteFrame.setScaleX(-1);
        }

    }

    private void moveCharacter() {
        spriteFrame.setTranslateX(iX);
        spriteFrame.setTranslateY(iY);
    }

}
