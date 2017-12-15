package lazytown.source.game.actor;

import javafx.scene.image.Image;

/**
 * A class extending Actor used for interactive objects, such as doors, water taps, etc.
 */
public class InteractiveActor extends Actor {

    private String id;

    /**
     * Our constructor, which calls the Actor constructor with the super keyword.
     * @param SVGdata a string which determines vector data for the collision area.
     * @param xLoc x coordinate of the rendered InteractiveActor.
     * @param yLoc y coordinate of the rendered InteractiveActor.
     * @param id ID is used to differentiate InteractiveActor objects from each other.
     * @param spriteCels an array of images for displaying the InteractiveActor.
     */
    public InteractiveActor(String SVGdata, double xLoc, double yLoc, String id, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        this.id = id;
        spriteFrame.setTranslateX(xLoc);
        spriteFrame.setTranslateY(yLoc);
    }

    /**
     * InteractiveActor is a subclass of Actor, therefore we need to implement the update() method, we don't use it in this
     * class however.
     * Potentially, it could be used to animate certain objects.
     */
    @Override
    public void update() { }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }
}
