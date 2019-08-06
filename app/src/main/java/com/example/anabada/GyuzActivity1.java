package com.example.anabada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;


public class GyuzActivity1 extends AppCompatActivity {
    RadioButton rb1,rb2,rb3,rb4;
    Button btn;
    Integer num1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyuz1);
        btn = findViewById(R.id.button);
        rb1 = findViewById(R.id.radioButton4);
        rb2 = findViewById(R.id.radioButton5);
        rb3 = findViewById(R.id.radioButton6);
        rb4 = findViewById(R.id.radioButton7);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(rb2.isChecked()==true){
                    num1=1;
                intent = new Intent(getApplicationContext(),GyuzActivity2.class);
                intent.putExtra("result",num1.intValue());
                }
                else{
                    num1=0;
                    intent = new Intent(getApplicationContext(),GyuzActivity2.class);
                    intent.putExtra("result", num1.intValue());
                }
                startActivity(intent);
            }
        });
    }
}
