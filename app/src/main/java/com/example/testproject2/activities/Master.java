package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testproject2.R;
import com.example.testproject2.adapter.AdapterOfMasterItem;
import com.example.testproject2.api.APIService;
import com.example.testproject2.api.APIURL;
import com.example.testproject2.helpers.SharedPreference;
import com.example.testproject2.helpers.TokenInterceptor;
import com.example.testproject2.models.MasterItem;
import com.example.testproject2.models.MasterResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Master extends AppCompatActivity {
   private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        listView=findViewById(R.id.masterListView);
         getMasterItems();
    }


    public void getMasterItems(){
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(APIURL.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIService apiServices= retrofit.create(APIService.class);
        System.out.println(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dbname","cw_saleszing");
        Call<List<MasterItem>> call=apiServices.getMasterList(jsonObject);
        call.enqueue(new Callback<List<MasterItem>>() {
            @Override
            public void onResponse(Call<List<MasterItem>> call, Response<List<MasterItem>> response) {
                if(response.isSuccessful())
                {
                    System.out.println(response.body());
                    System.out.println("hello");
                   List<MasterItem> rs = response.body();
                    Log.e("Success", new Gson().toJson(response.body()));
                    //Toast.makeText(getApplicationContext(),rs.get(0).getTaxper(),Toast.LENGTH_LONG).show();
                    AdapterOfMasterItem adapter=new AdapterOfMasterItem(Master.this,rs);
                   listView.setAdapter(adapter);

                }
                else
                {
                    System.out.println("error");
                    System.out.println(response.message());
                    System.out.println(response.body());
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