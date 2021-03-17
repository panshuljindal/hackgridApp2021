package com.adgvit.hackgrid;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.adgvit.hackgrid.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class FirebaseNotificationService extends com.google.firebase.messaging.FirebaseMessagingService{

    private static final String TAG = "NotificationService";
    private static final String CHANNEL_ID = "PushNotifications";

    public FirebaseNotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        //FOR WAKE LOCK
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn = false; // check if screen is on
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = pm.isInteractive();
        }
        if (!isScreenOn) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myApp:notificationLock");
            wl.acquire(3000);
        }

    }

    @Override
    public void onMessageReceived( RemoteMessage remoteMessage) {
        //ONLY WHEN APP IS IN FOREGROUND
        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        String content = remoteMessage.getNotification().getBody();
        assert title != null;
        if(title.contains(":")){
            if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                title=title.split(":")[1];
                sendNotification(title,content);
            }
        }
        else{
            sendNotification(title,content);
        }

    }

    @Override
    public void onDeletedMessages() {

    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token : "+token);
        //send token to your app server
    }

    @SuppressLint("InvalidWakeLockTag")
    private void sendNotification(String title, String messageBody) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_tracks)
                //.setBadgeIconType(13)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
              //  .setColor(getResources().getColor(R.color.log_bg_color))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0,notificationBuilder.build());

    }

    //ANDROID 8.0 AND ABOVE
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyNotifications";
            String description = "All MyNotifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}