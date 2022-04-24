package com.example.kampung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.UserViewModel;
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
    private ImageButton btnLogin;
    private CheckBox mCheckBox;
    private User currUser;
    private UserViewModel userViewModel;

    private final ValueEventListener queryListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String reqkey = null;
            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                reqkey = childSnapshot.getKey();
            }
            if (reqkey == null) {
                reqkey = DAO.getInstance().add(currUser);
            }
            userViewModel.getUser(DAO.getInstance(), reqkey);
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mTeleHandle = (EditText) findViewById(R.id.telehandle);
        mUsername=(EditText) findViewById(R.id.enter_username);
        btnLogin = (ImageButton) findViewById(R.id.login);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        mPreferences = getSharedPreferences(getString(R.string.package_name), Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        checkSharePreferences();
        mCheckBox.setChecked(true);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teleHandle = mTeleHandle.getText().toString();
                String userName = mUsername.getText().toString();

                currUser = new User(userName,teleHandle);

                if (mCheckBox.isChecked()){
                    // set a checkbox when the application starts
                    mEditor.putString(getString(R.string.package_name), "True");
                    mEditor.putString(getString(R.string.package_name), teleHandle);
                    mEditor.putString(getString(R.string.username), userName);
                    mEditor.commit();
                } else {
                    // set a checkbox when the application starts
                    mEditor.putString(getString(R.string.package_name), "False");
                    mEditor.putString(getString(R.string.package_name), "");
                    mEditor.putString(getString(R.string.username), "");
                    mEditor.commit();
                }

                DatabaseReference userDb = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
                userDb.orderByChild("telegramHandle").equalTo(teleHandle).addListenerForSingleValueEvent(queryListener);
            }
        });
    }

    private void checkSharePreferences() {
        String checkbox = mPreferences.getString(getString(R.string.package_name), "False");
        String userTeleHandle = mPreferences.getString(getString(R.string.package_name), "");
        String username = mPreferences.getString(getString(R.string.username),"");

        mTeleHandle.setText(userTeleHandle);
        mUsername.setText(username);
        mCheckBox.setChecked(checkbox.equals("True"));
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