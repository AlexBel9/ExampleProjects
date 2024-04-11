package com.example.goldapplenotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.goldapplenotice.receivers.MainReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button addProductButton;
    private Button showListButton;
    private Button showChangeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 33) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    1);
        }

        init();
        Intent intent = new Intent(this, MainReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainReceiver receiver = new MainReceiver();
        receiver.onReceive(this, intent);

    }


    private void init(){
        addProductButton = findViewById(R.id.addProduct);
        showListButton = findViewById(R.id.showListProduct);
        showChangeList = findViewById(R.id.showChangeList);
        addProductButton.setOnClickListener(this);
        showListButton.setOnClickListener(this);
        showChangeList.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.equals(addProductButton)){
            Intent intent = new Intent(MainActivity.this, AddProduct.class);
            startActivity(intent);
        }
        if(view.equals(showListButton)){
            Intent intent = new Intent(MainActivity.this, ShowProductList.class);
            startActivity(intent);
        }
        if(view.equals(showChangeList)){
            Intent intent = new Intent(MainActivity.this,CheckChange.class);
            startActivity(intent);
        }
    }
}