package com.learning.smsforwarder.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsBroadCastReceiver extends BroadcastReceiver {
    final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
//        Bundle bundle = intent.getExtras();
//        SmsMessage[] msgs;
//        String strMessage = "";
//        String format = bundle.getString("format");
            Log.i(TAG, "BORADCAST RECEIVED");
//        if(intent.getAction().equals(SMS_RECEIVED)){

            Toast.makeText(context.getApplicationContext(), "SMS received", Toast.LENGTH_SHORT).show();
//        }
    }
}
