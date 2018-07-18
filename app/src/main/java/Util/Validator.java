package Util;
import java.util.Calendar;

public class Validator {
    public Validator() {
    }

    public boolean validateDateTime(int year,int month,int day,int hour,int minutes){
        Calendar calendar=Calendar.getInstance().getInstance();
        Calendar timeToValidate=Calendar.getInstance();
        timeToValidate.set(year,month,day,hour,minutes,0);
        if (timeToValidate.getTimeInMillis()-calendar.getTimeInMillis()>(3600000)){
            return true;
        }
        else {
            return false;
        }
    }
}
