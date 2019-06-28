package com.company;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Calendar;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DrawShapes extends JFrame {

   public DrawShapes()
   {
       setSize(new Dimension (600, 630));
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setVisible(true);

       JPanel p = new JPanel()
       {
           @Override
           public void paintComponent(Graphics g)
           {
               Graphics2D g2 = (Graphics2D) g;
               Shape rect = new Rectangle(0, 0, 588, 84);
               g2.draw(rect);
               for (int i = 0; i < 6; i++)
               {
                   for (int a = 0; a < 7; a++)
                   {
                      rect = new Rectangle(a*84, 84*(i+1), 84, 84);
                      g2.draw(rect);
                   }
               }
           }
       };
       setTitle("My Shapes");
       this.getContentPane().add(p);
   }

    public static void main(String arg[]) {
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                new DrawShapes();
            }
        });
    }
}
