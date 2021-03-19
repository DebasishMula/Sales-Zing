package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject2.R;
import com.example.testproject2.adapter.AdapterOfHomeItems;
import com.example.testproject2.api.APIService;
import com.example.testproject2.api.APIURL;
import com.example.testproject2.helpers.SharedPreference;
import com.example.testproject2.helpers.TokenInterceptor;
import com.example.testproject2.models.HomeList;
import com.google.gson.JsonObject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    private TextView FormDtae,ToDate;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        FormDtae=findViewById(R.id.homeFormDateButtom);
        ToDate=findViewById(R.id.homeToDateButtom);
        FormDtae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Home.this, fromDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Home.this, toDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        recyclerView=findViewById(R.id.home_recycler_view);
        getHomeLists();

    }
//    From Date
    DatePickerDialog.OnDateSetListener fromDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            FormDtae.setText(sdf.format( myCalendar.getTime()));
            //Toast.makeText(getApplicationContext(),sdf.format( myCalendar.getTime()),Toast.LENGTH_LONG).show();
            //System.out.println(sdf.format( myCalendar.getTime()));

        }

    };
//    ToDate
    DatePickerDialog.OnDateSetListener toDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            ToDate.setText(sdf.format( myCalendar.getTime()));
            //Toast.makeText(getApplicationContext(),sdf.format( myCalendar.getTime()),Toast.LENGTH_LONG).show();
            //System.out.println(sdf.format( myCalendar.getTime()));

        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home_toolbar_master:
                Intent intent = new Intent(Home.this, Master.class);
                startActivity(intent);
                break;
            case R.id.home_toolbar_logout:
                SharedPreference sharedPreference=SharedPreference.getInstance(getApplicationContext());
                sharedPreference.logout();
                Intent intent1=new Intent(Home.this,LogIn.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.home_toolbar_pos:
                Intent intent2=new Intent(Home.this,Pos.class);
                startActivity(intent2);
                break;
            case R.id.home_toolbar_register:
                Intent intent3=new Intent(Home.this,Register.class);
                startActivity(intent3);
                break;
            case R.id.home_toolbar_about:
                Intent intent4=new Intent(Home.this,About.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
        return true;
    }
    public void getHomeLists(){
        ProgressDialog progressDialog = new ProgressDialog(Home.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(APIURL.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIService apiServices= retrofit.create(APIService.class);
        System.out.println(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dbname","cw_ajanta");
        System.out.println(jsonObject);
        Call<ArrayList<HomeList>> call=apiServices.getHomeLists(jsonObject);
        call.enqueue(new Callback<ArrayList<HomeList>>() {
            @Override
            public void onResponse(Call<ArrayList<HomeList>> call, Response<ArrayList<HomeList>> response) {
                if(response.isSuccessful()){
                    System.out.println("hello brere");
                    progressDialog.dismiss();
                    ArrayList<HomeList> homeLists=response.body();
                    System.out.println(homeLists.toString());
                    AdapterOfHomeItems homeAdapter=new AdapterOfHomeItems(Home.this,homeLists);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(homeAdapter);

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<HomeList>> call, Throwable t) {
                Log.v("rese",call.request().url().toString());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}