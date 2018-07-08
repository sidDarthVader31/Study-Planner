package Receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import Activities.MainActivity;
import Data.DataBaseHandler;
import Util.NotificationHelper;
import siddharthbisht.targettracker.R;

public class AlarmReceiver extends BroadcastReceiver {
    NotificationHelper helper;
    private static final String TAG="AlarmReceiver";
    DataBaseHandler handler;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "inside on receive");
        handler = new DataBaseHandler(context);
        Bundle extras = intent.getExtras();
        String topic = extras.getString("topic");
        int id = extras.getInt("id");
        int completionStatus = handler.getCompletionStatus(id);
        Log.d("receiver", "id: " + String.valueOf(id));
        Log.d("receiver", "topic: " + topic);
        if (completionStatus == 0) {
            Intent IntentDone = new Intent(context, SetDueOnComplete.class);
            IntentDone.setAction("Mark as Complete");
           IntentDone.putExtra("id", id);
            PendingIntent snoozePendingIntent =
                    PendingIntent.getBroadcast(context, 0, IntentDone, PendingIntent.FLAG_UPDATE_CURRENT);
            helper = new NotificationHelper(context);
            NotificationCompat.Builder nb = helper.getChannelNotification("Incomplete task", "Your " + topic + " is still pending");
            nb.addAction(R.drawable.ic_menu_send, "Mark As Complete ", snoozePendingIntent);
            helper.getManager().notify(id, nb.build());
        }
    }

}
