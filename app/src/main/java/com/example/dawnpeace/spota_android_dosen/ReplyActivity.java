package com.example.dawnpeace.spota_android_dosen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.ReviewInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReplyActivity extends AppCompatActivity {
    private TextView tv_message;
    private SharedPrefHelper mSharedPref;
    private EditText et_message;
    private Button btn_submit;
    private boolean replying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        getSupportActionBar().setTitle("Komentar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSharedPref = SharedPrefHelper.getInstance(this);
        initView();
        Intent reviewIntent = getIntent();
        String message = reviewIntent.getExtras().getString("MESSAGE");
        if(message != null){
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText(message);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initView(){
        tv_message = findViewById(R.id.tv_parent_comment);
        et_message = findViewById(R.id.et_reply);
    }

    public void replyOnClick(View view){
        String message = et_message.getText().toString();
        if(message.isEmpty()){
            et_message.setError("Isikan komentar anda . ");
            return;
        }
        Intent parent_intent = getIntent();
        String parent_id = String.valueOf(parent_intent.getExtras().getString("PARENT_ID"));
        int preoutline_id = parent_intent.getExtras().getInt("PREOUTLINE_ID");

        reply(preoutline_id,parent_id,message);
    }


    private void reply(final int preoutline_id, String parent_comment, String message){
        if(replying){
            Toast.makeText(this, "Harap tunggu hingga proses selesai . .", Toast.LENGTH_SHORT).show();
            return ;
        }
        setReplying(true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mSharedPref.getInterceptor())
                .build();

        ReviewInterface review = retrofit.create(ReviewInterface.class);
        Call<Void> call = parent_comment.isEmpty() ? review.replyReview(preoutline_id,message) : review.replyReview(preoutline_id,parent_comment,message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ReplyActivity.this, "Komentar Berhasil ditambahkan .", Toast.LENGTH_SHORT).show();
                    finish();
                    setReplying(false);
                } else {
                    setReplying(false);
                    Toast.makeText(ReplyActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ReplyActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                setReplying(false);
            }
        });
    }

    public void setReplying(boolean replying) {
        this.replying = replying;
    }
}
