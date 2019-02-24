package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.APIUrl;
import com.example.dawnpeace.spota_android_dosen.Model.Announcement;
import com.example.dawnpeace.spota_android_dosen.MyTagHandler;
import com.example.dawnpeace.spota_android_dosen.R;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.AnnouncementInterface;
import com.example.dawnpeace.spota_android_dosen.SharedPrefHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_date.setText(mAnnouncement.get(i).getDate());
        myViewHolder.tv_title.setText(mAnnouncement.get(i).getTitle());
        String sender = "Oleh : " + mAnnouncement.get(i).getSender();
        myViewHolder.tv_sender.setText(sender);
        if(mAnnouncement.get(i).isRead()){
            myViewHolder.tv_title.setTextColor(Color.parseColor("#d3d3d3"));
        } else {
            myViewHolder.tv_title.setTextColor(Color.parseColor("#000000"));
        }

        final String htmlAnnouncement = "<html>"+mAnnouncement.get(i).getContent()+"</html>";
        myViewHolder.ll_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mAnnouncement.get(myViewHolder.getAdapterPosition()).isRead()){
                    readAnnouncement(mAnnouncement.get(myViewHolder.getAdapterPosition()).getId());
                }
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
                        myViewHolder.tv_title.setTextColor(Color.parseColor("#d3d3d3"));
                    }
                }).create().show();



            }
        });

    }

    private void readAnnouncement(int id){
        SharedPrefHelper mSharedPref = SharedPrefHelper.getInstance(mContext);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mSharedPref.getInterceptor())
                .build();

        AnnouncementInterface announcementInterface = retrofit.create(AnnouncementInterface.class);
        Call<Void> call = announcementInterface.readAnnouncement(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "Terjadi kesalahan, ERR :"+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

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
