package com.example.dawnpeace.spota_android_dosen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.ConsultationInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationReplyActivity extends AppCompatActivity {
private int consultation_id ;
private String section;
private SharedPrefHelper mSharedPref;
private EditText et_message;
private Button btn_submit;
private boolean replying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_reply);

        mSharedPref = SharedPrefHelper.getInstance(this);

        Bundle bundle = getIntent().getExtras();
        consultation_id = bundle.getInt("consultation_id");
        section = bundle.getString("consultation_section");

        getSupportActionBar().setTitle("Konsultasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_message = findViewById(R.id.et_consultation_reply);
        btn_submit = findViewById(R.id.btn_consultation_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_message.getText().toString().isEmpty()){
                    et_message.setError("Field tidak boleh kosong !");
                } else {
                    sendMessage(consultation_id,section,et_message.getText().toString());
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void sendMessage(int consultation_id,String section,String message){
        if(replying){
            Toast.makeText(this, "Harap tunggu proses selesai . .", Toast.LENGTH_SHORT).show();
            return;
        }

        setReplying(true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConsultationInterface consultationInterface = retrofit.create(ConsultationInterface.class);
        Call<Void> call = consultationInterface.sendMessage(consultation_id,section,message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ConsultationReplyActivity.this, "Komentar berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    finish();
                    setReplying(false);
                } else {
                    Toast.makeText(ConsultationReplyActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    setReplying(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ConsultationReplyActivity.this, "Terjadi Kesalahan . . ", Toast.LENGTH_SHORT).show();
                setReplying(false);
            }
        });
    }

    public void setReplying(boolean replying) {
        this.replying = replying;
    }
}
