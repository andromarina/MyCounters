//package counters;
//
///**
// * Created by mara on 7/14/15.
// */
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
//import android.util.Log;
//import model.CollectionChangedListener;
//import model.Record;
//
//public class StarterService implements CollectionChangedListener {
//    private static final String TAG = "MyService";
//    private AlarmManager alarmManager;
//    private PendingIntent pendingIntent;
//   // private final long MONTH = 30 * 24 * 60 * 60 * 1000;
//    private final long MONTH = 30 * 1000;
//
//    /**
//     * The started service starts the AlarmManager.
//     */
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent i = new Intent(this, NotificationBarAlarm.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        this.pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        this.alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        this.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, Preferences.getLastRecordDate().getTime() + MONTH,
//                MONTH, this.pendingIntent);
//
//        Log.d(TAG, "onStart");
//        CountersApplication.getRepository().addCollectionChangedListener(this);
//        return START_NOT_STICKY;
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onDestroy() {
//        Log.d(TAG, "onDestroy");
//    }
//
//    @Override
//    public void OnRecordAdded(Record record) {
//        this.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, Preferences.getLastRecordDate().getTime() + MONTH,
//                MONTH, this.pendingIntent);
//
//    }
//
//    @Override
//    public void OnRecordRemoved(Record record) {
//
//    }
//
//    @Override
//    public void OnFilterChanged() {
//
//    }
//}