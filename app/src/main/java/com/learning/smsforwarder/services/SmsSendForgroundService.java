package com.learning.smsforwarder.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.learning.smsforwarder.R;
import com.learning.smsforwarder.broadcastReceivers.SmsBroadCastReceiver;

public class SmsSendForgroundService extends Service {
    // 1. To create a foreground service you have to override two methods
    //    one is onStartCommand:
    //     |-> It will we called when service is started
    //     |-> Still running in background although app is killed

    // 2. another is onBind

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SmsManager sms = SmsManager.getDefault();
                Intent smsIntent = new Intent(getApplicationContext(), SmsSendForgroundService.class);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, smsIntent, 0);
                // Accessing sharedPref of system
                SharedPreferences sp = getSharedPreferences("smsForwarderSharedPref", Context.MODE_MULTI_PROCESS);
                // accessing mobile number
                String mobileNumber = sp.getString("mobileNumber", "");
                // accessing delay time in millisecond
                int delay = sp.getInt("delay", 5000);
//                registerReceiver(SmsBroadCastReceiver,intentFilter);
                while (true) {
                    try {
                        // sending the sms
//                        sms.sendTextMessage(mobileNumber, null, "Hello Suman Kamilya", pi, null);
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        // To Implement Foreground service you have to call startForeground method after calling this service intent
        // Otherwise app will throw exception

        // To call startForeground method you have to pass two params
        // 1. -> ID [unique id]
        // 2. -> notification Object
        // The notification will be shown when the service will running
        final String ID = "SERVICE_STARTED_007";
        // Creating notification channel
        NotificationChannel notificationChannel = new NotificationChannel(ID, ID, NotificationManager.IMPORTANCE_LOW);
        // Accessing Notification Service from system
        getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
        // building notification object to use it in startForeground() method
        Notification.Builder notification = new Notification.Builder(this, ID)
                .setContentText("Service is started")
                .setContentTitle("SMS Forwarder")
                .setSmallIcon(R.drawable.ic_launcher_background);
        startForeground(1001, notification.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
