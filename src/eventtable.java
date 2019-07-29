import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


class event{
    private String company;
    private String desc;
    private LocalDate date;

    public event(String comp, String description, String day){
        company = comp;
        desc = description;

        String[]temp = new String[3];
        int []newtemp = new int[3];
        temp = day.split("-");
        temp[2] = temp[2].substring(0,2);

        for(int i=0; i<temp.length; i++){
            newtemp[i] = Integer.parseInt(temp[i]);
        }

        date = LocalDate.of(newtemp[0], newtemp[1], newtemp[2]);
    }

    String getCompany(){
        return this.company;
    }

    String getDescription(){
        return this.desc;
    }

    LocalDate getDateOf(){
        return this.date;
    }
}

public class eventtable{
    private ArrayList<event>eventlist;
    public eventtable()
    {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://PATH");
            System.out.println("Connected Successfully");

            //Using sql select query
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Event_List");
            ResultSet resultSet = preparedStatement.executeQuery();
            eventlist = new ArrayList<>();
            while(resultSet.next()){
                String company = resultSet.getString("Ticker");
                String Event = resultSet.getString("Event Name");
                String date = resultSet.getString("Initiation_Date");
                event ev = new event(company, Event, date);
                eventlist.add(ev);
            }
        }catch(Exception e){
            System.out.println("Error: ");
            System.out.print(e);
        }


    }

    public String[] getInfo(int num){
        ArrayList<event>list = this.eventlist;
        String [] descriptions = new String[3];
        descriptions [0] = list.get(num).getCompany();
        descriptions [1] = list.get(num).getDateOf().toString();
        descriptions [2] = list.get(num).getDescription();

        return descriptions;
    }

    public boolean getDate(LocalDate date){
        ArrayList<event>list = this.eventlist;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getDateOf().equals(date)){
                return true;
            }
        }

        return false;
    }

    public int[] getCompanies(LocalDate date){
        ArrayList <event> list = this.eventlist;
        int counter = 0;
        int [] returnlist = new int[100];
        for (int i = 0; i<list.size(); i++){

            if (list.get(i).getDateOf().equals(date)){
             returnlist[counter] = i;
             counter ++;
            }
        }
        int [] newreturnlist = new int [counter];
        for(int i = 0; i<newreturnlist.length; i++){
            newreturnlist[i] = returnlist[i];
        }
        return newreturnlist;
    }
}
