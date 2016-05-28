/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.logic;

import java.util.List;
import waffegame2.cardOwner.*;
import waffegame2.player.*;

/**
 * The table containing all information about the cardselectors and cardowners
 * of the game. Used by UI and AI.
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-05-28
 */
public class Table {
    Scoreboard sb;
    List<Player> players;
    Pack pack;
    Pile pile;
    Hand tableHand;
    
    
}
