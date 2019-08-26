package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Product_modification extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_modification);
        final TextView textView1=findViewById(R.id.modi_text1);
        final TextView textView2=findViewById(R.id.modi_text2);

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String desc=intent.getStringExtra("desc");

        textView1.setText(title);
        textView2.setText(desc);
    }

    public void regist(View view) {
        Intent intent =new Intent(getApplicationContext(),Product_regist.class);

        //String title= (String) textView1.getText();
        //String desc= (String) textView2.getText();

        //intent.putExtra("title",title);
        //intent.putExtra("desc",desc);
        startActivity(intent);
    }
}
