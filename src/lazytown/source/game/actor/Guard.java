package lazytown.source.game.actor;

import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import lazytown.source.Main;
import lazytown.source.game.Game;
import lazytown.source.game.UI;
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

    /**
     * Our constructor again calling up to the superclass.
     * @param SVGdata a string which determines vector data for the collision area.
     * @param xLoc x coordinate of the rendered Guard.
     * @param yLoc y coordinate of the rendered Guard.
     * @param spriteCels an array of images for animating the Guard.
     */
    public Guard(String SVGdata, double xLoc, double yLoc, boolean movesHorizontally, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        this.movesHorizontally = movesHorizontally;
    }

    /**
     * An update method for our guard, as the guard object is animated and moving, we are using the update method to
     * allow the game play loop to reflect changes
     */
    @Override
    public void update() {
        checkCollision();
        setXYLocation();
        setImageState();
        moveCharacter();
    }

    // The guard currently moves side to side horizontally.
    private void setXYLocation() {
        if (movesHorizontally) {
            if (facingLeft) {
                iX-=velX;
                if (Game.getLevel().getTiles()[(int)(iX/50)][(int)(iY/50)].isCollides()) facingLeft = false;
            } else {
                iX+=velX;
                if (Game.getLevel().getTiles()[(int)(iX/50)+1][(int)(iY/50)].isCollides()) facingLeft = true;
            }
        }
        else {
            if (facingUp) {
                iY-=velY;
                if (Game.getLevel().getTiles()[(int)(iX/50)][(int)(iY/50)].isCollides()) facingUp = false;
            } else {
                iY+=velY;
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

    private void checkCollision() {
        // When we check for collision we cycle through the list of all of our actors
        for (int i = 0; i < Game.director.getCurrentActors().size(); i++) {
            // And as we do that we set them up in a temporary object
            Actor object = Game.director.getCurrentActors().get(i);
            // This object is then being parsed and tested in our collide method
            if (collide(object)) {

                if (object instanceof Tile || object instanceof InteractiveActor) {
                    if (!facingUp) {
                        iY -= velY;
                    }
                    else if (facingUp) {
                        iY += velY;
                    }
                    if (!facingLeft) {
                        iX -= velX;
                    }
                    else if (facingLeft) {
                        iX += velX;
                    }
                }
            }
        }
    }

    /**
     * We check for collision in a two stage process, this is to save resources, as checking for collision can be
     * quite costly. The way we do this is by first using an if statement to check if two imageView nodes intersect
     * with each other, if they do we create a new Shape object from the two intersecting ImageView nodes, the width
     * of which we measure. If this width is not negative 1, we return true, else we return false.
     * @param object object which is checked for collision.
     */
    private boolean collide(Actor object){
        if (object.getSpriteFrame().getBoundsInParent().intersects(
                iX+levelWidth/2-20, iY+levelHeight/2-37.5, 40, 75)) {
            Shape intersection = SVGPath.intersect(Game.playerOne.getSpriteBoundary(), object.getSpriteBoundary());
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                return true;
            }
        }
        return false;
    }

    private void moveCharacter() {
        spriteFrame.setTranslateX(iX);
        spriteFrame.setTranslateY(iY);
    }

}
