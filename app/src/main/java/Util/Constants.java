package Util;

public class Constants {


    /*
    * This class is used to store all the database related constants that we will use in our database handler class.
    * All the tables names, column names are declared here as constants.
     */
    public static final int DB_VERSION=1;
    public static final String DB_NAME="TargetDB";
    public static final String TABLE_NAME="TargetDetailsTable";

    //Defining columns
    public static final String KEY_ID="_id";
    public static final String KEY_TOPIC="topic";
    public static final String KEY_DATE_ADDED="dateAdded";
    public static final String KEY_TIME_ADDED="timeAdded";
    public static final String KEY_FINISH_DATE="finishDate";
    public static final String KEY_FINISH_MONTH="finishMonth";
    public static final String KEY_FINISH_YEAR="finishYear";
    public static final String KEY_FINISH_HOURS="finishHour";
    public static final String KEY_FINISH_MINUTES="finishMinutes";
    public static final String KEY_COMPLETION_STATUS="CompletionStatus";
    public static final String KEY_DUE="due";


}
