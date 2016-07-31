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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;
import pkg07_24_patience.Main;
import static pkg07_24_patience.Main.MY_WIDTH;

/**
 *
 * @author qubcio
 */
public class Game implements MouseListener {
    public static final int CW = 122,

    /**
     * CW - card witdth, CH - card height
     */
    CH = 186;

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
    /**
     * Number of top right decks.
     */
    public static final int TD = 4;
    //top first queue
    DequeMyClass tF;
    //top first queue
    DequeMyClass tS;
    //bottom all columns
    List<Card> dA;
    ListContainer l;
    BColumn[] mY;
    Card cP;
    TopDeck[] tD;
    boolean fromTop;
    boolean ag;
    
    /**
     *
     * @param main
     */
    public Game(Main main) {
        tF = new DequeMyClass();
        dA = new LinkedList<>();
        tS = new DequeMyClass();
        l = new ListContainer();
        mY = new BColumn[COL];
        tD = new TopDeck[TD];
        for (int i = 0; i < tD.length; i++) {
            tD[i] = new TopDeck(i+1);            
        }
        init();
    }
    
    private void init() {
        List<Card> cards = new LinkedList<>();
        cP = null;
        fromTop = false;
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
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (cP != null) {
                moveColumn(x, y);
                moveTopRight(x,y);
                cP = null;
                l.setSel();
                return;
            }
            cP = null;
            l.setSel();
            topLeft(x,y);
            bottom(x,y);
            topRightL(x, y);
        } else if(SwingUtilities.isRightMouseButton(e)) {
            topRightR();
        }
    }
    
    private void moveTopRight(int x, int y) {
        outer: for (BColumn b : mY) {
            if (!b.isEmpty() && b.getLast().clickIn(x, y)) {
                for (TopDeck t : tD) {
                    if (!t.isEmpty() && t.getLast().selected && b.getLast().t 
                            != cP.t && b.getLast().nmb == cP.nmb + 1) {
                        b.add(cP);
                        t.remove(cP);
                        cP.selected = false;
                        cP = null;
                        b.positioning();
                        break outer;
                    }
                }
            }
            for (TopDeck t : tD) {
                if (b.isEmpty() && b.empty(x,y) && !t.isEmpty() && t.getLast().selected) {
                    System.out.println("tak");
                    b.add(cP);
                    t.remove(cP);
                    cP.selected = false;
                    cP = null;
                    b.positioning();
                    break outer;
                }
            }
        }
    }
    
    private void topRightL(int x, int y) {
        for (TopDeck t : tD) {
            if (!t.isEmpty() && t.getLast().clickIn(x, y)) {
                cP = t.getLast();
                cP.selected = true;
            }
        }
    }
    
    private void topRightR() {
        // tS and mY check if can take
        do {
            ag = false;
            for (TopDeck tR : tD) {
                if (tR.isEmpty()) {
                    check(2, tR);
                } else {
                    check(tR.getLast().nmb+1, tR);
                }
            }
        } while (ag);
    }
    
    private void check(int x, TopDeck td) {
        if (x == 2) {
            if (!tS.isEmpty() && tS.getLast().nmb == 2) {
                td.add(tS.getLast());
                tS.removeLast();
                ag = true;
            } else {
                for (BColumn mY1 : mY) {
                    if (!mY1.isEmpty() && mY1.getLast().nmb == 2) {
                        td.add(mY1.getLast());
                        mY1.removeLast();
                        if (!mY1.isEmpty()) {
                            mY1.getLast().show = true;
                        }
                        ag = true;
                        break;
                    }
                }
            }
        } else {
            if (!tS.isEmpty() && checkV(tS.getLast(), td.getLast())) {
                td.add(tS.getLast());
                tS.removeLast();
                ag = true;
            } else {
                for (BColumn mY1 : mY) {
                    if (!mY1.isEmpty() && checkV(mY1.getLast(), td.getLast())) {
                        td.add(mY1.getLast());
                        mY1.removeLast();
                        if (!mY1.isEmpty())
                            mY1.getLast().show = true;
                        ag = true;
                        break;
                    }
                }
            }
        }
    }
    
    private boolean checkV(Card a, Card b) {
        return (a.nmb == (b.nmb + 1) && a.T == b.T);
    }
    
    /**
     * adding list to main container
     */
    private void addingToL() {
        l.add(tF);
        l.add(dA);
        l.add(tS);
        l.addAll(Arrays.asList(mY));
        l.addAll(Arrays.asList(tD));
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
            cP = tS.getLast();
            fromTop = true;
        }
    }

    private void bottom(int x, int y) {
        for (BColumn bC : mY) {
            int a = bC.inList(x, y);
            if (a != -1) {
                cP = bC.highlight(a);
                break;
            }
        }
    }

    private boolean moveColumn(int x, int y) {
        for (BColumn bC : mY) {
            if (!bC.isEmpty()) {
                if(bC.getLast().clickIn(x, y) && bC.getLast().nmb == cP.nmb + 1 
                    && bC.getLast().t != cP.t) {
                    if(fromTop) {
                        bC.addLast(cP);
                        tS.removeLast();
                        fromTop = false;
                        bC.positioning();
                        return true;
                    } else {
                        for (BColumn bS : mY) {
                            for(Card cD : bS) {
                                if (cD.equals(cP)) {
                                    bC.addAll(bS.removeFrom(cP));
                                    bC.positioning();
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else {
                if (bC.empty(x,y) && cP.nmb == CL) {
                    if(fromTop) {
                        bC.addLast(cP);
                        tS.removeLast();
                        fromTop = false;
                        bC.positioning();
                        return true;
                    } else {
                        for (BColumn bS : mY) {
                            for(Card cD : bS) {
                                if (cD.equals(cP)) {
                                    bC.addAll(bS.removeFrom(cP));
                                    bC.positioning();
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public void wipe() {
        tF.clear();
        dA.clear();
        tS.clear();
        for (BColumn m : mY) {
            m.clear();
        }
        for (TopDeck t : tD) {
            t.clear();
        }
        l.clear();
        init();
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
