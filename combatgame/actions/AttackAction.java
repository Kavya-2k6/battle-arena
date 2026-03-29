package actions;

import characters.GameCharacter;
import exceptions.InvalidActionException;


public class AttackAction implements Action {

    @Override
    public String execute(GameCharacter performer, GameCharacter target) throws InvalidActionException {
        if (performer == null || target == null) {
            throw new InvalidActionException("Invalid performer or target!");
        }

        int damage = performer.getAttackPower() + (int) (Math.random() * 6);
        target.takeDamage(damage);

        int recoveredMana = 5;
        int oldMana = performer.getMana();
        performer.setMana(oldMana + recoveredMana);
        int actualRecovered = performer.getMana() - oldMana;

        return performer.getName() + " attacks " + target.getName() + " for " + damage + " damage!\n" +
               (actualRecovered > 0 ? performer.getName() + " recovers " + actualRecovered + " Mana!\n" : "") +
               target.getName() + " takes " + damage + " damage! HP: " + target.getHp() + "/" + target.getMaxHp();
    }

    @Override
    public String getActionName() {
        return "Attack";
    }
}