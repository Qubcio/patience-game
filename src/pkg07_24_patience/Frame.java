/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_24_patience;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author qubcio
 */
public class Frame extends Canvas {
    Main main;
    Frame(Main main) {
        this.main = main;
        JFrame frame = new JFrame("Patience");
        GraphicsDevice gD;
        GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gD = gE.getDefaultScreenDevice();
        frame.setResizable(false);
        //frame.setUndecorated(true);
        gD.setFullScreenWindow(frame);
        Main.MY_WIDTH = frame.getWidth();
        Main.MY_HEIGHT = frame.getHeight();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = new Container();
        c.setLayout(new BorderLayout());
        JButton b = new JButton("Restart");
        b.addActionListener(new MyAction());
        c.add(b, BorderLayout.SOUTH);
        c.add(main, BorderLayout.CENTER);
        main.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                main.wipe();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
            
        });
        frame.add(c);
        frame.setVisible(true);
    }
    class MyAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            main.wipe();
        }
        
    }
}
