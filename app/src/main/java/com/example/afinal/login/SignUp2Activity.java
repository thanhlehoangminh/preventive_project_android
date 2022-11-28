package com.example.afinal.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.afinal.R;
import com.example.afinal.option.CheckInternet;
import com.example.afinal.option.ProgressDialogNotify;

import java.util.Calendar;
import java.util.Date;

public class SignUp2Activity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private DatePicker datePicker;
    private ImageButton btnNext, btnBack;
    private TextView btnSignIn;
    private RadioButton selectGender;

    private CheckInternet checkInternet;
    private ProgressDialogNotify progress;

    private String userFullname,userName, userEmail, userPassword;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_sign_up2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        radioGroup = (RadioGroup) findViewById(R.id.rdGroup);
        datePicker = (DatePicker) findViewById(R.id.chooseDate);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);

        userFullname = getIntent().getStringExtra("USER_FULLNAME");
        userName = getIntent().getStringExtra("USER_USERNAME");
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        userPassword = getIntent().getStringExtra("USER_PASSWORD");


        checkInternet = new CheckInternet();
        progress = ProgressDialogNotify.getInstance();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToSignUp3();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp2Activity.this,SignUp1Activity.class));
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp2Activity.this,SignInActivity.class));
                finish();
            }
        });
    }

    private void sendUserToSignUp3()
    {
        selectGender = findViewById(radioGroup.getCheckedRadioButtonId());
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        boolean cancel = true;

        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(SignUp2Activity.this, "Please Select Gender",Toast.LENGTH_LONG).show();
            cancel = false;
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int isAgeValid = currentYear - year;
        if (isAgeValid < 15)
        {
            Toast.makeText(SignUp2Activity.this, "You are not eligible to apply",Toast.LENGTH_LONG).show();
            cancel = false;
        }

        if (cancel)
        {
            progress.showProgressDialog(SignUp2Activity.this,getString(R.string.progress_message_signup),false);

            if (!checkInternet.isConnected(this))
            {
                progress.stopProgressDialog();
                Toast.makeText(SignUp2Activity.this,getString(R.string.noti_no_internet),Toast.LENGTH_LONG).show();
            }
            else
            {
                String _gender = selectGender.getText().toString();
                String date = day + "/" + month + "/" + year;
                progress.stopProgressDialog();
                Intent intent = new Intent(SignUp2Activity.this, SignUp3Activity.class);
                intent.putExtra("USER_GENDER", _gender);
                intent.putExtra("USER_DATE", date);
                intent.putExtra("USER_FULLNAME",userFullname);
                intent.putExtra("USER_NAME",userName);
                intent.putExtra("USER_EMAIL",userEmail);
                intent.putExtra("USER_PASSWORD",userPassword);
                startActivity(intent);
            }
        }
    }
}
