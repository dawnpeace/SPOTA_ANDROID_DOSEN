package com.example.dawnpeace.spota_android_dosen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.MailSetting;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.Auth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MailSettingActivity extends AppCompatActivity {

    private SharedPrefHelper mSharedPref;
    private LinearLayout ll_new_draft;
    private LinearLayout ll_draft_review;
    private LinearLayout ll_consultation;
    private SwitchCompat switch_new_draft;
    private SwitchCompat switch_draft_review;
    private SwitchCompat switch_consultation;
    private Button button_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_setting);
        initView();
        getSupportActionBar().setTitle("Pemberitahuan E-Mail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSharedPref = SharedPrefHelper.getInstance(this);
        getCurrentSetting();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    private void initView(){
        ll_new_draft = findViewById(R.id.ll_new_draft);
        ll_draft_review = findViewById(R.id.ll_draft_review);
        ll_consultation = findViewById(R.id.ll_consultation);

        switch_new_draft = findViewById(R.id.switch_new_draft);
        switch_draft_review = findViewById(R.id.switch_draft_review);
        switch_consultation = findViewById(R.id.switch_consultation);

        button_submit = findViewById(R.id.bt_submit_change);
    }


    private void getCurrentSetting(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mSharedPref.getInterceptor())
                .build();
        Auth auth = retrofit.create(Auth.class);
        Call<MailSetting> call = auth.getCurrentSetting();
        call.enqueue(new Callback<MailSetting>() {
            @Override
            public void onResponse(Call<MailSetting> call, Response<MailSetting> response) {
                if(response.isSuccessful()){
                    setSwitchState(response.body());
                    setViewsVisible();
                } else {
                    Toast.makeText(MailSettingActivity.this, "ERR : "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MailSetting> call, Throwable t) {
                Toast.makeText(MailSettingActivity.this, "Terjadi Kesalahan !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setViewsVisible(){
        ll_new_draft.setVisibility(View.VISIBLE);
        ll_draft_review.setVisibility(View.VISIBLE);
        ll_consultation.setVisibility(View.VISIBLE);
        button_submit.setVisibility(View.VISIBLE);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSetting(switch_new_draft.isChecked(),switch_draft_review.isChecked(),switch_consultation.isChecked());
            }
        });

    }

    private void setSwitchState(MailSetting setting){
        if(setting.getNew_draft()){
            switch_new_draft.setChecked(true);
        } else {
            switch_new_draft.setChecked(false);
        }

        if(setting.getDraft_review()){
            switch_draft_review.setChecked(true);
        } else {
            switch_draft_review.setChecked(false);
        }

        if(setting.getConsultation()){
            switch_consultation.setChecked(true);
        } else {
            switch_consultation.setChecked(false);
        }
    }

    public void sendSetting(Boolean new_draft_checked,Boolean draft_review_checked, Boolean consultation_checked){
        String new_draft = new_draft_checked ? "on" : "off";
        String draft_review = draft_review_checked ? "on" : "off";
        String consultation = consultation_checked ? "on" : "off";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mSharedPref.getInterceptor())
                .build();

        Auth auth = retrofit.create(Auth.class);
        Call<Void> call = auth.changeSetting(new_draft,draft_review,consultation);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MailSettingActivity.this, "Perubahan berhasil disimpan !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MailSettingActivity.this,MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(MailSettingActivity.this, "ERR : "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MailSettingActivity.this, "Terjadi Kesalahan !", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
