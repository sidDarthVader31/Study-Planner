package Fragments;

import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import Activities.MainActivity;
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
    private DataBaseHandler db;
    private static final String TAG="CURRENTTASKFRAGMENT";

    private OnFragmentInteractionListener mListener;

    public CurrentTaskFragment() {
        // Required empty public constructor
    }
    private Boolean firstTime = null;
    /**
     * Checks if the user is opening the app for the first time.
     * Note that this method should be placed inside an activity and it can be called multiple times.
     * @return boolean
     */

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
        db=new DataBaseHandler(this.getContext());
        View view;
        if (db.getCurrentTaskCount()>0){
             view= inflater.inflate(R.layout.fragment_current_task, container, false);
            recyclerView=view.findViewById(R.id.rvList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            targetList=new ArrayList<>();
            listItems=new ArrayList<>();
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

        targetList=db.getAllCurrentTargets();

        for(Target c: targetList){
            Target target=new Target();
            target.setTopic(c.getTopic());
            target.setFinishDate(c.getFinishDate());
            target.setFinishMonth(c.getFinishMonth());
            target.setFinishYear(c.getFinishYear());
            target.setFinishHour(c.getFinishHour());
            target.setFinishMinute(c.getFinishMinute());
            target.setId(c.getId());
            listItems.add(target);
        }
        adapter=new RecyclerViewAdapter(this.getContext(),listItems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RecyclerViewAdapter.ViewHolder) {
            if (direction == ItemTouchHelper.LEFT) {
                // get the removed item name to display it in snack bar
                Target target=targetList.get(viewHolder.getAdapterPosition());
              adapter.removeTarget(target,viewHolder.getAdapterPosition());
              Toast.makeText(getContext(),target.getTopic()+" deleted successfully",Toast.LENGTH_SHORT).show();
            }
            else if (direction==ItemTouchHelper.RIGHT){
                String name = targetList.get(viewHolder.getAdapterPosition()).getTopic();
                Toast.makeText(getContext(), name+" is marked as complete", Toast.LENGTH_SHORT).show();
                Target target = targetList.get(viewHolder.getAdapterPosition());
                adapter.moveToDone(target, viewHolder.getAdapterPosition());
            }
        }
    }

}
