package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.Model.Announcement;
import com.example.dawnpeace.spota_android_dosen.MyTagHandler;
import com.example.dawnpeace.spota_android_dosen.R;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

    private Context mContext;
    private List<Announcement> mAnnouncement;
    private View mView;

    public AnnouncementAdapter(Context mContext, List<Announcement> mAnnouncement) {
        this.mContext = mContext;
        this.mAnnouncement = mAnnouncement;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.announcement_layout,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(mView);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_date.setText(mAnnouncement.get(i).getDate());
        myViewHolder.tv_title.setText(mAnnouncement.get(i).getTitle());
        String sender = "Oleh : " + mAnnouncement.get(i).getSender();
        myViewHolder.tv_sender.setText(sender);
        final Spanned content = Html.fromHtml(mAnnouncement.get(i).getContent());
        final String htmlAnnouncement = "<html>"+mAnnouncement.get(i).getContent()+"</html>";
        myViewHolder.ll_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logoutAlert = new AlertDialog.Builder(mContext,R.style.AlertDialog);
                TextView textView = new TextView(mContext);
                textView.setText(Html.fromHtml(htmlAnnouncement, null, new MyTagHandler()));
                textView.setPadding(20,20,20,20);
                textView.setMinHeight(500);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setMinWidth(750);
                logoutAlert.setView(textView);
                logoutAlert.setCancelable(true).setPositiveButton(R.string.dialog_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mAnnouncement.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_sender;
        private LinearLayout ll_announcement;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sender = itemView.findViewById(R.id.tv_announcement_sender);
            tv_date = itemView.findViewById(R.id.tv_announcement_date);
            tv_title = itemView.findViewById(R.id.tv_announcement_title);
            ll_announcement = itemView.findViewById(R.id.ll_announcement);
        }
    }
}
