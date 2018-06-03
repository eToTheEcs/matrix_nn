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
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 * this class uses "singleton" design pattern
 * @author Nicolas Benatti
 */
public class Canvas extends JFrame implements MouseMotionListener {

    private static Canvas instance = null;
    
    private int thicknessX, thicknessY;
    
    private List<Pair<Integer, Integer>> pts = null;   // all the points drawen by the user
    private ArrayList<Pair<Integer, Integer>> boundingBoxCoords = null;
    
    private Canvas() {
        
        pts = new LinkedList<>();
        thicknessX = thicknessY = 5;
        
        //addMouseMotionListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(600, 480));
        
        this.boundingBoxCoords = new ArrayList<>();
    }
    
    public static Canvas getInstance() {
        if(instance == null)
            instance = new Canvas();
        
        return instance;
    }
    
    @Override
    public void paint(Graphics g) {
        
        //System.out.println("I painted");
        
        for(Pair it : pts)
            g.fillOval((int)it.getFirst(), (int)it.getSecond(), thicknessX, thicknessY);
        
        this.drawBoundingBox(g);
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
        //System.out.println("you clicked the mouse");

        pts.add(new Pair<>(me.getX(), me.getY()));
        this.repaint();
        
        System.out.println("POINTS => " + pts);
        
        boundingBoxCoords = this.getBoundingPoints();
        
        System.out.println("BOUNDING BOX => " + boundingBoxCoords);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //System.out.println("mouse in pressed state, position: ("+me.getX()+", "+me.getY()+")");
        
        pts.add(new Pair<>(me.getX(), me.getY()));
        this.repaint();
        
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
        
        System.out.println(pts);
        
        //boundingBoxCoords = this.getBoundingPoints();
        
        //System.out.println("BOUNDING BOX => " + boundingBoxCoords);
        
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //System.out.println("mouse in moved state");
        //repaint();
    }
       
    /**
     * finds the coordinates of the bounding box around the handwrittem item
     *     v[0]   [0]   v[1]
     *     +--------------+
     *     |              |
     *     |              |
     * [3] |              | [1]
     *     |              |
     *     |              |
     *     +--------------+
     *     v[3]   [2]   v[2]
     * @return the 4 corners of the bounding box 
     */
    public ArrayList<Pair<Integer, Integer>> getBoundingPoints() {
        
         int i;
        
        ArrayList<Integer> boundingCoords = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> boundingPts = new ArrayList<>();
        
        for(i = 0; i < 4; ++i)  boundingCoords.add(-1);
        for(i = 0; i < 4; ++i)  boundingPts.add(new Pair<>(-1, -1));
        
        boundingCoords.set(0, Integer.MAX_VALUE);
        boundingCoords.set(1, Integer.MIN_VALUE);
        boundingCoords.set(2, Integer.MIN_VALUE);
        boundingCoords.set(3, Integer.MAX_VALUE);
        
        // find upper y coordinate
        
        for(Pair<Integer, Integer> it : this.pts) {
            
            if(it.getSecond() < boundingCoords.get(0)) {
                boundingCoords.set(0, it.getSecond());
            }
        }
        
        // find lower y coordinate
        
        for(Pair<Integer, Integer> it : this.pts) {
            
            if(it.getSecond() > boundingCoords.get(2)) {
                boundingCoords.set(2, it.getSecond());
            }
        }
        
        // find right x coordinate
        
        for(Pair<Integer, Integer> it : this.pts) {
            
            if(it.getFirst() > boundingCoords.get(1)) {
                boundingCoords.set(1, it.getFirst());
            }
        }
        
        // find left x coordinate
        
        for(Pair<Integer, Integer> it : this.pts) {
            
            if(it.getFirst() < boundingCoords.get(3)) {
                boundingCoords.set(3, it.getFirst());
            }
        }
        
        //System.out.println("{"+boundingCoords+"}");
        
        boundingPts.set(0, new Pair<>(boundingCoords.get(3), boundingCoords.get(0)));
        boundingPts.set(1, new Pair<>(boundingCoords.get(1), boundingCoords.get(0)));
        boundingPts.set(2, new Pair<>(boundingCoords.get(1), boundingCoords.get(2)));
        boundingPts.set(3, new Pair<>(boundingCoords.get(3), boundingCoords.get(2)));
        
        return boundingPts;
    }
    
    /**
     * actually draw the bounding box around the pattern
     */
    public void drawBoundingBox(Graphics g) {
        
        // only compute the box when is needed
        boundingBoxCoords = this.getBoundingPoints();
        
        if(boundingBoxCoords.size() != 0) {
            for(int i = 0; i < 4; ++i)
                g.drawLine(boundingBoxCoords.get(i).getFirst(), boundingBoxCoords.get(i).getSecond(), 
                           boundingBoxCoords.get((i+1) % 4).getFirst(), boundingBoxCoords.get((i+1) % 4).getSecond());
        }
    }
}
