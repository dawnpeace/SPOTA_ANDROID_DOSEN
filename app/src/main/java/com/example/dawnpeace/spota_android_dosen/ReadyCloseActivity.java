package com.example.dawnpeace.spota_android_dosen;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.Draft;
import com.example.dawnpeace.spota_android_dosen.Model.Praoutline;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.PraoutlineDraftAdapter;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.PraoutlineInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadyCloseActivity extends AppCompatActivity {

    private SharedPrefHelper mSharedPref;
    private LinearLayout ll_progressbar;
    private RecyclerView rv_praoutline;
    private SwipeRefreshLayout sr_draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_close);
        mSharedPref = SharedPrefHelper.getInstance(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Draft Siap Close");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initView();
        loadData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initView(){
        ll_progressbar = findViewById(R.id.ll_pb_readyclose);
        rv_praoutline = findViewById(R.id.rv_ready_close);
        sr_draft = findViewById(R.id.sr_readyclose);
        sr_draft.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

        PraoutlineInterface praoutlineInterface = retrofit.create(PraoutlineInterface.class);
        Call<List<Praoutline>> call = praoutlineInterface.getReadyDraft();
        call.enqueue(new Callback<List<Praoutline>>() {
            @Override
            public void onResponse(Call<List<Praoutline>> call, Response<List<Praoutline>> response) {
                if(response.isSuccessful()){
                    rv_praoutline.setLayoutManager(new LinearLayoutManager(ReadyCloseActivity.this));
                    rv_praoutline.setAdapter(new PraoutlineDraftAdapter(ReadyCloseActivity.this,response.body()));
                    sr_draft.setVisibility(View.VISIBLE);
                    ll_progressbar.setVisibility(View.GONE);
                    sr_draft.setRefreshing(false);
                } else {
                    Toast.makeText(ReadyCloseActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    sr_draft.setVisibility(View.VISIBLE);
                    ll_progressbar.setVisibility(View.GONE);
                    sr_draft.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Praoutline>> call, Throwable t) {
                sr_draft.setVisibility(View.VISIBLE);
                ll_progressbar.setVisibility(View.GONE);
                sr_draft.setRefreshing(false);
            }
        });
    }
}
