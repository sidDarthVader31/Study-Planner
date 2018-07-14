package Receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import Data.DataBaseHandler;
import Model.Target;

public class MarkAsComplete extends BroadcastReceiver {
    private static final String TAG="MarkAsComplete";
    DataBaseHandler handler;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"Inside complete receiver");
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
        handler = new DataBaseHandler(context);
        int completionStatus = handler.getCompletionStatus(id);
        if (completionStatus == 0) {
            handler.updateCompletionStatus(id);
            Target target = handler.getTarget(id);
            Log.d(TAG, String.valueOf(id) + " updated");
            Log.d(TAG, "Completion Status of " + String.valueOf(id) + " is:" + String.valueOf(target.getCompletionStatus()));
        }
        else {
            Log.d(TAG, "task already done :" + id);
        }
    }
}

