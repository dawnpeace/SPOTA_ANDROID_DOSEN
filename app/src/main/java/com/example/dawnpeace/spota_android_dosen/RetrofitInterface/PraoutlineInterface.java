package com.example.dawnpeace.spota_android_dosen.RetrofitInterface;

import com.example.dawnpeace.spota_android_dosen.Model.Draft;
import com.example.dawnpeace.spota_android_dosen.Model.Praoutline;
import com.example.dawnpeace.spota_android_dosen.Model.Statistic.PraoutlineInfo;
import com.example.dawnpeace.spota_android_dosen.Model.Statistic.Statistic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PraoutlineInterface {

    @POST("api/dosen/praoutline/daftar-baru")
    Call<List<Praoutline>> getNewDrafts();

    @POST("api/dosen/praoutline/review-saya")
    Call<List<Praoutline>> getReviewedDrafts();

    @POST("api/dosen/praoutline/draft/{id}")
    Call<Draft> getDraft(@Path("id") int id);

    @FormUrlEncoded
    @POST("api/dosen/pencarian")
    Call<List<Praoutline>> search(@Field("query") String query);

    @POST("api/dosen/statistik")
    Call<Statistic> getStatistic();

    @POST("api/dosen/info-praoutline/{id}")
    Call<PraoutlineInfo> getDraftInfo(@Path("id") int id);

    @POST("api/dosen/praoutline/siap-close")
    Call<List<Praoutline>> getReadyDraft();

    @FormUrlEncoded
    @POST("api/dosen/close-draft/{preoutline_id}")
    Call<Void> approveDraft(@Path("preoutline_id") int preoutline_id,
                          @Field("result") String result,
                          @Field("first_supervisor") int first_supervisor,
                          @Field("second_supervisor") int second_supervisor,
                          @Field("first_examiner") int first_examiner,
                          @Field("second_examiner") int second_examiner,
                          @Field("expertise_id") int expertise_id,
                          @Field("notes") String notes,
                            @Field("final_title") String final_title);

    @FormUrlEncoded
    @POST("api/dosen/close-draft/{preoutline_id}")
    Call<Void> closeDraft(@Path("preoutline_id") int preoutline_id,@Field("result") String result);
}
