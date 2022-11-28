package com.example.afinal.fragment_room;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.afinal.DeviceLivingRoomActivity;
import com.example.afinal.DeviceLivingRoomActivity;
import com.example.afinal.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragmentLivingRoom extends Fragment {
    DatabaseReference mHome;
    Switch swlight, swac, swtv, swaudio;
    ImageButton imgac, imgtv, imgaudio, imglight;
    TextView lv_light_tvname, lv_light_tvdevice, lv_light_tvonoff;
    TextView lv_ac_tvname, lv_ac_tvdevice, lv_ac_tvonoff;
    TextView lv_tv_tvname, lv_tv_tvdevice, lv_tv_tvonoff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_living_room, container, false);
        //Light_Living
        lv_light_tvname = (TextView) view.findViewById(R.id.name_light);
        lv_light_tvdevice = (TextView) view.findViewById(R.id.light_device);
        lv_light_tvonoff = (TextView) view.findViewById(R.id.on_off_light);
        swlight = (Switch) view.findViewById(R.id.switch_light_off);
        imglight = (ImageButton) view.findViewById(R.id.light_off);
        //Ac_Living
        lv_ac_tvname = (TextView) view.findViewById(R.id.name_ac);
        lv_ac_tvdevice = (TextView) view.findViewById(R.id.ac_device);
        lv_ac_tvonoff = (TextView) view.findViewById(R.id.on_off_ac);
        swac = (Switch) view.findViewById(R.id.switch_ac_off);
        imgac = (ImageButton) view.findViewById(R.id.ac_off);

        //TV_Living
        lv_tv_tvname = (TextView) view.findViewById(R.id.name_tv);
        lv_tv_tvdevice = (TextView) view.findViewById(R.id.tv_device);
        lv_tv_tvonoff = (TextView) view.findViewById(R.id.on_off_tv);
        swtv = (Switch) view.findViewById(R.id.switch_tv_off);
        imgtv = (ImageButton) view.findViewById(R.id.tv_off);
        // Firebase
        mHome = FirebaseDatabase.getInstance().getReference();

        // Lighting
        swlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // push to firebase
                    mHome.child("HOME").child("Living room").child("Lighting").child("Status").setValue("ON");
                    mHome.child("HOME").child("Living room").child("Lighting").child("Intensity").setValue("75");
                }
                else {
                    // push to firebase
                    mHome.child("HOME").child("Living room").child("Lighting").child("Status").setValue("OFF");
                    mHome.child("HOME").child("Living room").child("Lighting").child("Intensity").setValue("0");
                }
            }
        });
        mHome.child("HOME").child("Living room").child("Lighting").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    swlight.setChecked(true);
                    imglight.setBackgroundResource(R.drawable.bg_roomitem_on);
                    lv_light_tvname.setTextColor(view.getResources().getColor(R.color.tvnameon));
                    lv_light_tvdevice.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                    lv_light_tvonoff.setText("ON");
                    lv_light_tvonoff.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                }
                else {
                    swlight.setChecked(false);
                    imglight.setBackgroundResource(R.drawable.bg_roomitem_off);
                    lv_light_tvname.setTextColor(view.getResources().getColor(R.color.tvoff));
                    lv_light_tvdevice.setTextColor(view.getResources().getColor(R.color.tvoff));
                    lv_light_tvonoff.setText("OFF");
                    lv_light_tvonoff.setTextColor(view.getResources().getColor(R.color.tvoff));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mHome.child("HOME").child("Living room").child("Lighting").child("Intensity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lv_light_tvdevice.setText(snapshot.getValue().toString() + " %");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Air conditioner
        swac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    // push to firebase
                    mHome.child("HOME").child("Living room").child("AC").child("Status").setValue("ON");
                }
                else {
                    // push to firebase
                    mHome.child("HOME").child("Living room").child("AC").child("Status").setValue("OFF");
                }
            }
        });
        mHome.child("HOME").child("Living room").child("AC").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    swac.setChecked(true);
                    imgac.setBackgroundResource(R.drawable.bg_roomitem_on);
                    lv_ac_tvname.setTextColor(view.getResources().getColor(R.color.tvnameon));
                    lv_ac_tvdevice.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                    lv_ac_tvonoff.setText("ON");
                    lv_ac_tvonoff.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                }
                else {
                    swac.setChecked(false);
                    imgac.setBackgroundResource(R.drawable.bg_roomitem_off);
                    lv_ac_tvname.setTextColor(view.getResources().getColor(R.color.tvoff));
                    lv_ac_tvdevice.setTextColor(view.getResources().getColor(R.color.tvoff));
                    lv_ac_tvonoff.setText("OFF");
                    lv_ac_tvonoff.setTextColor(view.getResources().getColor(R.color.tvoff));
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
                if(b){
                    // push to firebase
                    mHome.child("HOME").child("Living room").child("TV").child("Status").setValue("ON");
                }
                else {
                    // push to firebase
                    mHome.child("HOME").child("Living room").child("TV").child("Status").setValue("OFF");
                }
            }
        });
        mHome.child("HOME").child("Living room").child("TV").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("ON")) {
                    swtv.setChecked(true);
                    imgtv.setBackgroundResource(R.drawable.bg_roomitem_on);
                    lv_tv_tvname.setTextColor(view.getResources().getColor(R.color.tvnameon));
                    lv_tv_tvdevice.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                    lv_tv_tvonoff.setText("ON");
                    lv_tv_tvonoff.setTextColor(view.getResources().getColor(R.color.tvdeviceon));
                }
                else {
                    swtv.setChecked(false);
                    imgtv.setBackgroundResource(R.drawable.bg_roomitem_off);
                    lv_tv_tvname.setTextColor(view.getResources().getColor(R.color.tvoff));
                    lv_tv_tvdevice.setTextColor(view.getResources().getColor(R.color.tvoff));
                    lv_tv_tvonoff.setText("OFF");
                    lv_tv_tvonoff.setTextColor(view.getResources().getColor(R.color.tvoff));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imglight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DeviceLivingRoomActivity.class);
                i.putExtra("selector","0");
                startActivity(i);
            }
        });
        imgac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DeviceLivingRoomActivity.class);
                i.putExtra("selector","1");
                startActivity(i);
            }
        });
        imgtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DeviceLivingRoomActivity.class);
                i.putExtra("selector","2");
                startActivity(i);
            }
        });
        return view;
    }

}