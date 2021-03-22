package com.example.testproject2.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testproject2.R;
import com.example.testproject2.adapter.AdapterOfMasterItem;
import com.example.testproject2.adapter.AdapterOfPositems;
import com.example.testproject2.api.APIService;
import com.example.testproject2.api.APIURL;
import com.example.testproject2.helpers.CaptureActivity;
import com.example.testproject2.helpers.SharedPreference;
import com.example.testproject2.helpers.TokenInterceptor;
import com.example.testproject2.models.MasterItem;
import com.example.testproject2.models.PosItem;
import com.example.testproject2.models.ResponseSms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
    ArrayList<PosItem> posItemList=new ArrayList<>();
    FloatingActionButton save,cancel,add;
    Button scan;
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
        cancel=findViewById(R.id.pos_floating_action_button2);
        save=findViewById(R.id.pos_floating_action_button1);
        add=findViewById(R.id.pos_floating_action_button3);
        scan=findViewById(R.id.posScan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             scanBarcode();
            }
        });


        barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==13)
                {

                    getPosItemList(s.toString());
                    barcode.setText("");
                    barcode.clearFocus();
                    hideKeyboardFrom(getApplicationContext(),barcode);
                }

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePosItemList();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               onBackPressed();
               finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Pos.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });

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
    jsonObject.addProperty("dbname","cw_ajanta");
    jsonObject.addProperty("batchname",batchname);
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
                try {
                    ArrayList<PosItem> rs = response.body();
                    if (rs.size()>0) {
                        PosItem pos1 = rs.get(0);
                        posItemList.add(pos1);
                        Log.e("Success", new Gson().toJson(response.body()));
                        AdapterOfPositems adapter = new AdapterOfPositems(Pos.this, posItemList,recyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Batch Name",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }



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

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void savePosItemList() {
        ProgressDialog progressDialog = new ProgressDialog(Pos.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(APIURL.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIService apiServices= retrofit.create(APIService.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dbname","cw_ajanta");
        jsonObject.addProperty("emailid","cw_ajanta");
        jsonObject.addProperty("authtoken","123456");
        JsonArray jsonArray=new JsonArray();
        JsonObject jsonObject1=new JsonObject();
        jsonObject1.addProperty("branchid","1");
        jsonObject1.addProperty("salespersonid","1");
        jsonObject1.addProperty("voucherno","11254");
        jsonObject1.addProperty("voucherdate","12/12/2021");
        Gson gson = new GsonBuilder().create();
        JsonArray jsonArray1 = gson.toJsonTree(posItemList).getAsJsonArray();
        jsonObject1.add("items", jsonArray1);
        jsonArray.add(jsonObject1);
        jsonObject.add("data",jsonArray);
        System.out.println(jsonObject);
        Call<ResponseSms> call=apiServices.savePosList(jsonObject);
        System.out.println(jsonObject);
        call.enqueue(new Callback<ResponseSms>() {
            @Override
            public void onResponse(Call<ResponseSms> call, Response<ResponseSms> response) {
                if(response.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),response.body().getMsgstr()+" Saved Successfully",Toast.LENGTH_LONG).show();
                    onBackPressed();
                    finish();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ResponseSms> call, Throwable t) {
                Log.v("rese",call.request().url().toString());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void scanBarcode(){
        IntentIntegrator intentIntegrator=new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureActivity.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning..");
        intentIntegrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestcode,int resultCode,Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestcode,resultCode,data);
        if(intentResult!=null){
            if(intentResult.getContents()!=null)
            {


                barcode.setText(intentResult.getContents());

            }
            else {
                Toast.makeText(this,"Invalid Barcode",Toast.LENGTH_SHORT);
            }
        }
        else {

            super.onActivityResult(requestcode,resultCode,data);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

