package com.example.kampung;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

public class ViewModelTestUtil {

    public static void callOnClear(final ViewModel viewModel) throws InterruptedException {
        ViewModelStore viewModelStore = new ViewModelStore();
        ViewModelProvider viewModelProvider = new ViewModelProvider(viewModelStore, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
                return (T) viewModel;
            }
        });
        viewModelProvider.get(viewModel.getClass());
        viewModelStore.clear();
    }

}
