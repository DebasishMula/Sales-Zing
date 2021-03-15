package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testproject2.R;
import com.example.testproject2.api.APIService;
import com.example.testproject2.api.APIURL;
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
      return new User(email.getText().toString(),password.getText().toString());
  }
    void signIn()
    {
     this.user=getUserCredentials();
     //creating Retrofit Object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIService service = retrofit.create(APIService.class);
        Call<LogInResult> call = service.userLogin(user.getEmailId(),user.getPassword());
        call.enqueue(new Callback<LogInResult>() {
            @Override
            public void onResponse(Call<LogInResult> call, Response<LogInResult> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),response.body().getToken(),Toast.LENGTH_LONG).show();
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