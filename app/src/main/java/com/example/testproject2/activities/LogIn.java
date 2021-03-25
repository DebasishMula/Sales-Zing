package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testproject2.R;
import com.example.testproject2.api.APIService;
import com.example.testproject2.helpers.SharedPreference;
import com.example.testproject2.models.LogInResult;
import com.example.testproject2.models.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.provider.Settings.Secure;

public class LogIn extends AppCompatActivity {
    private Button logIn;
    private EditText email, password;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logIn = findViewById(R.id.login_login_btn);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    signIn();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    User getUserCredentials() {

        return new User(email.getText().toString(), password.getText().toString());
    }

    public boolean validateInput() {
        if (email.getText().toString().equals("") || password.getText().toString().equals(""))
            return false;
        else
            return true;
    }

    void signIn() {
        ProgressDialog progressDialog = new ProgressDialog(LogIn.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        this.user = getUserCredentials();
        //creating Retrofit Object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://103.205.67.69/saleszingapi2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//         creating Api service
        APIService service = retrofit.create(APIService.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", user.getEmailId());
        jsonObject.addProperty("userPassword", user.getPassword());
        jsonObject.addProperty("deviceid", getDeviceID());
        jsonObject.addProperty("ipAddress", "");
        jsonObject.addProperty("appVer", "");
        System.out.println(jsonObject);
        Call<LogInResult> call = service.userLogin(jsonObject);
        call.enqueue(new Callback<LogInResult>() {
            @Override
            public void onResponse(Call<LogInResult> call, Response<LogInResult> response) {
                System.out.println(user.getEmailId());
                System.out.println(user.getPassword());
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    LogInResult result = response.body();
                    System.out.println(result.toString());
                    if(result.getStatus_code().equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),result.getStatus_message(),Toast.LENGTH_SHORT).show();
                        User user1 = new User(user.getEmailId(), "", result.getAuthtoken(), getDeviceID(), result.getUserid(), result.getBranchname(),user.getBranchid());
                        SharedPreference sharedPreference = SharedPreference.getInstance(getApplicationContext());
                        sharedPreference.userLogin(user1);
                        Intent intent = new Intent(LogIn.this, Home.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),result.getStatus_message(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LogInResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("HardwareIds")
    private String getDeviceID()
    {
        return  Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);
    }
}