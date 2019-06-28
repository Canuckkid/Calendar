package com.company;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.time.YearMonth;
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
               Calendar c = Calendar.getInstance();
               c.set(2019, Calendar.JUNE, 1);

               int counter = 0;
               int dayofweek = c.get(c.DAY_OF_WEEK);
               YearMonth yearmonthobject = YearMonth.of(c.get(c.YEAR), c.get(c.MONTH) + 1);

               int numberofdays = yearmonthobject.lengthOfMonth();

               System.out.print(c.get(c.YEAR));

               for (int i = dayofweek-1; i < 7; i++)
               {
                   counter++;
                   g2.drawString(String.valueOf(counter), i * 84 + 3, 168);
               }

               for (int i = 0; i < 6; i++)
               {
                   for (int a = 0; a < 7; a++) {
                       rect = new Rectangle(a * 84, 84 * (i + 1), 84, 84);
                       g2.draw(rect);
                   }
                   for (int a = 0; a < 7; a++)
                   {
                       if (counter < numberofdays)
                       {
                           counter++;
                           g2.drawString(String.valueOf(counter), a * 84 + 3, 84 * (i + 3));
                       }
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
