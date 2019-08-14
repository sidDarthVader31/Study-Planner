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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<Target> targetList;



    public RecyclerViewAdapter(Context context, List<Target> targetList) {
        this.context = context;
        this.targetList = targetList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Target target = targetList.get(position);
        holder.taskName.setText(target.getTopic());
        holder.finishDate.setText(target.getFinishDate() + "/" + (target.getFinishMonth() + 1) + "/" + target.getFinishYear());
        holder.finishTime.setText(target.getFinishHour() + ":" + target.getFinishMinute());
    }

    @Override
    public int getItemCount() {
        return targetList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taskName;
        public TextView finishDate;
        public TextView finishTime;
        public RelativeLayout viewBackground;
        public RelativeLayout viewForeground;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            taskName = itemView.findViewById(R.id.tvTaskName);
            finishDate = itemView.findViewById(R.id.tvFinishDate);
            finishTime = itemView.findViewById(R.id.tvFinishTime);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}

