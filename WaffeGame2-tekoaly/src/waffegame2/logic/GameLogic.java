/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import waffegame2.player.Player;
import waffegame2.ui.UI;

/**
 * Abstract class extended by all game logic
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public abstract class GameLogic {

    protected UI ui;
    protected List<Player> players;
    String name;

    public GameLogic(UI ui) {
        this.ui = ui;
        this.players = new ArrayList();
    }

    /**
     * Adds players to the game
     *
     * @param players The players to be added
     */
    public void addPlayers(Collection<Player> players) {
        this.players.addAll(players);
    }

    /**
     * @return the name of the game
     */
    abstract public String getGameName();

    /**
     * @return the GameRule object containing all the rules to the GameLogic
     */
    abstract public GameRules getRules();

    /**
     * Initializes the GameLogic prior to setting up
     */
    abstract public void init();

    /**
     * Resets all values to the default ones. Also resets the Hand objects, Pile
     * object and Pack object.
     */
    abstract public void setup();

    /**
     * Starts the main game loop. The loop ends when the game ends (when
     * winners/losers are declared).
     */
    abstract public void playGame();

}
