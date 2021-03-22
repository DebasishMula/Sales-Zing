package com.example.testproject2.api;

import com.example.testproject2.models.HomeList;
import com.example.testproject2.models.LogInResult;
import com.example.testproject2.models.MasterItem;
import com.example.testproject2.models.MasterResponse;
import com.example.testproject2.models.PosItem;
import com.example.testproject2.models.RegisterItem;
import com.example.testproject2.models.ResponseSms;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {


    @POST("szapi.php?action=getitemdetails")
    Call<List<MasterItem>> getMasterList(
            @Body JsonObject dbname


            );


    //the signin call
    @FormUrlEncoded
    @POST("auth/login/")
    Call<LogInResult> userLogin(
            @Field("mobile") String email,
            @Field("password") String password);
           // @Field("deviceId") String deviceId,
            //@Field("ipAddress") String ipAddress);

    @POST("szapi.php?action=getbatchdetails")
    Call<ArrayList<PosItem>> getPosLists(
            @Body JsonObject dbname


    );

    @POST("szapi.php?action=postposdetails")
    Call<ResponseSms> savePosList(
            @Body JsonObject data
    );
    @POST("szapi.php?action=getdashboarddetails")
    Call<ArrayList<HomeList>> getHomeLists(
            @Body JsonObject data
    );
    @POST("szapi.php?action=getsalesregister")
    Call<ArrayList<RegisterItem>> getRegisterList(
            @Body JsonObject data
    );

}
