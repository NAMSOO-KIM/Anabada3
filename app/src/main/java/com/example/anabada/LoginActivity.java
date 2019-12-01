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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioButton option_client;
    private RadioButton option_server;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);



        findViewById(R.id.gotomainButton).setOnClickListener(onClickListener);//gotomain버튼 클릭시 로직
        findViewById(R.id.gotoregistButton).setOnClickListener(onClickListener);


    }
    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.gotomainButton:
                    Log.w(TAG, "signUp done");
                    signUp();
                    break;
                case R.id.gotoregistButton:
                    createUp();
                    break;

            }
        }
    };
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
        }

    }
    private void signUp(){
        String username = ((EditText)findViewById(R.id.idText)).getText().toString();
        String password = ((EditText)findViewById(R.id.idText)).getText().toString();
        Log.w(TAG, "name pass");
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.w(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            startToast("회원 정보가 없습니다");

                        }
                    }
                });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Log.w(TAG, user.getUid());
        Log.w(TAG, "create done");
    }

    private void createUp(){
        String username= ((EditText)findViewById(R.id.idText)).getText().toString();
        String password = ((EditText)findViewById(R.id.idText)).getText().toString();
        Log.w(TAG, "name pass");
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.w(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startToast("회원 가입 완료!!");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            startToast("회원정보가 존재합니다.");

                        }
                    }
                });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Log.w(TAG, user.getUid());
        Log.w(TAG, "create done");
    }

    private void startToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}
