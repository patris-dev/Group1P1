package lazytown.source.game.actor;

import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import lazytown.source.Main;
import lazytown.source.game.Game;
import javafx.scene.image.Image;

/**
 * A class used for the main character, extends MovedActor class.
 */
public class MainCharacter extends MovedActor {
    protected Game game;

    boolean animation = false;
    boolean facingUp = false;
    boolean facingDown = true;
    boolean facingLeft = false;
    boolean facingRight = false;


    boolean leftSide, rightSide, upSide, downSide;
    int levelWidth = Game.getLevel().getImageWidth() * 50;
    int levelHeight = Game.getLevel().getImageHeight() * 50;
    int windowWidth = Main.getWindowWidth();
    int windowHeight = Main.getWindowHeight();


    int framecounter = 0;
    int runningspeed = 12;

    // Our constructor again calling up to the superclass
    public MainCharacter(Game newGame, String SVGdata, double xLoc, double yLoc, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        game = newGame;
    }

    @Override
    public void update() {
        setXYLocation();
        setImageState();
        moveCharacter();
        checkCollision();
    }

    private void setXYLocation() {
        if (game.isRight())
            iX += velX;
        if (game.isLeft())
            iX -= velX;
        if (game.isUp())
            iY -= velY;
        if (game.isDown())
            iY += velY;
    }

    private void setImageState() {
        // Idle (not moving) (need to be expanded with facing direction)
        if (!game.isDown() && !game.isUp() && !game.isLeft() && !game.isRight()){
            if(facingDown) {
                spriteFrame.setScaleX(1);
                spriteFrame.setImage(imageStates.get(5));
            }
            else if(facingUp){
                spriteFrame.setScaleX(1);
                spriteFrame.setImage(imageStates.get(2));
            }
            else if (facingRight) {
                spriteFrame.setScaleX(-1);
                spriteFrame.setImage(imageStates.get(8));
            } else if (facingLeft){
                spriteFrame.setScaleX(1);
                spriteFrame.setImage(imageStates.get(8));
            }
            animation = false;
            framecounter = 0;
        }

        // Moving left
        if (game.isLeft()) {
            spriteFrame.setScaleX(1);
            this.setFlipH(false);
            if (!animation && (!game.isDown() && !game.isUp())) {
                spriteFrame.setImage(imageStates.get(11));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else if(animation) {
                spriteFrame.setImage(imageStates.get(9));
                if (framecounter >= runningspeed) {
                    animation = false;
                    framecounter = 0;
                } else {framecounter += 1;}
            }
            facingLeft = true;
            facingDown = facingRight = facingUp = false;
        }

        // Moving right
        if (game.isRight()) {
            spriteFrame.setScaleX(-1);
            this.setFlipH(true);
            if (!animation && (!game.isDown() && !game.isUp())) {
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
            facingRight = true;
            facingDown = facingLeft = facingUp = false;
        }

        // Moving down
        if (game.isDown()) {
            if (!animation && (!game.isLeft() && !game.isRight())) {
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
            facingDown = true;
            facingUp = facingRight = facingLeft = false;
        }

        // Moving up
        if (game.isUp()) {
            if (!animation && (!game.isLeft() && !game.isRight())) {
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
            facingUp = true;
            facingDown = facingRight = facingLeft = false;
        }

    }

    private void moveCharacter() {

        // Checks if the player character is on a side of the map;
        // If so, then instead of moving the tiled background, move the player character.
        if (iX < -levelWidth/2  + windowWidth/2)    leftSide = true;    else leftSide = false;
        if (iX > levelWidth/2   - windowWidth/2)    rightSide = true;   else rightSide = false;
        if (iY < -levelHeight/2 + windowHeight/2)   upSide = true;      else upSide = false;
        if (iY > levelHeight/2  - windowHeight/2)   downSide = true;    else downSide = false;

        if (leftSide) spriteFrame.setTranslateX(iX + (levelWidth/2 - windowWidth/2));
        else if (rightSide) spriteFrame.setTranslateX(iX - (levelWidth/2 - windowWidth/2));
        else Game.getBackground().setTranslateX(-iX);

        if (upSide) spriteFrame.setTranslateY(iY + (levelHeight/2 - windowHeight/2));
        else if (downSide) spriteFrame.setTranslateY(iY - (levelHeight/2 - windowHeight/2));
        else Game.getBackground().setTranslateY(-iY);

    }

    private void checkCollision() {
        // When we check for collision we cycle through the list of all of our actors
        for (int i = 0; i < Game.director.getCurrentActors().size(); i++) {
            // And as we do that we set them up in a temporary object
            Actor object = Game.director.getCurrentActors().get(i);
            // This object is then being parsed and tested in our collide method
            if (collide(object)) {
                // If collision has been detected, this code runs, in it's current state, it plays a sound, adds the
                // object to another list, removes the sprite graphically and then removes it from existence by
                // resetting the list of removed actors. Finally we call the scoringEngine() method on our object.
                System.out.println("Collision with tile at " + object.spriteFrame.getTranslateX() + " " + object.spriteFrame.getTranslateY() + " and " + iX + " " + iY);
//                game.director.addToRemovedActors(object);
//                game.root.getChildren().remove(object.getSpriteFrame());
//                game.director.resetRemovedActors();
            }
        }
    }

    // The player characters collision is set to true. This does not actually do anything at the moment other than being
    // a placeholder stating that the player character can collide.
    public boolean collide(Actor object){
        // We check for collision in a two stage process, this is to save resources, as checking for collision can be
        // quite costly. The way we do this is by first using an if statement to check if two imageView nodes intersect
        // with each other, if they do we create a new Shape object from the two intersecting ImageView nodes, the width
        // of which we measure. If this width is not negative 1, we return true, else we return false.
        if (object.getSpriteFrame().getBoundsInParent().intersects(
                iX+levelWidth/2, iY+levelHeight/2, 50, 50)) {
            Shape intersection = SVGPath.intersect(Game.playerOne.getSpriteBoundary(), object.getSpriteBoundary());
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                return true;
            }
        }
        return false;
    }
}
