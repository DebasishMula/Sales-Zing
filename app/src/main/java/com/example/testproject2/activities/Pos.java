package com.example.testproject2.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testproject2.R;

public class Pos extends AppCompatActivity {
    private EditText barcode;
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
//        barcode.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Toast.makeText(getApplicationContext(),barcode.getText().toString(),Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Pos.this,Home.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }//to open navigation after clicking action bar

}