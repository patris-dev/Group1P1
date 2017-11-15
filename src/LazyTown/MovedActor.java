package LazyTown;

import javafx.scene.image.Image;

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

    // Our constructor, which calls the Actor constructor with the super keyword. We also set some default values for
    // some of our variables. Health to 100 and movement speed to 2
    public MovedActor(String SVGdata, double xLoc, double yLoc, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        health = 100;
        velX = velY = 3;
    }

    @Override
    // Here we carry our update method over from the actor superclass
    public abstract void update();

    // We add a new method here for our collision, it is defaulted to false, as this class can also be used for particle
    // effects, which we don't want to collide with anything else in the world.
    public boolean collide(Actor object) {return false;}

    // Finally we have our getters and setters
    public double getVelX() {
        return velX;
    } public void setVelX(double velX) {
        this.velX = velX;
    } public double getVelY() {
        return velY;
    } public void setVelY(double velY) {
        this.velY = velY;
    } public double getHealth() {
        return health;
    } public void setHealth(double lifespan) {
        this.health = lifespan;
    } public double getDamage() {
        return damage;
    } public void setDamage(double damage) {
        this.damage = damage;
    } public float getBoundScale() {
        return boundScale;
    } public void setBoundScale(float boundScale) {
        this.boundScale = boundScale;
    } public float getBoundRotation() {
        return boundRotation;
    } public void setBoundRotation(float boundRotation) {
        this.boundRotation = boundRotation;
    } public float getFriction() {
        return friction;
    } public void setFriction(float friction) {
        this.friction = friction;
    }
}
