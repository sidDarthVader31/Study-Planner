package Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import Model.Target;

import siddharthbisht.targettracker.R;

public class ArchivedRecyclerViewAdapter extends RecyclerView.Adapter<ArchivedRecyclerViewAdapter.ViewHolder>{
    Context context;
    List<Target> targetList;
    public ArchivedRecyclerViewAdapter(Context context, List<Target> targetList) {
        this.context=context;
        this.targetList=targetList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.archived_item_row,parent,false);
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

    public  class ViewHolder extends RecyclerView.ViewHolder {

        public TextView taskName;
        public TextView finishDate;
        public TextView finishTime;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            taskName = itemView.findViewById(R.id.tvTaskNameArchive);
            finishDate = itemView.findViewById(R.id.tvFinishDateArchive);
            finishTime = itemView.findViewById(R.id.tvFinishTimeArchive);
        }
    }
}
