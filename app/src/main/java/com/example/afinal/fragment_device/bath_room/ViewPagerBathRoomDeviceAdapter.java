package com.example.afinal.fragment_device.bath_room;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerBathRoomDeviceAdapter extends FragmentStateAdapter {
    public ViewPagerBathRoomDeviceAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new fragment_bath_room_light();
            case 1:
                return new fragment_bath_room_washing_machine();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
