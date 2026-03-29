package main;

import actions.AttackAction;
import actions.HealAction;
import actions.SpecialAttackAction;
import characters.Enemy;
import characters.Hero;
import engine.BattleEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;

public class Game extends Application {
    private BattleEngine battleEngine;
    private Hero hero;
    private Enemy enemy;

    private TextArea logArea;
    private ProgressBar heroHpBar;
    private ProgressBar heroManaBar;
    private ProgressBar enemyHpBar;
    private Label heroHpLabel;
    private Label heroManaLabel;
    private Label enemyHpLabel;
    private Button attackBtn;
    private Button healBtn;
    private Button specialBtn;

    @Override
    public void start(Stage primaryStage) {
        hero = new Hero("Player", 100, 12);
        enemy = new Enemy("Goblin Warrior", 100, 12);
        
        battleEngine = new BattleEngine(msg -> {
            Platform.runLater(() -> {
                logArea.appendText(msg + "\n");
                updateBars();
            });
        });

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root-pane");

        Label titleLabel = new Label("TURN-BASED COMBAT");
        titleLabel.getStyleClass().add("game-title");

        HBox cardsBox = new HBox(50);
        cardsBox.setAlignment(Pos.CENTER);
        
        VBox heroCard = createPlayerCard(hero, true);
        Label vsLabel = new Label("VS");
        vsLabel.getStyleClass().add("vs-label");
        VBox enemyCard = createPlayerCard(enemy, false);
        
        cardsBox.getChildren().addAll(heroCard, vsLabel, enemyCard);

        HBox actionBox = createActionBox();

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setPrefHeight(150);
        logArea.setMaxHeight(150);
        logArea.getStyleClass().add("console-log");
        VBox.setVgrow(logArea, Priority.NEVER);

        root.getChildren().addAll(titleLabel, cardsBox, actionBox, logArea);

        Scene scene = new Scene(root, 800, 650);
        try {
            File cssFile = new File("style.css");
            scene.getStylesheets().add(cssFile.toURI().toString());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        primaryStage.setTitle("Turn-Based RPG");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();

        updateBars();

        Thread battleThread = new Thread(() -> {
            battleEngine.fight(hero, enemy);
            Platform.runLater(() -> {
                attackBtn.setDisable(true);
                healBtn.setDisable(true);
                specialBtn.setDisable(true);
            });
        });
        battleThread.setDaemon(true);
        battleThread.start();
    }

    private VBox createPlayerCard(characters.GameCharacter character, boolean isHero) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("entity-card");

        ImageView sprite = new ImageView();
        sprite.setFitWidth(100);
        sprite.setFitHeight(100);
        sprite.getStyleClass().add("sprite-image");
        
        Label nameLabel = new Label(character.getName());
        nameLabel.getStyleClass().add("entity-name");
        
        VBox hpBox = new VBox(2);
        hpBox.setAlignment(Pos.CENTER_LEFT);
        Label hpTitle = new Label("HP");
        hpTitle.getStyleClass().add("stat-label");
        ProgressBar hpBar = new ProgressBar(1.0);
        Label hpValLabel = new Label();
        hpValLabel.getStyleClass().add("stat-value");
        
        hpBox.getChildren().addAll(hpTitle, hpBar, hpValLabel);

        if (isHero) {
            hpBar.getStyleClass().addAll("stat-bar", "hp-bar-hero");
            heroHpBar = hpBar;
            heroHpLabel = hpValLabel;
            
            VBox manaBox = new VBox(2);
            manaBox.setAlignment(Pos.CENTER_LEFT);
            Label manaTitle = new Label("MANA");
            manaTitle.getStyleClass().add("stat-label");
            heroManaBar = new ProgressBar(1.0);
            heroManaBar.getStyleClass().addAll("stat-bar", "mana-bar");
            heroManaLabel = new Label();
            heroManaLabel.getStyleClass().add("stat-value");
            
            manaBox.getChildren().addAll(manaTitle, heroManaBar, heroManaLabel);
            card.getChildren().addAll(sprite, nameLabel, hpBox, manaBox);
        } else {
            hpBar.getStyleClass().addAll("stat-bar", "hp-bar-enemy");
            enemyHpBar = hpBar;
            enemyHpLabel = hpValLabel;
            card.getChildren().addAll(sprite, nameLabel, hpBox);
        }

        return card;
    }

    private HBox createActionBox() {
        HBox box = new HBox(20);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("action-box");

        attackBtn = new Button("ATTACK");
        attackBtn.getStyleClass().addAll("action-btn", "btn-attack");
        attackBtn.setOnAction(e -> hero.provideAction(new AttackAction()));

        healBtn = new Button("HEAL");
        healBtn.getStyleClass().addAll("action-btn", "btn-heal");
        healBtn.setOnAction(e -> hero.provideAction(new HealAction()));

        specialBtn = new Button("SPECIAL");
        specialBtn.getStyleClass().addAll("action-btn", "btn-special");
        specialBtn.setOnAction(e -> hero.provideAction(new SpecialAttackAction()));

        box.getChildren().addAll(attackBtn, healBtn, specialBtn);
        return box;
    }

    private void updateBars() {
        double heroHpPct = Math.max(0, (double) hero.getHp() / hero.getMaxHp());
        heroHpBar.setProgress(heroHpPct);
        heroHpLabel.setText(hero.getHp() + " / " + hero.getMaxHp());
        
        double heroManaPct = Math.max(0, (double) hero.getMana() / hero.getMaxMana());
        heroManaBar.setProgress(heroManaPct);
        heroManaLabel.setText(hero.getMana() + " / " + hero.getMaxMana());

        double enemyHpPct = Math.max(0, (double) enemy.getHp() / enemy.getMaxHp());
        enemyHpBar.setProgress(enemyHpPct);
        enemyHpLabel.setText(enemy.getHp() + " / " + enemy.getMaxHp());

        healBtn.setText("HEAL (" + hero.getHealsRemaining() + ")");
        healBtn.setDisable(hero.getHealsRemaining() <= 0 || !hero.isAlive() || !enemy.isAlive());
        
        specialBtn.setText("SPECIAL (20 MP)");
        specialBtn.setDisable(hero.getMana() < 20 || !hero.isAlive() || !enemy.isAlive());
        
        attackBtn.setDisable(!hero.isAlive() || !enemy.isAlive());
    }

    public static void main(String[] args) {
        launch(args);
    }
}