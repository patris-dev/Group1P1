package lazytown.source.game.level;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lazytown.assets.AssetManager;
import lazytown.source.game.Game;
import lazytown.source.game.actor.Actor;
import lazytown.source.game.actor.Door;
import lazytown.source.game.actor.Guard;
import lazytown.source.game.actor.Item;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import static lazytown.source.game.Game.director;

/**
 * This class is used for rendering levels from tiles.
 * The method renderMap(Group background) renders a level from a map on the background.
 */
public class Level {

    // Path of the folder which holds all level map assets.
    private int levelNumber;
    // Our image file.
    private BufferedImage image;
    // Array of Tile objects.
    private Tile[][] tiles;
    // Determines whether the tile should collide with the player.
    private boolean collides;
    // Array of actors for rendering items, guards, etc.
    private Actor[][] actors;

    // Level constructor, takes in the name of our map file.
    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    // Renders the map made from tiles to the user's screen.
    public void renderMap(Group background) {
        try {
            // Reads an image as data.
            image = ImageIO.read(getClass().getResource(AssetManager.getMap(levelNumber)));

            // Tile images.
            Image voidTile = AssetManager.getTile("void.png");
            Image nullTile = AssetManager.getTile("null.png");
            Image wall = AssetManager.getTile("wall.png");
            Image grayFloor = AssetManager.getTile("gray_floor.png");
            Image grayCarpet = AssetManager.getTile("gray_carpet.png");
            Image redCarpet  = AssetManager.getTile("red_carpet.png");

            // Item images.
            Image pizza = AssetManager.getItem("pizza_slice.png");
            Image canOfSoda = AssetManager.getItem("can_of_soda.png");

            // Furniture images.
            Image tableTop = AssetManager.getFurniture("table_top.png");
            Image tableBottom = AssetManager.getFurniture("table_bottom.png");
            Image glassDoor = AssetManager.getFurniture("glass_door.png");
            Image whiteDoor = AssetManager.getFurniture("white_door.png");
//            Image chairUp = AssetManager.getFurniture("chair_up.png");
//            Image chairRight = AssetManager.getFurniture("chair_right.png");
//            Image chairDown = AssetManager.getFurniture("chair_down.png");
//            Image chairLeft = AssetManager.getFurniture("chair_left.png");

            // Array of guard sprites.
            Image[] gSprites = AssetManager.getGuardSprites();

            // Image object to render a selected tile.
            Image tileImage = null;

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
                            tileImage = voidTile;
                            collides = false;
                            break;
                        case 'B':
                            tileImage = wall;
                            collides = true;
                            break;
                        case 'I':
                            tileImage = grayFloor;
                            collides = false;
                            break;
                        case 'V':
                            tileImage = grayCarpet;
                            collides = false;
                            break;
                        case 'j':
                            tileImage = redCarpet;
                            collides = false;
                            break;
                        default:
                            tileImage = nullTile;
                            collides = false;
                            break;
                    }

                    tiles[x][y] = new Tile(collides, x, y, tileImage);

                    // Sets the coordinates of current tile.
                    tiles[x][y].spriteFrame.setTranslateX(x * 50);
                    tiles[x][y].spriteFrame.setTranslateY(y * 50);

                    // Adds the current tile to our StackPane root.
                    background.getChildren().add(tiles[x][y].spriteFrame);

                    // If collides is true, then add the current tile to our director for collision
                    if (collides) {
                        director.addCurrentActors(tiles[x][y]);
                    }

                    // Creates an array of actors (items, guards, etc.)
                    // Renders them externally later on (in Game.java renderActors() method) so it goes on top of tiles.
                    switch (entity) {
                        case 'E':
                            actors[x][y] = new Item("M0,0 L 50,0 50,50 0,50 Z", x*50, y*50, "pizza", pizza);
                            break;
                        case 'F':
                            actors[x][y] = new Tile(true, x, y, tableTop);
                            break;
                        case 'G':
                            actors[x][y] = new Tile(true, x, y, tableBottom);
                            break;
                        case 'J':
                            actors[x][y] = new Item("M0,0 L 50,0 50,50 0,50 Z", x*50, y*50, "can", canOfSoda);
                            break;
//                        case 'K':
//                            actors[x][y] = new Tile(true, x, y, chairUp);
//                            break;
//                        case 'L':
//                            actors[x][y] = new Tile(true, x, y, chairRight);
//                            break;
//                        case 'M':
//                            actors[x][y] = new Tile(true, x, y, chairDown);
//                            break;
//                        case 'N':
//                            actors[x][y] = new Tile(true, x, y, chairLeft);
//                            break;
                        case 'O':
                            actors[x][y] = new Guard("M0,0 L 50,0 50,50 0,50 Z", x*50, y*50, true, gSprites);
                            break;
                        case 'd':
                            actors[x][y] = new Door("M0,0 L 50,0 50,50 0,50 Z", x*50, y*50, "1", glassDoor);
                            break;
                        case 'e':
                            actors[x][y] = new Door("M0,0 L 50,0 50,50 0,50 Z", x*50, y*50, "1", rotate(glassDoor));
                            break;
                        case 'n':
                            actors[x][y] = new Guard("M0,0 L 50,0 50,50 0,50 Z", x*50, y*50, false, gSprites);
                            break;
                        case 'v':
                            actors[x][y] = new Door("M0,0 L 50,0 50,50 0,50 Z", x*50, y*50, "0", whiteDoor);
                            break;
                        case 'w':
                            actors[x][y] = new Door("M0,0 L 50,0 50,50 0,50 Z", x*50, y*50, "0", rotate(whiteDoor));
                            break;
                        default: actors[x][y] = null;
                    }
                    if (actors[x][y] != null) {
                        director.addCurrentActors(actors[x][y]);
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
            case "000050": spawnString += "B"; break;
            case "000100": spawnString += "C"; break;
            case "000150": spawnString += "D"; break;
            case "000200": spawnString += "E"; break;
            case "000250": spawnString += "F"; break;
            case "000255": spawnString += "G"; break;
            case "050000": spawnString += "H"; break;
            case "050050": spawnString += "I"; break;
            case "050100": spawnString += "J"; break;
            case "050150": spawnString += "K"; break;
            case "050200": spawnString += "L"; break;
            case "055250": spawnString += "M"; break;
            case "050255": spawnString += "N"; break;
            case "100000": spawnString += "O"; break;
            case "100050": spawnString += "P"; break;
            case "100100": spawnString += "Q"; break;
            case "100150": spawnString += "R"; break;
            case "100200": spawnString += "S"; break;
            case "100250": spawnString += "T"; break;
            case "100255": spawnString += "U"; break;
            case "150000": spawnString += "V"; break;
            case "150050": spawnString += "W"; break;
            case "150100": spawnString += "X"; break;
            case "150150": spawnString += "Y"; break;
            case "150200": spawnString += "Z"; break;
            case "150250": spawnString += "a"; break;
            case "150255": spawnString += "b"; break;
            case "200000": spawnString += "c"; break;
            case "200050": spawnString += "d"; break;
            case "200100": spawnString += "e"; break;
            case "200150": spawnString += "f"; break;
            case "200200": spawnString += "g"; break;
            case "200250": spawnString += "h"; break;
            case "200255": spawnString += "i"; break;
            case "250000": spawnString += "j"; break;
            case "250050": spawnString += "k"; break;
            case "250100": spawnString += "l"; break;
            case "250150": spawnString += "m"; break;
            case "250200": spawnString += "n"; break;
            case "250250": spawnString += "o"; break;
            case "250255": spawnString += "p"; break;
            case "255000": spawnString += "q"; break;
            case "255050": spawnString += "r"; break;
            case "255100": spawnString += "s"; break;
            case "255150": spawnString += "t"; break;
            case "255200": spawnString += "u"; break;
            case "255250": spawnString += "v"; break;
            case "255255": spawnString += "w"; break;
            default: spawnString += "0";
        }
        switch (b) {
            case "000": spawnString += "0"; break;
            case "005": spawnString += "A"; break;
            case "010": spawnString += "B"; break;
            case "015": spawnString += "C"; break;
            case "020": spawnString += "D"; break;
            case "025": spawnString += "E"; break;
            case "030": spawnString += "F"; break;
            case "035": spawnString += "G"; break;
            case "040": spawnString += "H"; break;
            case "045": spawnString += "I"; break;
            case "050": spawnString += "J"; break;
            case "055": spawnString += "K"; break;
            case "060": spawnString += "L"; break;
            case "065": spawnString += "M"; break;
            case "070": spawnString += "N"; break;
            case "075": spawnString += "O"; break;
            case "080": spawnString += "P"; break;
            case "085": spawnString += "Q"; break;
            case "090": spawnString += "R"; break;
            case "095": spawnString += "S"; break;
            case "100": spawnString += "T"; break;
            case "105": spawnString += "U"; break;
            case "110": spawnString += "V"; break;
            case "115": spawnString += "W"; break;
            case "120": spawnString += "X"; break;
            case "125": spawnString += "Y"; break;
            case "130": spawnString += "Z"; break;
            case "135": spawnString += "a"; break;
            case "140": spawnString += "b"; break;
            case "145": spawnString += "c"; break;
            case "150": spawnString += "d"; break;
            case "155": spawnString += "e"; break;
            case "160": spawnString += "f"; break;
            case "165": spawnString += "g"; break;
            case "170": spawnString += "h"; break;
            case "175": spawnString += "i"; break;
            case "180": spawnString += "j"; break;
            case "185": spawnString += "k"; break;
            case "190": spawnString += "l"; break;
            case "195": spawnString += "m"; break;
            case "200": spawnString += "n"; break;
            case "205": spawnString += "o"; break;
            case "210": spawnString += "p"; break;
            case "215": spawnString += "q"; break;
            case "220": spawnString += "r"; break;
            case "225": spawnString += "s"; break;
            case "230": spawnString += "t"; break;
            case "235": spawnString += "u"; break;
            case "240": spawnString += "v"; break;
            case "245": spawnString += "w"; break;
            case "250": spawnString += "x"; break;
            case "255": spawnString += "y"; break;
            default: spawnString += "0";
        }

        return spawnString;

    }

    private Image rotate(Image image) {
        ImageView rotated = new ImageView(image);
        rotated.setRotate(90);
        return rotated.getImage();
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

    public Tile[][] getTiles() {
        return tiles;
    }
}