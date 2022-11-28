package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.afinal.fragment_device.bath_room.ViewPagerBathRoomDeviceAdapter;
import com.example.afinal.fragment_device.bath_room.fragment_bath_room_light;
import com.example.afinal.fragment_device.bath_room.fragment_bath_room_washing_machine;
import com.example.afinal.fragment_device.bed_room.ViewPagerBedRoomDeviceAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DeviceBathRoomActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    TabLayout tabLayout;
    ImageButton btnBack;
    private String select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_bath_room);
        btnBack = findViewById(R.id.btnBack);
        tabLayout = findViewById(R.id.tab_layout_device_bath_room);
        viewPager = findViewById(R.id.view_pager_device_bath_room);
        ViewPagerBathRoomDeviceAdapter adapter = new ViewPagerBathRoomDeviceAdapter(this);
        viewPager.setAdapter(adapter);
        fragment_bath_room_light fragmentBathRoomLight = new fragment_bath_room_light();
        fragment_bath_room_washing_machine fragmentWashingMachine = new fragment_bath_room_washing_machine();

        select = getIntent().getStringExtra("selector");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager.setCurrentItem(Integer.parseInt(select));

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                        tab.setIcon(R.drawable.icon_lamp);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.icon_washer);
                        break;
                }
            }
        }).attach();

    }
}