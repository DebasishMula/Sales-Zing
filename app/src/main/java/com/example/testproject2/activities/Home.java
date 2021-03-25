package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.testproject2.R;
import com.example.testproject2.adapter.AdapterOfHomeItems;
import com.example.testproject2.api.APIService;
import com.example.testproject2.api.APIURL;
import com.example.testproject2.helpers.SharedPreference;
import com.example.testproject2.helpers.TokenInterceptor;
import com.example.testproject2.models.HomeList;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.JsonObject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    LinearLayout fromLayout,toLayout,no_data_found;
    final Calendar myCalendar = Calendar.getInstance();
    ImageButton searchButton;
    SwipeRefreshLayout swipeRefreshLayout;
    ExtendedFloatingActionButton posTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        // set Toolbar Title
        toolbar.setTitle(SharedPreference.getInstance(getApplicationContext()).getUser().getBranchName());
//        set toolbar sub title
        toolbar.setSubtitle(SharedPreference.getInstance(getApplicationContext()).getUser().getEmailId());
        no_data_found =findViewById(R.id.No_Data_Found_Layout);
        swipeRefreshLayout=findViewById(R.id.homeSwipeToRefresh);


        FormDtae=findViewById(R.id.homeFormDateButtom);
        ToDate=findViewById(R.id.homeToDateButtom);
        fromLayout=findViewById(R.id.home_start_date_layout);
        toLayout=findViewById(R.id.home_end_date_layout);
        searchButton=findViewById(R.id.homeSearchButton);
        posTab=findViewById(R.id.home_extendedFloatingButton1);
        recyclerView=findViewById(R.id.home_recycler_view);
//        initialize FromDate And ToDate
        initializeFromAndToDate();

        fromLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Home.this,  R.style.MyDatePickerDialogTheme, fromDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        toLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Home.this,  R.style.MyDatePickerDialogTheme, toDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        getHomeLists(FormDtae.getText().toString(),ToDate.getText().toString());
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeLists(FormDtae.getText().toString(),ToDate.getText().toString());
            }
        });
        posTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Pos.class));
            }
        });
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        doYourUpdate();
                    }
                }
        );

    }
//    From Date
    DatePickerDialog.OnDateSetListener fromDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd-MM-yyyy"; //In which you need put here
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
            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            ToDate.setText(sdf.format( myCalendar.getTime()));
            //Toast.makeText(getApplicationContext(),sdf.format( myCalendar.getTime()),Toast.LENGTH_LONG).show();
            //System.out.println(sdf.format( myCalendar.getTime()));

        }

    };

    private void  doYourUpdate()
    {
        getHomeLists(FormDtae.getText().toString(),ToDate.getText().toString());
        swipeRefreshLayout.setRefreshing(false); // Disables the refresh icon
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);
        // change color for icon 0

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
//    initialize From Date And To Date
    public void initializeFromAndToDate()
    {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        Date date = new Date();
        FormDtae.setText(sdf.format(date));
        ToDate.setText(sdf.format(date));
    }
    public void getHomeLists(String fromDate,String toDate){

        ProgressDialog progressDialog = new ProgressDialog(Home.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(APIURL.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIService apiServices= retrofit.create(APIService.class);
        System.out.println(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("authtoken",SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
        jsonObject.addProperty("userid",SharedPreference.getInstance(getApplicationContext()).getUser().getUserId());
        jsonObject.addProperty("fromdate",fromDate);
        jsonObject.addProperty("todate",toDate);
        System.out.println(jsonObject);
        Call<ArrayList<HomeList>> call=apiServices.getHomeLists(jsonObject);
        call.enqueue(new Callback<ArrayList<HomeList>>() {
            @Override
            public void onResponse(Call<ArrayList<HomeList>> call, Response<ArrayList<HomeList>> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    ArrayList<HomeList> homeLists=response.body();
                    if(homeLists.size()==0)
                    {

                        no_data_found.setVisibility(View.VISIBLE);
                        AdapterOfHomeItems homeAdapter=new AdapterOfHomeItems(Home.this,homeLists);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(homeAdapter);
                    }
                    else
                    {
                        no_data_found.setVisibility(View.GONE);
                        float tCount=0,tValue=0;
                        int tQty=0;
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        for(int i=0;i<homeLists.size();i++)
                        {
                            tCount=tCount+ Float.valueOf( homeLists.get(i).getCnt());
                            tValue=tValue+ Float.valueOf( homeLists.get(i).getValue());
                            tQty=tQty+ Integer.parseInt( homeLists.get(i).getQty());
                        }
                        HomeList totalRowItem=new HomeList("",String.valueOf(tQty), String.format("%.02f", tValue),String.valueOf(tCount));
                        homeLists.add(totalRowItem);
                        AdapterOfHomeItems homeAdapter=new AdapterOfHomeItems(Home.this,homeLists);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(homeAdapter);
                    }

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