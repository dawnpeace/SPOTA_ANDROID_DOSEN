package com.example.dawnpeace.spota_android_dosen;

import android.util.Log;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.Auth;
import com.google.firebase.messaging.FirebaseMessagingService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private SharedPrefHelper mSharedPref = SharedPrefHelper.getInstance(this);

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("FCMTOKEN", "onNewToken: " + s);
        mSharedPref.storeFirebaseToken(s);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Auth auth = retrofit.create(Auth.class);
        Call<Void> call = auth.storeFCMToken(s);
        final String token = s;
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("FCMTOKEN", "onResponse: " + token);
                    mSharedPref.storeFirebaseToken(token);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }


}
