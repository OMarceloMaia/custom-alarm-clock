package com.marcelomaia.remind.views.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcelomaia.remind.R;
import com.marcelomaia.remind.viewmodels.AlarmListAdapter;
import com.marcelomaia.remind.viewmodels.MainViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    public String TAG = "MainActivity";
    private AlarmListAdapter adapter;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new AlarmListAdapter();

        RecyclerView recyclerView = this.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getAlarms().observe(this, items -> {
            if(adapter != null) {
                adapter.setAlarmList(items);
            }
        });
    }

    public void addAlarm (View view) {
        Intent intent = new Intent(this, AlarmPicker.class);
        startActivity(intent);
    }
}