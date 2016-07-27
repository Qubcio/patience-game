/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience.game;

import java.util.ArrayDeque;
import java.util.Collection;
import static pkg07_24_patience.game.Game.CW;
import static pkg07_24_patience.game.Game.DIF;
import static pkg07_24_patience.game.Game.HL1;
import static pkg07_24_patience.game.Game.WL1;

/**
 *
 * @author qubcio
 */
public class DequeMyClass extends ArrayDeque<Card> {

    public boolean addM(Card e) {
        e.x = WL1 + DIF + CW;
        e.y = HL1;
        e.show = true;
        return super.add(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends Card> c) {
        c.stream().forEach((el) -> {
            el.x = WL1;
            el.y = HL1;
            el.show = false;
        });
        return super.addAll(c); //To change body of generated methods, choose Tools | Templates.
    }
    
}
