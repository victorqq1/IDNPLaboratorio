package com.example.lab_10;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AudioPlayerService extends Service {
    public static final String CHANNEL_ID = "AudioPlayerChannel";
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializa el reproductor de audio
        mediaPlayer = MediaPlayer.create(this, R.raw.audio_file); // Asegúrate de incluir un archivo en res/raw
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if ("START".equals(action)) {
            startForegroundService();
            mediaPlayer.start();
        } else if ("PAUSE".equals(action)) {
            mediaPlayer.pause();
        } else if ("CONTINUE".equals(action)) {
            mediaPlayer.start();
        } else if ("STOP".equals(action)) {
            mediaPlayer.stop();
            stopForeground(true);
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    private void startForegroundService() {
        // Crear canal de notificación
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Audio Player Service",
                NotificationManager.IMPORTANCE_LOW
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }

        // Crear notificación
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Reproduciendo audio")
                .setContentText("Tu música está sonando")
                .setSmallIcon(R.drawable.ic_music_note)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}