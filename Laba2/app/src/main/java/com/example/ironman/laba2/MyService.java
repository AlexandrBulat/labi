package com.example.ironman.laba2;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    NotificationManager nm;
    AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {

            sendNotif();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void sendNotif() {
        SharedPreferences sharedPref = getSharedPreferences("Key",Context.MODE_PRIVATE);
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(sharedPref.getString("filexml",""))
                        .setContentText("Hello World!");

        Intent intent = new Intent(this, Main2Activity.class);


        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);


        // отправляем
        nm.notify(1, mBuilder.build());
        Intent intent1 = new Intent(this,Unclocker.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);

    }
}

