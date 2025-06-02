package com.example.loginandregisterusingretrofit;

import com.example.loginandregisterusingretrofit.ModelClass.DeleteModel;
import com.example.loginandregisterusingretrofit.ModelClass.FetchModel;
import com.example.loginandregisterusingretrofit.ModelClass.ForgetPasswordModel;
import com.example.loginandregisterusingretrofit.ModelClass.LoginModel;
import com.example.loginandregisterusingretrofit.ModelClass.RegisterModel;
import com.example.loginandregisterusingretrofit.ModelClass.UpdateModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegisterApi {
    @FormUrlEncoded
    @POST("user")
    Call<RegisterModel> Register(@Field("key_name") String name,
                                 @Field("key_email") String email,
                                 @Field("key_password") String password,
                                 @Field("key_gender") String gender,
                                 @Field("key_course") String course,
                                 @Field("key_location") String location,
                                 @Field("key_phone") String phone);

    @FormUrlEncoded
    @POST("user")
    Call<LoginModel> Logins(@Field("key_email") String email,
                            @Field("key_password") String password);
    @GET("Fetch.php")
    Call<ArrayList<FetchModel>> GetAllDetails();

    @FormUrlEncoded
    @POST("user")
    Call<ForgetPasswordModel>  ForgetPassword(@Field("key_email") String email,
                                              @Field("key_password") String password);

    @FormUrlEncoded
    @POST("user")
    Call<UpdateModel>  UpdateAccount(@Field("key_name") String name,
                                     @Field("key_email") String email,
                                     @Field("key_password") String password,
                                     @Field("key_gender") String gender,
                                     @Field("key_course") String course,
                                     @Field("key_location") String location,
                                     @Field("key_phone") String phone);

    @FormUrlEncoded
    @POST("user")
    Call<DeleteModel> DeleteAccount(@Field("key_name")String name);

}
