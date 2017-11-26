package lazytown.source.game.level;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class is used for rendering levels from tiles.
 */
public class Level {

    // Path of the folder which holds all image assets.
    private String path = "/lazytown/assets/images/";
    // Full path of the file (path + fileName).
    private String fullPath;
    // Our image file.
    private BufferedImage image;
    // Array of rendered tiles.
    private ImageView[][] tiles;

    // Level constructor, takes in the name of our map file.
    public Level(String fileName) {
        fullPath = path + fileName;
    }

    // Renders the map made from tiles to the user's screen.
    public void renderMap(Group background) {
        try {
            // Reads an image as data.
            image = ImageIO.read(getClass().getResource(fullPath));

            Image brick = new Image("/lazytown/assets/images/bricksorwhatever.png");
            Image stone = new Image("/lazytown/assets/images/stone.png");
            Image sand = new Image("/lazytown/assets/images/sand.png");

            // Instantiates the array of tiles.
            tiles = new ImageView[image.getWidth()][image.getHeight()];

            // Checks each pixel for it's RGB data, render the map from tiles based on that data.
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Scans the color of a single pixel, returns a String.
                    String tile = this.scanPixel(x, y);
                    // Sets the current tile based on RGB data.
                    switch (tile) {
                        case "B":
                            tiles[x][y] = new ImageView(brick);
                            break;
                        case "W":
                            tiles[x][y] = new ImageView(stone);
                            break;
                        case "r": case "g": case "b":
                            tiles[x][y] = new ImageView(sand);
                            break;
                    }

                    // Sets the coordinates of current tile.
                    tiles[x][y].setTranslateX(x * 50);
                    tiles[x][y].setTranslateY(y * 50);

                    // Adds the current tile to our StackPane root.
                    background.getChildren().add(tiles[x][y]);

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

        // Extracts the Red, Green and Blue values from the Color object, adds them all to a single string.
        // Format: rrrgggbbb; each value has a min of 000 and max of 255.
        String rgb = String.format("%03d%03d%03d", color.getRed(), color.getGreen(), color.getBlue());

        // Checks the rgb code - returns a string based on it.
        switch (rgb) {
            case "000000000": return "B";
            case "255255255": return "W";
            case "255000000": return "r";
            case "000255000": return "g";
            case "000000255": return "b";


            default: return "X";
        }
    }
}