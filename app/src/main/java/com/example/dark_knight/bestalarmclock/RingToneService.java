package com.example.dark_knight.bestalarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Dark-Knight on 02-Oct-17.
 */
public class RingToneService extends Service {

    MediaPlayer music ;
    private int startId; // !!!!!!
    private boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags ,int startId){

        //intent = this.getIntent();
        String fetchState = intent.getExtras().getString("extra");
        Log.e("sRingtone_Fetch_State:",""+fetchState);

        //notification
        final NotificationManager notifyManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //intent ---> main activity
        Intent intentMainAc = new Intent(this.getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntentMainAc = PendingIntent.getActivity(this,0,intentMainAc,0);
        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Alarm is going off")
                .setContentText("Click Me")
                .setContentIntent(pendingIntentMainAc)
                .setAutoCancel(true)
                .build();




        assert fetchState != null ; // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        if(fetchState.equals("alarm on")) startId = 1;
        else if (fetchState.equals("alarm off")) startId=0;
        else this.startId = 0;

        //ALARM_ON
        if(!this.isRunning && startId ==1){
            Log.e("There is no music","you want to start");
            music = MediaPlayer.create(this,R.raw.walk);
            music.start();
            this.isRunning=true;
            this.startId=0;//start ringtone

            notifyManager.notify(0,mNotify);
        }
        //ALARM_OFF
        else if(this.isRunning && startId ==0){
            Log.e("There is  music","you want to end");
            music.stop();
            music.reset();
            this.isRunning=false ;
            this.startId = 0;
        }
        else if(!this.isRunning && startId ==0){
            Log.e("There is no music","you want to end");
            this.isRunning=false;
            this.startId = 0;
        }
        else if(this.isRunning && startId ==1){
            Log.e("There is music","you want to start again");
            this.isRunning=true;
            this.startId =1;
        }
        else {
            Log.e("else", "somehoe u reach this");
            //this.isRunning=false;
        }

        Log.e(""+isRunning,""+startId);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.isRunning = false ;
        //Toast.makeText(this,"On destroy",Toast.LENGTH_LONG).show();
    }
}
