package asteroidsTest;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene; //
import javafx.scene.input.KeyCode; // To interact with keyboard
import javafx.scene.layout.Pane; // Game field
import javafx.scene.paint.Color; // To color elements
import javafx.scene.shape.Circle; //To draw a circle
import javafx.scene.shape.Rectangle; // To draw a rectangle
import javafx.stage.Stage; //control basic window properties such as title, icon, visibility, resizability, fullscreen mode, and decorations
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;
import java.util.List;

public class AsteroidsApp extends Application {

    private Pane root;

    private int spawnDelay = 60;

    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();

    private GameObject player;

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(600, 600);

        player = new Player();
        player.setVelocity(new Point2D(1, 0));

        addGameObject(player, root.getPrefWidth()/2, root.getPrefHeight()/2);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addEnemy(GameObject enemy, double x, double y) {
        enemies.add(enemy);
        addGameObject(enemy, x, y);
        enemy.setVelocity(new Point2D(Math.random()-0.5, Math.random()-0.5)); //adds movement to the asteroids (enemies)
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate() {
        for (GameObject bullet : bullets) {
            for (GameObject enemy : enemies) {
                if (bullet.isColliding(enemy)) {
                    bullet.setAlive(false);
                    enemy.setAlive(false);

                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                }
            }
        }

        for (GameObject enemy : enemies) {
            if (player.isColliding(enemy)) {

                root.getChildren().remove(player.getView());
                player.setVelocity(Point2D.ZERO);

                if(player.isAlive())System.out.println("Game Over!");
                player.setAlive(false);
            }
        }

        bullets.removeIf(GameObject::isDead); //go through the list; if a bullet is dead, remove it
        enemies.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);

        player.update();

        if (spawnDelay > 0) spawnDelay--; //delay for asteroid spawns

        if (spawnDelay == 0 && player.isAlive()) { //asteroid spawn mechanics
            double direction = Math.random();
            if (0.75 < direction && direction <= 1) addEnemy(new Enemy(), player.getView().getTranslateX() + 50 + Math.random() * root.getPrefWidth()/2, player.getView().getTranslateY() + 50 + Math.random() * root.getPrefHeight()/2);
            else if (0.50 < direction && direction <= 0.75) addEnemy(new Enemy(), player.getView().getTranslateX() + 50 + Math.random() * root.getPrefWidth()/2, player.getView().getTranslateY() - 50 - Math.random() * root.getPrefHeight()/2);
            else if (0.25 < direction && direction <= 0.50) addEnemy(new Enemy(), player.getView().getTranslateX() - 50 - Math.random() * root.getPrefWidth()/2, player.getView().getTranslateY() - 50 - Math.random() * root.getPrefHeight()/2);
            else if (0.00 < direction && direction <= 0.25) addEnemy(new Enemy(), player.getView().getTranslateX() - 50 - Math.random() * root.getPrefWidth()/2, player.getView().getTranslateY() + 50 + Math.random() * root.getPrefHeight()/2);
            spawnDelay = 120;
        }
    }

    private static class Player extends GameObject {
        Player() {
            super(new Rectangle(40, 20, Color.BLUE));
        }
    }

    private static class Enemy extends GameObject {
        Enemy() {
            super(new Circle(15, 15, 15, Color.RED));
        }
    }

    private static class Bullet extends GameObject {
        Bullet() {
            super(new Circle(5, 5, 5, Color.BROWN));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                player.rotateLeft();

            }
            if (e.getCode() == KeyCode.RIGHT) {
                player.rotateRight();
            }
            if (e.getCode() == KeyCode.SPACE && player.isAlive()) { //player cant shoot if dead
                Bullet bullet = new Bullet();
                bullet.setVelocity(player.getVelocity().normalize().multiply(5));
                addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
