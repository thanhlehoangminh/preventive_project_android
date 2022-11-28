package com.example.afinal.fragment_room;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.afinal.DeviceBedRoomActivity;
import com.example.afinal.DeviceLivingRoomActivity;
import com.example.afinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragmentBedRoom extends Fragment {

    DatabaseReference mHome;
    Switch swlamp, swac, swtv, swss;
    ImageButton imgac, imgtv, imgss, imglamp;
    TextView bed_light_tvname, bed_light_tvdevice, bed_light_tvonoff;
    TextView bed_ac_tvname, bed_ac_tvdevice, bed_ac_tvonoff;
    TextView bed_tv_tvname, bed_tv_tvdevice, bed_tv_tvonoff;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bed_room, container, false);
        //Light_bed
        bed_light_tvname = (TextView) view.findViewById(R.id.name_lamp);
        bed_light_tvdevice = (TextView) view.findViewById(R.id.lamp_device);
        bed_light_tvonoff = (TextView) view.findViewById(R.id.on_off_lamp);
        swlamp = (Switch) view.findViewById(R.id.switch_lamp_off);
        imglamp = (ImageButton) view.findViewById(R.id.lamp_off);
        //Ac_Bed
        bed_ac_tvname = (TextView) view.findViewById(R.id.name_ac_bed_off);
        bed_ac_tvdevice = (TextView) view.findViewById(R.id.ac_bed_device);
        bed_ac_tvonoff = (TextView) view.findViewById(R.id.on_off_ac_bed);
        swac = (Switch) view.findViewById(R.id.switch_ac_bed_off);
        imgac = (ImageButton) view.findViewById(R.id.ac_bed_off);
        //TV_Bed
        bed_tv_tvname = (TextView) view.findViewById(R.id.name_tv_bed);
        bed_tv_tvdevice = (TextView) view.findViewById(R.id.tv_bed_device);
        bed_tv_tvonoff = (TextView) view.findViewById(R.id.on_off_tv_bed);
        swtv = (Switch) view.findViewById(R.id.switch_tv_bed_off);
        imgtv = (ImageButton) view.findViewById(R.id.tv_bed_off);
        // Firebase
        mHome = FirebaseDatabase.getInstance().getReference();

        // Lamp
        swlamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // push to firebase
                    mHome.child("HOME").child("Bed room").child("Lamp").child("Status").setValue("ON");
                    mHome.child("HOME").child("Bed room").child("Lamp").child("Intensity").setValue("45");
                }
                else {
                    // push to firebase
                    mHome.child("HOME").child("Bed room").child("Lamp").child("Status").setValue("OFF");
                    mHome.child("HOME").child("Bed room").child("Lamp").child("Intensity").setValue("0");
                }
            }
        });
        mHome.child("HOME").child("Bed room").child("Lamp").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    swlamp.setChecked(true);
                    imglamp.setBackgroundResource(R.drawable.bg_roomitem_on);
                    bed_light_tvname.setTextColor(view.getResources().getColor(R.color.tvnameon));
                    bed_light_tvdevice.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                    bed_light_tvonoff.setText("ON");
                    bed_light_tvonoff.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                }
                else {
                    swlamp.setChecked(false);
                    imglamp.setBackgroundResource(R.drawable.bg_roomitem_off);
                    bed_light_tvname.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bed_light_tvdevice.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bed_light_tvonoff.setText("OFF");
                    bed_light_tvonoff.setTextColor(view.getResources().getColor(R.color.tvoff));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mHome.child("HOME").child("Bed room").child("Lamp").child("Intensity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bed_light_tvdevice.setText(snapshot.getValue().toString() + " %");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Air conditioner
        swac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // push to firebase
                    mHome.child("HOME").child("Bed room").child("AC").child("Status").setValue("ON");
                }
                else {
                    // push to firebase
                    mHome.child("HOME").child("Bed room").child("AC").child("Status").setValue("OFF");
                }
            }
        });
        mHome.child("HOME").child("Bed room").child("AC").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    swac.setChecked(true);
                    imgac.setBackgroundResource(R.drawable.bg_roomitem_on);
                    bed_ac_tvname.setTextColor(view.getResources().getColor(R.color.tvnameon));
                    bed_ac_tvdevice.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                    bed_ac_tvonoff.setText("ON");
                    bed_ac_tvonoff.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                }
                else {
                    swac.setChecked(false);
                    imgac.setBackgroundResource(R.drawable.bg_roomitem_off);
                    bed_ac_tvname.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bed_ac_tvdevice.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bed_ac_tvonoff.setText("OFF");
                    bed_ac_tvonoff.setTextColor(view.getResources().getColor(R.color.tvoff));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // TV
        swtv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // push to firebase
                    mHome.child("HOME").child("Bed room").child("TV").child("Status").setValue("ON");
                }
                else {
                    // push to firebase
                    mHome.child("HOME").child("Bed room").child("TV").child("Status").setValue("OFF");
                }
            }
        });
        mHome.child("HOME").child("Bed room").child("TV").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    swtv.setChecked(true);
                    imgtv.setBackgroundResource(R.drawable.bg_roomitem_on);
                    bed_tv_tvname.setTextColor(view.getResources().getColor(R.color.tvnameon));
                    bed_tv_tvdevice.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                    bed_tv_tvonoff.setText("ON");
                    bed_tv_tvonoff.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                }
                else {
                    swtv.setChecked(false);
                    imgtv.setBackgroundResource(R.drawable.bg_roomitem_off);
                    bed_tv_tvname.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bed_tv_tvdevice.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bed_tv_tvonoff.setText("OFF");
                    bed_tv_tvonoff.setTextColor(view.getResources().getColor(R.color.tvoff));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imglamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DeviceBedRoomActivity.class);
                i.putExtra("selector","0");
                startActivity(i);
            }
        });
        imgac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DeviceBedRoomActivity.class);
                i.putExtra("selector","1");
                startActivity(i);
            }
        });
        imgtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DeviceBedRoomActivity.class);
                i.putExtra("selector","2");
                startActivity(i);
            }
        });
        return view;
    }
}