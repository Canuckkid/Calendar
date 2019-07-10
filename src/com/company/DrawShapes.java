package com.company;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.YearMonth;
import java.util.Calendar;
import java.awt.*;

import javax.swing.*;

public class DrawShapes extends JFrame {

    Calendar c = Calendar.getInstance();
    JLabel month = new JLabel(new SimpleDateFormat("MMMM").format(c.getTime()) +  " " + new SimpleDateFormat("YYYY").format(c.getTime()));

   public DrawShapes()
   {
       setSize(new Dimension (600, 630));
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setVisible(true);
       setResizable(false);

       month.setMinimumSize(new Dimension(400, 50));
       month.setPreferredSize(new Dimension(400, 50));
       month.setMaximumSize(new Dimension(400, 50));
       month.setHorizontalAlignment(JLabel.CENTER);

       JButton nextButton = new JButton("Next");
       nextButton.addActionListener(this::nextMonthListener);

       JButton lastButton = new JButton("Last");
       lastButton.addActionListener(this::lastMonthListener);
       c.set(c.DAY_OF_MONTH, 1);

       JPanel p = new JPanel()
       {
           @Override
           public void paintComponent(Graphics g)
           {
               Graphics2D g2 = (Graphics2D) g;
               Shape rect = new Rectangle(0, 0, 588, 84);
               g2.draw(rect);


               int counter = 0;
               int dayofweek = c.get(c.DAY_OF_WEEK);
               YearMonth yearmonthobject = YearMonth.of(c.get(c.YEAR), c.get(c.MONTH) + 1);

               int numberofdays = yearmonthobject.lengthOfMonth();

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
       p.add(lastButton);
       p.add(month);
       p.add(nextButton);
       setTitle("Calendar");
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

    public void nextMonthListener(ActionEvent event){
       c.add(Calendar.MONTH, 1);
       month.setText(new SimpleDateFormat("MMMM").format(c.getTime()) +  " " + new SimpleDateFormat("YYYY").format(c.getTime()));
        rootPane.revalidate();
        rootPane.repaint();
    }

    public void lastMonthListener (ActionEvent event){
       c.add(Calendar.MONTH, -1);
        month.setText(new SimpleDateFormat("MMMM").format(c.getTime()) +  " " + new SimpleDateFormat("YYYY").format(c.getTime()));
       rootPane.revalidate();
       rootPane.repaint();
    }
}
