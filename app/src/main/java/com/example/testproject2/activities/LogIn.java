package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;

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
    private EditText email,password;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logIn=findViewById(R.id.login_login_btn);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });

    }

  User getUserCredentials()
  {
//      user.setEmailId(email.getText().toString());
//      user.setPassword(password.getText().toString());
//      return user;

     return new User(email.getText().toString(),password.getText().toString());
  }
    void signIn()
    {
     this.user=getUserCredentials();
     //creating Retrofit Object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-3-16-108-16.us-east-2.compute.amazonaws.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIService service = retrofit.create(APIService.class);
        Call<LogInResult> call = service.userLogin(user.getEmailId(),user.getPassword());
        call.enqueue(new Callback<LogInResult>() {
            @Override
            public void onResponse(Call<LogInResult> call, Response<LogInResult> response) {
                System.out.println(user.getEmailId());
                System.out.println(user.getPassword());
                if(response.isSuccessful())
                {
                    User  user1 =new User(user.getEmailId(),user.getPassword(),response.body().getToken());
                    SharedPreference sharedPreference=SharedPreference.getInstance(getApplicationContext());
                    sharedPreference.userLogin(user1);
                    Toast.makeText(getApplicationContext(),sharedPreference.getUser().getEmailId(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),sharedPreference.getUser().getToken(),Toast.LENGTH_LONG).show();
                    Log.e("Email",sharedPreference.getUser().getEmailId());
                    Log.e("Token",sharedPreference.getUser().getToken());

                    Intent intent=new Intent(LogIn.this,Home.class);
                    startActivity(intent);
                }

                else
                    {

                    Toast.makeText(getApplicationContext(),"Log In Failed",Toast.LENGTH_LONG).show();
                   }
                //Toast.makeText(getApplicationContext(),response.,Toast.LENGTH_LONG).show();
               // Intent intent=new Intent(LogIn.this,MainActivity.class);
               // startActivity(intent);
            }

            @Override
            public void onFailure(Call<LogInResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


}