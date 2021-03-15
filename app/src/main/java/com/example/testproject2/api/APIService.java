package com.example.testproject2.api;

import com.example.testproject2.models.LogInResult;
import com.example.testproject2.models.MasterItem;
import com.example.testproject2.models.MasterResponse;

import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @FormUrlEncoded
    @POST("szapi.php/")
    Call<List<MasterItem>> getMasterList(
            @Query("action") String getItemDtails,
            @Field("dbname") String dbname
    );


    //the signin call
    @FormUrlEncoded
    @POST("auth/login/")
    Call<LogInResult> userLogin(
            @Field("mobile") String email,
            @Field("password") String password);
           // @Field("deviceId") String deviceId,
            //@Field("ipAddress") String ipAddress);
}
