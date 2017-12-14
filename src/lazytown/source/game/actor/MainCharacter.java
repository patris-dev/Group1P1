package lazytown.source.game.actor;

import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import lazytown.source.Main;
import lazytown.source.game.Game;
import javafx.scene.image.Image;
import lazytown.source.game.UI;
import lazytown.source.game.level.Tile;

/**
 * A class used for the main character, extends MovedActor class, and therefore the Actor as well.
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


    int framecounter = 0;
    int runningspeed = 12;

    /**
     * Our constructor again calling up to the superclass.
     * @param newGame a Game object used for keyboard interaction, collision, etc.
     * @param SVGdata a string which determines vector data for the collision area.
     * @param xLoc x coordinate of the rendered MovedActor.
     * @param yLoc y coordinate of the rendered MovedActor.
     * @param spriteCels an array of images for animating the MovedActor.
     */
    public MainCharacter(Game newGame, String SVGdata, double xLoc, double yLoc, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        game = newGame;
    }

    /**
     * The necessary update method, which in this class is finally implemented and used.
     * It is used for translating the character around the level, animation, collision, and adjusting the various stats
     * as the game progresses.
     */
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
        // Idle (not moving)
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
        else if (game.isLeft() || ((game.isLeft()) && game.isDown()) || ((game.isLeft()) && game.isUp())) {
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
            facingLeft = true;
            facingDown = facingRight = facingUp = false;
        }
        // Moving right
        else if (game.isRight() || ((game.isRight()) && game.isDown()) || ((game.isRight()) && game.isUp())) {
            spriteFrame.setScaleX(-1);
            this.setFlipH(true);
            if (!animation) {
                spriteFrame.setImage(imageStates.get(9));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else {
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
        else if (game.isDown()) {
            if (!animation) {
                spriteFrame.setImage(imageStates.get(6));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else {
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
        else if (game.isUp()) {
            if (!animation) {
                spriteFrame.setImage(imageStates.get(0));
                if (framecounter >= runningspeed) {
                    animation = true;
                    framecounter = 0;
                } else {framecounter +=1;}
            } else {
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

        if(framecounter >= runningspeed)
            Game.getFootsteps().play("footsteps.mp3");

    }

    private void checkCollision() {
        // When we check for collision we cycle through the list of all of our actors
        for (int i = 0; i < Game.director.getCurrentActors().size(); i++) {
            // And as we do that we set them up in a temporary object
            Actor object = Game.director.getCurrentActors().get(i);
            // This object is then being parsed and tested in our collide method
            if (collide(object) && !isDead) {
                if (object instanceof Item) {
                    // If the player has acquired a backpack, he can hold up to 5 of both pizzas and beers.
                    if(UI.isBackpack()){
                        if (!(((Item) object).getId().equals("pizza") && UI.getPizza() == 5) &&
                                !(((Item) object).getId().equals("can") && UI.getBeer() == 5)) {
                            // Bumps up the counter, symbolizing that the player picked up the item.
                            UI.bumpItem(((Item) object).getId());
                            // Removes the item from the director and the background.
                            Game.level.setPickedUp((int)object.getiX()/50, (int)object.getiY()/50);
                            Game.director.addToRemovedActors(object);
                            Game.getBackground().getChildren().remove(object.getSpriteFrame());
                            Game.director.resetRemovedActors();
                        }
                    }
                    // If the player has not yet acquired a backpack, he can hold up to 2 of both pizzas and beers.
                    else if(!UI.isBackpack()){
                        if (!(((Item) object).getId().equals("pizza") && UI.getPizza() == 2) &&
                                !(((Item) object).getId().equals("can") && UI.getBeer() == 2)) {
                            // Bumps up the counter, symbolizing that the player picked up the item.
                            UI.bumpItem(((Item) object).getId());
                            // Removes the item from the director and the background.
                            Game.level.setPickedUp((int)object.getiX()/50, (int)object.getiY()/50);
                            Game.director.addToRemovedActors(object);
                            Game.getBackground().getChildren().remove(object.getSpriteFrame());
                            Game.director.resetRemovedActors();
                        }
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
                                else {
                                    int levelNumber = Game.level.getLevelNumber();
                                    if (levelNumber == 1) Game.changeLevel(2, 175, 3300);
                                    else if (levelNumber == 2) Game.changeLevel(1, 2925, 2100);
                                }
                                break;
                            case "key2":
                                if (!UI.getKeycard(2)) UI.loadTextWindow("This door requires a keycard with ID 2 to unlock.");
                                else {
                                    int levelNumber = Game.level.getLevelNumber();
                                    if (levelNumber == 2) Game.changeLevel(3, 675, 500);
                                    else if (levelNumber == 3) Game.changeLevel(2, 725, 300);
                                }
                                break;
                            case "key3":
                                if (!UI.getKeycard(3)) UI.loadTextWindow("This door requires a keycard with ID 3 to unlock.");
                                else {
                                    int levelNumber = Game.level.getLevelNumber();
                                    if (levelNumber == 3) Game.changeLevel(4, 75, 1600);
                                    else if (levelNumber == 4) Game.changeLevel(3, 1275, 1600);
                                }
                                break;
                            case "key4":
                                if (!UI.getKeycard(4)) UI.loadTextWindow("This door requires a keycard with ID 4 to unlock.");
                                else {
                                    UI.bumpItem("key5");
                                    UI.loadTextWindow("You activated your keycard. Time to escape!");
                                }
                                break;
                            case "key5":
                                if (!UI.getKeycard(5)) UI.loadTextWindow("You need to activate your card PIN code to unlock this door.\nAs far as you remember, you can do that somewhere in building A...");
                                else UI.loadTextWindow("You escaped with all your stuff, congrats!");
                                break;
                            case "water":
                                UI.loadTextWindow("You drank some water.");
                                UI.drinkWater();
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

    /**
     * Moves the player to specific coordinates.
     * To spawn the player in the center of a level, X and Y values would be level.getImageWidth()*50/2
     * and level.getImageWidth()*50/2.
     * @param X x coordinate of spawned character, 0 being the very left edge.
     * @param Y y coordinate of spawned character, 0 being the very top.
     */
    public void setSpawnLocation(int X, int Y) {
        iX = X-levelWidth/2;
        iY = Y-levelHeight/2;

        if (iX < -levelWidth/2  + windowWidth/2)  Game.getBackground().setTranslateX(levelWidth/2   - windowWidth/2);
        if (iX > levelWidth/2   - windowWidth/2)  Game.getBackground().setTranslateX(windowWidth/2  - levelWidth/2);
        if (iY < -levelHeight/2 + windowHeight/2) Game.getBackground().setTranslateY(levelHeight/2  - windowHeight/2);
        if (iY > levelHeight/2  - windowHeight/2) Game.getBackground().setTranslateY(windowHeight/2 - levelHeight/2);
    }

    /**
     * Updates level size based on currently set level, used for switching levels.
     */
    public void updateLevelSize() {
        levelWidth = Game.getLevel().getImageWidth() * 50;
        levelHeight = Game.getLevel().getImageHeight() * 50;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
