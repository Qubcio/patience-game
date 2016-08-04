/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static pkg07_24_patience.game.Game.CH;
import static pkg07_24_patience.game.Game.CW;

/**
 *
 * @author qubcio
 */
public class Card {
    int x,y;
    int nmb;
    boolean show;
    boolean selected;
    char t;
    Type T;
    String name;
    Image image;
    Image unknow;

    public Card(int x, int y, int nmb, Type T, boolean show) {
        this.x = x;
        this.y = y;
        this.nmb = nmb;
        this.show = show;
        this.T = T;
        name = "pic/";
        selected = false;
        switch(T) {
            case CLUB: {
                //zoladz
                t = 'b';
                name += "c";
            }   break;
            case DIAMOND: {
                //dzwonek
                t = 'w';
                name += "d";
            }   break;
            case HEART: {
                //serce
                t = 'w';
                name += "h";
            }   break;
            case SPADE: {
                //wino
                t = 'b';
                name += "s";
            }   break;
        }
        name += "_";
        name += Integer.toString(nmb);
        image = null;
        unknow = null;
        try {
            image = ImageIO.read(new File(name+".png"));
            unknow = ImageIO.read(new File("pic/nn.png"));
        } catch (IOException ex) {
            Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void render(Graphics g) {
        if (show) {
            g.drawImage(image, x, y, null);
        } else {
            g.drawImage(unknow, x, y, null);
        }
        if (selected) {
            g.setColor(Color.green);
            for (int i = 0; i < 6; i++) {
                g.drawRect(x+i, y+i, CW-2*i, CH-2*i);
            }
        }
    }
    
    public boolean clickIn(int x, int y) {
        return (x > this.x && x < this.x + CW) && (y > this.y && y < this.y + CH);
    }
    
    public boolean clickIn(int x, int y, int sY) {
        return (x > this.x && x < this.x + CW) && (y > this.y && y < sY);
    }
    
    public boolean dCTake(Card card) {
        return (card.t != this.t && card.nmb == this.nmb +1);
    }

    @Override
    public String toString() {
        return "Card{" + "x=" + x + ", y=" + y + ", nmb=" + nmb + ", show=" + show + ", selected=" + selected + ", t=" + t + ", T=" + T + '}';
    }
    
}
