package com.example.afinal.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.MainActivity;
import com.example.afinal.R;
import com.example.afinal.option.CheckInternet;
import com.example.afinal.option.ProgressDialogNotify;
import com.example.afinal.option.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private ImageButton btnSignIn;
    private TextView btnForgotpw,btnSignUp;
    private EditText txtEmail, txtPassword;
    private Button openEye;
    private CheckBox checkboxRememberMe;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private boolean checkEye = true;

    static final private String EMAIL_PATTERN = "[a-zA-Z0-9.-_]+@[a-z]+\\.+[a-z]+";
    static final private String PHONE_PATTERN = "^[0-9]{10}$";
    static final public String PATH_PHONE = "1";
    static final public String PATH_EMAIL = "2";

    public String userName, userEmail, userPhone, userPassword, userPath, userID;

    private CheckInternet checkInternet;
    private ProgressDialogNotify progress;

    public String _path;
    public String _userID;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();

        //check weather email or phone and password is already saved in Shared Preferences or not
        SessionManager sessionManager = new SessionManager(SignInActivity.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()) {
            HashMap<String, String> rememberDetails = sessionManager.getRememberMeDetailFromSession();
            txtEmail.setText(rememberDetails.get(SessionManager.KEY_SESSIONPHONENUMBER));
            txtPassword.setText(rememberDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();

        checkInternet = new CheckInternet();
        progress = ProgressDialogNotify.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        closePassword();
        openEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEye)
                {
                    closePassword();
                    checkEye = false;
                }
                else
                {
                    openPassword();
                    checkEye = true;
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.showProgressDialog(SignInActivity.this,getString(R.string.progress_message_check_internet),false);
                if (!checkInternet.isConnected(SignInActivity.this)) {
                    progress.stopProgressDialog();
                    Toast.makeText(SignInActivity.this,getString(R.string.noti_no_internet),Toast.LENGTH_LONG).show();
                }
                else {
                    progress.stopProgressDialog();
                    Intent intent = new Intent(SignInActivity.this, SignUp1Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        btnForgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.showProgressDialog(SignInActivity.this,getString(R.string.progress_message_check_internet),false);
                if (!checkInternet.isConnected(SignInActivity.this)) {
                    progress.stopProgressDialog();
                    Toast.makeText(SignInActivity.this,getString(R.string.noti_no_internet),Toast.LENGTH_LONG).show();
                }
                else {
                    progress.stopProgressDialog();
                    Intent intent = new Intent(SignInActivity.this, forgotPassWord.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    public void Init()
    {
        btnSignIn = (ImageButton) findViewById(R.id.btnLogin);
        btnForgotpw = (TextView) findViewById(R.id.btnForgotpw);
        btnSignUp = (TextView) findViewById(R.id.btnSignUp);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        openEye = (Button) findViewById(R.id.openEye);
        checkboxRememberMe = (CheckBox) findViewById(R.id.remmberCheck);
    }

    private void Login()
    {

        //reset error
        txtEmail.setError(null);
        txtPassword.setError(null);

        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        boolean cancel = true;

        //check valid email
        if (email.matches("")) {
            txtEmail.setError(getString(R.string.error_field_email_empty));
            cancel = false;
        }

        //check valid password
        if (password.isEmpty()) {
            txtPassword.setError(getString(R.string.error_field_password_empty));
            cancel = false;
        }

        if (checkboxRememberMe.isChecked()) {
            SessionManager sessionManager = new SessionManager(SignInActivity.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberSession(email,password);
        }

        //check email and password with database
        if (cancel) {

            progress.showProgressDialog(SignInActivity.this,getString(R.string.progress_message_login),false);

            if (!checkInternet.isConnected(SignInActivity.this)) {
                progress.stopProgressDialog();
                Toast.makeText(SignInActivity.this,getString(R.string.noti_no_internet),Toast.LENGTH_LONG).show();
            }
            else {
                if (email.matches(PHONE_PATTERN)) {
                    mRef = mDatabase.getReference("USER/PHONE");
                    mRef.child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {

                                if (task.getResult().exists()) {

                                    DataSnapshot dataSnapshot = task.getResult();
                                    userPassword = String.valueOf(dataSnapshot.child("userPassword").getValue());
                                    userName = String.valueOf(dataSnapshot.child("userName").getValue());
                                    userEmail = String.valueOf(dataSnapshot.child("userEmail").getValue());
                                    userPhone = String.valueOf(dataSnapshot.child("userPhone").getValue());

                                    userPath = PATH_PHONE;
                                    userID = email;

                                    if (password.equals(userPassword)) {
                                        progress.stopProgressDialog();
                                        sendUserToMainActivity();
                                    }
                                    else {
                                        progress.stopProgressDialog();
                                        Toast.makeText(SignInActivity.this,getString(R.string.noti_incorrect_password),Toast.LENGTH_LONG).show();
                                    }

                                }
                                else {
                                    progress.stopProgressDialog();
                                    Toast.makeText(SignInActivity.this,getString(R.string.noti_incorrect_phone),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });

                }

                else if (!email.matches(PHONE_PATTERN)){
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progress.stopProgressDialog();
                                userPath = PATH_EMAIL;
                                _userID = mAuth.getCurrentUser().getUid();
                            }
                            else {
                                progress.stopProgressDialog();
                                Toast.makeText(SignInActivity.this,getString(R.string.noti_incorrect_email),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        }
    }

    private void sendUserToMainActivity()
    {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.putExtra("KEY_NAME", userName);
        intent.putExtra("KEY_EMAIL",userEmail);
        intent.putExtra("KEY_PHONE", userPhone);
        intent.putExtra("KEY_PASSWORD",userPassword);
        intent.putExtra("KEY_PATH",userPath);
        intent.putExtra("KEY_ID",userID);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void closePassword()
    {
        openEye.setBackgroundResource(R.drawable.eye_open);
        txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPassword.setSelection(txtPassword.getText().length());
    }

    private void openPassword()
    {
        openEye.setBackgroundResource(R.drawable.eye_close);
        txtPassword.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        txtPassword.setSelection(txtPassword.getText().length());
    }
}
