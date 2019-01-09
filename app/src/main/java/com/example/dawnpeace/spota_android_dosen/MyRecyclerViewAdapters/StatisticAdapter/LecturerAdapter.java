package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.StatisticAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.Model.Statistic.Lecturer;
import com.example.dawnpeace.spota_android_dosen.R;

import java.util.List;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.MyViewHolder> {

    private Context context;
    private List<Lecturer> lecturers;

    public LecturerAdapter(Context context, List<Lecturer> lecturers) {
        this.context = context;
        this.lecturers = lecturers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.statistic_lecturer_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Lecturer lecturer = lecturers.get(i);
        String alltime_supervised_count = String.valueOf(lecturer.getSupervised_allttime());
        String semester_supervised_count = String.valueOf(lecturer.getSupervised_semester());
        String alltime_examined_count = String.valueOf(lecturer.getExamined_alltime());
        String semester_examined_count = String.valueOf(lecturer.getExamined_semester());

        myViewHolder.tv_lecturer_name.setText(lecturer.getLecturer());
        myViewHolder.tv_semester_examined.setText(semester_examined_count);
        myViewHolder.tv_semester_supervised.setText(semester_supervised_count);
        myViewHolder.tv_alltime_examined.setText(alltime_examined_count);
        myViewHolder.tv_alltime_supervised.setText(alltime_supervised_count);

    }

    @Override
    public int getItemCount() {
        return lecturers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv_lecturer_name;
        protected TextView tv_alltime_supervised;
        protected TextView tv_alltime_examined;
        protected TextView tv_semester_supervised;
        protected TextView tv_semester_examined;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_lecturer_name = itemView.findViewById(R.id.tv_statistic_lecturer_name);
            tv_alltime_supervised = itemView.findViewById(R.id.tv_supervised_count_alltime);
            tv_semester_supervised = itemView.findViewById(R.id.tv_supervised_count_semester);
            tv_alltime_examined = itemView.findViewById(R.id.tv_examined_alltime);
            tv_semester_examined = itemView.findViewById(R.id.tv_examined_semester);
        }
    }

}
