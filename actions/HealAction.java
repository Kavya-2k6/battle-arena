package actions;

import characters.GameCharacter;
import characters.Hero;
import characters.Enemy;
import exceptions.InvalidActionException;

public class HealAction implements Action {

    @Override
    public String execute(GameCharacter performer, GameCharacter target) throws InvalidActionException {
        if (performer == null) {
            throw new InvalidActionException("Invalid performer!");
        }

        int healAmount = performer.getMaxHp() / 4;

        if (performer instanceof Hero) {
            Hero hero = (Hero) performer;
            if (hero.getHealsRemaining() <= 0) {
                throw new InvalidActionException("No heals remaining!");
            }
            hero.useHeal();
        } else if (performer instanceof Enemy) {
            Enemy enemy = (Enemy) performer;
            if (!enemy.canHeal()) {
                throw new InvalidActionException("Cannot heal now!");
            }
            enemy.performHeal();
        }

        performer.setHp(performer.getHp() + healAmount);
        return performer.getName() + " heals for " + healAmount + " HP!";
    }

    @Override
    public String getActionName() {
        return "Heal";
    }
}