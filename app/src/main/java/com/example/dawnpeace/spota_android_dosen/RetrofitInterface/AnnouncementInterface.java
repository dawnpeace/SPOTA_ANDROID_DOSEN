package com.example.dawnpeace.spota_android_dosen.RetrofitInterface;

import com.example.dawnpeace.spota_android_dosen.Model.Announcement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnnouncementInterface {
    @POST("api/dosen/pengumuman")
    Call<List<Announcement>> getAnnouncement();

    @POST("api/dosen/pengumuman/{id}/lihat")
    Call<Void> readAnnouncement(@Path("id") int id);
}
