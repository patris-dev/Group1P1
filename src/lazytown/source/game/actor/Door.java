package lazytown.source.game.actor;

import javafx.scene.image.Image;

/**
 * An abstract class extending Actor used for moving entities.
 */
public class Door extends Actor {

    private String id;



    // Our constructor, which calls the Actor constructor with the super keyword.
    // Id is used to differentiate items from each other.
    // (for example, based on the id, we can know which item counter in inventory to bump up when picking it up.)

    public Door(String SVGdata, double xLoc, double yLoc, String id, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        this.id = id;
        spriteFrame.setTranslateX(xLoc);
        spriteFrame.setTranslateY(yLoc);
    }

    @Override
    // Here we carry our update method over from the actor superclass
    public void update() {
        // update for items.
        // might likely be empty, except for animating certain items.
    }

    public String getId() {
        return id;
    }
}
