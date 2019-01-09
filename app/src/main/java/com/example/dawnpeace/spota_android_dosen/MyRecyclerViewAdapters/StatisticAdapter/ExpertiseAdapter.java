package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.StatisticAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.Model.Statistic.Expertise;
import com.example.dawnpeace.spota_android_dosen.R;

import java.util.List;

public class ExpertiseAdapter extends RecyclerView.Adapter<ExpertiseAdapter.MyViewHolder> {

    private Context context;
    private List<Expertise> expertises;

    public ExpertiseAdapter(Context context, List<Expertise> expertises) {
        this.context = context;
        this.expertises = expertises;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.statistic_expertise_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Expertise expertise = expertises.get(i);
        myViewHolder.tv_expertise_name.setText(expertise.getExpertise_name());
        String alltime_count = String.valueOf(expertise.getAlltime_count());
        String semester_count = String.valueOf(expertise.getSemester_count());
        myViewHolder.tv_alltime_count.setText(alltime_count);
        myViewHolder.tv_semester_count.setText(semester_count);
    }

    @Override
    public int getItemCount() {
        return expertises.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv_expertise_name;
        protected TextView tv_semester_count;
        protected TextView tv_alltime_count;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_expertise_name = itemView.findViewById(R.id.tv_expertise_name);
            tv_semester_count = itemView.findViewById(R.id.tv_expertise_semester_count);
            tv_alltime_count = itemView.findViewById(R.id.tv_expertise_alltime_count);
        }
    }

}
