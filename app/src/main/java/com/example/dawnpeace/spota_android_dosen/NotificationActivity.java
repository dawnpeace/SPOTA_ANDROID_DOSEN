package com.example.dawnpeace.spota_android_dosen;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dawnpeace.spota_android_dosen.Model.Notification;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.NotificationListAdapter;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.NotificationInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationActivity extends AppCompatActivity {
    private SharedPrefHelper mSharedPref;
    private RecyclerView rv_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mSharedPref = SharedPrefHelper.getInstance(this);
        rv_notification = findViewById(R.id.rv_notification_list);
        getSupportActionBar().setTitle("Pemberitahuan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadNotification();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void loadNotification(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mSharedPref.getInterceptor())
                .build();

        NotificationInterface notification = retrofit.create(NotificationInterface.class);
        Call<List<Notification>> call = notification.getNotificationList();
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if(response.isSuccessful()){
                    rv_notification.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
                    NotificationListAdapter adapter = new NotificationListAdapter(NotificationActivity.this,response.body());
                    rv_notification.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {

            }
        });
    }
}
