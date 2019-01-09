package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.Model.Approval;
import com.example.dawnpeace.spota_android_dosen.R;

import java.util.List;

public class DraftViewAdapter extends RecyclerView.Adapter<DraftViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Approval> mApproval;

    public DraftViewAdapter(Context mContext, List<Approval> mApproval) {
        this.mContext = mContext;
        this.mApproval = mApproval;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.approval_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String content = mApproval.get(i).getName() + " telah " + "<b>" + mApproval.get(i).getApproval() + "</b> .";
        myViewHolder.tv_content.setText(Html.fromHtml(content));
        myViewHolder.tv_date.setText(mApproval.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return mApproval.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_content;
        private TextView tv_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_approval_content);
            tv_date = itemView.findViewById(R.id.tv_approval_date);
        }
    }
}
