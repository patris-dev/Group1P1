package lazytown.source.game.actor;

import lazytown.source.game.Game;
import javafx.scene.image.Image;


public class MainCharacter extends MovedActor {
    protected Game game;

    boolean animation = false;
    boolean facingUp = false;
    boolean facingDown = true;
    boolean facingLeft = false;
    boolean facingRight = false;

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
        Game.getBackground().setTranslateX(-iX);
        Game.getBackground().setTranslateY(-iY);
    }

    private void checkCollision() {

    }

    // The player characters collision is set to true. This does not actually do anything at the moment other than being
    // a placeholder stating that the player character can collide.
    public boolean collide(Actor object){return true;}
}
