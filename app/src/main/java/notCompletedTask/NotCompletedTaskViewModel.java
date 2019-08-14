package notCompletedTask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import Model.Target;
import repository.Repository;

public class NotCompletedTaskViewModel extends AndroidViewModel {
    private Repository repository;
    public NotCompletedTaskViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getInstance(application.getApplicationContext());
    }

    public int getNotCompletedTaskCount(){
        return repository.getNotCompletedTaskCount();
    }
    public List<Target> getIncompleteTasks(){
        return repository.getNotCompletedTask();
    }
}
