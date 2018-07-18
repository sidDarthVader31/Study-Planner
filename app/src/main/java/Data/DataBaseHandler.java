package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import Model.Target;
import Util.Constants;

public class DataBaseHandler extends SQLiteOpenHelper {
    Context context;
    public DataBaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + Constants.TABLE_NAME + "( " +
                Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Constants.KEY_TOPIC + " TEXT  ," +
                Constants.KEY_DATE_ADDED + " LONG , " + Constants.KEY_TIME_ADDED + " LONG ," +
                Constants.KEY_FINISH_DATE + " LONG , " + Constants.KEY_FINISH_MONTH + " LONG , " + Constants.KEY_FINISH_YEAR + " LONG ," +
                Constants.KEY_FINISH_HOURS + " LONG ," +
                Constants.KEY_FINISH_MINUTES + " LONG , " +
                Constants.KEY_COMPLETION_STATUS + " NUMBER, " + Constants.KEY_DUE + " NUMBER);";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    /*
    All the helper methods will now we defined
    CRUD OPERATIONS
     */
    //Method to add a target item to the database
    public void addTarget(Target target) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TOPIC, target.getTopic());
        values.put(Constants.KEY_DATE_ADDED, target.getDateAdded());
        values.put(Constants.KEY_TIME_ADDED, target.getTimeAdded());
        values.put(Constants.KEY_FINISH_DATE, target.getFinishDate());
        values.put(Constants.KEY_FINISH_MONTH, target.getFinishMonth());
        values.put(Constants.KEY_FINISH_YEAR, target.getFinishYear());
        values.put(Constants.KEY_FINISH_HOURS, target.getFinishHour());
        values.put(Constants.KEY_FINISH_MINUTES, target.getFinishMinute());
        values.put(Constants.KEY_COMPLETION_STATUS, target.getCompletionStatus());
        values.put(Constants.KEY_DUE, target.getDue());
        db.insert(Constants.TABLE_NAME, null, values);
        db.close();
    }
    //Getting one target item from database
    public Target getTarget(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.KEY_TOPIC, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED,
                        Constants.KEY_FINISH_DATE, Constants.KEY_FINISH_MONTH, Constants.KEY_FINISH_YEAR, Constants.KEY_FINISH_HOURS, Constants.KEY_FINISH_MINUTES, Constants.KEY_COMPLETION_STATUS, Constants.KEY_DUE}
                , Constants.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Target target = new Target();
        target.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        target.setTopic(cursor.getString(cursor.getColumnIndex(Constants.KEY_TOPIC)));
        target.setDateAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)));
        target.setTimeAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_TIME_ADDED)));
        target.setFinishDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_DATE))));
        target.setFinishMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MONTH))));
        target.setFinishYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_YEAR))));
        target.setFinishHour(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_HOURS))));
        target.setFinishMinute(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MINUTES))));
        target.setCompletionStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_COMPLETION_STATUS))));
        target.setDue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DUE))));
        return target;
    }

    //Getting all targets  items from database
    //Will store all targets in an arralist and return that arraylist
    public List<Target> getAllTargets() {
        SQLiteDatabase db = getReadableDatabase();
        List<Target> targetList = new ArrayList<>();

        //Select all contacts
        String SELECT_ALL = "SELECT * FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(SELECT_ALL, null, null);

        //loop through the cursor to get all contacts
        if (cursor.moveToFirst()) {
            do {
                Target target = new Target();
                target.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                target.setTopic(cursor.getString(cursor.getColumnIndex(Constants.KEY_TOPIC)));
                target.setDateAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)));
                target.setTimeAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_TIME_ADDED)));
                target.setFinishDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_DATE))));
                target.setFinishMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MONTH))));
                target.setFinishYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_YEAR))));
                target.setFinishHour(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_HOURS))));
                target.setFinishMinute(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MINUTES))));
                target.setCompletionStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_COMPLETION_STATUS))));
                target.setDue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DUE))));
                //adding target item to targetList
                targetList.add(target);
            } while (cursor.moveToNext());
        }
        return targetList;
    }

    //Method to update a target item
    public int updateTarget(Target target) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TOPIC, target.getTopic());
        values.put(Constants.KEY_DATE_ADDED, target.getDateAdded());
        values.put(Constants.KEY_TIME_ADDED, target.getTimeAdded());
        values.put(Constants.KEY_FINISH_DATE, target.getFinishDate());
        values.put(Constants.KEY_FINISH_MONTH, target.getFinishMonth());
        values.put(Constants.KEY_FINISH_YEAR, target.getFinishYear());
        values.put(Constants.KEY_FINISH_HOURS, target.getFinishHour());
        values.put(Constants.KEY_FINISH_MINUTES, target.getFinishMinute());
        values.put(Constants.KEY_COMPLETION_STATUS, target.getCompletionStatus());
        values.put(Constants.KEY_DUE, target.getDue());
        //UPDATING THE ROW
        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + " =?", new String[]{String.valueOf(target.getId())});
    }

    public int updateDueStatusOnNotificaton(int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        Target target = getTarget(id);
        target.setCompletionStatus(0);
        target.setDue(1);
        values.put(Constants.KEY_TOPIC, target.getTopic());
        values.put(Constants.KEY_DATE_ADDED, target.getDateAdded());
        values.put(Constants.KEY_TIME_ADDED, target.getTimeAdded());
        values.put(Constants.KEY_FINISH_DATE, target.getFinishDate());
        values.put(Constants.KEY_FINISH_MONTH, target.getFinishMonth());
        values.put(Constants.KEY_FINISH_YEAR, target.getFinishYear());
        values.put(Constants.KEY_FINISH_HOURS, target.getFinishHour());
        values.put(Constants.KEY_FINISH_MINUTES, target.getFinishMinute());
        values.put(Constants.KEY_COMPLETION_STATUS, target.getCompletionStatus());
        values.put(Constants.KEY_DUE,target.getDue());
        //UPDATING THE ROW
        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + " =?", new String[]{String.valueOf(id)});
    }

    public int updateCompletionStatus(int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        Target target = getTarget(id);
        target.setCompletionStatus(1);
        target.setDue(1);
        values.put(Constants.KEY_TOPIC, target.getTopic());
        values.put(Constants.KEY_DATE_ADDED, target.getDateAdded());
        values.put(Constants.KEY_TIME_ADDED, target.getTimeAdded());
        values.put(Constants.KEY_FINISH_DATE, target.getFinishDate());
        values.put(Constants.KEY_FINISH_MONTH, target.getFinishMonth());
        values.put(Constants.KEY_FINISH_YEAR, target.getFinishYear());
        values.put(Constants.KEY_FINISH_HOURS, target.getFinishHour());
        values.put(Constants.KEY_FINISH_MINUTES, target.getFinishMinute());
        values.put(Constants.KEY_COMPLETION_STATUS, target.getCompletionStatus());
        values.put(Constants.KEY_DUE, target.getDue());
        //UPDATING THE ROW
        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + " =?", new String[]{String.valueOf(id)});
    }

    //Deleting a target item
    public void DeleteTarget(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null, null);
        return cursor.getCount();
    }

    public Target getLastItem() {
        SQLiteDatabase db = getReadableDatabase();
        String QUERY = "SELECT * FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(QUERY, null, null);
        if (cursor != null)
            cursor.moveToLast();
        Target target = new Target();
        target.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        target.setTopic(cursor.getString(cursor.getColumnIndex(Constants.KEY_TOPIC)));
        target.setDateAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)));
        target.setTimeAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_TIME_ADDED)));
        target.setFinishDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_DATE))));
        target.setFinishMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MONTH))));
        target.setFinishYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_YEAR))));
        target.setFinishHour(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_HOURS))));
        target.setFinishMinute(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MINUTES))));
        target.setCompletionStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_COMPLETION_STATUS))));
        target.setDue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DUE))));
        return target;
    }

    public List<Target> getAllCurrentTargets() {
        SQLiteDatabase db = getReadableDatabase();
        List<Target> targetList = new ArrayList<>();
        //Select all contacts
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_TOPIC, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED, Constants.KEY_FINISH_YEAR, Constants.KEY_FINISH_MONTH, Constants.KEY_FINISH_DATE, Constants.KEY_FINISH_HOURS, Constants.KEY_FINISH_MINUTES, Constants.KEY_COMPLETION_STATUS, Constants.KEY_DUE},
                Constants.KEY_DUE + "=?" + " AND " + Constants.KEY_COMPLETION_STATUS + "=?", new String[]{String.valueOf(0), String.valueOf(0)}, null, null, null);
        //loop through the cursor to get all contacts

        if (cursor.moveToFirst()) {
            do {
                Target target = new Target();
                target.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                target.setTopic(cursor.getString(cursor.getColumnIndex(Constants.KEY_TOPIC)));
                target.setDateAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)));
                target.setTimeAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_TIME_ADDED)));
                target.setFinishDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_DATE))));
                target.setFinishMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MONTH))));
                target.setFinishYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_YEAR))));
                target.setFinishHour(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_HOURS))));
                target.setFinishMinute(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MINUTES))));
                target.setCompletionStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_COMPLETION_STATUS))));
                target.setDue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DUE))));

                //adding target item to targetList
                targetList.add(target);
            } while (cursor.moveToNext());
        }
        return targetList;
    }

    public List<Target> getAllCompletedTargets() {
        SQLiteDatabase db = getReadableDatabase();
        List<Target> targetList = new ArrayList<>();

        //Select all contacts
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_TOPIC, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED, Constants.KEY_FINISH_YEAR, Constants.KEY_FINISH_MONTH, Constants.KEY_FINISH_DATE, Constants.KEY_FINISH_HOURS, Constants.KEY_FINISH_MINUTES, Constants.KEY_COMPLETION_STATUS, Constants.KEY_DUE},
                Constants.KEY_COMPLETION_STATUS + "=?", new String[]{String.valueOf(1)}, null, null, null);
        //loop through the cursor to get all contacts

        if (cursor.moveToFirst()) {
            do {
                Target target = new Target();
                target.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                target.setTopic(cursor.getString(cursor.getColumnIndex(Constants.KEY_TOPIC)));
                target.setDateAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)));
                target.setTimeAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_TIME_ADDED)));
                target.setFinishDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_DATE))));
                target.setFinishMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MONTH))));
                target.setFinishYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_YEAR))));
                target.setFinishHour(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_HOURS))));
                target.setFinishMinute(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MINUTES))));
                target.setCompletionStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_COMPLETION_STATUS))));
                target.setDue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DUE))));
                 //adding target item to targetList
                targetList.add(target);
            } while (cursor.moveToNext());
        }
        return targetList;
    }

    public List<Target> getAllIncompleteTargets() {
        SQLiteDatabase db = getReadableDatabase();
        List<Target> targetList = new ArrayList<>();

        //Select all contacts
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_TOPIC, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED, Constants.KEY_FINISH_YEAR, Constants.KEY_FINISH_MONTH, Constants.KEY_FINISH_DATE, Constants.KEY_FINISH_HOURS, Constants.KEY_FINISH_MINUTES, Constants.KEY_COMPLETION_STATUS, Constants.KEY_DUE},
                Constants.KEY_COMPLETION_STATUS + "=?" + "AND " + Constants.KEY_DUE + "=?", new String[]{String.valueOf(0), String.valueOf(1)}, null, null, null);
        //loop through the cursor to get all contacts

        if (cursor.moveToFirst()) {
            do {
                Target target = new Target();
                target.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                target.setTopic(cursor.getString(cursor.getColumnIndex(Constants.KEY_TOPIC)));
                target.setDateAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)));
                target.setTimeAdded(cursor.getString(cursor.getColumnIndex(Constants.KEY_TIME_ADDED)));
                target.setFinishDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_DATE))));
                target.setFinishMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MONTH))));
                target.setFinishYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_YEAR))));
                target.setFinishHour(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_HOURS))));
                target.setFinishMinute(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FINISH_MINUTES))));
                target.setCompletionStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_COMPLETION_STATUS))));
                target.setDue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DUE))));
                //adding target item to targetList
                targetList.add(target);
            } while (cursor.moveToNext());
        }
        return targetList;
    }

    public int getCompletionStatus(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.KEY_TOPIC, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED,
                        Constants.KEY_FINISH_DATE, Constants.KEY_FINISH_MONTH, Constants.KEY_FINISH_YEAR, Constants.KEY_FINISH_HOURS, Constants.KEY_FINISH_MINUTES, Constants.KEY_COMPLETION_STATUS, Constants.KEY_DUE}
                , Constants.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        int completionStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_COMPLETION_STATUS)));
        return completionStatus;
    }

    public int getDue(int id) {
        int due;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.KEY_TOPIC, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED,
                        Constants.KEY_FINISH_DATE, Constants.KEY_FINISH_MONTH, Constants.KEY_FINISH_YEAR, Constants.KEY_FINISH_HOURS, Constants.KEY_FINISH_MINUTES, Constants.KEY_COMPLETION_STATUS, Constants.KEY_DUE}
                , Constants.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        due = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_DUE)));
        return due;
    }

    public int getCompletedTaskCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_TOPIC, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED, Constants.KEY_FINISH_YEAR, Constants.KEY_FINISH_MONTH, Constants.KEY_FINISH_DATE, Constants.KEY_FINISH_HOURS, Constants.KEY_FINISH_MINUTES, Constants.KEY_COMPLETION_STATUS, Constants.KEY_DUE},
                Constants.KEY_COMPLETION_STATUS + "=?" , new String[]{String.valueOf(1)}, null, null, null);
        //loop through the cursor to get all contacts

        return cursor.getCount();
    }
    public int getIncompleteTaskCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_TOPIC, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED, Constants.KEY_FINISH_YEAR, Constants.KEY_FINISH_MONTH, Constants.KEY_FINISH_DATE, Constants.KEY_FINISH_HOURS, Constants.KEY_FINISH_MINUTES, Constants.KEY_COMPLETION_STATUS, Constants.KEY_DUE},
                Constants.KEY_COMPLETION_STATUS + "=?" + "AND " + Constants.KEY_DUE + "=?", new String[]{String.valueOf(0), String.valueOf(1)}, null, null, null);
        //loop through the cursor to get all contacts
        return cursor.getCount();

    }
    public void deleteAllEntries(){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_DUE+"=?",new String[]{String.valueOf(1)});
        db.close();
    }
    public int getCurrentTaskCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_TOPIC, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED, Constants.KEY_FINISH_YEAR, Constants.KEY_FINISH_MONTH, Constants.KEY_FINISH_DATE, Constants.KEY_FINISH_HOURS, Constants.KEY_FINISH_MINUTES, Constants.KEY_COMPLETION_STATUS, Constants.KEY_DUE},
                Constants.KEY_DUE + "=?" + " AND " + Constants.KEY_COMPLETION_STATUS + "=?",  new String[]{String.valueOf(0), String.valueOf(0)}, null, null, null);
        //loop through the cursor to get all contacts
        return cursor.getCount();
    }
}
