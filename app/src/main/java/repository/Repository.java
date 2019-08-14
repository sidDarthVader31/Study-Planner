package repository;

import android.content.Context;

import java.util.List;

import Data.DataBaseHandler;
import Model.Target;

public class Repository {
    Context context;
    private static Repository sInstance;
    private static final Object LOCK = new Object();
    private DataBaseHandler dataBaseHandler;


    public Repository(Context context) {
        this.context = context;
        dataBaseHandler=new DataBaseHandler(context);
    }
    public static Repository getInstance(Context context){
        if(null==sInstance){
            synchronized (LOCK){
                sInstance = new Repository(context);
            }
        }
        return sInstance;
    }
    public void addTarget(Target target){
        dataBaseHandler.addTarget(target);
    }

    public int getLastItemId() {
        return dataBaseHandler.getLastItem().getId();
    }

    public int getCompletedTaskCount() {
        return  dataBaseHandler.getCompletedTaskCount();
    }
    public int getIncompletedTaskCount(){
        return dataBaseHandler.getIncompleteTaskCount();
    }

    public void deleteAllEntries(){
        dataBaseHandler.deleteAllEntries();
    }

    public List<Target> getCompletedTasks() {
       return dataBaseHandler.getAllCompletedTargets();
    }

    public int getNotCompletedTaskCount() {
        return dataBaseHandler.getIncompleteTaskCount();
    }
    public List<Target> getNotCompletedTask(){
        return dataBaseHandler.getAllIncompleteTargets();
    }

    public List<Target> getCurrentTasks() {
        return dataBaseHandler.getAllCurrentTargets();
    }
    public int getCurrentTasksCount(){
        return dataBaseHandler.getCurrentTaskCount();
    }

    public void markAsDone(int id) {
        dataBaseHandler.updateCompletionStatus(id);
    }

    public void removeTarget(int id) {
        dataBaseHandler.DeleteTarget(id);
    }
}
