package completedTask;


import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mainActivity.MainActivity;
import Adapters.ArchivedRecyclerViewAdapter;
import Data.DataBaseHandler;
import Model.Target;
import siddharthbisht.targettracker.R;


public class CompletedTaskFragment extends Fragment{
    RecyclerView recyclerView;
    private ArchivedRecyclerViewAdapter adapter;
    private List<Target> targetList;
    private List<Target> listItems;
    private CompletedTaskViewModel completedTaskViewModel;
    public CompletedTaskFragment() {
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
                .setActionBarTitle("Completed");
       completedTaskViewModel= ViewModelProviders.of(this).get(CompletedTaskViewModel.class);
        View view;
        if (completedTaskViewModel.getCompletedTaskCount()>0){
            view= inflater.inflate(R.layout.fragment_completed_task, container, false);
            recyclerView=view.findViewById(R.id.rvListArchived);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            targetList=new ArrayList<>();
            listItems=new ArrayList<>();
            initializeData();
        }
        else {
            view=inflater.inflate(R.layout.empty_layout,container,false);
        }

        return view;
    }

    public void initializeData(){
        //Get items from database
        targetList=completedTaskViewModel.getCompletedTask();
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
        Collections.reverse(listItems);
        adapter=new ArchivedRecyclerViewAdapter(this.getContext(),listItems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
}
