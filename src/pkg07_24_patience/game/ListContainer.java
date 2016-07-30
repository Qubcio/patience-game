/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience.game;

import java.awt.Graphics;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author qubcio
 */
public class ListContainer extends LinkedList<Collection<Card>>{
    public void render(Graphics g) {
        this.stream().forEach((el -> {
            el.stream().forEach((el2) -> {
                el2.render(g);
            });
        }));
    }
    public void setSel() {
        stream().forEach((el) -> {
            el.stream().forEach((el2) -> {
                el2.selected = false;
            });
        });
    }
}
