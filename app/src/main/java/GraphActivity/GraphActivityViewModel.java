package GraphActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import repository.Repository;

public class GraphActivityViewModel extends AndroidViewModel {

    private Repository repository;

    public GraphActivityViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getInstance(application.getApplicationContext());
    }

    public int getCompletedTaskCount(){
        return repository.getCompletedTaskCount();
    }

    public int getIncompletedTaskCount(){
        return repository.getIncompletedTaskCount();
    }
    public void deleteAllEnteries(){
        repository.deleteAllEntries();
    }
}
