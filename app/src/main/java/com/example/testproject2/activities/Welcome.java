package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.testproject2.R;
import com.example.testproject2.helpers.SharedPreference;

public class Welcome extends AppCompatActivity {
    private static int splash_time_out=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreference sharedPreference=SharedPreference.getInstance(getApplicationContext());
                if(sharedPreference.isLoggedIn())
                {
                    Intent intent=new Intent(Welcome.this,Home.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(Welcome.this,LogIn.class);
                    startActivity(intent);
                }

                finish();
            }
        },splash_time_out);

    }
}