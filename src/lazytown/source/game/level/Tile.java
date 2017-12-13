package lazytown.source.game.level;


import javafx.scene.image.Image;
import lazytown.source.game.actor.Actor;

/**
 * This class is used for tiles which are displayed in the background of a level.
 * It includes an ImageView for the tile which is being rendered, an SVGPath object for collision and a boolean for
 * determining whether collision with the player should occur.
 */
public class Tile extends Actor{

    private boolean collides;

    /**
     * Our constructor for the tile objects that our levels are generated with.
     * @param collides boolean for determining whether a tile should collide with characters.
     * @param x x coordinate of Tile.
     * @param y y coordinate of Tile
     * @param image image used for rendering this Tile.
     */
    public Tile(boolean collides, int x, int y, Image image) {
        super("M0 0 H 50 V 50 H 0 Z", x*50, y*50, image);
        this.collides = collides;
    }

    /**
     * Since our Tile class inherits from the overarching Actor superclass, we need to have an update method, we don't
     * use it for anything though.
     * Potentially, it could be used to animate certain tiles.
     */
    @Override
    public void update() { }

    public boolean isCollides() {
        return collides;
    }
}
