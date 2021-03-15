package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
              if(validateInput())
              {
                  signIn();

              }
              else
              {
                  Toast.makeText(getApplicationContext(),"Invalid Entry",Toast.LENGTH_SHORT).show();
              }

            }
        });

    }

    User getUserCredentials() {

        return new User(email.getText().toString(), password.getText().toString());
    }

     public boolean validateInput()
     {
         if (email.getText().toString().equals("")   ||password.getText().toString().equals(""))
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
                .baseUrl("http://ec2-3-16-108-16.us-east-2.compute.amazonaws.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//         creating Api service
        APIService service = retrofit.create(APIService.class);
        Call<LogInResult> call = service.userLogin(user.getEmailId(), user.getPassword());

        call.enqueue(new Callback<LogInResult>() {
            @Override
            public void onResponse(Call<LogInResult> call, Response<LogInResult> response) {
                System.out.println(user.getEmailId());
                System.out.println(user.getPassword());
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    User user1 = new User(user.getEmailId(), user.getPassword(), response.body().getToken());
                    SharedPreference sharedPreference = SharedPreference.getInstance(getApplicationContext());
                    sharedPreference.userLogin(user1);
                    Intent intent = new Intent(LogIn.this, Home.class);
                    startActivity(intent);
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


}