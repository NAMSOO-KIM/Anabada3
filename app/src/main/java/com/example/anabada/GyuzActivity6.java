package com.example.anabada;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GyuzActivity6 extends AppCompatActivity {

    TextView tv;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyuz6);
        tv = findViewById(R.id.textView3);
        btn1 = findViewById(R.id.button2);
        Intent intent = getIntent();
        int num1 = intent.getIntExtra("result",0);
        tv.setText("참 잘했어요! "+num1+"개 맞았습니다!");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),GyuzActivity1.class);
                startActivity(intent1);
            }
        });

    }
}
