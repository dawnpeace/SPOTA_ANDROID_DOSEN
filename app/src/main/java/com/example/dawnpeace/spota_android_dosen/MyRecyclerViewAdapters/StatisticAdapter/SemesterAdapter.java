package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.StatisticAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.Model.Statistic.UploadedSemester;
import com.example.dawnpeace.spota_android_dosen.R;

import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.MyViewHolder> {
    private Context context;
    private List<UploadedSemester> semesters;

    public SemesterAdapter(Context context, List<UploadedSemester> semesters) {
        this.context = context;
        this.semesters = semesters;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.statistic_semester_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        UploadedSemester semester = semesters.get(i);
        myViewHolder.semester_label.setText(semester.getSemester());
        String preoutline_count = String.valueOf(semester.getPreoutline_count());
        myViewHolder.semester_preoutline_count.setText(preoutline_count);
    }

    @Override
    public int getItemCount() {
        return semesters.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        protected TextView semester_label;
        protected TextView semester_preoutline_count;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            semester_label = itemView.findViewById(R.id.tv_semester_label);
            semester_preoutline_count = itemView.findViewById(R.id.tv_semester_draft_count);
        }
    }
}
