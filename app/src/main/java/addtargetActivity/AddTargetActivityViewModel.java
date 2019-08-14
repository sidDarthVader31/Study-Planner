package addtargetActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import Model.Target;
import repository.Repository;

public class AddTargetActivityViewModel extends AndroidViewModel {
    private Repository repository;

    public AddTargetActivityViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getInstance(application.getApplicationContext());
    }

    public void saveTargetToDB(Target target) {
        repository.addTarget(target);
    }

    public int getLastItemId(){
        return  repository.getLastItemId();
    }
}
