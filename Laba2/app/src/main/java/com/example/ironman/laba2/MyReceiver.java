package com.example.ironman.laba2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        SharedPreferences sharedPref = context.getSharedPreferences("Key", Context.MODE_PRIVATE);
        if(hour == sharedPref.getInt("numberTime",0)) {
            context.startService(new Intent(context,MyService.class));

        }
        else {
            Toast.makeText(context,"NOO",Toast.LENGTH_SHORT).show();
        }
    }
}
