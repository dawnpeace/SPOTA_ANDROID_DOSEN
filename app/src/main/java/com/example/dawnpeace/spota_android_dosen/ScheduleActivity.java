package com.example.dawnpeace.spota_android_dosen;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.Schedule;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.ScheduleAdapter;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.ScheduleInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScheduleActivity extends AppCompatActivity {
    SharedPrefHelper mSharedPref;
    private RecyclerView rv_schedule;
    private RelativeLayout rl_progressbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mSharedPref = SharedPrefHelper.getInstance(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Jadwal Saya");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initView();
        loadData();
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
        rv_schedule = findViewById(R.id.rv_schedule);
        rl_progressbar = findViewById(R.id.rl_progress_bar);
        swipeRefreshLayout = findViewById(R.id.sr_schedule);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ScheduleInterface scheduleInterface = retrofit.create(ScheduleInterface.class);
        Call<List<Schedule>> call = scheduleInterface.getSchedule();
        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                if(response.isSuccessful()){
                    rv_schedule.setLayoutManager(new LinearLayoutManager(ScheduleActivity.this));
                    ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this,response.body());
                    rv_schedule.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(ScheduleActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {

            }
        });
    }
}
