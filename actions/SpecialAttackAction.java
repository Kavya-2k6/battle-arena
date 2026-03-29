package actions;

import characters.GameCharacter;
import exceptions.InvalidActionException;

public class SpecialAttackAction implements Action {
    private static final int MANA_COST = 20;

    @Override
    public String execute(GameCharacter performer, GameCharacter target) throws InvalidActionException {
        if (performer == null || target == null) {
            throw new InvalidActionException("Invalid performer or target!");
        }

        if (performer.getMana() < MANA_COST) {
            throw new InvalidActionException("Not enough mana! Need " + MANA_COST + " mana.");
        }

        
        performer.setMana(performer.getMana() - MANA_COST);

        
        int damage = performer.getAttackPower() * 2;
        target.takeDamage(damage);
        return performer.getName() + " uses SPECIAL ATTACK for " + damage + " damage!\n" +
               target.getName() + " takes " + damage + " damage! HP: " + target.getHp() + "/" + target.getMaxHp();
    }

    @Override
    public String getActionName() {
        return "Special Attack";
    }
}