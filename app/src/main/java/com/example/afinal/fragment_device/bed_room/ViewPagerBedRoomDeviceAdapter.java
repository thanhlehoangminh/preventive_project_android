package com.example.afinal.fragment_device.bed_room;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerBedRoomDeviceAdapter extends FragmentStateAdapter {
    public ViewPagerBedRoomDeviceAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new fragment_bed_room_lamp();
            case 1:
                return new fragment_bed_room_air_conditioner();
            case 2:
                return new fragment_bed_room_tv();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
