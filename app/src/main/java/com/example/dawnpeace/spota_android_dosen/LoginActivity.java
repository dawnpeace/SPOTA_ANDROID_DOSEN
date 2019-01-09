package com.example.dawnpeace.spota_android_dosen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.Login;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.Auth;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText et_identity_number;
    private EditText et_password;
    private Button bt_login;
    SharedPrefHelper mSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPref = SharedPrefHelper.getInstance(this);
        if(mSharedPref.isLoggedIn()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);
        initview();

    }

    private void initview(){
        et_identity_number = findViewById(R.id.et_identity_number);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.btn_login);





        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String identity_number = et_identity_number.getText().toString();
                String password = et_password.getText().toString();
                if(isValid(identity_number,password)){
                    login(identity_number,password);
                }
            }
        });
    }

    private boolean isValid(String... texts){
        for(String text: texts){
            if(text.isEmpty()){
                return false;
            }
        }
        return true;
    }

    private void login(String identity_number, String password){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Auth auth = retrofit.create(Auth.class);
        Call<Login> call = auth.login(identity_number,password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    if(response.body().getType().equals("D") ){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        mSharedPref.storeToken(response.body().getAccess_token());
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, "Akun tidak ditemukan !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
