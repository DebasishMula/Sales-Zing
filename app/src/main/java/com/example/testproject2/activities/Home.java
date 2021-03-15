package com.example.testproject2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.testproject2.R;
import com.example.testproject2.helpers.SharedPreference;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

    }


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
}