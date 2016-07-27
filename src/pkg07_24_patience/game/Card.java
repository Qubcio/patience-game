/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import static pkg07_24_patience.game.Game.CH;
import static pkg07_24_patience.game.Game.CW;

/**
 *
 * @author qubcio
 */
public class Card {
    int x,y;
    int nmb;
    Color c;
    boolean show;
    boolean selected;
    char t;

    public Card(int x, int y, int nmb, Type T, boolean show) {
        this.x = x;
        this.y = y;
        this.nmb = nmb;
        this.show = show;
        selected = false;
        switch(T) {
            case CLUB: {
                c = Color.LIGHT_GRAY.darker();
                t = 'b';
            }   break;
            case DIAMOND: {
                c = Color.yellow.darker();
                t = 'w';
            }   break;
            case HEART: {
                c = Color.RED;
                t = 'w';
            }   break;
            case SPADE: {
                c = Color.BLACK;
                t = 'b';
            }   break;
        }
    }
    
    public void render(Graphics g) {
        if (!selected) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.pink.darker());
        }
        g.fillRect(x+1, y+1, CW-1, CH-1);
        if (show) {
            g.setColor(c);
            g.setFont(new Font("Arial", 1, CH/10));
            g.drawString(Integer.toString(nmb), x+5, y+CH/9);
            if (nmb < 10) {
                g.setFont(new Font("Arial", 1, CH/2));
                g.drawString(Integer.toString(nmb), x+35, y+CH-55);
            } else {
                g.setFont(new Font("Arial", 1, CH/3));
                g.drawString(Integer.toString(nmb), x+25, y+CH-70);
            }
        } else {
            g.setColor(Color.blue);
            g.setFont(new Font("Arial", 1, CH/2));
            g.drawString("?", x+39, y+CH-55);
            g.setFont(new Font("Arial", 1, CH/10));
            g.drawString("?", x+6, y+CH/9);
        }
        g.setColor(Color.black);
        g.drawRect(x, y, CW, CH);
    }
    
    public boolean clickIn(int x, int y) {
        return (x > this.x && x < this.x + CW) && (y > this.y && y < this.y + CH);
    }
    
    public boolean clickIn(int x, int y, int sY) {
        return (x > this.x && x < this.x + CW) && (y > this.y && y < sY);
    }
    
    public boolean dCTake(Card card) {
        return (!card.c.equals(this.c) && card.nmb == this.nmb +1);
    }
}
