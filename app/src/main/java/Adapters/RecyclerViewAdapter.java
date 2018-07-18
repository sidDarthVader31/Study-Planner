package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RelativeLayout;
import java.util.Calendar;
import java.util.List;
import Data.DataBaseHandler;
import Model.Target;
import Util.AlarmCreater;
import siddharthbisht.targettracker.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;
    List<Target> targetList;
    private AlertDialog.Builder alertDiaologBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    public DataBaseHandler db;
    public AlarmCreater creater;

    public RecyclerViewAdapter(Context context, List<Target> targetList) {
        this.context=context;
        this.targetList=targetList;
        db=new DataBaseHandler(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new ViewHolder(view,context);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Target target=targetList.get(position);
        holder.taskName.setText(target.getTopic());
        holder.finishDate.setText(target.getFinishDate()+"/"+(target.getFinishMonth()+1)+"/"+target.getFinishYear());
        holder.finishTime.setText(target.getFinishHour()+":"+target.getFinishMinute());
    }

    @Override
    public int getItemCount() {
        return targetList.size();
    }

    public void removeTarget(final Target target, final int position) {

        //delete the item
        long time = getTimeInMillis(target.getFinishYear(), target.getFinishMonth(), target.getFinishDate(), target.getFinishHour(), target.getFinishMinute());
        creater = new AlarmCreater();
        creater.DeleteAlarm(context, target.getId(), target.getTopic(), time);
        creater.DeleteDueStatus(context,target.getId(),time);
        db.DeleteTarget(target.getId());
        targetList.remove(position);
        notifyItemRemoved(position);
    }
    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taskName;
        public TextView finishDate;
        public TextView finishTime;
        public RelativeLayout viewBackground;
        public RelativeLayout viewForeground;
        public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            context=ctx;
            taskName=itemView.findViewById(R.id.tvTaskName);
            finishDate=itemView.findViewById(R.id.tvFinishDate);
            finishTime=itemView.findViewById(R.id.tvFinishTime);
            viewBackground=itemView.findViewById(R.id.view_background);
            viewForeground=itemView.findViewById(R.id.view_foreground);
        }
    }

    public void moveToDone(Target target,int position){
        db.updateCompletionStatus(target.getId());
        targetList.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    public long getTimeInMillis(int year,int month,int date,int hour,int minutes){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,date,hour,minutes,0);
        return calendar.getTimeInMillis();
    }

}
