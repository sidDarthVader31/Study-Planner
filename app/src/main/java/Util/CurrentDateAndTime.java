package Util;

import java.util.Calendar;

public class CurrentDateAndTime {
    Calendar c;

    public CurrentDateAndTime(Calendar c) {
        this.c = c;
    }

    public int getCurrentYear(){
        return  c.get(Calendar.YEAR);
    }
    public int getCurrentMonth(){
        return  c.get(Calendar.MONTH);
    }
    public int getCurrentDay(){
        return c.get(Calendar.DAY_OF_MONTH);
    }
    public int getCurrentHour(){
        return c.get(Calendar.HOUR_OF_DAY);
    }
    public int getCurrentMinute(){
        return c.get(Calendar.MINUTE);
    }

}
