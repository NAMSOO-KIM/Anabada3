package com.example.anabada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class Member_register extends AppCompatActivity {

    private static final String TAG="Member_register";

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_register);

        Button memberbutton = findViewById(R.id.member_register);
        memberbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.member_register:
                        profileUpdate();
                        break;
                }
            }
            });
    }

    //회원정보 업데이트
    private void profileUpdate(){
    String name= ((EditText)findViewById(R.id.nameEditText)).getText().toString();
    String phonenumber=((EditText)findViewById(R.id.phoneEditText)).getText().toString();

    if(name.length()>0 && phonenumber.length()>9){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//데이터베이스에 사용자 uid를 입력받을 수 있음 user 에 !! user가 키 값!
        FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어베이스 인스턴스 초기화

        Memberinfo memberinfo = new Memberinfo(name,phonenumber);

        if(user != null){
            db.collection("users").document(user.getUid()).set(memberinfo)
             .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startToast("회원정보 등록을 성공했습니다.");

                   finish();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            startToast("회원정보 등록에 실패했습니다.");
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

        }

        }
        else {
            startToast("회원 정보를 입력하세요");
        }
    }


    private void startToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }




}
