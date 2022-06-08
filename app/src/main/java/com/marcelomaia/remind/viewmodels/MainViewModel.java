package com.marcelomaia.remind.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.marcelomaia.remind.data.Alarm;
import com.marcelomaia.remind.data.AlarmRepository;
import com.marcelomaia.remind.data.IAlarmRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Alarm>> getAlarms() {
        IAlarmRepository repository = AlarmRepository.getInstance(getApplication().getApplicationContext());
        return repository.getAll();
    }
}
