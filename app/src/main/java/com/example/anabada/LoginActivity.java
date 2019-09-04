package com.example.anabada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
//    String loginname;
//    Boolean logout=false;
    private static final String TAG="LoginActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        findViewById(R.id.gotomainButton).setOnClickListener(onClickListener);//gotomain버튼 클릭시 로직

//        final EditText idText = (EditText)findViewById(R.id.idText);
//        final SharedPreferences auto=getSharedPreferences("auto", Activity.MODE_PRIVATE);
//        loginname=auto.getString("inputname",null);
//
//        if (loginname!=null){
//            Toast.makeText(getApplicationContext(), loginname +"님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            LoginActivity.this.startActivity(intent);
//        }
//
//        Button gotomainButton = (Button) findViewById(R.id.gotomainButton);
//        gotomainButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String userName = idText.getText().toString();
//
//                //Lister에서 원하는 결과값 다룰수 있게
//                Response.Listener<String> responseListener = new Response.Listener<String>(){
//
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//                            if (success) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                builder.setMessage("로그인 완료!")
//                                        .setPositiveButton("확인", null)
//                                        .create()
//                                        .show();
//
//
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                LoginActivity.this.startActivity(intent);
//                            }
//
//                            else{
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                builder.setMessage("로그인 실패!")
//                                        .setNegativeButton("확인",null)
//                                        .create()
//                                        .show();
//
//                            }
//
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//
//                    }
//                };
//                if (!TextUtils.isEmpty(idText.getText())){
//                    SharedPreferences.Editor autoLogin=auto.edit();
//                    autoLogin.putString("inputname",idText.getText().toString());
//                    autoLogin.commit();
//
//                    RegisterRequest userRegisterRequest = new RegisterRequest(userName,responseListener);
//                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                    queue.add(userRegisterRequest); //버튼 클릭시 roomRegisterRequest 실행
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"이름이 필요합니다",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//

    }
    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.gotomainButton:
                    signUp();
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인 완료!")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
              LoginActivity.this.startActivity(intent);
                break;

            }
        }
    };
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
    private void signUp(){
        String username = ((EditText)findViewById(R.id.idText)).getText().toString();
        String password = ((EditText)findViewById(R.id.idText)).getText().toString();
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           //UI
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                          //UI
                        }

                        // ...
                    }
                });
    }

}