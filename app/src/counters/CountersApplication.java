package counters;

import android.app.Application;
import counters.database.Repository;

/**
 * Created by mara on 1/6/15.
 */
public class CountersApplication extends Application {
    private static Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(this);
        Preferences.initialize(this);
//        Intent intent = new Intent(this, StarterService.class);
//        this.startService(intent);
    }

    public static Repository getRepository() {
        return repository;
    }
}
