/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import pkg07_24_patience.Main;
import static pkg07_24_patience.Main.MY_WIDTH;

/**
 *
 * @author qubcio
 */
public class Game implements MouseListener {
    public static final int CW = 130,

    /**
     * CW - card witdth, CH - card height
     */
    CH = 180;

    /**
     * the lowest type card
     */
    public static final int CF = 2;

    /**
     * the highest type card
     */
    public static final int CL = 14;

    /**
     * height line first in layout
     */
    public static final int HL1 = 80;

    /**
     * height line second in layout
     */
    public static final int HL2 = HL1 + CH + 80;

    /**
     * width line first in layout
     */
    public static final int WL1 = 150;

    /**
     * full number of cards
     */
    public static final int FULL = 52;
    /**
     * number of bottom columns
     */
    public static final int COL = 7;

    /**
     * distance between cards in layout(width)
     */
    @SuppressWarnings("StaticNonFinalUsedInInitialization")
    public static final int DIF = (MY_WIDTH - 2 * WL1 - COL * CW) / 6;
    public static final int DIFH = 30;
    //top first queue
    DequeMyClass tF;
    //top first queue
    DequeMyClass tS;
    //bottom all columns
    List<Card> dA;
    ListContainer l;
    BColumn[] mY;
    boolean columnPick;
    /**
     *
     * @param main
     */
    public Game(Main main) {
        
        List<Card> cards = new LinkedList<>();
        tF = new DequeMyClass();
        dA = new LinkedList<>();
        tS = new DequeMyClass();
        l = new ListContainer();
        mY = new BColumn[COL];
        columnPick = false;
        Type[] type = Type.values();
        for (int i = CF; i <= CL; i++) {
            for (Type t : type) {
                cards.add(new Card(MY_WIDTH, 0, i, t, false));
            }
        }
        Collections.shuffle(cards);
        for(int i = 1; i <= COL; i++) {
            mY[i-1] = new BColumn(i, cards);
        }
        tF.addAll(cards);
        addingToL();
    }
    
    /**
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        topLeft(x,y);
        bottom(x,y);
    }
    
    /**
     * adding list to main container
     */
    private void addingToL() {
        l.add(tF);
        l.add(dA);
        l.add(tS);
        l.addAll(Arrays.asList(mY));
    }
    
    /**
     *
     * @param g
     */
    public void render(Graphics g) {
        choosenFields(g);
        l.render(g);
    }

    private void choosenFields(Graphics g) {
        g.setColor(Color.lightGray);
        for (int i = 0; i < 7; i++) {
            if (i != 2) {
                g.fillRect(i * (DIF+CW) + WL1, HL1, CW, CH);
            }
            g.fillRect(i * (DIF+CW) + WL1, HL2, CW, CH);
        }
    }

    private void topLeft(int x, int y) {
        if (x > WL1 && x < WL1 + CW && y > HL1 && y < HL1 + CH) {
            if(!tS.isEmpty())
                tS.getLast().selected = false;
            if (tF.size() > 0) {
                tS.addM(tF.removeFirst());
            } else {
                tF.addAll(tS);
                tS.clear();
            }
        }
        if (!tS.isEmpty() && tS.getLast().clickIn(x, y)) {
            tS.getLast().selected = !tS.getLast().selected;
        }
    }

    private void bottom(int x, int y) {
        for (BColumn bC : mY) {
            int a = bC.inList(x, y);
            if (a != -1) {
                columnPick = bC.highlight(a);
                break;
            }
        }
    }
    
    /**
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
