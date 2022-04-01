package com.marcelomaia.myalarmclock.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.marcelomaia.myalarmclock.database.Alarm;
import com.marcelomaia.myalarmclock.database.AlarmRepository;
import com.marcelomaia.myalarmclock.database.IAlarmRepository;

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
