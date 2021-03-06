/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datBar;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ScaleTest extends JPanel {
static JFrame window;
static JPanel mainPanel;

BufferedImage originalImage;
BufferedImage scaledImage;

ScaleTest() {
    
}
ScaleTest(String ffs) {
    //setPreferredSize(new Dimension(320, 200));
    try {
        originalImage = ImageIO.read(new File("C:\\Documents and Settings\\Administrator\\My Documents\\NetBeansProjects\\datBar\\src\\com\\datBar\\pics\\white3.jpg"));
    } catch(Exception e){}

    addComponentListener(new ResizerListener());
}
public BufferedImage getImage(String url){
    setPreferredSize(new Dimension(320, 200));
    try {
        originalImage = ImageIO.read(new File(url));
    } catch(Exception e){}

    addComponentListener(new ResizerListener());
    return originalImage;
}

public void resize() {
    double widthScaleFactor = getWidth() / (double)originalImage.getWidth();
    double heightScaleFactor = getHeight() / (double)originalImage.getHeight();
    double scaleFactor = (widthScaleFactor > heightScaleFactor)? heightScaleFactor : widthScaleFactor;

    AffineTransform at = new AffineTransform();
    at.scale(widthScaleFactor, heightScaleFactor);

    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    scaledImage = scaleOp.filter(originalImage, null);

    repaint();
}

@Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.drawImage(scaledImage, 0, 0, null);
}

public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run(){
            window = new JFrame("Scale Test");
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            mainPanel = new ScaleTest();
            window.getContentPane().add(mainPanel);

            window.pack();
            window.setLocationRelativeTo(null);  // positions window in center of screen
            window.setVisible(true);
        }
    });
}

class ResizerListener implements ComponentListener {
    @Override
    public void componentResized(ComponentEvent e) {
        resize();
    }

    @Override public void componentHidden(ComponentEvent e) {}
    @Override public void componentMoved(ComponentEvent e) {}
    @Override public void componentShown(ComponentEvent e) {}
}
}
