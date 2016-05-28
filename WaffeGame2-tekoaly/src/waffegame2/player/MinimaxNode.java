/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player;

import java.util.List;

/**
 *
 * @author Walter
 */
public class MinimaxNode {

    int value;
    int depth;
    MinimaxNode parent;
    MinimaxNode bestChild;
    long state; //64 bits, 32+32
    //List<MinimaxNode> children;

    public MinimaxNode(int depth, long state) {
        this.value = 0;
        this.depth = depth;
        this.state = state;
        //this.children = new ArrayList();
    }

    public MinimaxNode(int value, int depth, long state) {
        this.value = value;
        this.depth = depth;
        this.state = state;
        //this.children = new ArrayList();
    }

//    public boolean isLeaf() {
//        return children.isEmpty();
//    }
}
