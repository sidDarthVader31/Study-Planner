package Model;

import java.io.Serializable;
import java.util.Calendar;

public class Target implements Serializable {

    private String topic; //to store the name of the topic user wants to enter
    private int id; // stores the id of the entry in database
    private String dateAdded; // stores the date at which the item was added
    private String timeAdded; // stores the time at which the item was added
    private int finishHour;
    private int finishMinute;// stores the target time provided by the user
    private int finishDate;// stores the target date provided by the user
    private int finishMonth;
    private int finishYear;


    private int completionStatus; // stores the status of the task whether it was completed or not
    /* this variable completionStatus stores value true(1) if user marks the task as complete else stores false(0)
    * this variable is rendered null until the time of task completion is elapsed.
    * For this I am using another variable due which checks whether the elapsed time for the task has passed or not.
    * If yes then we update the entry for completionStatus as true or false depending on the user input
    *
    */

    private int due;// flags true(1) if the time to finish the elapsed task has passed


    /*
    * for the variable completionStatus and due we are using integer type to store value as 1 for true and 0 for false
    * The reason for this is there are no boolean type column available in sqLite Database.
    * So we will implement our logic with 0 and 1
    * TODO: Find out if possible a better alternative for this logic.
     */
    public Target() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(String timeAdded) {
        this.timeAdded = timeAdded;
    }



    public int getFinishDate() {

        return finishDate;
    }

    public void setFinishDate(int finishDate) {

        this.finishDate = finishDate;
    }

    public int getCompletionStatus() {

        return completionStatus;
    }

    public void setCompletionStatus(int completionStatus) {
        this.completionStatus = completionStatus;
    }

    public int getDue() {
        /*
        Calendar currentTime=Calendar.getInstance();
        Calendar completionTime=Calendar.getInstance();

        completionTime.set(getFinishYear()
                ,getFinishMonth(),getFinishDate(),getFinishHour(),getFinishMinute(),0);
        if (completionTime.getTimeInMillis() - currentTime.getTimeInMillis() > 0) {
            due=0;
        }
        else{
            due=1;
        }*/
        return due;

    }

    public void setDue(int due) {
        this.due=due;
    }

    public int getFinishHour() {

        return finishHour;
    }

    public void setFinishHour(int finishHour) {
        this.finishHour = finishHour;
    }

    public int getFinishMinute() {
        return finishMinute;
    }

    public void setFinishMinute(int finishMinute) {
        this.finishMinute = finishMinute;
    }

    public int getFinishMonth() {
        return finishMonth;
    }

    public void setFinishMonth(int finishMonth) {
        this.finishMonth = finishMonth;
    }

    public int getFinishYear() {
        return finishYear;
    }

    public void setFinishYear(int finishYear) {
        this.finishYear = finishYear;
    }

}
