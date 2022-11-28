package com.example.afinal.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyOTP extends AppCompatActivity {

    private ImageButton btnBack, btnSubmit;
    private EditText txtOTP;
    private TextView btnSignIn;
    private String codeBySystem;

    private String userPhone, whatToDo, phoneNo, userEmail;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.enter_otp_code);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();

        userPhone = getIntent().getStringExtra("userPhone");
        whatToDo = getIntent().getStringExtra("whatToDo");
        phoneNo = getIntent().getStringExtra("phoneNo");
        userEmail = getIntent().getStringExtra("userEmail");

        sendVerificationCodeToUser(phoneNo);
    }

    public void Init()
    {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnSubmit = (ImageButton) findViewById(R.id.btnSubmit);
        txtOTP = (EditText) findViewById(R.id.txtYourOTP);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
    }

    private void sendVerificationCodeToUser(String phoneNo)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,
                60,
                TimeUnit.SECONDS,
                (Activity) TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        txtOTP.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(verifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            if (whatToDo.equals("updateData")) {
                                updateOldUserData();
                            }
                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(verifyOTP.this, "Verify Not Completed! Try Again",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void updateOldUserData()
    {
        Intent intent = new Intent(getApplicationContext(), resetPassword.class);
        intent.putExtra("phoneNo", userPhone);
        intent.putExtra("userEmail",userEmail);
        startActivity(intent);
        finish();
    }

    public void callNextScreenFromOTP(View view)
    {
        String code = txtOTP.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }
}
