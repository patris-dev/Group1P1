package lazytown.source.game.actor;

import lazytown.source.game.Game;
import javafx.scene.image.Image;


public class MainCharacter extends MovedActor {
    Game game;

    // Our constructor again calling up to the superclass
    public MainCharacter(Game newGame, String SVGdata, double xLoc, double yLoc, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        game = newGame;
    }

    @Override
    public void update() {
       // for now, all we update is the playable characters movement and position according to key presses
        if (game.isRight())
            iX += velX;
        if (game.isLeft())
            iX -= velX;
        if (game.isUp())
            iY -= velY;
        if (game.isDown())
            iY += velY;
        spriteFrame.setTranslateX(iX);
        spriteFrame.setTranslateY(iY);
    }

    // The player characters collision is set to true. This does not actually do anything at the moment other than being
    // a placeholder stating that the player character can collide.
    public boolean collide(Actor object){return true;}
}
