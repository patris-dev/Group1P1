package lazytown.source.game.level;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lazytown.source.game.Game;
import lazytown.source.game.actor.Actor;
import lazytown.source.game.actor.Guard;
import lazytown.source.game.actor.Item;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class is used for rendering levels from tiles.
 * The method renderMap(Group background) renders a level from a map on the background.
 */
public class Level {

    // Path of the folder which holds all level map assets.
    private String path = "/lazytown/assets/images/levels/";
    // Full path of the file (path + fileName).
    private String fullPath;
    // Our image file.
    private BufferedImage image;
    // Array of Tile objects.
    private Tile[][] tiles;
    // Determines whether the tile should collide with the player.
    private boolean collides;
    // Array of actors for rendering items, guards, etc.
    private Actor[][] actors;

    // Guard sprite data.
    private static final int SPRITE_WIDTH = 75;
    private static final int SPRITE_HEIGHT = SPRITE_WIDTH;

    Image p0 = new Image("lazytown/assets/images/animationsprites/guard/P0.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p1 = new Image("lazytown/assets/images/animationsprites/guard/P1.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p2 = new Image("lazytown/assets/images/animationsprites/guard/P2.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p3 = new Image("lazytown/assets/images/animationsprites/guard/P3.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p4 = new Image("lazytown/assets/images/animationsprites/guard/P4.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p5 = new Image("lazytown/assets/images/animationsprites/guard/P5.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p6 = new Image("lazytown/assets/images/animationsprites/guard/P6.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p7 = new Image("lazytown/assets/images/animationsprites/guard/P7.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p8 = new Image("lazytown/assets/images/animationsprites/guard/P8.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p9 = new Image("lazytown/assets/images/animationsprites/guard/P9.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p10 = new Image("lazytown/assets/images/animationsprites/guard/P10.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);
    Image p11 = new Image("lazytown/assets/images/animationsprites/guard/P11.png", SPRITE_WIDTH, SPRITE_HEIGHT, true,
            false, true);

    // Level constructor, takes in the name of our map file.
    public Level(String fileName) {
        fullPath = path + fileName;
    }

    // Renders the map made from tiles to the user's screen.
    public void renderMap(Group background) {
        try {
            // Reads an image as data.
            image = ImageIO.read(getClass().getResource(fullPath));

            Image brick = new Image("/lazytown/assets/images/tiles/bricksorwhatever.png");
            Image stone = new Image("/lazytown/assets/images/tiles/stone.png");
            Image sand = new Image("/lazytown/assets/images/tiles/sand.png");

            ImageView imageView = null;

            // Instantiates the arrays of tiles and actors.
            tiles = new Tile[image.getWidth()][image.getHeight()];
            actors = new Actor[image.getWidth()][image.getHeight()];

            // Checks each pixel for it's RGB data, render the map from tiles based on that data.
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Scans the color of a single pixel, returns a String.
                    String render = this.scanPixel(x, y);

                    // The returned string consists of two chars - one for tiles, one for entities.
                    char tile = render.charAt(0);
                    char entity = render.charAt(1);

                    // Sets the current tile based on RG data.
                    switch (tile) {
                        case 'A':
                            imageView = new ImageView(brick);
                            collides = true;
                            break;
                        case 'B':
                            imageView = new ImageView(stone);
                            collides = false;
                            break;
                        case 'C':
                            imageView = new ImageView(sand);
                            collides = false;
                            break;
                    }

                    tiles[x][y] = new Tile(collides);
                    tiles[x][y].setImage(imageView);

                    // Sets the coordinates of current tile.
                    tiles[x][y].getImage().setTranslateX(x * 50);
                    tiles[x][y].getImage().setTranslateY(y * 50);

                    // Adds the current tile to our StackPane root.
                    background.getChildren().add(tiles[x][y].getImage());

                    // Creates an array of actors (items, guards, etc.)
                    // Renders them externally later on (in Game.java renderActors() method) so it goes on top of tiles.
                    switch (entity) {
                        case 'A':
                            actors[x][y] = new Item("", x*50, y*50, "050", new Image("/lazytown/assets/images/UI/pizza_slice.png"));
                            break;
                        case 'B':
                            actors[x][y] = new Item("", x*50, y*50, "100", new Image("/lazytown/assets/images/UI/can_of_soda.png"));
                            break;
                        case 'C':
                            actors[x][y] = new Guard("", x*50, y*50, p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11);
                            break;
                        default: actors[x][y] = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Checks the data of a pixel at [x y] - returns a String based on it.
    private String scanPixel(int x, int y) {
        // Gets the color data of a pixel, creates a Color object for it.
        int c = image.getRGB(x, y);
        Color color = new Color(c);

        // Extracts the Red, Green and Blue values from the Color object, adds them all to two strings.
        // Format: rrrggg and bbb; each value has a min of 000 and max of 255.
        String rg = String.format("%03d%03d", color.getRed(), color.getGreen());
        String b = String.format("%03d", color.getBlue());

        String spawnString = "";

        // Checks the rg and b values, adds chars accordingly to our return string.
        // "0" stands for empty data.
        switch (rg) {
            case "000000": spawnString += "A"; break;
            case "000100": spawnString += "B"; break;
            case "000200": spawnString += "C"; break;
            case "000255": spawnString += "D"; break;
            case "100000": spawnString += "E"; break;
            case "100100": spawnString += "F"; break;
            case "100200": spawnString += "G"; break;
            case "100255": spawnString += "H"; break;
            case "200000": spawnString += "I"; break;
            case "200100": spawnString += "J"; break;
            case "200200": spawnString += "K"; break;
            case "200255": spawnString += "L"; break;
            case "255000": spawnString += "M"; break;
            case "255100": spawnString += "N"; break;
            case "255200": spawnString += "O"; break;
            case "255255": spawnString += "P"; break;
            default: spawnString += "0";
        }
        switch (b) {
            case "000": spawnString += "0"; break;
            case "050": spawnString += "A"; break;
            case "100": spawnString += "B"; break;
            case "150": spawnString += "C"; break;
            case "200": spawnString += "D"; break;
            case "250": spawnString += "E"; break;
            default: spawnString += "0";
        }

        return spawnString;

    }

    public int getImageWidth() {
        return image.getWidth();
    }

    public int getImageHeight() {
        return image.getHeight();
    }

    public Actor[][] getActors() {
        return actors;
    }
}