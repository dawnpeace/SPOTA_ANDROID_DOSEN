package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.Model.Schedule;
import com.example.dawnpeace.spota_android_dosen.R;

import java.util.List;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {
    private Context context;
    private List<Schedule> scheduleList;

    public ScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Schedule schedule = scheduleList.get(i);
        myViewHolder.tv_type.setText(schedule.getType());
        myViewHolder.tv_date.setText(schedule.getLocalized_date());
        myViewHolder.tv_name.setText(schedule.getName());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
    protected TextView tv_type;
    protected TextView tv_name;
    protected TextView tv_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_type = itemView.findViewById(R.id.tv_sch_type);
            tv_name = itemView.findViewById(R.id.tv_sch_name);
            tv_date = itemView.findViewById(R.id.tv_sch_date);
        }
    }
}
