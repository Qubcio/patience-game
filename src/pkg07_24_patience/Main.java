/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg07_24_patience.game.Game;

/**
 *
 * @author qubcio
 */
public class Main extends Canvas implements Runnable {
    public double fps = 0;
    public static int MY_WIDTH = 0;
    public static int MY_HEIGHT = 0;
    public Font font;
    private Game game;
    /**
     * @param args the command line arguments
     */
    
    
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", "CallToThreadStartDuringObjectConstruction"})
    Main() {
        font = new Font("Arial", 1, 20);
        new Frame(this);
        game = new Game(this);
        this.addMouseListener(game);
        new Thread(this).start();
    }
    
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new Main();
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        long startLoop;
        double targetFPS = 60.0;
        long newFrame;
        double counter;
        while(true) {
            startLoop = System.currentTimeMillis();
            counter = 0;
            while(true){
                counter++;
                newFrame = System.currentTimeMillis();
                try{
                    render();
                } catch(Exception e){
                }
                tick();
                double different = ((double)(System.currentTimeMillis() - newFrame))/1000;
                if(different < 1.0/targetFPS) {
                    
                    try {
                        Thread.sleep((long)((1.0/targetFPS - different)*1000));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                if(counter >= targetFPS) {
                    fps = ((double)((int)((counter/(((double)(System.currentTimeMillis() - startLoop))/1000))*10)))/10;
                    break;
                }
            }
            sec();
        }
    }
    private synchronized void render() {
        BufferStrategy bS = this.getBufferStrategy();
        if(bS == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bS.getDrawGraphics();
        bG(g);
        game.render(g);
        g.dispose();
        bS.show();
    }

    private synchronized void tick() {
    }

    private void sec() {
    }
    
    private void bG(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, MY_WIDTH, MY_HEIGHT);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("FPS : " + Double.toString(fps), MY_WIDTH - 125, 30);
    }

    void wipe() {
        game.wipe();
    }
    
}
