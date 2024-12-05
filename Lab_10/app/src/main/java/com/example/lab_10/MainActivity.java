package com.example.lab_10;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStart).setOnClickListener(v -> startService("START"));
        findViewById(R.id.btnPause).setOnClickListener(v -> startService("PAUSE"));
        findViewById(R.id.btnContinue).setOnClickListener(v -> startService("CONTINUE"));
        findViewById(R.id.btnStop).setOnClickListener(v -> startService("STOP"));
    }

    private void startService(String action) {
        Intent serviceIntent = new Intent(this, AudioPlayerService.class);
        serviceIntent.setAction(action);
        startService(serviceIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        stopForegroundService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        startForegroundService();
    }

    private void startForegroundService() {
        Intent serviceIntent = new Intent(this, AudioPlayerService.class);
        serviceIntent.setAction("START");
        startService(serviceIntent);
    }

    private void stopForegroundService() {
        Intent serviceIntent = new Intent(this, AudioPlayerService.class);
        serviceIntent.setAction("PAUSE");
        startService(serviceIntent);
    }
}