package com.riontech.calendar.utils;

import java.text.SimpleDateFormat;


/**
 * Created by Dhaval Soneji on 26/1/16.
 */
public class CalendarUtils {
    private static final String TAG = CalendarUtils.class.getSimpleName();
    private static final String CALENDAR_DB_FORMAT = "yyyy-MM-dd";
    private static final String CALENDAR_DATE_FORMAT = "MMM dd yyyy";
    private static final String CALENDAR_MONTH_TITLE_FORMAT = "MMMM yyyy";
    private static final String[] NAMES = {"Alex", "John", "Dwayne"};
    private static final String[] EVENTS = {"Task", "Birthday", "Events"};
    private static final String[] EVENTS_DESCRIPTION = {"Prepare Presentation", "Wish Him on his Birthday"
            , "@ Some Place"};

    private String name;
    private String time;
    private String room;

    public CalendarUtils(String name, String time, String room) {
        this.name = name;
        this.time = time;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }


    public static SimpleDateFormat getCalendarDBFormat() {
        return new SimpleDateFormat(CALENDAR_DB_FORMAT);
    }

    public static SimpleDateFormat getCalendarDateFormat() {
        return new SimpleDateFormat(CALENDAR_DATE_FORMAT);
    }

    public static String getCalendarMonthTitleFormat(){
        return CALENDAR_MONTH_TITLE_FORMAT;
    }

    public static String[] getNAMES() {
        return NAMES;
    }

    public static String[] getEVENTS() {
        return EVENTS;
    }

    public static String[] getEventsDescription() {
        return EVENTS_DESCRIPTION;
    }
}
