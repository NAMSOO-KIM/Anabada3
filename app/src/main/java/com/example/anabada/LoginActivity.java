package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//회원가입 버튼 눌렀을때 이벤트 처리
        TextView registerButton = (TextView)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 누를때 화면 전환
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class    );
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        //회원가입 버튼 눌렀을때 이벤트 처리
        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 누를때 화면 전환
                Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
                LoginActivity.this.startActivity(loginIntent);
            }
        });

    }
}
