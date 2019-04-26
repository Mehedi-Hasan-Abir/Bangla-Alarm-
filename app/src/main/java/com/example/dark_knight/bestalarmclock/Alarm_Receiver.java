package com.example.dark_knight.bestalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Dark-Knight on 02-Oct-17.
 */
public class Alarm_Receiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("get extra aReceiver",intent.getExtras().getString("extra"));

        Toast.makeText(context, "Create a toast", Toast.LENGTH_SHORT).show();

        Intent service_intent = new Intent(context,RingToneService.class);
        service_intent.putExtra("extra",intent.getExtras().getString("extra"));//put extra service intent
        context.startService(service_intent);
    }
}
