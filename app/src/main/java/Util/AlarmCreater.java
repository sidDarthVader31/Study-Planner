package Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import Activities.MainActivity;
import Data.DataBaseHandler;
import Model.Target;
import Receiver.AlarmReceiver;
import Receiver.SetDueOnComplete;

public class AlarmCreater {



    public AlarmCreater() {

    }

    public void setAlarm(Context context, long timeInMillis, int id,String topic) {
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, AlarmReceiver.class);
        intent.putExtra("id",id);
        intent.putExtra("topic",topic);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);
        Toast.makeText(context,"ALARM iS SET",Toast.LENGTH_SHORT).show();
        Log.d("Alarm:","alarm set for "+String.valueOf(timeInMillis));
    }
    public void DeleteAlarm(Context context,int id,String topic,long timeInMillis){
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, AlarmReceiver.class);
        intent.putExtra("id",id);
        intent.putExtra("topic",topic);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);
        alarmManager.cancel(pendingIntent);

    }
    public void setDueStatus(Context context,int id,long timeInMillis){

        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, SetDueOnComplete.class);
        intent.putExtra("id",id);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);
        DataBaseHandler handler=new DataBaseHandler(context);
        Target target=handler.getTarget(id);//this line will not be included in the final version of the code
        Log.d("AlarmCreater:","setDueStatus set for:"+target.getTopic());//this line too
    }
}
