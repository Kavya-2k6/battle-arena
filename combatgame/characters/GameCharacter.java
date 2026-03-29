package characters;

import actions.Action;
import exceptions.InvalidActionException;

public abstract class GameCharacter {

    private String name;
    private int hp;
    private int maxHp;
    private int attackPower;
    private int mana;
    private int maxMana;

    public GameCharacter(String name, int hp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.maxMana = 40;
        this.mana = maxMana;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    public int getMaxMana() {
        return maxMana;
    }


    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
        setHp(this.hp - damage);
    }
    public abstract Action chooseAction(GameCharacter target);

    public String performAction(Action action, GameCharacter target) throws InvalidActionException {
        if (action == null) {
            throw new InvalidActionException("Action cannot be null!");
        }
        return action.execute(this, target);
    }

    @Override
    public String toString() {
        return name + " (HP: " + hp + "/" + maxHp + ", Mana: " + mana + "/" + maxMana + ")";
    }
}