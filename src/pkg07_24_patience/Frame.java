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
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author qubcio
 */
public class Frame extends Canvas {
    Frame(Main main) {
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
        b.addActionListener((ActionEvent e) -> {
            main.wipe();
        });
        c.add(b, BorderLayout.SOUTH);
        c.add(main, BorderLayout.CENTER);
        frame.add(c);
        frame.setVisible(true);
    }
}
