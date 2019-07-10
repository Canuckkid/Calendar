package com.company;

public class Event {

    private String ticker;
    private String event;
    private boolean primary;
    private int date;

    public Event (String tik, String ev, boolean pri, int da)
    {
        ticker = tik;
        event = ev;
        primary = pri;
        date = da;
    }
}
