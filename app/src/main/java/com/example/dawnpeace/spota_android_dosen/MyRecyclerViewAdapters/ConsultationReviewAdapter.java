package com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.APIUrl;
import com.example.dawnpeace.spota_android_dosen.Model.ConsultationReview;
import com.example.dawnpeace.spota_android_dosen.R;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.ConsultationInterface;
import com.example.dawnpeace.spota_android_dosen.SharedPrefHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationReviewAdapter extends  RecyclerView.Adapter<ConsultationReviewAdapter.MyViewHolder>{
    private Context context;
    private List<ConsultationReview> reviewList;
    private SharedPrefHelper mSharedPref;

    public ConsultationReviewAdapter(Context context, List<ConsultationReview> reviewList,String title) {
        this.context = context;
        this.reviewList = reviewList;
        mSharedPref = SharedPrefHelper.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final ConsultationReview review = reviewList.get(i);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(12, 8, 12, 8);
        myViewHolder.cv_review.setLayoutParams(lp);
        myViewHolder.tv_name.setText(review.getName());
        myViewHolder.tv_identity_number.setText(review.getIdentity_number());
        myViewHolder.tv_review.setText(review.getMessage());
        myViewHolder.tv_date.setText(review.getDate_locale());
        if(!mSharedPref.getUser().getIdentity_number().equals(review.getIdentity_number())){
            myViewHolder.tv_sub_menu.setVisibility(View.GONE);
        } else {
            myViewHolder.tv_sub_menu.setVisibility(View.VISIBLE);
            myViewHolder.tv_sub_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context,myViewHolder.tv_sub_menu);
                    popupMenu.inflate(R.menu.delete_only_menu);
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AlertDialog.Builder alert = new AlertDialog.Builder((Activity) context);
                            alert.setTitle("Hapus Komentar");
                            alert.setMessage("Apakah Anda yakin ingin melanjutkan ?");
                            final int review_id = review.getId();
                            final int position = myViewHolder.getAdapterPosition();

                            alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delete(review_id,position);
                                }
                            });
                            alert.show();
                            return  true;
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    private void delete(int review_id, final int position){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConsultationInterface consultationInterface = retrofit.create(ConsultationInterface.class);
        Call<Void> call = consultationInterface.deleteReview(review_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    reviewList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,reviewList.size());
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_identity_number;
        private TextView tv_review;
        private TextView tv_date;
        private TextView tv_sub_menu;
        private CardView cv_review;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.cv_name);
            tv_identity_number = itemView.findViewById(R.id.cv_identity_number);
            tv_review = itemView.findViewById(R.id.cv_comment);
            tv_date = itemView.findViewById(R.id.cv_date);
            tv_sub_menu = itemView.findViewById(R.id.tv_sub_menu);
            cv_review = itemView.findViewById(R.id.cv_review);
        }
    }


}
