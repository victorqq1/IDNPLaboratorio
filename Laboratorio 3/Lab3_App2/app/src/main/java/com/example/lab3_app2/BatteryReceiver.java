package com.example.lab3_app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Porcentaje de la batería
        float batteryPct = level * 100 / (float) scale;

        // Nivel de batería en un Toast
        Toast.makeText(context, "Porcentaje de bateria: " + batteryPct + "%", Toast.LENGTH_SHORT).show();

        // Imprimir en consola
        Log.d("BatteryReceiver", "Porcentaje de bateria: " + batteryPct + "%");
    }
}

