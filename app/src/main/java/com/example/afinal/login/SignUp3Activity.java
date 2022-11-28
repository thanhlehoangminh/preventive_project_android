package com.example.afinal.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.R;
import com.example.afinal.option.CheckInternet;
import com.example.afinal.option.ProgressDialogNotify;
import com.hbb20.CountryCodePicker;

public class SignUp3Activity extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText txtPhone;
    private ImageButton btnNext, btnBack;
    private TextView btnSignIn;

    static final private String PHONE_PATTERN = "^[0-9]{10}$";

    private CheckInternet checkInternet;
    private ProgressDialogNotify progress;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_sign_up3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCode);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);

        checkInternet = new CheckInternet();
        progress = ProgressDialogNotify.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp3Activity.this,SignUp1Activity.class));
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp3Activity.this,SignInActivity.class));
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToOTPVerification();
//                startActivity(new Intent(SignUp3Activity.this,VerifyOTPActivity.class));
//                finish();
            }
        });
    }

    private void sendUserToOTPVerification()
    {
        txtPhone.setError(null);
        String getUserEnteredPhone = txtPhone.getText().toString().trim();

        boolean cancel = true;

        if (getUserEnteredPhone.matches("")){
            txtPhone.setError(getString(R.string.error_field_phone_empty));
            cancel = false;
        }
        else if (!getUserEnteredPhone.matches(PHONE_PATTERN)) {
            txtPhone.setError(getString(R.string.error_field_phone_required));
            cancel = false;
        }

        String _fullname = getIntent().getStringExtra("USER_FULLNAME");
        String _username = getIntent().getStringExtra("USER_NAME");
        String _email = getIntent().getStringExtra("USER_EMAIL");
        String _password = getIntent().getStringExtra("USER_PASSWORD");
        String _gender = getIntent().getStringExtra("USER_GENDER");
        String _date = getIntent().getStringExtra("USER_DATE");
        String subPhone = getUserEnteredPhone.substring(1);
        String _phone = "+" + countryCodePicker.getSelectedCountryCode() + subPhone;

        Log.d("PHONE",_phone);

        if(cancel)
        {
//            progress.showProgressDialog(getApplicationContext(),getString(R.string.progress_message_signup),false);
//
//            if (!checkInternet.isConnected(SignUp3Activity.this))
//            {
//                progress.stopProgressDialog();
//                Toast.makeText(SignUp3Activity.this,getString(R.string.noti_no_internet),Toast.LENGTH_LONG).show();
//            }
//            else
//            {
//                progress.stopProgressDialog();
                Intent intent = new Intent(SignUp3Activity.this, VerifyOTPActivity.class);
                intent.putExtra("FULLNAME", _fullname);
                intent.putExtra("USERNAME", _username);
                intent.putExtra("EMAIL", _email);
                intent.putExtra("PASSWORD", _password);
                intent.putExtra("GENDER", _gender);
                intent.putExtra("DATE", _date);
                intent.putExtra("PHONE", getUserEnteredPhone);
                intent.putExtra("PHONENO", _phone);
                startActivity(intent);
            //}
        }
    }
}
