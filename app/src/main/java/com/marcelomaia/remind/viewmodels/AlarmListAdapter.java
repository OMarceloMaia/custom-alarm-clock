package com.marcelomaia.remind.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marcelomaia.remind.R;
import com.marcelomaia.remind.data.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmViewHolder> {
    private List<Alarm> alarmList;

    public AlarmListAdapter() {
        this.alarmList = new ArrayList<>();
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        holder.tvName.setText(String.valueOf(alarm.name));
        holder.tvTime.setText(String.valueOf(alarm.time));
        holder.tvDate.setText(String.valueOf(alarm.date));
        holder.swActive.setChecked(alarm.active);
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvTime;
        public TextView tvDate;
        public Switch swActive;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRecyclerItemName);
            tvTime = itemView.findViewById(R.id.tvRecyclerItemTime);
            tvDate = itemView.findViewById(R.id.tvRecyclerItemDate);
            swActive = itemView.findViewById(R.id.switchRecyclerItem);
        }
    }
}
