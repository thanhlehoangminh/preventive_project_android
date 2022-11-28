package com.example.afinal;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class MyApp extends Application {
    public static final String CHANNEL_ID_TEMP= "CHANNEL TEMP";
    public static final String CHANNEL_ID_HUMI= "CHANNEL HUMI";
    public static final String CHANNEL_ID_NOISE= "CHANNEL NOISE";
    public static final String CHANNEL_ID_AIR= "CHANNEL AIR";

    @Override
    public void onCreate() {
        createNotificationChannel();
        super.onCreate();
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CHANNEL TEMP
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            CharSequence name_channel_temp = getString(R.string.channel_temp);
            String description_channel_temp = getString(R.string.channel_temp_description);
            int importance1 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel_temp = new NotificationChannel(CHANNEL_ID_TEMP, name_channel_temp, importance1);
            channel_temp.setDescription(description_channel_temp);
            channel_temp.enableVibration(true);








//
//            //CHANNEL TEMP
//            CharSequence name_channel_humi = getString(R.string.channel_humi);
//            String description_channel_humi = getString(R.string.channel_humi_description);
//            int importance2 = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel_humi = new NotificationChannel(CHANNEL_ID_HUMI, name_channel_humi, importance2);
//            channel_humi.setDescription(description_channel_humi);
//            //CHANNEL NOISE
//            CharSequence name_channel_noise = getString(R.string.channel_noise);
//            String description_channel_noise = getString(R.string.channel_noise_description);
//            int importance3 = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel_noise = new NotificationChannel(CHANNEL_ID_NOISE, name_channel_noise, importance3);
//            channel_noise.setDescription(description_channel_noise);
//            //CHANNEL TEMP
//            CharSequence name_channel_air = getString(R.string.channel_air);
//            String description_channel_air = getString(R.string.channel_air_description);
//            int importance4 = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel_air = new NotificationChannel(CHANNEL_ID_AIR, name_channel_air, importance4);
//            channel_air.setDescription(description_channel_temp);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager!=null)
            {
                notificationManager.createNotificationChannel(channel_temp);
//                notificationManager.createNotificationChannel(channel_humi);
//                notificationManager.createNotificationChannel(channel_noise);
//                notificationManager.createNotificationChannel(channel_air);
            }
        }
    }

}
