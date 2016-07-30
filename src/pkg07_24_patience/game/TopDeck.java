/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience.game;

import java.util.ArrayDeque;
import static pkg07_24_patience.game.Game.CW;
import static pkg07_24_patience.game.Game.DIF;
import static pkg07_24_patience.game.Game.HL1;
import static pkg07_24_patience.game.Game.WL1;

/**
 *
 * @author qubcio
 */
public class TopDeck extends ArrayDeque<Card> {
    int x;
    TopDeck(int x) {
        this.x = x;
    }

    @Override
    public boolean add(Card e) {
        e.x = (x+2) * (CW + DIF) + WL1;
        e.y = HL1;
        return super.add(e); //To change body of generated methods, choose Tools | Templates.
    }
    
}
