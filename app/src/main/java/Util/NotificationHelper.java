package Util;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import Activities.MainActivity;
import Receiver.SetDueOnComplete;
import siddharthbisht.targettracker.R;

public class NotificationHelper extends ContextWrapper {
    public static final String CHANNEL_ID="CHANNEL1";
    public static final String CHANNEL_NAME="ChannelName";
    public NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        Log.d("helper","inside helper");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannel();
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel() {
        NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        Log.d("helper","channel created");
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager(){
        if(mManager==null){
            mManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Log.d("helper","manager created");
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification(String title,String message){
        Log.d("helper","building notification ");
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Intent IntentDone=new Intent(this, SetDueOnComplete.class);
        IntentDone.setAction("Marking as Complete");

        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }
}
