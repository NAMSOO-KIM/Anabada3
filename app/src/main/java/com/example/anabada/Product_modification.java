package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Product_modification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_modification);
    }

    public void regist(View view) {
        Intent intent =new Intent(getApplicationContext(),Product_regist.class);
        startActivity(intent);
    }
}
