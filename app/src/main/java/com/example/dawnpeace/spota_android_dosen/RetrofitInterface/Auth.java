package com.example.dawnpeace.spota_android_dosen.RetrofitInterface;

import com.example.dawnpeace.spota_android_dosen.Model.Login;
import com.example.dawnpeace.spota_android_dosen.Model.MailSetting;
import com.example.dawnpeace.spota_android_dosen.Model.Message;
import com.example.dawnpeace.spota_android_dosen.Model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Auth {

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<Login> login(@Field("identity_number") String identity_number, @Field("password") String password);

    @POST("api/auth/me")
    Call<User> getUser();

    @FormUrlEncoded
    @POST("api/auth/fcm-token")
    Call<Void> storeFCMToken(@Field("fcm_token") String fcm_token);

    @Multipart
    @POST("api/dosen/update-profile")
    Call<Message> updateProfile(@Part("name") RequestBody name, @Part("email") RequestBody email, @Part("password") RequestBody password, @Part("old_password") RequestBody old_password,@Part MultipartBody.Part picture);

    @POST("api/auth/destroy-fcm")
    Call<Void> destroyFcmToken();

    @POST("api/dosen/pengaturan_mail")
    Call<MailSetting> getCurrentSetting();

    @FormUrlEncoded
    @POST("api/dosen/pengaturan_mail/ubah")
    Call<Void> changeSetting(@Field("new_draft")String new_draft, @Field("draft_review") String draft_review, @Field("consultation")String consultation);
}
