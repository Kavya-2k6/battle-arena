package characters;

import actions.Action;
import actions.AttackAction;
import actions.HealAction;
import actions.SpecialAttackAction;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Hero extends GameCharacter {
    private int healsRemaining;
    private BlockingQueue<Action> actionQueue;

    public Hero(String name, int hp, int attackPower) {
        super(name, hp, attackPower);
        this.healsRemaining = 3;
        this.actionQueue = new LinkedBlockingQueue<>();
    }

    public int getHealsRemaining() {
        return healsRemaining;
    }

    public void useHeal() {
        if (healsRemaining > 0) {
            healsRemaining--;
        }
    }

    public void provideAction(Action action) {
        try {
            actionQueue.put(action);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Action chooseAction(GameCharacter target) {
        try {
            return actionQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new AttackAction();
        }
    }
}