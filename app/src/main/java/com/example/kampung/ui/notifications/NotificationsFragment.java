package com.example.kampung.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kampung.R;
import com.example.kampung.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View notifView =inflater.inflate(R.layout.fragment_notifications, container, false);
        return notifView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}