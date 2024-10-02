package com.example.lab3_app2;

// MainActivity.java
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BatteryReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instancia del BroadcastReceiver
        batteryReceiver = new BatteryReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Intent y un PendingIntent con FLAG_IMMUTABLE o FLAG_MUTABLE que es necesario a partir de Android 12
        Intent batteryIntent = new Intent(this, BatteryReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, batteryIntent, PendingIntent.FLAG_IMMUTABLE);

        // Registro del BroadcastReceiver
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter, null, null);
        Log.d("MainActivity", "BatteryReceiver registrado satisfactoriamente usando PendingIntent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistro el BroadcastReceiver
        unregisterReceiver(batteryReceiver);
        Log.d("MainActivity", "BatteryReceiver desregistrado satisfactoriamente");
    }
}
