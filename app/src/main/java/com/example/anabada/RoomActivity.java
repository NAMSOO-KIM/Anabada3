package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
                Button makeroomButton = (Button)findViewById(R.id.makeroomButton);
        makeroomButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                gotoIntent를 통해 버튼눌렀을 때 화면전환 가능

                Intent makeroomIntent = new Intent(RoomActivity.this, RoomRegisterActivity.class);
                RoomActivity.this.startActivity(makeroomIntent);

            }
        });
    }
}
