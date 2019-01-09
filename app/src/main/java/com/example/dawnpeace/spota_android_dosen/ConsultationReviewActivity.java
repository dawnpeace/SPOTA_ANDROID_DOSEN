package com.example.dawnpeace.spota_android_dosen;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.ConsultationReview;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.ConsultationReviewAdapter;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.ConsultationInterface;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationReviewActivity extends AppCompatActivity {
    private RecyclerView rv_consultation_review;
    private TextView tv_title;
    private SharedPrefHelper mSharedPref;
    private int consultation_id;
    private String section;
    private String title;
    private FloatingActionButton fab_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_review);
        Bundle bundle = getIntent().getExtras();
        section = bundle.getString("consultation_section");
        consultation_id = bundle.getInt("consultation_id");
        title = bundle.getString("preoutline_title");
        getSupportActionBar().setTitle(section(section));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void initView(){
        mSharedPref = SharedPrefHelper.getInstance(this);
        rv_consultation_review = findViewById(R.id.rv_consultation_review);
        rv_consultation_review.setNestedScrollingEnabled(false);
        tv_title = findViewById(R.id.tv_consultation_preoutline_title);
        tv_title.setText(title);
        fab_review = findViewById(R.id.fab_consultation_review);
        fab_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultationReviewActivity.this,ConsultationReplyActivity.class);
                intent.putExtra("consultation_id",consultation_id);
                intent.putExtra("consultation_section",section);
                startActivity(intent);
            }
        });
    }

    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConsultationInterface consultationInterface = retrofit.create(ConsultationInterface.class);
        Call<List<ConsultationReview>> call = consultationInterface.getReviews(consultation_id,section);
        call.enqueue(new Callback<List<ConsultationReview>>() {
            @Override
            public void onResponse(Call<List<ConsultationReview>> call, Response<List<ConsultationReview>> response) {
                if(response.isSuccessful()){
                    rv_consultation_review.setLayoutManager(new LinearLayoutManager(ConsultationReviewActivity.this));
                    ConsultationReviewAdapter adapter = new ConsultationReviewAdapter(ConsultationReviewActivity.this,response.body(),title);
                    rv_consultation_review.setAdapter(adapter);
                } else {
                    Toast.makeText(ConsultationReviewActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ConsultationReview>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private String section(String section){
        String bab = "";
        switch (section){
            case "first":
                bab = "BAB I";
                break;
            case "second":
                bab = "BAB II";
                break;
            case "third":
                bab = "BAB III";
                break;
            case "fourth":
                bab = "BAB IV";
                break;
            case "fifth":
                bab = "BAB V";
                break;
        }
        return bab;
    }
}
