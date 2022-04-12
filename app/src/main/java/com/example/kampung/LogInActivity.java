package com.example.kampung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kampung.controllers.DAO;
import com.example.kampung.models.User;
import com.example.kampung.utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText mTeleHandle, mUsername;
    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    private ImageButton btnLogin;
    private CheckBox mCheckBox;
    private User currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mTeleHandle = (EditText) findViewById(R.id.telehandle);
        mUsername=(EditText) findViewById(R.id.enter_username);
        btnLogin = (ImageButton) findViewById(R.id.login);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        mPreferences = getSharedPreferences("com.example.kampung", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        checkSharePreferences();
        mCheckBox.setChecked(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teleHandle = mTeleHandle.getText().toString();
                String userName = mUsername.getText().toString();

                currUser = new User(userName,teleHandle);

                if (mCheckBox.isChecked()){
                    // set a checkbox when the application starts
                    mEditor.putString(getString(R.string.checkbox), "True");
                    mEditor.commit();

                    //DatabaseReference dbref=db.getReference("Users");
                    //DAO.getInstance().add(currUser);
                    mEditor.putString(getString(R.string.userTeleHandle), teleHandle);
                    mEditor.commit();

                    // save username

                    mEditor.putString(getString(R.string.username), userName);
                    mEditor.commit();


                    currUser=new User(userName,teleHandle);

                    mEditor.putString("telehandle", teleHandle);
                    mEditor.putString("username",userName);
                    mEditor.commit();

                } else {
                    // set a checkbox when the application starts
                    mEditor.putString(getString(R.string.checkbox), "False");
                    mEditor.commit();

                    // save tele handle
                    mEditor.putString(getString(R.string.userTeleHandle), "");
                    mEditor.commit();

                    // save username
                    mEditor.putString(getString(R.string.username), "");
                    mEditor.commit();

//                    currUser=new User(userName,teleHandle);

                }


//                // edit saved preferences: telehandle, username, checkbox preference
//                mEditor.putString(getString(R.string.userTeleHandle), "");
//                mEditor.putString(getString(R.string.username), "");
//                if (mCheckBox.isChecked()){
//                    mEditor.putString(getString(R.string.checkbox), "True");
//                } else {
//                    mEditor.putString(getString(R.string.checkbox), "False");
//                }
//                mEditor.commit();

                DatabaseReference dbref = db.getReference("User");

                dbref.orderByChild("telegramHandle")
                        .equalTo(teleHandle)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User reqkey=null;
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    reqkey = childSnapshot.getValue(User.class);

                                }
                                if(reqkey==null){
                                    UserSingleton.getInstance(currUser);
                                    DAO.getInstance().add(currUser);
                                    Intent intent = new Intent(LogInActivity.this, BottomNavActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    UserSingleton.getInstance(reqkey);
                                    Toast.makeText(LogInActivity.this, "user found", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LogInActivity.this, BottomNavActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                    );
            }
        });
    }

    private void checkSharePreferences() {
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String userTeleHandle = mPreferences.getString("telehandle", "");
        String username = mPreferences.getString("username","");

        mTeleHandle.setText(userTeleHandle);
        mUsername.setText(username);

        if (checkbox.equals("True")){
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }

    }

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    // CHECK INTERNET CONNECTION
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }








}