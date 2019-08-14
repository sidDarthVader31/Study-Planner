package currentTask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import Model.Target;
import repository.Repository;

public class CurrentTaskViewModel extends AndroidViewModel {

    Repository repository;
    public CurrentTaskViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getInstance(application.getApplicationContext());
    }

    public List<Target> getCurrentTasks(){
        return  repository.getCurrentTasks();
    }

    public int getCurrentTasksCount(){
        return repository.getCurrentTasksCount();
    }

    public void removeTarget(int id){
        repository.removeTarget(id);
    }
    public void markAsDone(int id){
        repository.markAsDone(id);
    }
}
