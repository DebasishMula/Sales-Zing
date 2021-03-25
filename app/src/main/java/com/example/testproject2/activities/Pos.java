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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.example.testproject2.models.PosItemSave;
import com.example.testproject2.models.ResponseSms;
import com.example.testproject2.models.SalesPerson;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
   private Switch aSwitch;
    private Spinner spinner;
    private String SalesPersonId;
   private boolean isCheckedSwitch;
    private EditText barcode,mobileInput;
    ArrayList<PosItem> posItemList=new ArrayList<>();
    ArrayList<PosItemSave>saveList=new ArrayList<>();
    FloatingActionButton save,cancel,add;
    Button scan;
    ImageButton btn_clear;
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
        aSwitch=findViewById(R.id.pos_switchButton);
        spinner =  findViewById(R.id.pos_sales_spinner);
//         Assign to salesPerson Name to Spinner
        getSalesPersonList();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               isCheckedSwitch=isChecked;
               if(isChecked)
                   barcode.setHint("Return Batch No.");
               else
                   barcode.setHint("Enter Batch No.");


            }
        });
        btn_clear=findViewById(R.id.pos_btn_clear);
        mobileInput=findViewById(R.id.posMobile);
        recyclerView=findViewById(R.id.pos_recycler_view);
        cancel=findViewById(R.id.pos_floating_action_button2);
        save=findViewById(R.id.pos_floating_action_button1);
        add=findViewById(R.id.pos_floating_action_button3);
        scan=findViewById(R.id.posScan);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcode.setText("");
            }
        });
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
                if (!barcode.getText().toString().equals("")) { //if edittext include text
                    btn_clear.setVisibility(View.VISIBLE);
                } else { //not include text
                    btn_clear.setVisibility(View.GONE);


                }
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
               if(saveList.size()!=0)
               {
                   AlertDialog.Builder builder=new AlertDialog.Builder(Pos.this,R.style.AlertDialogCustom);
                   builder.setTitle("Alert");
                   builder.setMessage("Want to Close this tab?");
                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           onBackPressed();
                           finish();
                       }
                   }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();

                       }

                   });

                   AlertDialog dialog =builder.create();
                   dialog.show();
               }
               else
               {
                   onBackPressed();
                   finish();
               }

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Pos.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                ActivityManager activityManager = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
//                List<ActivityManager.RunningTaskInfo> list=activityManager.getRunningTasks(2);
//                System.out.println(list);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return false;

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
            String qty;
            if(isCheckedSwitch)
                qty="-1";
            else qty="1";
            if(response.isSuccessful())
            {
                progressDialog.dismiss();
                try {
                    ArrayList<PosItem> rs = response.body();
                    if (rs.size()>0) {

                        PosItem pos1 = rs.get(0);
                        pos1.setQty(qty);
                        posItemList.add(pos1);
                        PosItemSave posItemSave=new PosItemSave(pos1.getItem_id(),pos1.getBatch_name(),qty, pos1.getMRP(),pos1.getColor_name(),pos1.getUomName(),pos1.getGstRate(),String.valueOf(1*Float.valueOf(pos1.getMRP())),pos1.getFSize());
                        System.out.println(qty);
                        saveList.add(posItemSave);
                        AdapterOfPositems adapter = new AdapterOfPositems(Pos.this, posItemList,saveList,recyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);
                        aSwitch.setChecked(false);
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
        jsonObject1.addProperty("mobileno",mobileInput.getText().toString());
        Gson gson = new GsonBuilder().create();

        JsonArray jsonArray1 = gson.toJsonTree(saveList).getAsJsonArray();
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
                Toast.makeText(this,"Invalid Barcode",Toast.LENGTH_SHORT).show();
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
    private void getSalesPersonList()
    {


        ProgressDialog progressDialog = new ProgressDialog(Pos.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        TokenInterceptor interceptor=new TokenInterceptor(SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(APIURL.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIService apiServices= retrofit.create(APIService.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("authtoken",SharedPreference.getInstance(getApplicationContext()).getUser().getToken());
        jsonObject.addProperty("userid",SharedPreference.getInstance(getApplicationContext()).getUser().getUserId());

        System.out.println(jsonObject);
        Call<ArrayList<SalesPerson>> call=apiServices.getSalesPersonList(jsonObject);
        System.out.println(jsonObject);
        call.enqueue(new Callback<ArrayList<SalesPerson>>() {
            @Override
            public void onResponse(Call<ArrayList<SalesPerson>> call, Response<ArrayList<SalesPerson>> response) {

                if(response.isSuccessful())
                {
                    progressDialog.dismiss();
                    ArrayList<SalesPerson> salesPersonList=response.body();
                    String[] item =new String[salesPersonList.size()];
                    for(int i=0;i<salesPersonList.size();i++){

                        item[i]=salesPersonList.get(i).getSalespersonname();
                    }
                    //System.out.println(item.length);
                    ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,item);
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            SalesPersonId=salesPersonList.get(position).getSalespersonID();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<SalesPerson>> call, Throwable t) {
                Log.v("rese",call.request().url().toString());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}

