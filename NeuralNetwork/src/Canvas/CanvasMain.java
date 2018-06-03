/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Canvas;

import javax.swing.JFrame;

/**
 *
 * @author Nicolas Benatti
 */
public class CanvasMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        Canvas cvs = Canvas.getInstance();
        
        cvs.setThicknessX(10);
        cvs.setThicknessY(10);
        cvs.setLocationRelativeTo(null);
        cvs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cvs.pack();
        cvs.setVisible(true);
    }
    
}
