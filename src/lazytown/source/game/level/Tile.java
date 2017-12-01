package lazytown.source.game.level;


import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

/**
 * This class is used for tiles which are displayed in the background of a level.
 * It includes an ImageView for the tile which is being rendered, an SVGPath object for collision and a boolean for
 * determining whether collision with the player should occur.
 */
public class Tile {

    private ImageView image;
    private boolean collides;
    private static SVGPath tileBoundary = new SVGPath();
    private static String boundaryShape = "M0 0 H 50 V 50 H 0 Z";

    public Tile(boolean collides) {
        tileBoundary.setContent(boundaryShape);
        this.collides = collides;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public static SVGPath getTileBoundary() {
        return tileBoundary;
    }
}
