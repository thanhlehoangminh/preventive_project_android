package com.example.afinal.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.afinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    private PinView pinFromUser;
    private TextView btnSignIn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataPhone, mDataEmail;
    private FirebaseDatabase mFbData;
    private String codeBySystem;
    private TextView tvOTP;

    private String fullname, phoneNo, email, username, phone, password, date, gender;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.verify_otp);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pinFromUser = (PinView) findViewById(R.id.pin_view);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        tvOTP = (TextView) findViewById(R.id.tvOTP);

        fullname = getIntent().getStringExtra("FULLNAME");
        username = getIntent().getStringExtra("USERNAME");
        email = getIntent().getStringExtra("EMAIL");
        password = getIntent().getStringExtra("PASSWORD");
        date = getIntent().getStringExtra("DATE");
        gender = getIntent().getStringExtra("GENDER");
        phone = getIntent().getStringExtra("PHONE");
        phoneNo = getIntent().getStringExtra("PHONENO");

        tvOTP.setText("Enter one time password sent on " + phoneNo);

        mAuth = FirebaseAuth.getInstance();
        //mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        mFbData = FirebaseDatabase.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerifyOTPActivity.this,SignInActivity.class));
                finish();
            }
        });

        sendVerificationCodeToUser(phoneNo);
    }

    private void sendVerificationCodeToUser(String phone)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null)
                    {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTPActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            };

    private void verifyCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            USER_INFOR addUser = new USER_INFOR(fullname,username,email,password,phone,date,gender);
                            createAccount(email,password, addUser);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTPActivity.this, "Verifycation Not Completed! Try again",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(VerifyOTPActivity.this, "Phone is registered",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void storeNewUsersDataByPhone(USER_INFOR newUser)
    {
        mDataPhone = mFbData.getReference("USER/PHONE");
        mDataPhone.child(phone).setValue(newUser);
    }

    public void clickVerifyOTP(View view)
    {
        String code = pinFromUser.getText().toString();
        if (!code.isEmpty())
        {
            verifyCode(code);
        }
    }

    private void createAccount(String _email, String _password, USER_INFOR newUser)
    {
        mAuth.createUserWithEmailAndPassword(_email,_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseDatabase.getInstance().getReference("USER/UID")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            storeNewUsersDataByPhone(newUser);
                                            Toast.makeText(VerifyOTPActivity.this, "Verifycation Completed! ",Toast.LENGTH_LONG).show();
                                        }
                                    });

                            startActivity(new Intent(VerifyOTPActivity.this,SignInActivity.class));

                        }
                        else
                        {
                            Toast.makeText(VerifyOTPActivity.this, "Email is existed ",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
