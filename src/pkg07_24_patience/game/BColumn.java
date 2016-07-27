/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience.game;

import java.util.LinkedList;
import java.util.List;
import static pkg07_24_patience.game.Game.CH;
import static pkg07_24_patience.game.Game.CW;
import static pkg07_24_patience.game.Game.DIF;
import static pkg07_24_patience.game.Game.DIFH;
import static pkg07_24_patience.game.Game.HL2;
import static pkg07_24_patience.game.Game.WL1;

/**
 *
 * @author qubcio
 */
public class BColumn extends LinkedList<Card> {

    /**
     *
     * @param x number of column and card in that deck
     * @param d where cards where taking from
     */
    public BColumn(int x, List<? extends Card> d) {
        for (int i = 0; i < x; i++) {
            super.add(d.remove(0));
        }
        positioning(x);
        this.getLast().show = true;
    }
    
    private void positioning(int x) {
        this.stream().forEach((el) -> {
            el.x = WL1 + (CW + DIF) * (x-1);
            el.show = false;
        });
        for (int i = 0; i < this.size(); i++) {
            this.get(i).y = HL2 + DIFH * i;
        }
    }
    
    public boolean highlight(int x) {
        if (size() == 1 || x == size() - 1) {
            this.getLast().selected = true;
            return true;
        }
        if (this.get(x).show){
            for (int i = x + 1; i < size(); i++) {
                if (!get(i).dCTake(get(i-1))) {
                    break;
                }
                if (i == size() - 1) {
                    for (int j = x; j < size(); j++) {
                        get(j).selected = true;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public int inList(int x, int y) {
        if ((x > getFirst().x && x < getLast().x + CW) 
                && (y > getFirst().y && y < getLast().y + CH)) {
            if (size() == 1) {
                return 0;
            } else if (this.getLast().clickIn(x, y)) {
                return size()-1;
            } else {
                for (int i = 0; i < size()-1; i++) {
                    if (get(i).clickIn(x, y, get(i+1).y)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}
