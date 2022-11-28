package com.example.afinal.fragment_home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.afinal.MainActivity;
import com.example.afinal.R;
import com.example.afinal.login.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragmentSetting extends Fragment {
//    DatabaseReference mHome;
//    Switch aSwitch;

    DatabaseReference mHome;
    private MainActivity mMainActivity;
    private TextView tvUserName, tvPhoneNumber;
    private Button btnEditProfile;
    private RelativeLayout btnLogout;
    private String userPath, userID;
    private DatabaseReference mData;
    private String getName, getEmail, getPhone, getPassword;

    static final public String PATH_PHONE = "1";
    static final public String PATH_EMAIL = "2";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_setting, container, false);

        mHome = FirebaseDatabase.getInstance().getReference();
        btnEditProfile = mview.findViewById(R.id.btnEditProfile);
        btnLogout = mview.findViewById(R.id.btnLogout);
        mMainActivity = (MainActivity) getActivity();

        tvUserName = mview.findViewById(R.id.tvUserName);
        tvPhoneNumber = mview.findViewById(R.id.tvPhoneNumber);

        userPath = mMainActivity.getUserPath();
        userID = mMainActivity.getUserID();

        getUsersDataByPath(userPath, userID);


       btnEditProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openAccountDialog(Gravity.CENTER);
           }
       });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SignInActivity.class));
            }
        });
        return mview;
    }

    private void getUsersDataByPath(String user_path, String user_ID)
    {
        if (user_path.equals(PATH_PHONE))
        {
            mData = FirebaseDatabase.getInstance().getReference("USER/PHONE");
            mData.child(user_ID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        if (task.getResult().exists())
                        {
                            DataSnapshot dataSnapshot = task.getResult();
                            getName = String.valueOf(dataSnapshot.child("userName").getValue());
                            getEmail = String.valueOf(dataSnapshot.child("userEmail").getValue());
                            getPhone = String.valueOf(dataSnapshot.child("userPhone").getValue());
                            getPassword = String.valueOf(dataSnapshot.child("userPassword").getValue());
                            mHome.child("USER").child("PHONE").child(getPhone).child("userName").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    tvUserName.setText(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            mHome.child("USER").child("PHONE").child(getPhone).child("userPhone").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    tvPhoneNumber.setText(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            });

        }
        else if (user_path.equals(PATH_EMAIL))
        {
            mData = FirebaseDatabase.getInstance().getReference("USER/UID");
            mData.child(user_ID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        if (task.getResult().exists())
                        {
                            DataSnapshot dataSnapshot = task.getResult();
                            getName = String.valueOf(dataSnapshot.child("userName").getValue());
                            getEmail = String.valueOf(dataSnapshot.child("userEmail").getValue());
                            getPhone = String.valueOf(dataSnapshot.child("userPhone").getValue());
                            getPhone = String.valueOf(dataSnapshot.child("userPassword").getValue());
                            tvUserName.setText(getName);
                            tvPhoneNumber.setText(getPhone);
                        }
                    }
                }
            });
        }
    }

    private void openAccountDialog(int gravity){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        if (Gravity.CENTER == gravity)
        {
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        EditText edtUsername = dialog.findViewById(R.id.ed_username);
        EditText edtPassword = dialog.findViewById(R.id.ed_password);
        EditText edtEmail = dialog.findViewById(R.id.ed_email);
        EditText edtPhone = dialog.findViewById(R.id.ed_phonenum);
        Button btn_clear = dialog.findViewById(R.id.btn_clear);
        Button btn_apply = dialog.findViewById(R.id.btn_apply);

        mHome.child("USER").child("PHONE").child(getPhone).child("userName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                edtUsername.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHome.child("USER").child("PHONE").child(getPhone).child("userEmail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                edtEmail.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHome.child("USER").child("PHONE").child(getPhone).child("userPhone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                edtPhone.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHome.child("USER").child("PHONE").child(getPhone).child("userPassword").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                edtPassword.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String valueEditText;
                String change = "changed ";
                int check = 0;
                if (!(edtUsername.getText().toString().equals(getName)))
                {
                    valueEditText = edtUsername.getText().toString();
                    mHome.child("USER").child("PHONE").child(getPhone).child("userName").setValue(valueEditText);
                    change += " username";
                    check = 1;
                }
                if (!(edtPassword.getText().toString().equals("")))
                {
                    valueEditText = edtPassword.getText().toString();
                    mHome.child("USER").child("PHONE").child(getPhone).child("userPassword").setValue(valueEditText);
                    change += " password";
                    check = 1;
                }
                if (!(edtEmail.getText().toString().equals("")))
                {
                    valueEditText = edtEmail.getText().toString();
                    mHome.child("USER").child("PHONE").child(getPhone).child("userEmail").setValue(valueEditText);
                    change += " email";
                    check = 1;
                }
                if (!(edtPhone.getText().toString().equals("")))
                {
                    valueEditText = edtPhone.getText().toString();
                    mHome.child("USER").child("PHONE").child(getPhone).child("userPhone").setValue(valueEditText);
                    change += " phone number";
                    check = 1;
                }
                if (check == 1)
                {
                    Toast.makeText(getContext(),change,Toast.LENGTH_SHORT).show();
                }
                else if (check == 0)
                {
                    Toast.makeText(getContext(),"nothing was changed",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}