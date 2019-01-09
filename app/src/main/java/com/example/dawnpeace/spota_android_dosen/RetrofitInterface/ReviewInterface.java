package com.example.dawnpeace.spota_android_dosen.RetrofitInterface;

import com.example.dawnpeace.spota_android_dosen.Model.Approval;
import com.example.dawnpeace.spota_android_dosen.Model.DraftApproval;
import com.example.dawnpeace.spota_android_dosen.Model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewInterface {
    @FormUrlEncoded
    @POST("api/dosen/praoutline/{id}/reply")
    Call<Void> replyReview(@Path("id") int preoutline_id, @Field("review") String review);

    @FormUrlEncoded
    @POST("api/dosen/praoutline/{id}/reply/{comment_id}")
    Call<Void> replyReview(@Path("id") int preoutline_id, @Path("comment_id") String comment_id, @Field("review") String review);

    @POST("api/dosen/praoutline/{id}/review")
    Call<List<Review>> getReviews(@Path("id") int id);

    @POST("api/dosen/review/{id}/hapus")
    Call<Void> deleteReview(@Path("id") int id);

    @FormUrlEncoded
    @POST("api/dosen/persetujuan/{id}")
    Call<DraftApproval> approve(@Path("id") int id, @Field("approval") String approval);
}
