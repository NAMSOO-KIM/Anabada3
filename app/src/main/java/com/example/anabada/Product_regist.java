package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Product_regist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_regist);

        //idText 는 방 제목
        //passwordText 는 방 비밀번호
        final EditText TitleText = (EditText)findViewById(R.id.editText6_title);
        final EditText ContentsText = (EditText)findViewById(R.id.editText6_description);

        Button BoardRegisterButton = (Button) findViewById(R.id.button6_registration);
        BoardRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boardTitle = TitleText.getText().toString();
                String boardContents= ContentsText.getText().toString();

                //Lister에서 원하는 결과값 다룰수 있게
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Product_regist.this);
                                builder.setMessage("등록 완료!")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();


                                Intent intent = new Intent(Product_regist.this, MainActivity.class);
                                Product_regist.this.startActivity(intent);
                            }

                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Product_regist.this);
                                builder.setMessage("등록 실패!")
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

               BoardRegisterRequest boardRegisterRequest = new BoardRegisterRequest(boardTitle, boardContents, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Product_regist.this);
                queue.add(boardRegisterRequest); //버튼 클릭시 roomRegisterRequest 실행


            }
        });
    }
    //회원 등록 창 꺼지면 실행

    }

