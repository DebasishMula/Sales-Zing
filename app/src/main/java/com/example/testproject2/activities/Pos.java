package com.example.testproject2.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testproject2.R;
import com.example.testproject2.adapter.AdapterOfMasterItem;
import com.example.testproject2.adapter.AdapterOfPositems;
import com.example.testproject2.api.APIService;
import com.example.testproject2.api.APIURL;
import com.example.testproject2.helpers.SharedPreference;
import com.example.testproject2.helpers.TokenInterceptor;
import com.example.testproject2.models.MasterItem;
import com.example.testproject2.models.PosItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pos extends AppCompatActivity {
   private RecyclerView recyclerView;
    private EditText barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        Toolbar toolbar = findViewById(R.id.posToolbar);//connecting toolbar  view with toolbar object
        setSupportActionBar(toolbar); //calling method to support actionbar with toolbar
        ActionBar actionBar = getSupportActionBar();//initializing actionbar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);//setting actionbar indicator
        barcode=findViewById(R.id.posBarcode);
        recyclerView=findViewById(R.id.pos_recycler_view);
//        barcode.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Toast.makeText(getApplicationContext(),barcode.getText().toString(),Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getPosItemList("fg");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Pos.this,Home.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }//to open navigation after clicking action bar
public void getPosItemList(String batchname){
    ProgressDialog progressDialog = new ProgressDialog(Pos.this);
    progressDialog.setMessage("Loading...");
    progressDialog.show();
    TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(APIURL.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    APIService apiServices= retrofit.create(APIService.class);
    System.out.println(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("dbname","cw_saleszing");
    jsonObject.addProperty("batchname","10008050204GB");
    System.out.println(jsonObject);
    Call<ArrayList<PosItem>> call=apiServices.getPosLists(jsonObject);
    System.out.println();
    call.enqueue(new Callback<ArrayList<PosItem>>() {
        @Override
        public void onResponse(Call<ArrayList<PosItem>> call, Response<ArrayList<PosItem>> response) {
            if(response.isSuccessful())
            {
                progressDialog.dismiss();
                System.out.println(response.body());
                System.out.println("hello");
                ArrayList<PosItem> rs = response.body();
                Log.e("Success", new Gson().toJson(response.body()));
                //Toast.makeText(getApplicationContext(),rs.get(0).getTaxper(),Toast.LENGTH_LONG).show();
                AdapterOfPositems adapter=new AdapterOfPositems(Pos.this,rs);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);

            }
            else
            {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_LONG).show();
            }

        }
        @Override
        public void onFailure(Call<ArrayList<PosItem>> call, Throwable t) {
            Log.v("rese",call.request().url().toString());
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
        }
    });
}
}
