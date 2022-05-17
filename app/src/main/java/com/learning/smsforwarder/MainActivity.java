package com.learning.smsforwarder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;
import com.learning.smsforwarder.broadcastReceivers.SmsBroadCastReceiver;
import com.learning.smsforwarder.services.SmsSendForgroundService;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        // First Declare the setContentView before findViewById
        EditText text = findViewById(R.id.editText);
        RangeSlider slider = findViewById(R.id.rangeSlider);
        Button sendButton = findViewById(R.id.button);
        SharedPreferences smsForwarderSharedPref = getSharedPreferences("smsForwarderSharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = smsForwarderSharedPref.edit();


        slider.setStepSize(100.0f);
        slider.setValues(100.0f);
        slider.setValueFrom(100.0f);
        slider.setValueTo(5000.0f);
//         SMS sending: First send and test the sms service
        Intent intent = new Intent(this, SmsSendForgroundService.class);
        slider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
//                Toast.makeText(getApplicationContext(),String.valueOf(value), Toast.LENGTH_SHORT);
                editor.putInt("delay", Math.round(value));
                text.setHint(String.valueOf(value));
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("mobileNumber", String.valueOf(text.getText()));
                editor.commit();
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                startForegroundService(intent);
            }
        });

    }
}