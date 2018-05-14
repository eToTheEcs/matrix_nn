/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 * this class implements singleton design pattern
 * @author Nicolas Benatti
 */
public class Canvas extends JFrame implements MouseMotionListener {

    private static Canvas instance = null;
    
    //private int mouseX, mouseY;
    private int thicknessX, thicknessY;
    
    private List<Pair<Integer, Integer>> pts;   // all the points drawen by the user
    
    private Canvas() {
        
        pts = new LinkedList<Pair<Integer, Integer>>();
        thicknessX = thicknessY = 5;
        
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(600, 480));
    }
    
    public static Canvas getInstance() {
        if(instance == null)
            instance = new Canvas();
        
        return instance;
    }
    
    @Override
    public void paint(Graphics g) {
        
        super.paint(g);
        System.out.println("i painted");
        //g.fillOval(mouseX, mouseY, thicknessX, thicknessY);
        
        for(Pair it : pts)
            g.fillOval((int)it.getFirst(), (int)it.getSecond(), thicknessX, thicknessY);
    }

    public int getThicknessX() {
        return thicknessX;
    }

    public int getThicknessY() {
        return thicknessY;
    }

    public void setThicknessX(int thicknessX) {
        this.thicknessX = thicknessX;
    }

    public void setThicknessY(int thicknessY) {
        this.thicknessY = thicknessY;
    }
    
    /*@Override
    public void mouseClicked(MouseEvent me) {
        System.out.println("you clicked the mouse");
        //mouseX = me.getX();
        //mouseY = me.getY();
        pts.add(new Pair<Integer, Integer>(me.getX(), me.getY()));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        System.out.println("mouse in pressed state, position: ("+me.getX()+", "+me.getY()+")");
        pts.add(new Pair<Integer, Integer>(me.getX(), me.getY()));
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}*/

    @Override
    public void mouseDragged(MouseEvent me) {
        System.out.println("mouse in dragged state");
        
        pts.add(new Pair<Integer, Integer>(me.getX(), me.getY()));
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        System.out.println("mouse in moved state");
    }
}
