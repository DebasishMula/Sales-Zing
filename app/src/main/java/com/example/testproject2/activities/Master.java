package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.testproject2.R;
import com.example.testproject2.adapter.AdapterOfMasterItem;
import com.example.testproject2.api.APIService;
import com.example.testproject2.api.APIURL;
import com.example.testproject2.models.MasterItem;
import com.example.testproject2.models.MasterResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Master extends AppCompatActivity {
   private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        recyclerView=findViewById(R.id.masterRecyclerView);
         getMasterItems();
    }


    public void getMasterItems(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(APIURL.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIService apiServices= retrofit.create(APIService.class);
        Call<List<MasterItem>> call=apiServices.getMasterList(  "getitemdetails","cw_saleszing");
        call.enqueue(new Callback<List<MasterItem>>() {
            @Override
            public void onResponse(Call<List<MasterItem>> call, Response<List<MasterItem>> response) {
                if(response.isSuccessful())
                {
                    //System.out.println(response.body());
                   List<MasterItem> rs = response.body();
                    Log.e("Success", new Gson().toJson(response.body()));
                    //Toast.makeText(getApplicationContext(),rs.get(0).getTaxper(),Toast.LENGTH_LONG).show();
                   // AdapterOfMasterItem adapter=new AdapterOfMasterItem(Master.this,rs);
                   // recyclerView.setLayoutManager(new LinearLayoutManager(Master.this));
                  //  recyclerView.setAdapter(adapter);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<MasterItem>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}