package com.example.anabada;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PressGameActivity extends AppCompatActivity implements View.OnClickListener{
    ProgressBar mProgressBar;
    TextView mTextView_progress;
    Boolean IsRunning;
    ProgressHandler mHandler_progress;
    private DatabaseReference mDatabase;
    private Button[] mButton = new Button[20];
    private ImageView[] imv=new ImageView[20];
    private int current_number;
    String name="dong";
    private String username;
    private String userID;
    long score;
    static FirebasePost post;
    static  Map<String, Object> postValues = null;
    private static final String TAG = "DocSnippets";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press_game);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar_timer);
        mTextView_progress = (TextView) findViewById(R.id.textView_progress);
        mHandler_progress = new ProgressHandler();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            userID = user.getUid();
        }
        getDocument();

        mButton[0] = (Button) findViewById(R.id.button1);
        mButton[1] = (Button) findViewById(R.id.button2);
        mButton[2] = (Button) findViewById(R.id.button3);
        mButton[3] = (Button) findViewById(R.id.button4);
        mButton[4] = (Button) findViewById(R.id.button5);
        mButton[5] = (Button) findViewById(R.id.button6);
        mButton[6] = (Button) findViewById(R.id.button7);
        mButton[7] = (Button) findViewById(R.id.button8);
        mButton[8] = (Button) findViewById(R.id.button9);
        mButton[9] = (Button) findViewById(R.id.button10);
        mButton[10] = (Button) findViewById(R.id.button11);
        mButton[11] = (Button) findViewById(R.id.button12);
        mButton[12] = (Button) findViewById(R.id.button13);
        mButton[13] = (Button) findViewById(R.id.button14);
        mButton[14] = (Button) findViewById(R.id.button15);
        mButton[15] = (Button) findViewById(R.id.button16);
        mButton[16] = (Button) findViewById(R.id.button17);
        mButton[17] = (Button) findViewById(R.id.button18);
        mButton[18] = (Button) findViewById(R.id.button19);
        mButton[19] = (Button) findViewById(R.id.button20);

        Toast.makeText(getApplicationContext(),"숫자를 1부터 20까지 순서대로  빠르게 눌러주세요",Toast.LENGTH_LONG).show();

        int[] number_random = new int[20];
        for (int i = 0; i < 20; i++)
            number_random[i] = i + 1;
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0, n = r.nextInt(100); i < n; ++i) {
            int index1 = r.nextInt(20);
            int index2 = r.nextInt(20);
            int temp = number_random[index1];
            number_random[index1] = number_random[index2];
            number_random[index2] = temp;
        }
        for (int i = 0; i < 20; i++) {
            mButton[i].setOnClickListener(this);
            mButton[i].setText(Integer.toString(number_random[i]));
        }
    } // 버튼에 랜덤으로 숫자 넣기

    public class ProgressHandler extends Handler {
        public void handleMessage(Message msg){
            mProgressBar.incrementProgressBy(1);
            if(mProgressBar.getProgress() == mProgressBar.getMax()){
                mTextView_progress.setText("끝");
                IsRunning = false;
                AlertDialog mDialog = createDialogBox();
                mDialog.show();
            }
            else
                mTextView_progress.setText("시간: "+(mProgressBar.getProgress()));
        }
    } // 남은시간 표시(프로그래스 바)

    public void onStart(){
        super.onStart();
        current_number = 1;
        mProgressBar.setProgress(0);
        Thread mThread1 = new Thread(new Runnable(){
            public void run(){
                try{
                    for(int i = 0; i< 100 && IsRunning; i++){
                        Log.e("Thread Run: ",""+i);
                        Thread.sleep(1000);
                        Message msg = mHandler_progress.obtainMessage();
                        mHandler_progress.sendMessage(msg);
                    }
                }
                catch(Exception ex){
                    Log.e("MainActivity", "Exception in processing message.", ex);
                }
            }
        });
        IsRunning = true;
        mThread1.start();
    }

    public void onStop(){
        super.onStop();
        IsRunning = false;
    }

    public void onClick(View v){
        //클릭한 버튼을 받아옴
        Button mButton_Click = (Button) v;
        if(current_number == Integer.parseInt(String.valueOf(mButton_Click.getText()))){
            mButton_Click.setBackgroundColor(0x66FF0000);
            current_number++;
        }
        if(current_number == 21) {
            IsRunning = false;
            score=(long)mProgressBar.getProgress();
            AlertDialog mDialog = createDialogBox();
            mDialog.show();
            //postFirebaseDatabase(true);
        }
    } // 버튼 클릭할 때 색 바뀌기 & 다 누르면 점수 창 뜨기

    public AlertDialog createDialogBox(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("게임 종료");
        mBuilder.setMessage("걸린시간: "+(mProgressBar.getProgress())+"name : "+userID);

        mBuilder.setPositiveButton("등수 확인", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                postFirebaseDatabase(true);
                Intent intent =new Intent(getApplicationContext(),ScoreActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog mAlertDialog = mBuilder.create();
        return mAlertDialog;
    } // 점수 창 뜨기 구현

    @IgnoreExtraProperties
    public class FirebasePost {
        public Long score;
        public String name;

        public FirebasePost(){
            // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
        }

        public FirebasePost(String name, Long score) {
            this.score = score;
            this.name = name;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("name", name);
            result.put("score", score);
            return result;
        }
    }

    public void postFirebaseDatabase(boolean add){
        Map<String, Object> childUpdates = new HashMap<>();

        if(add){
            post = new FirebasePost(username, score);
            postValues = post.toMap();
        }
        childUpdates.put("/id_list/" + username, postValues);
        mDatabase.updateChildren(childUpdates);
        Log.d(TAG, "postFirebase: " + username);
    }

    public void getDocument() {
        // [START get_document]
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        username= (String) document.get("name");
                        Log.d(TAG, "DocumentSnapshot data: " + username);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        // [END get_document]
    }

}
