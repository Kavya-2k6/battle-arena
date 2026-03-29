package actions;

import characters.GameCharacter;
import exceptions.InvalidActionException;


public interface Action {

    String execute(GameCharacter performer, GameCharacter target) throws InvalidActionException;
    String getActionName();
}