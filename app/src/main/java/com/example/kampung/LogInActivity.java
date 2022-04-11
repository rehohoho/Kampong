package com.example.kampung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

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

import com.example.kampung.controllers.DAO;
import com.example.kampung.models.User;
import com.example.kampung.utility.SharedPrefs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogInActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText mTeleHandle, mUsername;
    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    private ImageButton btnLogin;
    private CheckBox mCheckBox;


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

//                Intent intent = new Intent(LogInActivity.this, BottomNavActivity.class);
//                startActivity(intent);

                // save checkbox preference
                if (mCheckBox.isChecked()){
                    // set a checkbox when the application starts
                    mEditor.putString(getString(R.string.checkbox), "True");
                    mEditor.commit();

                    // save the name
                    String teleHandle = mTeleHandle.getText().toString();
                    String userName=mUsername.getText().toString();
                    User currUser=new User(userName,teleHandle);
                    //DatabaseReference dbref=db.getReference("Users");
                    //DAO.getInstance().add(currUser);
                    mEditor.putString("telehandle", teleHandle);
                    mEditor.putString("username",userName);
                    mEditor.commit();

                    Intent intent = new Intent(LogInActivity.this, BottomNavActivity.class);
                    startActivity(intent);
                } else {
                    // set a checkbox when the application starts
                    mEditor.putString(getString(R.string.checkbox), "False");
                    mEditor.commit();

                    // save the name
                    mEditor.putString("telehandle", "");
                    mEditor.putString("username","");
                    mEditor.commit();

                    Intent intent = new Intent(LogInActivity.this, BottomNavActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void checkSharePreferences() {
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String userTeleHandle = mPreferences.getString("telehandle", "");
        String username=mPreferences.getString("username","");

        mTeleHandle.setText(userTeleHandle);
        mUsername.setText(username);

        if (checkbox.equals("True")){
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }

    }


}