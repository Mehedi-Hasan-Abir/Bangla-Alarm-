package com.example.dark_knight.bestalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {


    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    TextView update_alarm;
    Context context;
    PendingIntent pendingIntent;

    private void setAlarmText(String s){
        update_alarm.setText(s);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        final Intent intent = new Intent(this.context,Alarm_Receiver.class);
        final Calendar calender = Calendar.getInstance();
        //alarmManager
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //timepick
        alarmTimePicker = (TimePicker)findViewById(R.id.timePicker);
        //update textview
        update_alarm = (TextView)findViewById(R.id.update_alarm);

        Button start_alarm = (Button)findViewById(R.id.start_alarm);
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender.set(Calendar.YEAR,2016);
                calender.set(Calendar.HOUR_OF_DAY,alarmTimePicker.getHour());
                calender.set(Calendar.MINUTE,alarmTimePicker.getMinute());

                String hour = String.valueOf(alarmTimePicker.getHour());
                String minute = String.valueOf(alarmTimePicker.getMinute());

                if (calender.getTimeInMillis() < System.currentTimeMillis()) {
                    //Add one day to the calendar (or whatever repeat interval you would like)
                    calender.add(Calendar.DAY_OF_YEAR, 1);
                }

                if(alarmTimePicker.getHour() > 12) hour = String.valueOf(alarmTimePicker.getHour() - 12);
                if(alarmTimePicker.getMinute() < 10) minute = "0"+ String.valueOf(alarmTimePicker.getMinute());

                setAlarmText("Alarm On at "+hour+":"+minute);
                intent.putExtra("extra","alarm on");
                //pending !!!!!'intent'!!!! setTime
                //sendBroadcast(intent);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0 ,intent,PendingIntent.FLAG_UPDATE_CURRENT);    //intent pending


                //manager
                alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(30*1000),pendingIntent );
                Log.e(""+pendingIntent.toString(),""+System.currentTimeMillis());
            }
        });

        Button end_alarm = (Button)findViewById(R.id.end_alarm);
        end_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarmText("Alarm Off");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","alarm off"); //put extra string
                sendBroadcast(intent);
            }
        });


    }


}
