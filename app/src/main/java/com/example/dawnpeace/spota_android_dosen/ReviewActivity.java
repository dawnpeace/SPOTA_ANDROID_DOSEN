package com.example.dawnpeace.spota_android_dosen;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.Review;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.ReviewAdapter;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.ReviewInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewActivity extends AppCompatActivity {

    private SharedPrefHelper mSharedPref;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private int preoutline_id;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initView();
        mSharedPref = SharedPrefHelper.getInstance(this);
        getSupportActionBar().setTitle("Review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        preoutline_id = intent.getExtras().getInt("preoutline_id");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setReview(preoutline_id);
    }



    public void setReview(int preoutline_id){
        final int id = preoutline_id;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReviewInterface review = retrofit.create(ReviewInterface.class);
        Call<List<Review>> call = review.getReviews(preoutline_id);

        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(response.isSuccessful()){
                    mRecyclerView = findViewById(R.id.rv_review);
                    ReviewAdapter adapter = new ReviewAdapter(ReviewActivity.this,response.body(),id,status);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mRecyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Toast.makeText(ReviewActivity.this, "Telah terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(){
        Bundle bundle = getIntent().getExtras();
        status = bundle.getString("preoutline_status");
        fab = findViewById(R.id.fab_review);
        if(!status.equals("open")){
            fab.hide();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewActivity.this,ReplyActivity.class);
                intent.putExtra("PREOUTLINE_ID",preoutline_id);
                intent.putExtra("PARENT_ID","");
                startActivity(intent);
            }
        });
    }
}
