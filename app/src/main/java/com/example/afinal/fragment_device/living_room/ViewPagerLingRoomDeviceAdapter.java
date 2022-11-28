package com.example.afinal.fragment_device.living_room;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.afinal.DeviceLivingRoomActivity;

public class ViewPagerLingRoomDeviceAdapter extends FragmentStateAdapter {
    public ViewPagerLingRoomDeviceAdapter (@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new fragment_living_room_light();
            case 1:
                return new fragment_living_room_air_conditioner();
            case 2:
                return new fragment_living_room_tv();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
