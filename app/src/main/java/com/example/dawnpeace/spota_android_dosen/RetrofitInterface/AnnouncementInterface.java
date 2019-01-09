package com.example.dawnpeace.spota_android_dosen.RetrofitInterface;

import com.example.dawnpeace.spota_android_dosen.Model.Announcement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface AnnouncementInterface {
    @POST("api/dosen/pengumuman")
    Call<List<Announcement>> getAnnouncement();
}
