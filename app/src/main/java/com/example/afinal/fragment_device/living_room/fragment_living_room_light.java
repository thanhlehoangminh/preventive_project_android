package com.example.afinal.fragment_device.living_room;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.afinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class fragment_living_room_light extends Fragment{
    DatabaseReference mRoom;
    CircularSeekBar circularSeekBar;
    TextView txIntensity;
    ImageButton imgBtnOnOff;
    boolean checkOn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_living_room_light, container, false);

        circularSeekBar = view.findViewById(R.id.circularSeekBarLightLiving);
        txIntensity = view.findViewById(R.id.txLightLiving);
        imgBtnOnOff = view.findViewById(R.id.imgBtn_on_off_living);
        // firebase
        mRoom = FirebaseDatabase.getInstance().getReference();

        // Init from firebase
        mRoom.child("HOME").child("Living room").child("Lighting").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    checkOn = true;
                    circularSeekBar.setEnabled(true);
                    imgBtnOnOff.setImageResource(R.drawable.icon_btn_on);
                }
                else {
                    checkOn = false;
                    circularSeekBar.setEnabled(false);
                    imgBtnOnOff.setImageResource(R.drawable.icon_btn_off);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRoom.child("HOME").child("Living room").child("Lighting").child("Intensity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txIntensity.setText(snapshot.getValue().toString() + " %");
                float f= new Float(snapshot.getValue().toString());
                circularSeekBar.setProgress(f);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imgBtnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkOn) {
                    // push to firebase
                    mRoom.child("HOME").child("Living room").child("Lighting").child("Status").setValue("OFF");
                }
                else {
                    // push to firebase
                    mRoom.child("HOME").child("Living room").child("Lighting").child("Status").setValue("ON");
                }
            }
        });

        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                int intensity = (int)progress;
                txIntensity.setText(intensity + " %");
                mRoom.child("HOME").child("Living room").child("Lighting").child("Intensity").setValue(intensity);
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