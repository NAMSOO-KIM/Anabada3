package com.example.anabada;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RoomRegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_register);


        //idText 는 방 제목
        //passwordText 는 방 비밀번호
       final EditText idText = (EditText)findViewById(R.id.idText);
       final EditText passwordText = (EditText)findViewById(R.id.passwordText);

        Button room_registerButton = (Button) findViewById(R.id.room_registerButton);
        room_registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomTitle = idText.getText().toString();
                String roomPassword= passwordText.getText().toString();

             //Lister에서 원하는 결과값 다룰수 있게
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RoomRegisterActivity.this);
                                builder.setMessage("방 생성 완료!")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();


                                Intent intent = new Intent(RoomRegisterActivity.this, RoomActivity.class);
                                RoomRegisterActivity.this.startActivity(intent);
                            }

                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RoomRegisterActivity.this);
                                builder.setMessage("방 생성 실패!")
                                        .setNegativeButton("확인",null)
                                        .create()
                                        .show();

                           }

                        }
                            catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                };

                RoomRegisterRequest roomRegisterRequest = new RoomRegisterRequest(roomTitle, roomPassword,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RoomRegisterActivity.this);
                queue.add(roomRegisterRequest); //버튼 클릭시 roomRegisterRequest 실행


            }
        });
    }
    //회원 등록 창 꺼지면 실행


}
