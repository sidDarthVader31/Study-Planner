package currentTask;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Util.AlarmCreater;
import mainActivity.MainActivity;
import Adapters.RecyclerViewAdapter;
import Data.DataBaseHandler;
import Model.Target;
import Util.RecyclerItemTouchHelper;
import siddharthbisht.targettracker.R;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;
import android.support.v4.app.Fragment;


public class CurrentTaskFragment extends Fragment implements  RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Target> targetList;
    private List<Target> listItems;
    private CurrentTaskViewModel currentTaskViewModel;
    private static final String TAG="CURRENTTASKFRAGMENT";
    public AlarmCreater creater;



    public CurrentTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity())
            .setActionBarTitle("Current Tasks");
       currentTaskViewModel= ViewModelProviders.of(this).get(CurrentTaskViewModel.class);
        View view;
        if (currentTaskViewModel.getCurrentTasksCount()>0){
             view= inflater.inflate(R.layout.fragment_current_task, container, false);
            recyclerView=view.findViewById(R.id.rvList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            targetList=new ArrayList<>();
            listItems=new ArrayList<>();
            creater=new AlarmCreater();
            initializeData();
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        }
        else{
            view=inflater.inflate(R.layout.empty_main_layout,container,false);
        }

        return view;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void initializeData(){

        //Get items from database

        targetList=currentTaskViewModel.getCurrentTasks();
        adapter=new RecyclerViewAdapter(this.getContext(),targetList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RecyclerViewAdapter.ViewHolder) {
            if (direction == ItemTouchHelper.LEFT) {
                // get the removed item name to display it in snack bar
              currentTaskViewModel.removeTarget(targetList.get(position).getId());
              String name=targetList.get(position).getTopic();
                long time = getTimeInMillis(
                        targetList.get(position).getFinishYear(),
                        targetList.get(position).getFinishMonth(),
                        targetList.get(position).getFinishDate(),
                        targetList.get(position).getFinishHour(),
                        targetList.get(position).getFinishMinute());
                creater = new AlarmCreater();
                creater.DeleteAlarm(getActivity(),
                        targetList.get(position).getId(),
                        targetList.get(position).getTopic(), time);
                creater.DeleteDueStatus(
                        getActivity(),
                        targetList.get(position).getId(),time);
                targetList.remove(position);
              adapter.notifyDataSetChanged();
              Toast.makeText(getContext(),name+" deleted successfully",Toast.LENGTH_SHORT).show();
            }
            else if (direction==ItemTouchHelper.RIGHT){
                Toast.makeText(getContext(),  targetList.get(viewHolder.getAdapterPosition()).getTopic()+" is marked as complete", Toast.LENGTH_SHORT).show();
                currentTaskViewModel.markAsDone(targetList.get(position).getId());
                targetList.remove(position);
                adapter.notifyDataSetChanged();
            }
        }
    }
    public long getTimeInMillis(int year,int month,int date,int hour,int minutes){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,date,hour,minutes,0);
        return calendar.getTimeInMillis();
    }

}
