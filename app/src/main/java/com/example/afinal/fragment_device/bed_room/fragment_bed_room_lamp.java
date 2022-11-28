package com.example.afinal.fragment_device.bed_room;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.afinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class fragment_bed_room_lamp extends Fragment {
    DatabaseReference mRoom;
    TextView txIntensity;
    SeekBar seekBar;
    Button btnVang, btnXanh, btnTim, btnHong, btnDo;
    ImageButton imgBtnOnOff;
    ImageView imageView;
    boolean checkOn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bed_room_lighting, container, false);

        seekBar = view.findViewById(R.id.seekBari);
        txIntensity = view.findViewById(R.id.intensityLamp);
        btnDo = view.findViewById(R.id.button_do);
        btnHong = view.findViewById(R.id.button_hong);
        btnTim = view.findViewById(R.id.button_tim);
        btnXanh = view.findViewById(R.id.button_xanh);
        btnVang = view.findViewById(R.id.button_vang);
        imgBtnOnOff = view.findViewById(R.id.imgBtn_on_off_lamp);
        imageView = view.findViewById(R.id.imageViewLamp);
        // firebase
        mRoom = FirebaseDatabase.getInstance().getReference();

        //Init from firebase
        mRoom.child("HOME").child("Bed room").child("Lamp").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    checkOn = true;
                    seekBar.setEnabled(true);
                    imgBtnOnOff.setImageResource(R.drawable.icon_btn_on);
                }
                else {
                    checkOn = false;
                    seekBar.setEnabled(false);
                    imgBtnOnOff.setImageResource(R.drawable.icon_btn_off);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRoom.child("HOME").child("Bed room").child("Lamp").child("Intensity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txIntensity.setText(snapshot.getValue().toString() + " %");
                int i= new Integer(snapshot.getValue().toString());
                seekBar.setProgress(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRoom.child("HOME").child("Bed room").child("Lamp").child("Choose color").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("YELLOW")) {
                    imageView.setImageResource(R.drawable.icon_lamp_vang);
                    seekBar.setProgressTintList(ColorStateList.valueOf(view.getResources().getColor(R.color.vang)));
                } else if (snapshot.getValue().toString().equals("BLUE")) {
                    imageView.setImageResource(R.drawable.icon_lamp_xanh);
                    seekBar.setProgressTintList(ColorStateList.valueOf(view.getResources().getColor(R.color.xanh)));
                } else if (snapshot.getValue().toString().equals("PURPLE")) {
                    imageView.setImageResource(R.drawable.icon_lighting_bed);
                    seekBar.setProgressTintList(ColorStateList.valueOf(view.getResources().getColor(R.color.tim)));
                }
                else if (snapshot.getValue().toString().equals("PINK")) {
                    imageView.setImageResource(R.drawable.icon_lamp_hong);
                    seekBar.setProgressTintList(ColorStateList.valueOf(view.getResources().getColor(R.color.hong)));
                }
                else {
                    imageView.setImageResource(R.drawable.icon_lamp_do);
                    seekBar.setProgressTintList(ColorStateList.valueOf(view.getResources().getColor(R.color.doLamp)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnVang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRoom.child("HOME").child("Bed room").child("Lamp").child("Choose color").setValue("YELLOW");
            }
        });

        btnXanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRoom.child("HOME").child("Bed room").child("Lamp").child("Choose color").setValue("BLUE");
            }
        });

        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRoom.child("HOME").child("Bed room").child("Lamp").child("Choose color").setValue("PURPLE");
            }
        });

        btnHong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRoom.child("HOME").child("Bed room").child("Lamp").child("Choose color").setValue("PINK");
            }
        });

        btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRoom.child("HOME").child("Bed room").child("Lamp").child("Choose color").setValue("RED");
            }
        });

        imgBtnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkOn) {
                    // push to firebase
                    mRoom.child("HOME").child("Bed room").child("Lamp").child("Status").setValue("OFF");
                }
                else {
                    mRoom.child("HOME").child("Bed room").child("Lamp").child("Status").setValue("ON");
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int intensity, boolean b) {
                txIntensity.setText(intensity + " %");
                mRoom.child("HOME").child("Bed room").child("Lamp").child("Intensity").setValue(intensity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }
}