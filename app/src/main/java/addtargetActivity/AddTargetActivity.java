package addtargetActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

import mainActivity.MainActivity;
import Data.DataBaseHandler;
import Model.Target;
import Util.AlarmCreater;
import Util.Validator;
import siddharthbisht.targettracker.R;

public class AddTargetActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etTopic;
    private TextView date;
    private TextView time;
    private Button submit;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int sYear, sMonth, sDate, sHour, sMinute;
    public AlarmCreater creater;
    public Validator validator;
    private AddTargetActivityViewModel addTargetActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_target2);
        addTargetActivityViewModel= ViewModelProviders.of(this).get(AddTargetActivityViewModel.class);
        initViews();
        initializeDateTime();
    }

    private void initViews(){
        etTopic = findViewById(R.id.etAddTopicName);
        date = findViewById(R.id.tvDateToStore);
        time = findViewById(R.id.tvTimeToStore);
        submit = findViewById(R.id.btSubmitAdd);

        date.setOnClickListener(this);
        time.setOnClickListener(this);
        submit.setOnClickListener(this);
        creater=new AlarmCreater();
        validator=new Validator();
        initializeDateTime();
    }

    /**
     * Add a method to store the information in the list and migrate to the current task fragement(Main Activity)
     * Add a  method to put current date and current time in date and time textviews
     * Add a method to put onclick listenet in date and time to store the date and time entered by the user
     */


    public void initializeDateTime() {
        Calendar calendar=Calendar.getInstance();
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        mHour=calendar.get(Calendar.HOUR_OF_DAY);
        mMinute=calendar.get(Calendar.MINUTE);
        String sDateStr=mDay+"/"+(mMonth+1)+"/"+mYear;
        String sTimeStr=mHour+":"+mMinute;
        date.setText(sDateStr);
        time.setText(sTimeStr);
        sYear=mYear;
        sMonth=mMonth;
        sDate=mDay;
        sHour=mHour;
        sMinute=mMinute;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDateToStore:
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTargetActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                sYear=year;
                                sMonth=monthOfYear;
                                sDate=dayOfMonth;
                                date.setText(sDate + "-" + (sMonth + 1) + "-" + sYear);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.tvTimeToStore:
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTargetActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                sHour=hourOfDay;
                                sMinute=minute;
                                time.setText(sHour+ ":" +sMinute);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

                break;
            case R.id.btSubmitAdd:
                if (etTopic.getText().toString().isEmpty()){
                    Toast.makeText(AddTargetActivity.this,"Enter topic name",Toast.LENGTH_SHORT).show();
                    date.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
                    time.setText(mHour+":"+mMinute);
                }
                else{
                    if (validator.validateDateTime(sYear,sMonth,sDate,sHour,sMinute)==true){

                        time.setText(sHour+":"+sMinute);
                        date.setText(sDate + "/" + (sMonth + 1) + "/" + sYear);
                        saveTargetToDb(v, sYear, sMonth, sDate, sHour, sMinute);
                    }
                    else {
                        Toast.makeText(AddTargetActivity.this,"Enter time atleast an hour ahead",Toast.LENGTH_SHORT).show();
                    }
                }
                  }
    }


    private void saveTargetToDb(View v,int year, int month, int date, int hours, int minutes) {
        Target target = new Target();
        String topic = etTopic.getText().toString();
        target.setTopic(topic);
        target.setFinishDate(date);
        target.setFinishMonth(month);
        target.setFinishYear(year);
        target.setFinishHour(hours);
        target.setFinishMinute(minutes);
        target.setDue(0);
        target.setCompletionStatus(0);
        addTargetActivityViewModel.saveTargetToDB(target);
        int id=addTargetActivityViewModel.getLastItemId();
        //Getting alarm time in millis
        Long timeinMillis=getTimeInMillis(year,month,date,hours,minutes);
        creater.setDueStatus(AddTargetActivity.this,id,(timeinMillis));
        creater.setAlarm(AddTargetActivity.this,(timeinMillis-(15*60*1000)),id,target.getTopic());
        Snackbar.make(v,  target.getTopic()+" Saved", Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(AddTargetActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
    public long getTimeInMillis(int year,int month,int date,int hour,int minutes){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,date,hour,minutes,0);
        return calendar.getTimeInMillis();
    }
}


