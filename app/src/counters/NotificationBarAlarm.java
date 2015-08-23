package counters;

/**
 * Created by mara on 7/14/15.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.counters.chart.R;
import counters.categories.MainActivity;

public class NotificationBarAlarm extends BroadcastReceiver {

    NotificationManager notifyManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("NotificationAlarm", "onReceive");

        notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notif = new Notification(R.drawable.ic_launcher, context.getString(R.string.notification_title),
                System.currentTimeMillis());
        notif.setLatestEventInfo(context, context.getString(R.string.notification_title),
                context.getString(R.string.notification_text), contentIntent);

        notifyManager.notify(1, notif);
    }
}
