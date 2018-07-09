package Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import Data.DataBaseHandler;
import siddharthbisht.targettracker.R;

public class GraphActivity extends AppCompatActivity {
    private static final String TAG="GraphActivity";
    private DataBaseHandler handler;

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        handler=new DataBaseHandler(this);
        Log.d(TAG,"Inside on Create");
        pieChart=findViewById(R.id.pcPieChart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Your Progress");
        ArrayList<PieEntry> yAxis=new ArrayList<>();
        ArrayList<String> xAxis=new ArrayList<>();
         int[] yValues={handler.getCompletedTaskCount(),handler.getIncompleteTaskCount()};
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
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        pieDataSet.setColors(colors);

        //create legend

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create a pie data object

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG,"inside onValueSelected:"+String.valueOf(e));
                Log.d(TAG,"y:"+String.valueOf(e.getY()));
                if ((e.getY()==handler.getIncompleteTaskCount())){
                    Toast.makeText(GraphActivity.this,"Incomplete tasks",Toast.LENGTH_SHORT).show();
                }
                else if(e.getY()==handler.getCompletedTaskCount()){
                    Toast.makeText(GraphActivity.this,"Completed tasks",Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.d(TAG,"Values do not match");
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }
}
