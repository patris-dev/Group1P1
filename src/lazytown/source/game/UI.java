package lazytown.source.game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lazytown.source.AssetManager;

/**
 * This class is used for displaying the in-game User Interface.
 * Inventory, as well as stats are handled here as well.
 */
public class UI {

    private static Text textArea;
    private static int textCounter = 0;
    private static String[] text;
    private static HBox textBorder;

    private static Label pizzaCounter;
    private static Label beerCounter;

    private static ProgressBar healthBar;
    private static ProgressBar hungerBar;
    private static ProgressBar thirstBar;

    private static boolean[] keycard = new boolean[6];
    private static boolean backpack = false;

    private static ImageView map;


    /**
     * The method getUI() sets up our in-game user interface.
     * @return returns a BorderPane which holds all of our in-game UI.
     */
    public static BorderPane getUI() {

        // BorderPane for our UI overlay.
        // Most of the layouts inside will be GridPanes.
        // GridPanes allow us to organize nodes in columns and rows.
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.getStylesheets().add(AssetManager.getTheme("UITheme.css"));

        GridPane counters;
        GridPane characterInfo;

        // UI section for item counters.
        // Includes item icons and counter indexes, which should change accordingly to the amount of items held.
        counters = new GridPane();
        counters.setId("counters");
        counters.setPadding(new Insets(0, 27, 0, 0));

        ImageView pizzaIcon = new ImageView(AssetManager.getItem("pizza_slice.png"));
        ImageView beerIcon  = new ImageView(AssetManager.getItem("can_of_soda.png"));

        pizzaCounter = new Label("0");
        beerCounter  = new Label("0");

        counters.add(pizzaIcon, 0, 0);
        counters.add(beerIcon, 0, 1);
        counters.add(pizzaCounter, 1, 0);
        counters.add(beerCounter, 1, 1);

        counters.setHgap(10);
        counters.setVgap(0);

        GridPane.setMargin(pizzaIcon, new Insets(5, 0, -5, 5));
        GridPane.setMargin(beerIcon, new Insets(0, 0, 0, 3));
        GridPane.setMargin(pizzaCounter, new Insets(4, 0, 0, 0));

        //Kris
        map = new ImageView(new Image("/lazytown/assets/images/levels/fullmap.png"));
        map.setVisible(false);

        // UI section for character info.
        // Includes an image of our main character, small icons for health/hunger/thirst bars and the bars themselves.
        characterInfo = new GridPane();
        characterInfo.setId("character-info");

        ImageView characterIcon = new ImageView(new Image("/lazytown/assets/images/animationsprites/playercharacter/P5.png"));

        ImageView healthIcon = new ImageView(new Image("/lazytown/assets/images/UI/full_heart.png"));
        ImageView hungerIcon = new ImageView(new Image("/lazytown/assets/images/UI/stomach.png"));
        ImageView thirstIcon = new ImageView(new Image("/lazytown/assets/images/UI/water_drop.png"));

        healthBar = new ProgressBar(0.6);
        hungerBar = new ProgressBar(0.1);
        thirstBar = new ProgressBar(0.2);

        characterInfo.add(characterIcon, 0, 0, 1, 3);
        characterInfo.add(healthIcon, 1, 0);
        characterInfo.add(hungerIcon, 1, 1);
        characterInfo.add(thirstIcon, 1, 2);
        characterInfo.add(healthBar, 2, 0);
        characterInfo.add(hungerBar, 2, 1);
        characterInfo.add(thirstBar, 2, 2);

        characterInfo.setHgap(10);
        characterInfo.setVgap(7);

        GridPane.setMargin(healthIcon, new Insets(-5, 0, 0, 0));
        GridPane.setMargin(healthBar, new Insets(-5, 0, 0, 0));

        // UI section for the text window, meant for displaying text to our user.
        // Placed inside the same grid as the character info.
        textBorder = new HBox();
        textBorder.setId("text-border");
        textArea = new Text();
        textArea.setVisible(false);
        textArea.getStyleClass().add("text-id");
        textBorder.getChildren().add(textArea);
        //textArea.setVisible(false);
        characterInfo.add(textBorder, 4, 0, 3, 3);


        // Lays out all UI sections inside root.
        root.setRight(counters);
        root.setBottom(characterInfo);
        root.setCenter(map); //Kris

        return root;
    }

    /**
     * Loads in an array of Strings and displays the first one.
     * @param textLines an array of Strings which will be displayed one by one.
     */
    public static void loadTextWindow(String... textLines) {
        textCounter = 0;
        text = textLines;
        textBorder.setVisible(true);
        textArea.setVisible(true);
        textArea.setText(text[textCounter]);
        textCounter++;
    }

