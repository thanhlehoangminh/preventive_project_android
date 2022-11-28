package com.example.afinal.fragment_device.bath_room;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.afinal.R;

public class fragment_bath_room_washing_machine extends Fragment {

    private TextView tx_time,tx_status;
    private ImageButton btn_onoff_wash, btn_cotton, btn_mix, btn_sportwear, btn_babycare, btn_run;
    private boolean check_onoff = true, counterIsActive = false;
    private int check_mode = 3, check = 0;
    CountDownTimer countDownTimer;

    public void reset(){
        btn_run.setImageResource(R.drawable.icon_start_wm);
        check_onoff = true;
        check = 1;
        switch (check_mode){
            case 1:{
                tx_time.setText("01 : 00");
                tx_status.setText("Ready");
                break;
            }
            case 2:{
                tx_time.setText("01 : 20");
                tx_status.setText("Ready");
                break;
            }
            case 3:{
                tx_time.setText("02 : 10");
                tx_status.setText("Ready");
                break;
            }
            case 4:{
                tx_time.setText("02 : 00");
                tx_status.setText("Ready");
                break;
            }
        }
        countDownTimer.cancel();
        counterIsActive = false;
    }
    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - (minutes * 60);
        String secondsString = Integer.toString(seconds);
        String minutesString = Integer.toString(minutes);
        if (seconds <= 9){
            secondsString = "0" + secondsString;
        }
        if (minutes <= 9){
            minutesString = "0" + minutesString;
        }
        tx_time.setText(minutesString +" : "+ secondsString);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bath_room_washing_machine, container, false);
        tx_time = (TextView) view.findViewById(R.id.tx_time);
        tx_status = (TextView) view.findViewById(R.id.tx_status);
        btn_onoff_wash = (ImageButton) view.findViewById(R.id.btn_onoff_wash);
        btn_run = (ImageButton) view.findViewById(R.id.btn_run);
        btn_cotton = (ImageButton) view.findViewById(R.id.btn_cotton);
        btn_babycare = (ImageButton) view.findViewById(R.id.btn_babycare);
        btn_sportwear = (ImageButton) view.findViewById(R.id.btn_sportwear);
        btn_mix = (ImageButton) view.findViewById(R.id.btn_Mix);

        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterIsActive){
                    reset();
                }else {
                    counterIsActive = true;
                    btn_run.setImageResource(R.drawable.icon_start_wm_on);
                    tx_status.setText("Start");
                    check_onoff = false;
                    check = 0;
                    switch (check_mode){
                        case 1:{
                            countDownTimer = new CountDownTimer(60 * 1000 + 100, 1000) {
                                @Override
                                public void onTick(long l) {
                                    updateTimer((int) l/1000);
                                }
                                @Override
                                public void onFinish() {
                                    MediaPlayer mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.music);
                                    mediaPlayer.start();
                                    btn_run.setEnabled(true);
                                    reset();
                                }
                            }.start();
                            break;
                        }
                        case 2:{
                            countDownTimer = new CountDownTimer(80 * 1000 + 100, 1000) {
                                @Override
                                public void onTick(long l) {
                                    updateTimer((int) l/1000);
                                }
                                @Override
                                public void onFinish() {
                                    btn_run.setEnabled(true);
                                    reset();
                                }
                            }.start();
                            break;
                        }
                        case 3:{
                            countDownTimer = new CountDownTimer(130 * 1000 + 100, 1000) {
                                @Override
                                public void onTick(long l) {
                                    updateTimer((int) l/1000);
                                }
                                @Override
                                public void onFinish() {
                                    btn_run.setEnabled(true);
                                    reset();
                                }
                            }.start();
                            break;
                        }
                        case 4:{
                            countDownTimer = new CountDownTimer(120 * 1000 + 100, 1000) {
                                @Override
                                public void onTick(long l) {
                                    updateTimer((int) l/1000);
                                }
                                @Override
                                public void onFinish() {
                                    btn_run.setEnabled(true);
                                    reset();
                                }
                            }.start();
                            break;
                        }
                    }
                }
            }
        });

        btn_babycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 1){
                    tx_time.setText("01:00");
                    btn_babycare.setImageResource(R.drawable.icon_baby_care_on);
                    btn_sportwear.setImageResource(R.drawable.icon_sport_wear);
                    btn_cotton.setImageResource(R.drawable.icon_cotton);
                    btn_mix.setImageResource(R.drawable.icon_mix);
                    check_mode = 1;
                }
            }
        });
        btn_sportwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 1){
                    tx_time.setText("01 : 20");
                    btn_babycare.setImageResource(R.drawable.icon_baby_care);
                    btn_sportwear.setImageResource(R.drawable.icon_sport_wear_on);
                    btn_cotton.setImageResource(R.drawable.icon_cotton);
                    btn_mix.setImageResource(R.drawable.icon_mix);
                    check_mode = 2;
                }
            }
        });
        btn_cotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 1){
                    tx_time.setText("02 : 10");
                    btn_babycare.setImageResource(R.drawable.icon_baby_care);
                    btn_sportwear.setImageResource(R.drawable.icon_sport_wear);
                    btn_cotton.setImageResource(R.drawable.icon_cotton_on);
                    btn_mix.setImageResource(R.drawable.icon_mix);
                    check_mode = 3;
                }
            }
        });
        btn_mix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 1){
                    tx_time.setText("02 : 00");
                    btn_babycare.setImageResource(R.drawable.icon_baby_care);
                    btn_sportwear.setImageResource(R.drawable.icon_sport_wear);
                    btn_cotton.setImageResource(R.drawable.icon_cotton);
                    btn_mix.setImageResource(R.drawable.icon_mix_on);
                    check_mode = 4;
                }
            }
        });

        btn_onoff_wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_onoff){
                    btn_onoff_wash.setImageResource(R.drawable.icon_btn_on);
                    check = 1;
                    check_onoff = false;
                }
                else {
                    btn_onoff_wash.setImageResource(R.drawable.icon_btn_off);
                    check = 0;
                    check_onoff = true;
                }
            }
        });

        return view;
    }
}