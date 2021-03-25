package com.example.testproject2.api;

import com.example.testproject2.models.HomeList;
import com.example.testproject2.models.LogInResult;
import com.example.testproject2.models.MasterItem;
import com.example.testproject2.models.MasterResponse;
import com.example.testproject2.models.PosItem;
import com.example.testproject2.models.RegisterItem;
import com.example.testproject2.models.ResponseSms;
import com.example.testproject2.models.SalesPerson;
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
    @POST("szapi.php?action=userdetails")
    Call<LogInResult> userLogin(
            @Body JsonObject authDetails
    );

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
    @POST("szapi.php?action=getsalesperson")
    Call<ArrayList<SalesPerson>> getSalesPersonList(
            @Body JsonObject data
    );

}
