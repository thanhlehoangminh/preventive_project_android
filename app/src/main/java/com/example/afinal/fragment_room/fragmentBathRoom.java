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

import com.example.afinal.DeviceBathRoomActivity;
import com.example.afinal.DeviceBedRoomActivity;
import com.example.afinal.DeviceLivingRoomActivity;
import com.example.afinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragmentBathRoom extends Fragment {

    DatabaseReference mHome;
    Switch swlight, swpana, swdryer, swwash;
    ImageButton imgpana, imgdryer, imgwash, imglight;
    TextView bath_light_tvname, bath_light_tvdevice, bath_light_tvonoff;
    TextView bath_pana_tvname, bath_pana_tvdevice, bath_pana_tvonoff;
    TextView bath_dryer_tvname, bath_dryer_tvdevice, bath_dryer_tvonoff;
    TextView bath_wash_tvname, bath_wash_tvdevice, bath_wash_tvonoff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bath_room, container, false);
        //Light_bath
        bath_light_tvname = (TextView) view.findViewById(R.id.name_light_bath);
        bath_light_tvdevice = (TextView) view.findViewById(R.id.light_bath_device);
        bath_light_tvonoff = (TextView) view.findViewById(R.id.on_off_light_bath);
        swlight = (Switch) view.findViewById(R.id.switch_light_bath_off);
        imglight = (ImageButton) view.findViewById(R.id.light_bath_off);
        //Wash_bath
        bath_wash_tvname = (TextView) view.findViewById(R.id.name_wash);
        bath_wash_tvdevice = (TextView) view.findViewById(R.id.wash_device);
        bath_wash_tvonoff = (TextView) view.findViewById(R.id.on_off_wash);
        swwash = (Switch) view.findViewById(R.id.switch_wash_off);
        imgwash = (ImageButton) view.findViewById(R.id.wash_off);
        // Firebase
        mHome = FirebaseDatabase.getInstance().getReference();

        // Lighting
        swlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // push to firebase
                    mHome.child("HOME").child("Bath room").child("Lighting").child("Status").setValue("ON");
                    mHome.child("HOME").child("Bath room").child("Lighting").child("Intensity").setValue("65");
                }
                else {
                    // push to firebase
                    mHome.child("HOME").child("Bath room").child("Lighting").child("Status").setValue("OFF");
                    mHome.child("HOME").child("Bath room").child("Lighting").child("Intensity").setValue("0");
                }
            }
        });
        // add value form firebase
        mHome.child("HOME").child("Bath room").child("Lighting").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().equals("ON")) {
                    swlight.setChecked(true);
                    bath_light_tvonoff.setText("ON");
                    imglight.setBackgroundResource(R.drawable.bg_roomitem_on);
                    bath_light_tvname.setTextColor(view.getResources().getColor(R.color.tvnameon));
                    bath_light_tvdevice.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                    bath_light_tvonoff.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                }
                else {
                    swlight.setChecked(false);
                    imglight.setBackgroundResource(R.drawable.bg_roomitem_off);
                    bath_light_tvname.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bath_light_tvdevice.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bath_light_tvonoff.setText("OFF");
                    bath_light_tvonoff.setTextColor(view.getResources().getColor(R.color.tvoff));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mHome.child("HOME").child("Bath room").child("Lighting").child("Intensity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bath_light_tvdevice.setText(snapshot.getValue().toString() + " %");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Washing machine
        swwash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // push to firebase
                    mHome.child("HOME").child("Bath room").child("Washing machine").child("Status").setValue("ON");
                }
                else {
                    // push to firebase
                    mHome.child("HOME").child("Bath room").child("Washing machine").child("Status").setValue("OFF");
                }
            }
        });
        mHome.child("HOME").child("Bath room").child("Washing machine").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    swwash.setChecked(true);
                    imgwash.setBackgroundResource(R.drawable.bg_roomitem_on);
                    bath_wash_tvname.setTextColor(view.getResources().getColor(R.color.tvnameon));
                    bath_wash_tvdevice.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                    bath_wash_tvonoff.setText("ON");
                    bath_wash_tvonoff.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                }
                else {
                    swwash.setChecked(false);
                    imgwash.setBackgroundResource(R.drawable.bg_roomitem_off);
                    bath_wash_tvname.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bath_wash_tvdevice.setTextColor(view.getResources().getColor(R.color.tvoff));
                    bath_wash_tvonoff.setText("OFF");
                    bath_wash_tvonoff.setTextColor(view.getResources().getColor(R.color.tvoff));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        imglight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DeviceBathRoomActivity.class);
                i.putExtra("selector","0");
                startActivity(i);
            }
        });
        imgwash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DeviceBathRoomActivity.class);
                i.putExtra("selector","1");
                startActivity(i);
            }
        });
        return view;
    }
}