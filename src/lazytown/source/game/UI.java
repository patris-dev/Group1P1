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

import java.security.SecureRandom;

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
    private static Label fps = new Label();

    private static ProgressBar healthBar;
    private static ProgressBar hungerBar;
    private static ProgressBar thirstBar;

    private static boolean[] keycard = new boolean[6];
    private static boolean backpack = false;

    private static ImageView map;
    private static ImageView eKey;


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


        //UI section for the map, which appears upon pressing M.
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

        healthBar = new ProgressBar(0.8);
        hungerBar = new ProgressBar(0.4);
        thirstBar = new ProgressBar(0.4);

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
        textBorder.getChildren().addAll(textArea);
        characterInfo.add(textBorder, 4, 0, 3, 3);
        eKey = new ImageView(AssetManager.getUI("e_key.png"));
        eKey.setTranslateX(966);
        eKey.setTranslateY(707);
        root.getChildren().add(eKey);

        // Lays out all UI sections inside root.
        root.setRight(counters);
        root.setBottom(characterInfo);
        root.setCenter(map);
        root.setTop(fps);

        return root;
    }

    /**
     * Updates the FPS value.
     * @param FPS Float representing Frames Per Second
     */
    public static void updateFPS(float FPS) {
        fps.setText(String.format("%4.0f", FPS));
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
        eKey.toFront();
        eKey.setVisible(true);
    }

    /**
     * Displays a text window with a single line. Bumps up the line index each time the method is called.
     */
    public static void displayTextWindow() {
        if(text != null && textCounter < text.length) {
            textArea.setText(text[textCounter]);
            textCounter++;
            eKey.toFront();
        }
        else hideTextWindow();
    }

    /**
     * Once the counter has reached the limit, make the text box invisible and clear the text array.
     */
    private static void hideTextWindow() {
        text = null;
        textBorder.setVisible(false);
        eKey.setVisible(false);
    }

    /**
     * Bumps a counter according to which item was found.
     * @param id identifying data of the item picked up.
     */
    public static void bumpItem(String id) {
        switch (id) {
            case "pizza":
                Game.getBackgroundSfx().play("pizzaPickup.mp3");
                pizzaCounter.setText(Integer.toString(Integer.parseInt(pizzaCounter.getText()) + 1));
                break;
            case "can":
                Game.getBackgroundSfx().play("beerPickup.mp3");
                beerCounter.setText(Integer.toString(Integer.parseInt(beerCounter.getText()) + 1));
                break;
            case "backpack":
                Game.getBackgroundSfx().play("backpackPickup.mp3");
                backpack = true;
                keycard[1] = true;
                keycard[4] = true;
                UI.loadTextWindow("You found your backpack! Now you can hold 5 of both pizzas and beers.\n" +
                        "There's also your keycard inside! You now have access to building A.");
                break;
            case "key1":
                Game.getBackgroundSfx().play("keycardPickup.mp3");
                keycard[1] = true;
                break;
            case "key2":
                Game.getBackgroundSfx().play("keycardPickup.mp3");
                keycard[2] = true;
                break;
            case "key3":
                Game.getBackgroundSfx().play("keycardPickup.mp3");
                keycard[3] = true;
                break;
            case "key4":
                Game.getBackgroundSfx().play("keycardPickup.mp3");
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

    /**
     * A method that fills the thirstBar, used when interacting with water taps.
     */
    public static void drinkWater() {
        Game.getBackgroundSfx().play("drinkingSound.mp3");
        thirstBar.setProgress(1.0);
    }

    /**
     * A method that generates loot into the locker.
     */
    public static void locker() {
        double rand = new SecureRandom().nextDouble();
        if (rand > 0.9) {
            bumpItem("pizza");
            UI.loadTextWindow("You found some food!");
        }
        else if (rand > 0.8) {
            bumpItem("can");
            UI.loadTextWindow("You found some sort of a drink.");
        }
        else UI.loadTextWindow("The locker is empty.");
    }

    /**
     * Reduce our hp value based on damage taken. Also writes to the text window upon player death.
     * @param damage amount of damage the main character will take.
     */
    public static void takeDamage(double damage) {
        if (healthBar.getProgress() > 0.065) healthBar.setProgress(healthBar.getProgress() - damage);
        else if (healthBar.getProgress() != 0){
            Game.playerOne.setDead(true);
            Game.root.getChildren().remove(Game.playerOne.spriteFrame);
            UI.loadTextWindow("You died! Congrats!\nPress E to restart.",
                                        "Whoops, seems like restarting isn't implemented yet, huh.",
                                        "It's a pretty lame game anyways, were you playing it because we asked you to?",
                                        "Well, now we're here. How was your day so far?",
                                        "Ours was pretty stressed, needed to finish up a game and stuff.",
                                        "But you don't really care about that, do you?",
                                        "Anyways, if you want to restart, right now you would need to exit the game\n"
                                                + "and start it again. Yup. Sorry!");
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
        double rate = 0.0003;
        if (healthBar.getProgress() > 0.065) healthBar.setProgress(healthBar.getProgress()+rate);

        if (hungerBar.getProgress() > 0.065) hungerBar.setProgress(hungerBar.getProgress()-rate/10);
        else takeDamage(rate);
        if (thirstBar.getProgress() > 0.065) thirstBar.setProgress(thirstBar.getProgress()-rate/10);
        else takeDamage(rate);

        if (healthBar.getProgress() > 1.0) healthBar.setProgress(1.0);
        if (thirstBar.getProgress() > 1.0) thirstBar.setProgress(1.0);
        if (hungerBar.getProgress() > 1.0) hungerBar.setProgress(1.0);
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
