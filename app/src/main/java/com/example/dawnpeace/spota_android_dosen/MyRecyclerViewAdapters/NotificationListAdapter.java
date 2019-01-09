package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.Model.Notification;
import com.example.dawnpeace.spota_android_dosen.R;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {
    private List<Notification> mNotif;
    private Context mContext;
    private View mView;

    public NotificationListAdapter(Context mContext,List<Notification> mNotif) {
        this.mNotif = mNotif;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.notification_list_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(mNotif.get(i).getTitle());
        myViewHolder.tv_content.setText(mNotif.get(i).getMessages());
        myViewHolder.tv_date.setText(mNotif.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return mNotif.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_notification_content);
            tv_date = itemView.findViewById(R.id.tv_notification_date);
            tv_title = itemView.findViewById(R.id.tv_notification_title);
        }
    }
}
