package lazytown.source.game.actor;

import javafx.scene.image.Image;
import lazytown.source.Main;
import lazytown.source.game.Game;

/**
 * An abstract class extending Actor used for moving entities.
 */
public abstract class MovedActor extends Actor {

    // First we add some variables that set our moved actor class apart from the other actors, which are static in
    // nature
    // For now we have only a small amount of variables, this probably will get expanded however as the development
    // progresses. These variables include velocity in the x and y direction, the actors health and the damage output
    // when it shoots, our player character wont be able to shoot, but there will also be enemy guards that will be able
    // to damage the player.
    protected double velX, velY, health, damage;
    // We also have variables for controlling the bounds and if there is friction on movement, this friction could be
    // used to adjust player speed when in water or similar. It is not an essential variable, but it seems like a fun
    // gimmick.
    protected float boundScale, boundRotation, friction;

    int levelWidth = Game.getLevel().getImageWidth() * 50;
    int levelHeight = Game.getLevel().getImageHeight() * 50;
    int windowWidth = Main.getWindowWidth();
    int windowHeight = Main.getWindowHeight();

    /**
     * Our constructor, which calls the Actor constructor with the super keyword. We also set some default values for
     * some of our variables. Health is set to 100 and movement speed to 4.
     * @param SVGdata a string which determines vector data for the collision area.
     * @param xLoc x coordinate of the rendered MovedActor.
     * @param yLoc y coordinate of the rendered MovedActor.
     * @param spriteCels an array of images for animating the MovedActor.
     */
    public MovedActor(String SVGdata, double xLoc, double yLoc, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        health = 100;
        velX = velY = 4;
    }

    /**
     * MovedActor is a subclass of Actor, therefore we need to implement the update() method, we don't use it in this
     * class however.
     */
    @Override
    public abstract void update();

    // Finally we have our getters and setters.
    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double lifespan) {
        this.health = lifespan;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public float getBoundScale() {
        return boundScale;
    }

    public void setBoundScale(float boundScale) {
        this.boundScale = boundScale;
    }

    public float getBoundRotation() {
        return boundRotation;
    }

    public void setBoundRotation(float boundRotation) {
        this.boundRotation = boundRotation;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }
}
