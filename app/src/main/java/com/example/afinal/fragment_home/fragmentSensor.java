package com.example.afinal.fragment_home;

import static com.example.afinal.MyApp.*;

import android.app.Notification;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.afinal.R;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class fragmentSensor extends Fragment {
    DatabaseReference mData;
    TextView temp_val;
    com.google.android.material.slider.RangeSlider slider_temp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_sensor, container, false);
        temp_val = mView.findViewById(R.id.temp_value);
        slider_temp = mView.findViewById(R.id.slider4);
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("SENSOR").child("Temperature").child("Value").setValue("30");
        mData.child("SENSOR").child("Temperature").child("Value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp_val.setText(snapshot.getValue().toString());
                List<Float> values = slider_temp.getValues();
                float val = Float.parseFloat(String.valueOf(snapshot.getValue()));
                if (val < values.get(0) || values.get(1) < val)
                {
                    sendTempNotification();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        slider_temp.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                mData.child("SENSOR").child("Temperature").child("Value").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        temp_val.setText(snapshot.getValue().toString());
                        List<Float> values = slider_temp.getValues();
                        float val = Float.parseFloat(String.valueOf(snapshot.getValue()));
                        if (val < values.get(0) || values.get(1) < val)
                        {
                            sendTempNotification();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        return mView;

    }

    private void sendTempNotification() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        RemoteViews notificationLayout = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            notificationLayout = new RemoteViews(getActivity().getOpPackageName(),R.layout.custom_notification_layout);
        }
        notificationLayout.setTextViewText(R.id.title,"OVER THRESHOLD");
        notificationLayout.setTextViewText(R.id.info,"temperature over");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String sdfDate = simpleDateFormat.format(new Date());
        notificationLayout.setTextViewText(R.id.time,sdfDate);

        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_ID_TEMP)
                .setSmallIcon(R.drawable.small_icon)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCustomContentView(notificationLayout)
                .setSound(alarmSound)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(getNotificationId(),notification);
    }

    private int getNotificationId()
    {
        return (int) new Date().getTime();
    }
}