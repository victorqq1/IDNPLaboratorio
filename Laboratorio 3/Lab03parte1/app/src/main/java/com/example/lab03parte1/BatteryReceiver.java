package com.example.lab03parte1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {

    private TextView batteryStatusTextView;

    public BatteryReceiver(TextView textView) {
        this.batteryStatusTextView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float) scale;

        String status = "Battery Level: " + batteryPct + "%";
        batteryStatusTextView.setText(status);
        Log.d("BatteryReceiver", "Battery level actualizado: " + batteryPct + "%");
    }
}
