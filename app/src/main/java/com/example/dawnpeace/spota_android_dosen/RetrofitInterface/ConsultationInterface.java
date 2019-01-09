package com.example.dawnpeace.spota_android_dosen.RetrofitInterface;

import com.example.dawnpeace.spota_android_dosen.Model.Consultation;
import com.example.dawnpeace.spota_android_dosen.Model.ConsultationReview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConsultationInterface {

    @POST("api/dosen/daftar-konsultasi")
    Call<List<Consultation>> getConsultationList();

    @POST("api/dosen/konsultasi/{id}/{section}")
    Call<List<ConsultationReview>> getReviews(@Path("id")int id,@Path("section") String section);

    @POST("api/dosen/review-konsultasi/{id}/hapus")
    Call<Void> deleteReview(@Path("id") int id);

    @FormUrlEncoded
    @POST("api/dosen/konsultasi/{id}/review/{section}")
    Call<Void> sendMessage(@Path("id") int id,@Path("section") String section,@Field("message") String message);
}
