package com.example.anabada;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class GyuzActivity4 extends AppCompatActivity {

    Button btn1;
    RadioButton rd1,rd2, rd3, rd4;
    TextView tv;
    Integer num1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyuz4);
        btn1 = findViewById(R.id.button);
        rd1 = findViewById(R.id.radioButton4);
        rd2 = findViewById(R.id.radioButton5);
        rd3 = findViewById(R.id.radioButton6);
        rd4 = findViewById(R.id.radioButton7);
        tv = findViewById(R.id.textView2);
        Intent intent = getIntent();
        num1 = intent.getIntExtra("result",0);//쎄컨액티에서가져온 점수
        tv.setText("현재까지 점수 : "+num1);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1;
                if(rd2.isChecked() == true){
                    num1++;
                    intent1 = new Intent(getApplicationContext(),GyuzActivity5.class);
                    intent1.putExtra("result",num1.intValue());
                }else{
                    intent1 = new Intent(getApplicationContext(),GyuzActivity5.class);
                    intent1.putExtra("result",num1.intValue());
                }
                startActivity(intent1);
            }
        });
    }
}
