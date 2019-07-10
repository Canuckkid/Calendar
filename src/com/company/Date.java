package com.company;

public class Date{

    private Event[] events;
    private int num;

    public Date (int day)
    {
        num = day;
    }

    public void addEvent(Event e)
    {
        events [0] = e;
    }

}
