package com.example.afinal.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.R;
import com.example.afinal.option.CheckInternet;
import com.example.afinal.option.ProgressDialogNotify;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp1Activity extends AppCompatActivity {

    private EditText txtUsername,txtEmail,txtFullName,txtPassword;
    private ImageButton btnNext;
    private TextView btnSignIn;
    private Button openEye;

    private boolean checkEye = true;

    static final private String EMAIL_PATTERN = "[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+";
    static final private String USERNAME_PATTERN = "^[a-z A-Z]{0,50}$";
    static final private String PASSWORD_PATTERN = "^[a-zA-z0-9]{6,20}$";

    private CheckInternet checkInternet;
    private ProgressDialogNotify progress;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_sign_up1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtFullName = (EditText) findViewById(R.id.txtFullname);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        openEye = (Button) findViewById(R.id.openEye);

        checkInternet = new CheckInternet();
        progress = ProgressDialogNotify.getInstance();

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

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp1Activity.this,SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToSignUp2();
            }
        });

    }

    private void sendUserToSignUp2()
    {
        //reset error
        txtFullName.setError(null);
        txtUsername.setError(null);
        txtEmail.setError(null);
        txtPassword.setError(null);

        //get data form edit text
        String fullname = txtUsername.getText().toString().trim();
        String username = txtUsername.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        boolean cancel = true;

        // Check username
        if (username.matches("")){
            txtUsername.setError(getString(R.string.error_field_username_empty));
            cancel = false;
        }
        else if (!username.matches(USERNAME_PATTERN)){
            txtUsername.setError(getString(R.string.error_field_username_required));
            cancel = false;
        }

        // Check email
        if (email.matches("")){
            txtEmail.setError(getString(R.string.error_field_email_empty));
            cancel = false;
        }
        else if (!email.matches(EMAIL_PATTERN)) {
            txtEmail.setError(getString(R.string.error_field_email_required));
            cancel = false;
        }

        // Check Phone number
        if (fullname.matches("")){
            txtFullName.setError(getString(R.string.error_field_username_empty));
            cancel = false;
        }
        else if (!fullname.matches(USERNAME_PATTERN)) {
            txtFullName.setError(getString(R.string.error_field_username_required));
            cancel = false;
        }

        // Check password
        if (password.matches("")){
            txtPassword.setError(getString(R.string.error_field_password_empty));
            cancel = false;
        }
        else if (!password.matches(PASSWORD_PATTERN)) {
            txtPassword.setError(getString(R.string.error_field_password_required));
            cancel = false;
        }

        if (cancel)
        {
            progress.showProgressDialog(SignUp1Activity.this,getString(R.string.progress_message_signup),false);

            if (!checkInternet.isConnected(SignUp1Activity.this))
            {
                progress.stopProgressDialog();
                Toast.makeText(SignUp1Activity.this,getString(R.string.noti_no_internet),Toast.LENGTH_LONG).show();
            }
            else
            {
                progress.stopProgressDialog();
                Intent intent = new Intent(SignUp1Activity.this, SignUp2Activity.class);
                intent.putExtra("USER_USERNAME", username);
                intent.putExtra("USER_EMAIL", email);
                intent.putExtra("USER_FULLNAME", fullname);
                intent.putExtra("USER_PASSWORD", password);
                startActivity(intent);
            }

        }
    }

//    private void registerNewUser()
//    {
//
//        //reset error
//        txtUsername.setError(null);
//        txtEmail.setError(null);
//        txtPhone.setError(null);
//        txtPassword.setError(null);
//
//        String name = txtUsername.getText().toString().trim();
//        String email = txtEmail.getText().toString().trim();
//        String phone = txtPhone.getText().toString().trim();
//        String password = txtPassword.getText().toString().trim();
//
//        boolean cancel = true;
//
//        // Check username
//        if (name.matches("")){
//            txtUsername.setError(getString(R.string.error_field_username_empty));
//            cancel = false;
//        }
//        else if (!name.matches(USERNAME_PATTERN)){
//            txtUsername.setError(getString(R.string.error_field_username_required));
//            cancel = false;
//        }
//
//        // Check email
//        if (email.matches("")){
//            txtEmail.setError(getString(R.string.error_field_email_empty));
//            cancel = false;
//        }
//        else if (!email.matches(EMAIL_PATTERN)) {
//            txtEmail.setError(getString(R.string.error_field_email_required));
//            cancel = false;
//        }
//
//        // Check Phone number
//        if (phone.matches("")){
//            txtPhone.setError(getString(R.string.error_field_phone_empty));
//            cancel = false;
//        }
//        else if (!phone.matches(PHONE_PATTERN)) {
//            txtPhone.setError(getString(R.string.error_field_phone_required));
//            cancel = false;
//        }
//
//        // Check password
//        if (password.matches("")){
//            txtPassword.setError(getString(R.string.error_field_password_empty));
//            cancel = false;
//        }
//        else if (!password.matches(PASSWORD_PATTERN)) {
//            txtPassword.setError(getString(R.string.error_field_password_required));
//            cancel = false;
//        }
//
//        if (cancel) {
//
//            progress.showProgressDialog(this,getString(R.string.progress_message_signup),false);
//
//            if (!checkInternet.isConnected(this)) {
//                Toast.makeText(this,getString(R.string.noti_no_internet),Toast.LENGTH_LONG).show();
//            }
//            else {
//                mAuth.createUserWithEmailAndPassword(email,password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    addUserByEmail(name, email, phone, password);
//                                    addUserByPhone(name, email, phone, password);
//                                    progress.stopProgressDialog();
//                                    sendUserToSignIn();
//                                    Toast.makeText(SignUp1Activity.this,getString(R.string.noti_signup_success),Toast.LENGTH_LONG).show();
//                                }
//                                else {
//                                    progress.stopProgressDialog();
//                                    Toast.makeText(SignUp1Activity.this,getString(R.string.noti_email_exist),Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//            }
//        }
//    }

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

//    private String customUserID(String email)
//    {
//        return email.replace(".","1");
//    }
//
//    private void addUserByEmail(String username, String email, String phone, String password)
//    {
//        user_infor = new USER_INFOR(username,email,phone,password);
//        String userID = customUserID(email);
//        mRef = mDatabase.getReference("USER/UID");
//        mRef.child(userID).setValue(user_infor);
//    }
//
//    private void addUserByPhone(String username, String email, String phone, String password)
//    {
//        user_infor = new USER_INFOR(username,email,phone,password);
//        String userID = phone;
//        mRef = mDatabase.getReference("USER/PHONE");
//        mRef.child(userID).setValue(user_infor);
//    }
//
//    private void sendUserToSignIn()
//    {
//        Intent intent = new Intent(SignUp1Activity.this, SignInActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }


}
