package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Product_description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String desc=intent.getStringExtra("desc");
        String img=intent.getStringExtra("image");

        final TextView textView1=findViewById(R.id.description_text1);
        final TextView textView2=findViewById(R.id.description_text2);
        final ImageView ImageView=findViewById(R.id.imageView_des);

        textView1.setText(title);
        textView2.setText(desc);
        Glide.with(this).load(img).into(ImageView);


    }
}
