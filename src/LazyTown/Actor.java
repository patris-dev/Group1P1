package LazyTown;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Actor {
    // The first object we declare is a new array list of images, this is for animation
    protected List<Image> imageStates = new ArrayList<>();
    // Then we make an ImageView object to view the images from
    protected ImageView spriteFrame;
    // Then we create and SVGPath object, which defines our bounds around the sprite, this is done for collision
    protected SVGPath spriteBoundary;
    // Variables for x and y coordinates in our scene
    protected double iX, iY;
    // Four booleans to keep track of if an object is alive, is fixed, or is flipped either vertically or horizontally.
    protected boolean isAlive;
    protected boolean isFixed;
    protected boolean isFlipV;
    protected boolean isFlipH;

    // The constructor that ensures that these variables are all set appropriately.
    public Actor(String SVGdata, double xLoc, double yLoc, Image... spriteCels){
        // Set up our boundaries around the sprite, this is done through a string command !!! More Research is Needed !!!
        spriteBoundary = new SVGPath();
        spriteBoundary.setContent(SVGdata);
        // Sets up a spriteFrame so we can cycle through the different images that make up our animated sprites.
        spriteFrame = new ImageView(spriteCels[0]);
        imageStates.addAll(Arrays.asList(spriteCels));
        // The coordinates for the x and y direction of our actors
        iX = xLoc;
        iY = yLoc;
        isAlive = false;
        isFixed = true;
        // Boolean values to check if our actor needs to be flipped vertically or horizontally
        isFlipV = false;
        isFlipH = false;
    }

    // We declare here that every actor object in our game, no matter what kind, needs to have an update method,
    // this one is essential for our game, since it tells the gameplay loop that logic has to be executed with every
    // pulse in our game. We enforce the implementation of this method in any subclasses by also making it abstract.
    public abstract void update();


    //Finally in our class, we need a series of setter and getter methods, so that we can access the variables.
    public List<Image> getImageStates() {
        return imageStates;
    }

    public void setImageStates(List<Image> imageStates) {
        this.imageStates = imageStates;
    }

    public ImageView getSpriteFrame() {
        return spriteFrame;
    }

    public void setSpriteFrame(ImageView spriteFrame) {
        this.spriteFrame = spriteFrame;
    }

    public SVGPath getSpriteBoundary() {
        return spriteBoundary;
    }

    public void setSpriteBoundary(SVGPath spriteBoundary) {
        this.spriteBoundary = spriteBoundary;
    }

    public double getiX() {
        return iX;
    }

    public void setiX(double iX) {
        this.iX = iX;
    }

    public double getiY() {
        return iY;
    }

    public void setiY(double iY) {
        this.iY = iY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public boolean isFlipV() {
        return isFlipV;
    }

    public void setFlipV(boolean flipV) {
        isFlipV = flipV;
    }

    public boolean isFlipH() {
        return isFlipH;
    }

    public void setFlipH(boolean flipH) {
        isFlipH = flipH;
    }
}
