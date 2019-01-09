package com.example.dawnpeace.spota_android_dosen;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.Praoutline;
import com.example.dawnpeace.spota_android_dosen.Model.Statistic.Lecturer;
import com.example.dawnpeace.spota_android_dosen.Model.Statistic.Statistic;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.StatisticAdapter.ExpertiseAdapter;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.StatisticAdapter.LecturerAdapter;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.StatisticAdapter.SemesterAdapter;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.PraoutlineInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatisticActivity extends AppCompatActivity {

    private RecyclerView rv_expertise;
    private RecyclerView rv_lecturer;
    private RecyclerView rv_semester;
    private TextView tv_alltime_open;
    private TextView tv_alltime_approved;
    private TextView tv_alltime_rejected;
    private TextView tv_alltime_closed;
    private TextView tv_semester_approved;
    private TextView tv_semester_rejected;
    private TextView tv_semester_closed;
    private RelativeLayout rl_progressbar;
    private NestedScrollView nsv_parent;

    private SharedPrefHelper mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        if(getSupportActionBar() !=null){
            getSupportActionBar().setTitle(getString(R.string.menu_statistic));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mSharedPref = SharedPrefHelper.getInstance(this);
        initView();
        loadData();
    }

    private void initView(){
        rv_expertise = findViewById(R.id.rv_expertise_statistic);
        rv_lecturer = findViewById(R.id.rv_lecturer_statistic);
        rv_semester = findViewById(R.id.rv_draft_count_by_semester);
        tv_alltime_open = findViewById(R.id.tv_current_open_draft);
        tv_alltime_approved = findViewById(R.id.tv_alltime_approved);
        tv_alltime_closed = findViewById(R.id.tv_alltime_closed);
        tv_alltime_rejected = findViewById(R.id.tv_alltime_rejected);
        tv_semester_approved = findViewById(R.id.tv_semester_approved);
        tv_semester_closed = findViewById(R.id.tv_semester_closed);
        tv_semester_rejected = findViewById(R.id.tv_semester_rejected);
        rl_progressbar = findViewById(R.id.rl_progress_bar);

        nsv_parent = findViewById(R.id.nsv_statistic);

        rv_lecturer.setNestedScrollingEnabled(false);
        rv_semester.setNestedScrollingEnabled(false);
        rv_expertise.setNestedScrollingEnabled(false);

    }

    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PraoutlineInterface praoutlineInterface = retrofit.create(PraoutlineInterface.class);
        Call<Statistic> call = praoutlineInterface.getStatistic();
        call.enqueue(new Callback<Statistic>() {
            @Override
            public void onResponse(Call<Statistic> call, Response<Statistic> response) {
                if(response.isSuccessful()){
                    setView(response.body());
                    rl_progressbar.setVisibility(View.GONE);
                    nsv_parent.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(StatisticActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Statistic> call, Throwable t) {

            }
        });
    }

    private void setView(Statistic statistic){
        ExpertiseAdapter expertiseAdapter = new ExpertiseAdapter(this,statistic.getExpertise());
        LecturerAdapter lecturerAdapter = new LecturerAdapter(this,statistic.getLecturers());
        SemesterAdapter semesterAdapter = new SemesterAdapter(this,statistic.getStatus().getUploaded_by_semester());

        rv_expertise.setLayoutManager(new LinearLayoutManager(this));
        rv_expertise.setAdapter(expertiseAdapter);

        rv_semester.setLayoutManager(new LinearLayoutManager(this));
        rv_semester.setAdapter(semesterAdapter);

        rv_lecturer.setLayoutManager(new LinearLayoutManager(this));
        rv_lecturer.setAdapter(lecturerAdapter);

        String alltime_open = "Draft yang masih terbuka : "+ String.valueOf(statistic.getStatus().getCurrent_open());
        String alltime_approved = String.valueOf(statistic.getStatus().getAlltime().getApproved());
        String alltime_rejected = String.valueOf(statistic.getStatus().getAlltime().getRejected());
        String alltime_closed = String.valueOf(statistic.getStatus().getAlltime().getClosed());

        String semester_approved = String.valueOf(statistic.getStatus().getSemester().getApproved());
        String semester_rejected = String.valueOf(statistic.getStatus().getSemester().getRejected());
        String semester_closed = String.valueOf(statistic.getStatus().getSemester().getClosed());

        tv_alltime_approved.setText(alltime_approved);
        tv_alltime_rejected.setText(alltime_rejected);
        tv_alltime_closed.setText(alltime_closed);

        tv_semester_approved.setText(semester_approved);
        tv_semester_rejected.setText(semester_rejected);
        tv_semester_closed.setText(semester_closed);
        tv_alltime_open.setText(alltime_open);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
