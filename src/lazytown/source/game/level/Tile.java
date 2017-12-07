package lazytown.source.game.level;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;
import lazytown.assets.AssetManager;
import lazytown.source.game.actor.Actor;

/**
 * This class is used for tiles which are displayed in the background of a level.
 * It includes an ImageView for the tile which is being rendered, an SVGPath object for collision and a boolean for
 * determining whether collision with the player should occur.
 */
public class Tile extends Actor{

    private boolean collides;
    private static SVGPath tileBoundary = new SVGPath();
    private String boundaryShape = "M0 0 H 50 V 50 H 0 Z";

    public Tile(boolean collides, int x, int y, Image image) {
        super("M0 0 H 50 V 50 H 0 Z", x*50, y*50, image);
        tileBoundary.setContent(boundaryShape);
        this.collides = collides;
    }

    public static SVGPath getTileBoundary() {
        return tileBoundary;
    }

    @Override
    public void update() {

    }
}
