package com.example.afinal;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.afinal.fragment_device.living_room.ViewPagerLingRoomDeviceAdapter;
import com.example.afinal.fragment_device.living_room.fragment_living_room_air_conditioner;
import com.example.afinal.fragment_device.living_room.fragment_living_room_light;
import com.example.afinal.fragment_device.living_room.fragment_living_room_tv;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DeviceLivingRoomActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    TabLayout tabLayout;
    ImageButton btnBack;
    private String select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_living_room);
        btnBack = findViewById(R.id.btnBack);
        tabLayout = findViewById(R.id.tab_layout_device_living_room);
        viewPager = findViewById(R.id.view_pager_device_living_room);
        ViewPagerLingRoomDeviceAdapter adapter = new ViewPagerLingRoomDeviceAdapter(this);
        viewPager.setAdapter(adapter);

        fragment_living_room_light fragmentLivingRoomLight = new fragment_living_room_light();
        fragment_living_room_air_conditioner fragmentLivingRoomAirConditioner = new fragment_living_room_air_conditioner();
        fragment_living_room_tv fragmentLivingRoomTv = new fragment_living_room_tv();

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
                        tab.setIcon(R.drawable.icon_ac);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.icon_tv);
                        break;
                }
            }
        }).attach();
    }
}