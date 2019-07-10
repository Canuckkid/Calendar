package com.company;

public class Calender{

    public Date[] date;

    public Calender ()
    {
        date = new Date[365];

        for (int i = 0; i < 365; i++)
        {
            date[i] = new Date(i);
        }
    }
}

