package com.example.kampung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kampung.utility.SharedPrefs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LogInActivity extends AppCompatActivity {

    public static String PREFS_NAME = "mypref";
    private Button LogIn;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputTele;
    private TextInputEditText textInputEditTele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        LogIn = findViewById(R.id.login);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefs.saveSharedSetting(LogInActivity.this, "telehandle", "false");
                Intent LoggedIn = new Intent(getApplicationContext(), BottomNavActivity.class);
                startActivity(LoggedIn);
                finish();
//                SharedPreferences sharedPreferences = getSharedPreferences(LogInActivity.PREFS_NAME, 0);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("hasLoggedIn", true);
//                editor.commit();
//                startActivity(new Intent(LogInActivity.this, BottomNavActivity.class));
//                finish();
            }
        });
    }


}