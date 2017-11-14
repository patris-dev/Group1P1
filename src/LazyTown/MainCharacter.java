package LazyTown;

import javafx.scene.image.Image;
import static LazyTown.Game.up;
import static LazyTown.Game.down;
import static LazyTown.Game.left;
import static LazyTown.Game.right;


public class MainCharacter extends MovedActor {

    // Our constructor again calling up to the superclass
    public MainCharacter(String SVGdata, double xLoc, double yLoc, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
    }

    @Override
    public void update() {
       // for now, all we update is the playable characters movement and position according to key presses
        if (right)
            iX += velX;
        if (left)
            iX -= velX;
        if (up)
            iY += velY;
        if (down)
            iY -= velY;
        spriteFrame.setTranslateX(iX);
        spriteFrame.setTranslateY(iY);
    }

    // The player characters collision is set to true. This does not actually do anything at the moment other than being
    // a placeholder stating that the player character can collide.
    public boolean collide(Actor object){return true;}
}
