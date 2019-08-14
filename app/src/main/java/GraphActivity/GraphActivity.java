package GraphActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.util.ArrayList;

import mainActivity.MainActivity;
import Data.DataBaseHandler;
import siddharthbisht.targettracker.R;


public class GraphActivity extends AppCompatActivity {

    PieChart pieChart;
    private AlertDialog.Builder alertDiaologBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private GraphActivityViewModel graphActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GraphActivity.this.setTitle("Your Progress");
        graphActivityViewModel= ViewModelProviders.of(this).get(GraphActivityViewModel.class);
        if (graphActivityViewModel.getCompletedTaskCount()>0 || graphActivityViewModel.getIncompletedTaskCount()>0){
            setContentView(R.layout.activity_graph);

            pieChart=findViewById(R.id.pcPieChart);
            pieChart.setRotationEnabled(true);
            pieChart.setUsePercentValues(true);
            pieChart.setHoleRadius(20f);
            pieChart.setTransparentCircleAlpha(0);
            pieChart.setCenterText("Statistics");
            ArrayList<PieEntry> yAxis=new ArrayList<>();
            ArrayList<String> xAxis=new ArrayList<>();
            int[] yValues={graphActivityViewModel.getCompletedTaskCount(),
                    graphActivityViewModel.getIncompletedTaskCount()};
            String[] xData={"Completed Task","Incomplete Task"};
            for (int i=0;i<yValues.length;i++){
                yAxis.add(new PieEntry(yValues[i],i));
            }
            for (int i=0;i<xData.length;i++){
                xAxis.add(xData[i]);
            }


            //creating the dataset
            PieDataSet pieDataSet=new PieDataSet(yAxis,"Progress");
            pieDataSet.setSliceSpace(2);
            pieDataSet.setValueTextSize(12);


            //adding colors
            ArrayList<Integer> colors=new ArrayList<>();
            colors.add(Color.rgb(22,173,62));
            colors.add(Color.rgb(193,9,25));
            pieDataSet.setColors(colors);


            //create legend
           /* Legend legend=pieChart.getLegend();
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
*/

            //create a pie data object
            PieData pieData=new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    if ((e.getY()==graphActivityViewModel.getIncompletedTaskCount())){
                        Toast.makeText(GraphActivity.this,"Incomplete tasks",Toast.LENGTH_SHORT).show();
                    }
                    else if(e.getY()==graphActivityViewModel.getCompletedTaskCount()){
                        Toast.makeText(GraphActivity.this,"Completed tasks",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //
                    }
                }

                @Override
                public void onNothingSelected() {
                }
            });
        }
        else{
            setContentView(R.layout.empty_layout);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_reset) {
            alertDiaologBuilder=new AlertDialog.Builder(GraphActivity.this);
            inflater=LayoutInflater.from(GraphActivity.this);
            View view=inflater.inflate(R.layout.reset_confirmation_dialog,null);
            Button noButton=view.findViewById(R.id.noButton);
            Button yesButton=view.findViewById(R.id.yesButton);
            alertDiaologBuilder.setView(view);
            dialog=alertDiaologBuilder.create();
            dialog.show();
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    graphActivityViewModel.deleteAllEnteries();
                    Toast.makeText(GraphActivity.this,"Data Reset Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(GraphActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
