/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience.game;

import java.util.Collection;
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
    int x;
    /**
     *
     * @param x number of column and card in that deck
     * @param d where cards where taking from
     */
    public BColumn(int x, List<? extends Card> d) {
        this.x = x;
        for (int i = 0; i < x; i++) {
            super.add(d.remove(0));
        }
        this.stream().forEach((el) -> {
            el.show = false;
        });
        positioning();
        this.getLast().show = true;
    }
    
    public boolean empty(int x, int y) {
        return (x > (WL1 + (CW + DIF) * (this.x-1)) && x < (WL1 + (CW + DIF) 
                * (this.x-1) + CW) && y > HL2 && y < HL2 +CH);
    }

    @Override
    public boolean addAll(Collection<? extends Card> c) {
        System.out.println(c.size());
        if (super.addAll(c)) {
             positioning();
        }
        return true;
    }
    
    
    public void positioning() {
        for (int i = 0; i < this.size(); i++) {
            this.get(i).y = HL2 + DIFH * i;
        }
        this.stream().forEach((el) -> {
            el.x = WL1 + (CW + DIF) * (x-1);
        });
    }
    
    /**
     * Method highlight cards in column if it is possible and return the highest
     * from them, if can't highlight any then return null.
     * @param x number of selected cards
     * @return last card from possible to use, null if no possible
     */
    public Card highlight(int x) {
        if (size() == 1 || x == size() - 1) {
            this.getLast().selected = true;
            return this.getLast();
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
                    System.out.println(size());
                    return this.get(x);
                }
            }
        }
        return null;
    }
    
    /**
     * 
     * @param x position on X, selected by mouse
     * @param y position on Y, selected by mouse
     * @return number from which card should be selected
     */
    public int inList(int x, int y) {
        if (!isEmpty() && (x > getFirst().x && x < getLast().x + CW) 
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
    
    public LinkedList<Card> removeFrom(Card c) {
        LinkedList<Card> d = new LinkedList<>();
        for (int i = 0; i < size(); i++) {
            if (this.get(i).equals(c)) {
                
                for (int j = size() - 1; j >= i; j--) {
                    d.addFirst(this.get(j));
                    this.remove(j);
                }
                
                break;
            }            
        }
        if (!this.isEmpty())
            this.getLast().show = true;
        return d;
    }    
}
