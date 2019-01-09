package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.ConsultationReviewActivity;
import com.example.dawnpeace.spota_android_dosen.Model.Consultation;
import com.example.dawnpeace.spota_android_dosen.R;

import java.util.List;


public class ConsultationAdapter extends RecyclerView.Adapter<ConsultationAdapter.MyViewHolder> {
    private Context context;
    private List<Consultation> consultationList;

    public ConsultationAdapter(Context context, List<Consultation> consultationList) {
        this.context = context;
        this.consultationList = consultationList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.consultation_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Consultation consultation = consultationList.get(i);
        myViewHolder.ll_consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ConsultationReviewActivity.class);
                intent.putExtra("preoutline_title",consultation.getTitle());
                intent.putExtra("consultation_id",consultation.getId());
                intent.putExtra("consultation_section",consultation.getLocalSection());
                context.startActivity(intent);
            }
        });
        myViewHolder.tv_title.setText(consultation.getTitle());
        myViewHolder.tv_name.setText(consultation.getName());
        myViewHolder.tv_identity_number.setText(consultation.getIdentity_number());
        myViewHolder.tv_section.setText(consultation.getSection());
        myViewHolder.tv_date.setText(consultation.getLast_commented());
    }

    @Override
    public int getItemCount() {
        return consultationList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_name;
        private TextView tv_identity_number;
        private TextView tv_section;
        private TextView tv_date;
        private LinearLayout ll_consultation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_consultation = itemView.findViewById(R.id.ll_consultation);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_identity_number = itemView.findViewById(R.id.tv_identity_number);
            tv_section = itemView.findViewById(R.id.tv_section);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}
