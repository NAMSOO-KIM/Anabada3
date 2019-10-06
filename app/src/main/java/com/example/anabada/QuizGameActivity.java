package com.example.anabada;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class QuizGameActivity extends AppCompatActivity {
    ProgressBar mProgressBar;
    TextView mTextView_progress;
    Boolean IsRunning;
    ProgressHandler mHandler_progress;
    TextView textview_qz,textView_qz_hint;
    Random rnd;
    EditText quiz_ex;

    private Button[] mButton = new Button[16];
    private int current_number,time=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar_timer_qz);
        mTextView_progress = (TextView) findViewById(R.id.textView_progress_qz);
        mHandler_progress = new ProgressHandler();
        textview_qz=(TextView) findViewById(R.id.textView_qz);
        textView_qz_hint=(TextView) findViewById(R.id.textView_qz_hint);
        quiz_ex=(EditText)findViewById(R.id.quiz_ex);

        Toast.makeText(getApplicationContext(),"퀴즈를 빠르게 맞춰주세요",Toast.LENGTH_LONG).show();


    }

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
        Quiz();
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
        String res=quiz_ex.getText().toString();
        if(res.equals("스톡홀름")&&current_number==1){
            current_number++;
            Quiz();
            quiz_ex.setText("");
        }
        else if (!res.equals("스톡홀름")&&current_number==1){
            quiz_ex.setText("");
            time+=10;
        }
        else if(res.equals("모드리치")&&current_number==2){
            quiz_ex.setText("");
            current_number++;
            Quiz();
        }
        else if (!res.equals("모드리치")&&current_number==2){
            quiz_ex.setText("");
            time+=10;
        }
        else if(res.equals("1953")&&current_number==3){
            quiz_ex.setText("");
            current_number++;
            Quiz();
        }
        else if (!res.equals("1953")&&current_number==3){
            quiz_ex.setText("");
            time+=10;
        }
        else if(res.equals("꿔바로우")&&current_number==4){
            quiz_ex.setText("");
            current_number++;
            Quiz();
        }
        else if (!res.equals("꿔바로우")&&current_number==4){
            quiz_ex.setText("");
            time+=10;
        }
        else if(res.equals("아랍에미리트")&&current_number==5){
            quiz_ex.setText("");
            current_number++;
            Quiz();
        }
        else if (!res.equals("아랍에미리트")&&current_number==5){
            quiz_ex.setText("");
            time+=10;
        }
        if(current_number == 6) {
            IsRunning = false;
            AlertDialog mDialog = createDialogBox();
            mDialog.show();
        }
    } // 버튼 클릭할 때 색 바뀌기 & 다 누르면 점수 창 뜨기

    public AlertDialog createDialogBox(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("게임 종료");
        int pro=mProgressBar.getProgress()+time;
        mBuilder.setMessage("걸린시간: "+pro);

        mBuilder.setPositiveButton("등수 확인", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

            }
        });

        AlertDialog mAlertDialog = mBuilder.create();
        return mAlertDialog;
    } // 점수 창 뜨기 구현

    public void Quiz(){
        if (current_number==1){
            textview_qz.setText("스웨덴의 수도는?");
            textView_qz_hint.setText("hint : 스웨덴시티, 스톡홀름, 오슬로, 브뤼셀");
        }
        else if (current_number==2){
            textview_qz.setText("가장 최근 발롱도르 수상자는?");
            textView_qz_hint.setText("hint : 호날두, 모드리치, 메시, 음바페");
        }
        else if (current_number==3){
            textview_qz.setText("6.25전쟁 휴전년도는?");
            textView_qz_hint.setText("hint : 1950, 1951 1952 1953");
        }
        else if (current_number==4){
            textview_qz.setText("3000년전부터 중국남부지방에서 한입크기로 만들어 먹던 음식은?");
            textView_qz_hint.setText("hint : 꿔바로우, 딤섬, 똠냠꿍, 깐풍기");
        }
        else if (current_number==5){
            textview_qz.setText("운동종목인 마라톤을 금지하는 페르시아의 후예인 나라는?");
            textView_qz_hint.setText("hint : 이란, 아랍에미리트, 이스라엘, 이라크");
        }
    }

}
