package com.example.dawnpeace.spota_android_dosen.RetrofitInterface;

import com.example.dawnpeace.spota_android_dosen.Model.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface NotificationInterface {
    @POST("api/dosen/notifikasi")
    Call<List<Notification>> getNotificationList();
}
