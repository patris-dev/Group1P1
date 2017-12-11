package lazytown.assets;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;

/**
 * This class is used to get all of our assets.
 * It includes all file paths that are used for assets.
 * All methods are static.
 */
public class AssetManager {

    private static String imagesPath = "/lazytown/assets/images/";
    private static String animationSpritesPath = imagesPath + "animationsprites/";

    private static String soundPath = "src/lazytown/assets/sounds/";

    private static String uiAssetsPath = "/lazytown/assets/uiassets/";

    private static final int SPRITE_WIDTH = 75;
    private static final int SPRITE_HEIGHT = SPRITE_WIDTH;

    public static Image[] getPlayerSprites() {
        Image[] playerSprites = new Image[12];
        for (int i = 0; i < playerSprites.length; i++) {
            playerSprites[i] = new Image(animationSpritesPath + "playercharacter/P" + i + ".png",
                    SPRITE_WIDTH, SPRITE_HEIGHT, true, false, true);
        }
        return playerSprites;
    }

    public static Image[] getGuardSprites() {
        Image[] guardSprites = new Image[12];
        for (int i = 0; i < guardSprites.length; i++) {
            guardSprites[i] = new Image(animationSpritesPath + "guard/P" + i + ".png",
                    SPRITE_WIDTH, SPRITE_HEIGHT, true, false, true);
        }
        return guardSprites;
    }

    public static Image getItem(String fileName) {
        return new Image(imagesPath + "items/" + fileName);
    }

    public static Image getFurniture(String fileName) {
        return new Image(imagesPath + "furniture/" + fileName);
    }

    public static String getMap(int index) {
        return imagesPath + "levels/map" + index + ".png";
    }

    public static Image getTile(String fileName) {
        return new Image(imagesPath + "tiles/" + fileName);
    }

    public static Image getUI(String fileName) {
        return new Image(imagesPath + "UI/" + fileName);
    }

    public static Media getSound(String fileName) {
        return new Media(new File(soundPath + fileName).toURI().toString());
    }

    public static String getTheme(String filename) {
        return uiAssetsPath + filename;
    }


}