package Receiver;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import Data.DataBaseHandler;

public class SetDueOnComplete extends BroadcastReceiver {
    DataBaseHandler handler;
    public SetDueOnComplete() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
        handler = new DataBaseHandler(context);
        int completionStatus = handler.getCompletionStatus(id);
        if (completionStatus == 0) {
            handler.updateDueStatusOnNotificaton(id);
            }
        }
}
