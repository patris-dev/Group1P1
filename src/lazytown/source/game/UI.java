package lazytown.source.game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * This class is used for displaying the in-game User Interface.
 * The method getUI() returns a BorderPane containing all of the User Interface.
 */
public class UI {

    private static Text textArea;
    private static int textCounter = 0;
    private static String[] text;

    public static BorderPane getUI() {

        // BorderPane for our UI overlay.
        // Most of the layouts inside will be GridPanes.
        // GridPanes allow us to organize nodes in columns and rows.
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.getStylesheets().add("lazytown/assets/uiassets/UITheme.css");

        GridPane counters;
        GridPane characterInfo;


        // UI section for item counters.
        // Includes item icons and counter indexes, which should change accordingly to the amount of items held.
        counters = new GridPane();
        counters.setId("counters");
        counters.setPadding(new Insets(0, 27, 0, 0));

        ImageView pizzaIcon = new ImageView(new Image("/lazytown/assets/images/UI/pizza_slice.png"));
        ImageView beerIcon  = new ImageView(new Image("/lazytown/assets/images/UI/can_of_soda.png"));

        Label pizzaCounter = new Label("0");
        Label beerCounter  = new Label("0");

        counters.add(pizzaIcon, 0, 0);
        counters.add(beerIcon, 0, 1);
        counters.add(pizzaCounter, 1, 0);
        counters.add(beerCounter, 1, 1);

        counters.setHgap(10);
        counters.setVgap(0);

        GridPane.setMargin(pizzaIcon, new Insets(5, 0, -5, 5));
        GridPane.setMargin(beerIcon, new Insets(0, 0, 0, 3));
        GridPane.setMargin(pizzaCounter, new Insets(4, 0, 0, 0));

        // UI section for character info.
        // Includes an image of our main character, small icons for health/hunger/thirst bars and the bars themselves.
        characterInfo = new GridPane();
        characterInfo.setId("character-info");

        ImageView characterIcon = new ImageView(new Image("/lazytown/assets/images/animationsprites/playercharacter/P5.png"));

        ImageView healthIcon = new ImageView(new Image("/lazytown/assets/images/UI/full_heart.png"));
        ImageView hungerIcon = new ImageView(new Image("/lazytown/assets/images/UI/stomach.png"));
        ImageView thirstIcon = new ImageView(new Image("/lazytown/assets/images/UI/water_drop.png"));

        ProgressBar healthBar = new ProgressBar(0.6);
        ProgressBar hungerBar = new ProgressBar(0.8);
        ProgressBar thirstBar = new ProgressBar(0.7);

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
        textArea = new Text();
        textArea.setVisible(false);
        textArea.getStyleClass().add("text-id");
        //textArea.setVisible(false);
        characterInfo.add(textArea, 4, 0, 3, 3);


        // Lays out all UI sections inside root.
        root.setRight(counters);
        root.setBottom(characterInfo);

        return root;
    }

    public static void loadTextWindow(String... textLines) {
        text = textLines;
    }

    public static void displayTextWindow() {
        if(text != null && textCounter < text.length) {
            textArea.setText(text[textCounter]);
            textArea.setVisible(true);
            textCounter++;
        }
        else hideTextWindow();
    }

    public static void hideTextWindow() {
        text = null;
        textArea.setVisible(false);
        textCounter = 0;
    }

    public static void bumpTextCounter() {
        textCounter++;
    }
}
