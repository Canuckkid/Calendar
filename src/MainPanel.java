//Julien Lin 2019
//Calendar table
//github.com/canuckkid

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public final class MainPanel extends JPanel {
  public final LocalDate realLocalDate = LocalDate.now();
  private final JLabel dateLabel = new JLabel(realLocalDate.toString(), SwingConstants.CENTER);
  private final JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
  private final JTable monthTable = new JTable();
  private LocalDate currentLocalDate;
  private eventtable eventlist;

  public LocalDate getCurrentLocalDate() {
    return currentLocalDate;
  }

  private MainPanel() {
    super(new BorderLayout());

    monthTable.setDefaultRenderer(LocalDate.class, new CalendarTableRenderer());
    monthTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    monthTable.setCellSelectionEnabled(true);
    monthTable.setRowHeight(20);
    monthTable.setFillsViewportHeight(true);

    eventlist = new eventtable();

    JTableHeader header = monthTable.getTableHeader();
    header.setResizingAllowed(false);
    header.setReorderingAllowed(false);
    ((JLabel) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

    ListSelectionListener selectionListener = e -> {
      if (!e.getValueIsAdjusting()) {
        LocalDate ld = (LocalDate) monthTable.getValueAt(monthTable.getSelectedRow(), monthTable.getSelectedColumn());
        dateLabel.setText(ld.toString());
      }
    };

    monthTable.getSelectionModel().addListSelectionListener(selectionListener);
    monthTable.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        if (e.getClickCount() == 2) {
          JTable target = (JTable) e.getSource();
          int row = target.getSelectedRow();
          int column = target.getSelectedColumn();
          GridLayout gridlayout = new GridLayout(3, 2);
          LocalDate ld = (LocalDate) monthTable.getValueAt(monthTable.getSelectedRow(), monthTable.getSelectedColumn());
          String [] lol = new String[3];
          if(eventlist.getDate(ld)){
            int [] uselist = eventlist.getCompanies(ld);
            System.out.print("good");
            for(int i = 0; i < uselist.length; i++){
              String [] move = eventlist.getInfo(uselist[i]);
              for(int x = 0; x < 3; x++){
                lol[x] = move[x];
              }
            }
          }
          else{
            lol = new String[3];
            for(int x = 0; x < 3; x++){
                lol[x] = "";
            }
          }

          JPanel displaypanel = new JPanel();
          displaypanel.setLayout(gridlayout);

          JLabel companylabel = new JLabel("Company:", JLabel.CENTER);
          JLabel companylabel2 = new JLabel(lol[0]);
          JLabel datelabel = new JLabel("Date:", JLabel.CENTER);
          JLabel datelabel2 = new JLabel (lol[1]);
          JLabel descriptionlabel = new JLabel ("Description:", JLabel.CENTER);
          JLabel descriptionlabel2 = new JLabel (lol[2]);

          displaypanel.add(companylabel);
          displaypanel.add(companylabel2);
          displaypanel.add(datelabel);
          displaypanel.add(datelabel2);
          displaypanel.add(descriptionlabel);
          displaypanel.add(descriptionlabel2);


          JFrame full = new JFrame();
          full.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
          full.add(displaypanel);
          full.pack();
          full.setLocationRelativeTo(null);
          full.setVisible(true);
        }
      }
    });
    monthTable.getColumnModel().getSelectionModel().addListSelectionListener(selectionListener);

    updateMonthView(realLocalDate);

    JButton prev = new JButton("<");
    prev.addActionListener(e -> updateMonthView(getCurrentLocalDate().minusMonths(1)));

    JButton next = new JButton(">");
    next.addActionListener(e -> updateMonthView(getCurrentLocalDate().plusMonths(1)));

    JPanel p = new JPanel(new BorderLayout());
    p.add(monthLabel);
    p.add(prev, BorderLayout.WEST);
    p.add(next, BorderLayout.EAST);

    add(p, BorderLayout.NORTH);
    add(new JScrollPane(monthTable));
    add(dateLabel, BorderLayout.SOUTH);
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setPreferredSize(new Dimension(320, 240));
  }

  public void updateMonthView(LocalDate localDate) {
    currentLocalDate = localDate;
    monthLabel.setText(localDate.format(DateTimeFormatter.ofPattern("YYYY / MM").withLocale(Locale.getDefault())));
    monthTable.setModel(new CalendarViewTableModel(localDate));
  }

  private class CalendarTableRenderer extends DefaultTableCellRenderer {
    @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
      super.getTableCellRendererComponent(table, value, selected, focused, row, column);
      setHorizontalAlignment(SwingConstants.CENTER);
      if (value instanceof LocalDate) {
        LocalDate d = (LocalDate) value;
        setText(Objects.toString(d.getDayOfMonth()));
        if (YearMonth.from(d).equals(YearMonth.from(getCurrentLocalDate()))) {
          setForeground(Color.BLACK);
        } else {
          setForeground(Color.GRAY);
        }
        DayOfWeek dow = d.getDayOfWeek();
        if (d.isEqual(realLocalDate)) {
          setBackground(new Color(220, 255, 220));
        } else if(eventlist.getDate(d)){
          setBackground(new Color(220, 220, 120));
        } else if (dow == DayOfWeek.SUNDAY) {
          setBackground(new Color(255, 220, 220));
        } else if (dow == DayOfWeek.SATURDAY) {
          setBackground(new Color(220, 220, 255));
        } else {
          setBackground(Color.WHITE);
        }
      }
      return this;
    }
  }

  public static void main(String... args) {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        createAndShowGui();
      }
    });
  }

  public static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
    }
    JFrame frame = new JFrame("CalendarViewTable");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MainPanel());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}

class CalendarViewTableModel extends DefaultTableModel {
  private final LocalDate startDate;
  private final WeekFields weekFields = WeekFields.of(Locale.getDefault());

  protected CalendarViewTableModel(LocalDate date) {
    super();
    LocalDate firstDayOfMonth = YearMonth.from(date).atDay(1); // date.with(TemporalAdjusters.firstDayOfMonth());
    // int dowv = firstDayOfMonth.get(WeekFields.SUNDAY_START.dayOfWeek()) - 1;
    int dowv = firstDayOfMonth.get(weekFields.dayOfWeek()) - 1;
    startDate = firstDayOfMonth.minusDays(dowv);
  }

  @Override public Class<?> getColumnClass(int column) {
    return LocalDate.class;
  }

  @Override public String getColumnName(int column) {
    return weekFields.getFirstDayOfWeek().plus(column)
      .getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault());
  }

  @Override public int getRowCount() {
    return 6;
  }

  @Override public int getColumnCount() {
    return 7;
  }

  @Override public Object getValueAt(int row, int column) {
    return startDate.plusDays(row * getColumnCount() + column);
  }

  @Override public boolean isCellEditable(int row, int column) {
    return false;
  }
}
