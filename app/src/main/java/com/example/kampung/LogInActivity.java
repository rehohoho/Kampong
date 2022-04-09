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

import com.example.kampung.utility.SharedPrefs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LogInActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText mTeleHandle;
    private Button btnLogin;
    private CheckBox mCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mTeleHandle = (EditText) findViewById(R.id.telehandle);
        btnLogin = (Button) findViewById(R.id.login);
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
                    mEditor.putString(getString(R.string.userTeleHandle), teleHandle);
                    mEditor.commit();

                    Intent intent = new Intent(LogInActivity.this, BottomNavActivity.class);
                    startActivity(intent);
                } else {
                    // set a checkbox when the application starts
                    mEditor.putString(getString(R.string.checkbox), "False");
                    mEditor.commit();

                    // save the name
                    mEditor.putString(getString(R.string.userTeleHandle), "");
                    mEditor.commit();

                    Intent intent = new Intent(LogInActivity.this, BottomNavActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void checkSharePreferences() {
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String userTeleHandle = mPreferences.getString(getString(R.string.userTeleHandle), "");

        mTeleHandle.setText(userTeleHandle);

        if (checkbox.equals("True")){
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }

    }


}