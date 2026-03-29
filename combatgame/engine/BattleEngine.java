package engine;

import characters.GameCharacter;
import characters.Enemy;
import actions.Action;
import exceptions.InvalidActionException;

import java.util.function.Consumer;

public class BattleEngine {

    private Consumer<String> uiCallback;

    public BattleEngine(Consumer<String> uiCallback) {
        this.uiCallback = uiCallback;
    }

    private void logToUI(String message) {
        if (uiCallback != null) {
            uiCallback.accept(message);
        }
    }

    public void fight(GameCharacter hero, GameCharacter enemy) {
        logToUI("BATTLE START");

        while (hero.isAlive() && enemy.isAlive()) {
            try {
                
                playerTurn(hero, enemy);
                if (!enemy.isAlive())
                    break;

                enemyTurn(hero, enemy);

                if (enemy instanceof Enemy) {
                    ((Enemy) enemy).reduceCooldown();
                }

            } catch (InvalidActionException e) {
                logToUI("Invalid action: " + e.getMessage());
                logToUI("Try a different action!");
            }
        }

        logToUI("\nBATTLE END");
        if (hero.isAlive()) {
            logToUI("!!! " + hero.getName() + " wins! !!!");
        } else {
            logToUI("!!! " + enemy.getName() + " wins! !!!");
        }
    }

    private void playerTurn(GameCharacter hero, GameCharacter enemy) throws InvalidActionException {
        logToUI("\n YOUR TURN ");
        
        Action playerAction = hero.chooseAction(enemy);
        String result = hero.performAction(playerAction, enemy);
        logToUI(result);


    }

    private void enemyTurn(GameCharacter hero, GameCharacter enemy) throws InvalidActionException {
        logToUI("\n ENEMY TURN ");

        try {
            Thread.sleep(1500); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Action enemyAction = enemy.chooseAction(hero);
        String result = enemy.performAction(enemyAction, hero);
        logToUI(result);


    }
}