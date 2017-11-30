package lazytown.source.game.level;


import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

public class Tile {

    private ImageView image;
    private static SVGPath tileBoundary = new SVGPath();
    private static String boundaryShape = "M0 0 H 50 V 50 H 0 Z";

    public Tile() {
        tileBoundary.setContent(boundaryShape);
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
