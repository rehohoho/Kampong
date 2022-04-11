package com.example.kampung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Trace;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kampung.controllers.DAO;
import com.example.kampung.models.User;
import com.example.kampung.utility.SharedPrefs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teleHandle = mTeleHandle.getText().toString();
                String userName = mUsername.getText().toString();
                //

                // save checkbox preference
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

                    currUser=new User(userName,teleHandle);



                }

                DatabaseReference dbref=db.getReference("User");

                dbref.orderByChild("telegramHandle")
                        .equalTo(teleHandle)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String reqkey=null;
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    reqkey = childSnapshot.getKey();

                                }
                                if(reqkey==null){
                                    UserSingleton.getInstance(currUser);
                                    DAO.getInstance().add(currUser);
                                    Intent intent = new Intent(LogInActivity.this, BottomNavActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    UserSingleton.getInstance(currUser);
                                    Intent intent = new Intent(LogInActivity.this, BottomNavActivity.class);
                                    startActivity(intent);

                                }





                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });
    }

    private void checkSharePreferences() {
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String userTeleHandle = mPreferences.getString(getString(R.string.userTeleHandle), "");
        String username = mPreferences.getString(getString(R.string.username),"");

        mTeleHandle.setText(userTeleHandle);
        mUsername.setText(username);

        if (checkbox.equals("True")){
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }

    }









}