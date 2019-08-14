package completedTask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import Model.Target;
import repository.Repository;

public class CompletedTaskViewModel extends AndroidViewModel {
    private Repository repository;
    public CompletedTaskViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getInstance(application.getApplicationContext());
    }

    public List<Target> getCompletedTask(){
        return repository.getCompletedTasks();
    }
    public int getCompletedTaskCount(){
        return repository.getCompletedTaskCount();
    }
}
