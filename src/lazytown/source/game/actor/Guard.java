package lazytown.source.game.actor;

import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import lazytown.source.Main;
import lazytown.source.game.Game;
import lazytown.source.game.level.Tile;

/**
 * A class used for the guards, extends MovedActor class.
 */
public class Guard extends MovedActor {

    boolean animation = false;
    boolean facingUp = false;
    boolean facingLeft = false;
    boolean movesHorizontally;

    int framecounter = 0;
    int runningspeed = 14;

    // Our constructor again calling up to the superclass
    public Guard(String SVGdata, double xLoc, double yLoc, boolean movesHorizontally, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        this.movesHorizontally = movesHorizontally;
    }

    @Override
    public void update() {
        setXYLocation();
        setImageState();
        moveCharacter();
    }

    // The guard currently moves side to side horizontally.
    private void setXYLocation() {
        if (movesHorizontally) {
            if (facingLeft) {
                iX-=2;
                if (Game.getLevel().getTiles()[(int)(iX/50)][(int)(iY/50)].isCollides()) facingLeft = false;
            } else {
                iX+=2;
                if (Game.getLevel().getTiles()[(int)(iX/50)+1][(int)(iY/50)].isCollides()) facingLeft = true;
            }
        }
        else {
            if (facingUp) {
                iY-=2;
                if (Game.getLevel().getTiles()[(int)(iX/50)][(int)(iY/50)].isCollides()) facingUp = false;
            } else {
                iY+=2;
                if (Game.getLevel().getTiles()[(int)(iX/50)][(int)(iY/50)+1].isCollides()) facingUp = true;
            }
        }

    }

    private void setImageState() {

        // Moving left
        if (movesHorizontally && facingLeft) {
            spriteFrame.setScaleX(1);
            this.setFlipH(false);
            if (!animation) {
                spriteFrame.setImage(imageStates.get(11));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else {
                spriteFrame.setImage(imageStates.get(9));
                if (framecounter >= runningspeed) {
                    animation = false;
                    framecounter = 0;
                } else {framecounter += 1;}
            }
        }

        // Moving right
        if (movesHorizontally && !facingLeft) {
            spriteFrame.setScaleX(-1);
            this.setFlipH(true);
            if (!animation) {
                spriteFrame.setImage(imageStates.get(9));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else if(animation) {
                spriteFrame.setImage(imageStates.get(11));
                if (framecounter >= runningspeed) {
                    animation = false;
                    framecounter = 0;
                } else {framecounter += 1;}
            }
        }

        // Moving down
        if (!movesHorizontally && !facingUp) {
            if (!animation) {
                spriteFrame.setImage(imageStates.get(6));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else if(animation) {
                spriteFrame.setImage(imageStates.get(7));
                if (framecounter >= runningspeed) {
                    animation = false;
                    framecounter = 0;
                } else {framecounter += 1;}
            }
        }

        // Moving up
        if (!movesHorizontally && facingUp) {
            if (!animation) {
                spriteFrame.setImage(imageStates.get(0));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else if(animation) {
                spriteFrame.setImage(imageStates.get(1));
                if (framecounter >= runningspeed) {
                    animation = false;
                    framecounter = 0;
                } else {framecounter += 1;}
            }
        }
    }

    private void moveCharacter() {
        spriteFrame.setTranslateX(iX);
        spriteFrame.setTranslateY(iY);
    }

}
