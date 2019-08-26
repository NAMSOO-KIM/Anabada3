package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Product_description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String desc=intent.getStringExtra("desc");

        final TextView textView1=findViewById(R.id.description_text1);
        final TextView textView2=findViewById(R.id.description_text2);

        textView1.setText(title);
        textView2.setText(desc);
    }
}