    /**
     * Displays a text window with a single line. Bumps up the line index each time the method is called.
     */
    public static void displayTextWindow() {
        if(text != null && textCounter < text.length) {
            textArea.setText(text[textCounter]);
            textCounter++;
        }
        else hideTextWindow();
    }

    /**
     * Once the counter has reached the limit, make the text box invisible and clear the text array.
     */
    private static void hideTextWindow() {
        text = null;
        textBorder.setVisible(false);
    }

    /**
     * Bumps a counter according to which item was found.
     * @param id identifying data of the item picked up.
     */
    public static void bumpItem(String id) {
        switch (id) {
            case "pizza":
                pizzaCounter.setText(Integer.toString(Integer.parseInt(pizzaCounter.getText()) + 1));
                break;
            case "can":
                beerCounter.setText(Integer.toString(Integer.parseInt(beerCounter.getText()) + 1));
                break;
            case "backpack":
                backpack = true;
                keycard[1] = true;
                keycard[4] = true;
                UI.loadTextWindow("You found your backpack! Inventory increased.\nThere's also your keycard inside! You now have access to building A.");
                break;
            case "key1":
                keycard[1] = true;
                break;
            case "key2":
                keycard[2] = true;
                break;
            case "key3":
                keycard[3] = true;
                break;
            case "key4":
                keycard[4] = true;
                break;
            case "key5":
                keycard[5] = true;
                break;
        }
    }

    /**
     * Makes the map in the UI visible.
     */
    public static void showMap(){
        map.setVisible(true);
    }

    /**
     * Makes the map in the UI invisible.
     */
    public static void hideMap(){
        map.setVisible(false);
    }

    /**
     * A method that removes one pizza from our UI counter when the user activates it, also fills the hungerBar.
     */
    public static void consumePizza() {
        if (!pizzaCounter.getText().equals("0")) {
            Game.getBackgroundSfx().play("eatingSound.mp3");
            pizzaCounter.setText(Integer.toString(Integer.parseInt(pizzaCounter.getText()) - 1));
            hungerBar.setProgress(hungerBar.getProgress()+0.5);
        }
    }

    /**
     * A method that removes one beer from our UI counter when the user activates it, also fills the thirstBar.
     */
    public static void consumeBeer() {
        if (!beerCounter.getText().equals("0")) {
            Game.getBackgroundSfx().play("drinkingSound.mp3");
            beerCounter.setText(Integer.toString(Integer.parseInt(beerCounter.getText()) - 1));
            thirstBar.setProgress(thirstBar.getProgress()+0.5);
        }
    }

    public static void drinkWater() {
        Game.getBackgroundSfx().play("drinkingSound.mp3");
        thirstBar.setProgress(1.0);
    }

    /**
     * Reduce our hp value based on damage taken. Also writes to the text window upon player death.
     * @param damage amount of damage the main character will take.
     */
    public static void takeDamage(double damage) {
        if (healthBar.getProgress() > damage) healthBar.setProgress(healthBar.getProgress()-damage);
        else {
            Game.playerOne.setDead(true);
            Game.root.getChildren().remove(Game.playerOne.spriteFrame);
            UI.loadTextWindow("You died! Congrats!\nPress E to restart.",
                                        "Whoops, seems like restarting isn't implemented yet, huh.",
                                        "It's a pretty lame game anyways, were you playing it because we asked you to?",
                                        "Well, now we're here. How was your day so far?",
                                        "Ours was pretty stressed, needed to finish up a game and stuff.",
                                        "But you don't really care about that, do you?",
                                        "By the way, there might or might not be an easter egg involving the\nKonami code.",
                                        "Anyways, if you want to restart, right now you would need to exit the game\nand start it again. Yup. Sorry!");
            healthBar.setProgress(0);
        }
    }

    /**
     * Update our stats.
     * Health slowly regenerates.
     * Hunger and Thirst will slowly go down. If one reaches 0, our Health will stop regenerating;
     * If both reach 0, our Health will start to go down.
     */
    public static void updateStats() {
        double rate = 0.0001;
        if (healthBar.getProgress() > rate) healthBar.setProgress(healthBar.getProgress()+rate);

        if (hungerBar.getProgress() > rate) hungerBar.setProgress(hungerBar.getProgress()-rate/10);
        else takeDamage(rate);
        if (thirstBar.getProgress() > rate) thirstBar.setProgress(thirstBar.getProgress()-rate/10);
        else takeDamage(rate);
    }

    public static boolean isBackpack() {
        return backpack;
    }

    public static boolean getKeycard(int id) {
        return keycard[id];
    }

    public static int getPizza() {
        return Integer.parseInt(pizzaCounter.getText());
    }

    public static int getBeer() {
        return Integer.parseInt(beerCounter.getText());
    }
}
