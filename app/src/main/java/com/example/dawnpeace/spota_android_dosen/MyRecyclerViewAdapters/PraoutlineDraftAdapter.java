package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.dawnpeace.spota_android_dosen.Model.Praoutline;
import com.example.dawnpeace.spota_android_dosen.PreoutlineActivity;
import com.example.dawnpeace.spota_android_dosen.R;
import com.example.dawnpeace.spota_android_dosen.SharedPrefHelper;

import java.util.List;

public class PraoutlineDraftAdapter extends RecyclerView.Adapter<PraoutlineDraftAdapter.MyViewHolder>{
    private Context mContext;
    private SharedPrefHelper mSharedPrefHelper;
    private List<Praoutline> mDraft;
    private View view;

    public PraoutlineDraftAdapter(Context mContext, List<Praoutline> mDraft) {
        this.mContext = mContext;
        this.mDraft = mDraft;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(mContext).inflate(R.layout.praoutline_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(mDraft.get(i).getTitle());
        String name_identity = mDraft.get(i).getName() + " (" + mDraft.get(i).getIdentity_number() + ")";
        myViewHolder.tv_student_name.setText(name_identity);
        myViewHolder.tv_date.setText(mDraft.get(i).getCreated_at());
        String upvote = String.valueOf(mDraft.get(i).getUpvote());
        String downvote = String.valueOf(mDraft.get(i).getDownvote());
        String comment = String.valueOf(mDraft.get(i).getReview_count());
        myViewHolder.tv_upvote.setText(upvote);
        myViewHolder.tv_downvote.setText(downvote);
        myViewHolder.tv_comment.setText(comment);
        String url = mDraft.get(i).getPicture();
        final int preoutline_id = mDraft.get(i).getId();
        if(url == null){
            Glide.with(mContext)
                    .load(R.drawable.ic_person_grey_24dp)
                    .into(myViewHolder.iv_pic);
        } else {
            Glide.with(mContext)
                    .load(url)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_person_grey_24dp)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(myViewHolder.iv_pic);
        }
        myViewHolder.cv_praoutline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PreoutlineActivity.class);
                intent.putExtra("preoutline_id",preoutline_id);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDraft.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_pic;
        private TextView tv_title;
        private TextView tv_student_name;
        private TextView tv_date;
        private TextView tv_upvote;
        private TextView tv_downvote;
        private TextView tv_comment;
        private CardView cv_praoutline;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_pic = itemView.findViewById(R.id.iv_student_preoutline);
            tv_title = itemView.findViewById(R.id.tv_preoutline_title);
            tv_student_name = itemView.findViewById(R.id.tv_preoutline_owner);
            tv_date = itemView.findViewById(R.id.tv_preoutline_date);
            tv_upvote = itemView.findViewById(R.id.tv_preoutline_upvote);
            tv_downvote = itemView.findViewById(R.id.tv_preoutline_downvote);
            tv_comment = itemView.findViewById(R.id.tv_preoutline_comment);
            cv_praoutline = itemView.findViewById(R.id.cv_praoutline);
        }
    }
}
