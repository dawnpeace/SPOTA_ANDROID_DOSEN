package com.example.dawnpeace.spota_android_dosen.RetrofitInterface;

import com.example.dawnpeace.spota_android_dosen.Model.Schedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ScheduleInterface {
    @POST("api/dosen/jadwal-saya")
    Call<List<Schedule>> getSchedule();
}
