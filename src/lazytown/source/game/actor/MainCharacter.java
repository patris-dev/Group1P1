package lazytown.source.game.actor;

import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import lazytown.source.Main;
import lazytown.source.game.Game;
import javafx.scene.image.Image;
import lazytown.source.game.UI;
import lazytown.source.game.level.Tile;

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
    boolean isDead = false;


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
        UI.updateStats();
    }

    private void setXYLocation() {
        if (!isDead) {
            if (game.isRight())
                iX += velX;
            if (game.isLeft())
                iX -= velX;
            if (game.isUp())
                iY -= velY;
            if (game.isDown())
                iY += velY;
        }
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

        if (!leftSide && !rightSide && !upSide && !downSide) {
            spriteFrame.setTranslateX(0);
            spriteFrame.setTranslateY(0);
        }

        if (leftSide) spriteFrame.setTranslateX(iX + (levelWidth / 2 - windowWidth / 2));
        else if (rightSide) spriteFrame.setTranslateX(iX - (levelWidth / 2 - windowWidth / 2));
        else Game.getBackground().setTranslateX(-iX);

        if (upSide) spriteFrame.setTranslateY(iY + (levelHeight / 2 - windowHeight / 2));
        else if (downSide) spriteFrame.setTranslateY(iY - (levelHeight / 2 - windowHeight / 2));
        else Game.getBackground().setTranslateY(-iY);

    }

    private void checkCollision() {
        // When we check for collision we cycle through the list of all of our actors
        for (int i = 0; i < Game.director.getCurrentActors().size(); i++) {
            // And as we do that we set them up in a temporary object
            Actor object = Game.director.getCurrentActors().get(i);
            // This object is then being parsed and tested in our collide method
            if (collide(object) && !isDead) {
                // If collision has been detected, this code runs, in it's current state, it plays a sound, adds the
                // object to another list, removes the sprite graphically and then removes it from existence by
                // resetting the list of removed actors. Finally we call the scoringEngine() method on our object.
                if (object instanceof Item) {

                    if (!(((Item) object).getId().equals("pizza") && !UI.isBackpack() && UI.getPizza() == 2) &&
                            !(((Item) object).getId().equals("can") && !UI.isBackpack() && UI.getBeer() == 2)) {
                        // Bumps up the counter, symbolizing that the player picked up the item.
                        UI.bumpItem(((Item) object).getId());
                        // Removes the item from the director and the background.
                        Game.director.addToRemovedActors(object);
                        Game.getBackground().getChildren().remove(object.getSpriteFrame());
                        Game.director.resetRemovedActors();
                    }
                }
                if (object instanceof Tile || object instanceof InteractiveActor) {
                    if (game.isDown()) {
                        iY -= velY;
                    }
                    else if (game.isUp()) {
                        iY += velY;
                    }
                    if (game.isRight()) {
                        iX -= velX;
                    }
                    else if (game.isLeft()) {
                        iX += velX;
                    }
                }
                if (object instanceof Guard) {
                    UI.takeDamage(0.01);
                }
                if (object instanceof InteractiveActor) {
                    String id = ((InteractiveActor) object).getId();
                    if (game.isKeyE()) {
                        switch (id) {
                            case "key0":
                                UI.loadTextWindow("This door is locked.");
                                break;
                            case "key1":
                                if (!UI.getKeycard(1)) UI.loadTextWindow("This door requires a keycard with ID 1 to unlock.");
                                else UI.loadTextWindow("Door unlocked.");
                                break;
                            case "key2":
                                if (!UI.getKeycard(2)) UI.loadTextWindow("This door requires a keycard with ID 2 to unlock.");
                                else UI.loadTextWindow("Door unlocked.");
                                break;
                            case "key3":
                                if (!UI.getKeycard(3)) UI.loadTextWindow("This door requires a keycard with ID 3 to unlock.");
                                else UI.loadTextWindow("Door unlocked.");
                                break;
                            case "key4":
                                if (!UI.getKeycard(4)) UI.loadTextWindow("This door requires a keycard with ID 4 to unlock.");
                                else UI.loadTextWindow("Door unlocked.");
                                break;
                            case "key5":
                                if (!UI.getKeycard(5)) UI.loadTextWindow("This door requires a keycard with ID 5 to unlock.");
                                else UI.loadTextWindow("Door unlocked.");
                                break;
                            case "water":
                                UI.loadTextWindow("You drink some water.");
                                break;
                            case "locker":
                                UI.loadTextWindow("This locker is empty.");
                                break;
                            default:
                                UI.loadTextWindow("Unidentified interactive object.");
                                break;
                        }
                        game.setKeyE(false);
                    }
                }
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
                iX+levelWidth/2-20, iY+levelHeight/2-37.5, 40, 75)) {
            Shape intersection = SVGPath.intersect(Game.playerOne.getSpriteBoundary(), object.getSpriteBoundary());
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                return true;
            }
        }
        return false;
    }

    // Moves the player to specific coordinates.
    // 0, 0 point is the top left corner of the level.
    // To spawn the player in the center of a level, X and Y values would be level.getImageWidth()*50/2
    // and level.getImageWidth()*50/2.
    public void setSpawnLocation(int X, int Y) {
        iX = X-levelWidth/2;
        iY = Y-levelHeight/2;

        if (iX < -levelWidth/2  + windowWidth/2)  Game.getBackground().setTranslateX(levelWidth/2   - windowWidth/2);
        if (iX > levelWidth/2   - windowWidth/2)  Game.getBackground().setTranslateX(windowWidth/2  - levelWidth/2);
        if (iY < -levelHeight/2 + windowHeight/2) Game.getBackground().setTranslateY(levelHeight/2  - windowHeight/2);
        if (iY > levelHeight/2  - windowHeight/2) Game.getBackground().setTranslateY(windowHeight/2 - levelHeight/2);

    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
