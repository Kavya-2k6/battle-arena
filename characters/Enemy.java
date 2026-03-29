package characters;

import actions.Action;
import actions.AttackAction;
import actions.HealAction;
import actions.SpecialAttackAction;

public class Enemy extends GameCharacter {
    private int healCooldown;
    private int healsRemaining;

    public Enemy(String name, int hp, int attackPower) {
        super(name, hp, attackPower);
        this.healCooldown = 0;
        this.healsRemaining = 2;
    }

    public void reduceCooldown() {
        if (healCooldown > 0) {
            healCooldown--;
        }
    }

    @Override
    public Action chooseAction(GameCharacter target) {

        if (shouldHeal()) {
            return new HealAction();
        } else if (shouldUseSpecial()) {
            return new SpecialAttackAction();
        } else {
            return new AttackAction();
        }
    }

    private boolean shouldHeal() {
        return getHp() < getMaxHp() * 0.5 && healsRemaining > 0 && healCooldown == 0;
    }

    private boolean shouldUseSpecial() {
        return getMana() >= 20 && getHp() > getMaxHp() * 0.5;
    }

    public void performHeal() {
        if (healsRemaining > 0) {
            healsRemaining--;
            healCooldown = 3;
        }
    }

    public boolean canHeal() {
        return healsRemaining > 0 && healCooldown == 0;
    }
}