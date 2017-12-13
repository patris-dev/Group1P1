package lazytown.source.game.actor;

import javafx.scene.image.Image;

/**
 * A class extending Actor used for items which are picked up upon collision.
 */
public class Item extends Actor {

    private String id;

    /**
     * Our constructor, which calls the Actor constructor with the super keyword.
     * @param SVGdata a string which determines vector data for the collision area.
     * @param xLoc x coordinate of the rendered Item.
     * @param yLoc y coordinate of the rendered Item.
     * @param id ID is used to differentiate Item objects from each other.
     * @param spriteCels an array of images for displaying the Item.
     */
    public Item(String SVGdata, double xLoc, double yLoc, String id, Image... spriteCels) {
        super(SVGdata, xLoc, yLoc, spriteCels);
        this.id = id;
        spriteFrame.setTranslateX(xLoc);
        spriteFrame.setTranslateY(yLoc);
    }

    /**
     * Item is a subclass of Actor, therefore we need to implement the update() method, we don't use it in this
     * class however.
     * Potentially, it could be used to animate certain items.
     */
    @Override
    public void update() { }

    public String getId() {
        return id;
    }
}
