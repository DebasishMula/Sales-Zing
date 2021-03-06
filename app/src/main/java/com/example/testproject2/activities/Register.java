package com.example.testproject2.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testproject2.R;
import com.example.testproject2.adapter.AdapterOfRegisterItems;
import com.example.testproject2.api.APIService;
import com.example.testproject2.api.APIURL;
import com.example.testproject2.helpers.SharedPreference;
import com.example.testproject2.helpers.TokenInterceptor;
import com.example.testproject2.models.RegisterItem;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    private RecyclerView recyclerView;

    LinearLayout startDate,endDate,reg_count_total_val_layout,reg_heading,no_data_found;
    TextView fromDate,toDate,count_row,total_val;
    ImageButton imageButton;
    DatePicker picker;
    ExtendedFloatingActionButton posTab;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.registerToolbar);//connecting toolbar  view with toolbar object
        setSupportActionBar(toolbar); //calling method to support actionbar with toolbar
        ActionBar actionBar = getSupportActionBar();//initializing actionbar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);//setting actionbar indicator

        startDate=findViewById(R.id.start_date_layout);
        reg_count_total_val_layout=findViewById(R.id.reg_count_total_val_layout);
        reg_heading=findViewById(R.id.reg_heading);
        no_data_found=findViewById(R.id.No_Data_Found_Layout);
        recyclerView=findViewById(R.id.reg_recycler_view);
        imageButton=findViewById(R.id.reg_search_btn);
        posTab=findViewById(R.id.register_extendedFloatingButton1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisterItemList(fromDate.getText().toString(),toDate.getText().toString());
            }
        });
        fromDate=findViewById(R.id.reg_start_date);
        toDate=findViewById(R.id.reg_end_date);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        toDate.setText(formattedDate);
        fromDate.setText(formattedDate);
        endDate=findViewById(R.id.end_date_layout);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Register.this, R.style.MyDatePickerDialogTheme,fromDatePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Register.this,R.style.MyDatePickerDialogTheme, toDatePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        posTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Pos.class));
            }
        });

        getRegisterItemList(fromDate.getText().toString(),toDate.getText().toString());

    }


    DatePickerDialog.OnDateSetListener fromDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            fromDate.setText(sdf.format( myCalendar.getTime()));
            //Toast.makeText(getApplicationContext(),sdf.format( myCalendar.getTime()),Toast.LENGTH_LONG).show();
            //System.out.println(sdf.format( myCalendar.getTime()));

        }

    };
    DatePickerDialog.OnDateSetListener toDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            toDate.setText(sdf.format( myCalendar.getTime()));
            //Toast.makeText(getApplicationContext(),sdf.format( myCalendar.getTime()),Toast.LENGTH_LONG).show();
            //System.out.println(sdf.format( myCalendar.getTime()));

        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Register.this,Home.class);
                startActivity(intent);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }//to open navigation after clicking action bar


    public void getRegisterItemList(String fromDate,String todate){
        ProgressDialog progressDialog = new ProgressDialog(Register.this);
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
        jsonObject.addProperty("todate",todate);
        jsonObject.addProperty("fromdate",fromDate);
        Call<ArrayList<RegisterItem>> call=apiServices.getRegisterList(jsonObject);
        System.out.println(jsonObject);
        call.enqueue(new Callback<ArrayList<RegisterItem>>() {
            @Override
            public void onResponse(Call<ArrayList<RegisterItem>> call, Response<ArrayList<RegisterItem>> response) {
                if(response.isSuccessful())
                {
                    System.out.println(response.body().isEmpty());

                    if(!response.body().isEmpty()){
                        progressDialog.dismiss();
                        no_data_found.setVisibility(View.GONE);
                        ArrayList<RegisterItem> rs = response.body();
                        //Toast.makeText(getApplicationContext(),rs.get(0).getTaxper(),Toast.LENGTH_LONG).show();

                        AdapterOfRegisterItems adapter=new AdapterOfRegisterItems(Register.this,rs,set_count_and_total_mrp_val(rs));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);

                    }
                    else {
                        set_count_and_total_mrp_val(new ArrayList<RegisterItem>());
                        progressDialog.dismiss();
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(new AdapterOfRegisterItems(Register.this,new ArrayList<>(),set_count_and_total_mrp_val(new ArrayList<>())));
                        no_data_found.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something is wrong, Try Again!",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<RegisterItem>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public ArrayList<Integer> set_count_and_total_mrp_val(ArrayList<RegisterItem> registerItems){
        int total_mrp_val=0;
        int count=0;
        Iterator<RegisterItem> it=registerItems.iterator();
        while (it.hasNext())
        {
            total_mrp_val+=Float.parseFloat(it.next().getMrpvalue());
            count++;
        }
        ArrayList<Integer> list=new ArrayList<>() ;
        list.add(total_mrp_val);
        list.add(count);
        return list;

    }
}