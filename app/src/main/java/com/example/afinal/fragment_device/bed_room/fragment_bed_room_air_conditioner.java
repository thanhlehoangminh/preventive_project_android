package com.example.afinal.fragment_device.bed_room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.afinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class fragment_bed_room_air_conditioner extends Fragment {
    private DatabaseReference mRoom;
    private CircularSeekBar circularSeekBar;
    private TextView txview,tx_onoff, tx_mode;
    private ImageButton imgbtn_onoff, imgbtn_dry,imgbtn_fan, imgbtn_cool;
    private boolean check_on;
    private int check_mode = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bed_room_air_conditioner, container, false);
        circularSeekBar = (CircularSeekBar) view.findViewById(R.id.circularSeekBar4);
        txview = (TextView) view.findViewById(R.id.textTempBed);
        imgbtn_onoff = (ImageButton) view.findViewById(R.id.btn_onOffBath);
        imgbtn_cool = (ImageButton) view.findViewById(R.id.btn_cool);
        imgbtn_dry = (ImageButton) view.findViewById(R.id.btn_dry);
        imgbtn_fan = (ImageButton) view.findViewById(R.id.btn_fan_Bath);
        tx_onoff = (TextView) view.findViewById(R.id.tx_onoff);
        tx_mode = (TextView) view.findViewById(R.id.tx_name_mode);
        // firebase
        mRoom = FirebaseDatabase.getInstance().getReference();

        // Init from firebase
        mRoom.child("HOME").child("Bed room").child("AC").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    check_mode = 1;
                    circularSeekBar.setEnabled(true);
                    check_on = true;
                    imgbtn_onoff.setImageResource(R.drawable.icon_on_air);
                    tx_onoff.setText("On");
                }
                else {
                    check_mode = 0;
                    circularSeekBar.setEnabled(false);
                    check_on = false;
                    imgbtn_onoff.setImageResource(R.drawable.icon_air_btn_off);
                    tx_onoff.setText("Off");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mRoom.child("HOME").child("Bed room").child("AC").child("Temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txview.setText(snapshot.getValue().toString() + "°C");
                float f= new Float(snapshot.getValue().toString());
                circularSeekBar.setProgress(f);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mRoom.child("HOME").child("Bed room").child("AC").child("Regime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("COOL")) {
                    tx_mode.setText("Cooling");
                    circularSeekBar.setCircleProgressColor(view.getResources().getColor(R.color.cool));
                    imgbtn_cool.setImageResource(R.drawable.icon_cool_air_on);
                    imgbtn_fan.setImageResource(R.drawable.icon_fan_air);
                    imgbtn_dry.setImageResource(R.drawable.icon_dry_air);
                }
                else if (snapshot.getValue().toString().equals("DRY")) {
                    tx_mode.setText("Drying");
                    circularSeekBar.setCircleProgressColor(view.getResources().getColor(R.color.dry));
                    imgbtn_dry.setImageResource(R.drawable.icon_dry_air_on);
                    imgbtn_cool.setImageResource(R.drawable.icon_cool_air);
                    imgbtn_fan.setImageResource(R.drawable.icon_fan_air);
                }
                else {
                    tx_mode.setText("Fan");
                    circularSeekBar.setCircleProgressColor(view.getResources().getColor(R.color.fan));
                    imgbtn_fan.setImageResource(R.drawable.icon_fan_air_on);
                    imgbtn_cool.setImageResource(R.drawable.icon_cool_air);
                    imgbtn_dry.setImageResource(R.drawable.icon_dry_air);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imgbtn_cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_mode == 1){
                    circularSeekBar.setProgress(20);
                    txview.setText("20°C");
                    mRoom.child("HOME").child("Bed room").child("AC").child("Regime").setValue("COOL");
                }
            }
        });
        imgbtn_dry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_mode == 1){
                    circularSeekBar.setProgress(30);
                    txview.setText("30°C");
                    mRoom.child("HOME").child("Bed room").child("AC").child("Regime").setValue("DRY");
                }
            }
        });
        imgbtn_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_mode == 1){
                    circularSeekBar.setProgress(25);
                    txview.setText("25°C");
                    mRoom.child("HOME").child("Bed room").child("AC").child("Regime").setValue("FAN");
                }
            }
        });
        imgbtn_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_on){
                    mRoom.child("HOME").child("Bed room").child("AC").child("Status").setValue("OFF");
                }
                else {
                    mRoom.child("HOME").child("Bed room").child("AC").child("Status").setValue("ON");
                }
            }
        });
        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                int tmp = (int)progress;
                txview.setText(tmp + "°C");
                mRoom.child("HOME").child("Bed room").child("AC").child("Temp").setValue(tmp);
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });
        return view;
    }

}