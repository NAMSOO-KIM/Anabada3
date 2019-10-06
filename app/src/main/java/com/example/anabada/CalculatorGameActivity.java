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

public class CalculatorGameActivity extends AppCompatActivity {
    ProgressBar mProgressBar;
    TextView mTextView_progress;
    Boolean IsRunning;
     ProgressHandler mHandler_progress;
    TextView ctextview;
    int a,b;
    Random rnd;
    EditText calc;

    private Button[] mButton = new Button[16];
    private int current_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_game);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar_timer_clac);
        mTextView_progress = (TextView) findViewById(R.id.textView_progress_clac);
        mHandler_progress = new ProgressHandler();
        ctextview=(TextView) findViewById(R.id.textView_calc);
        calc=(EditText)findViewById(R.id.calc);

        Toast.makeText(getApplicationContext(),"사칙연산을 빠르게 계산해주세요",Toast.LENGTH_LONG).show();


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
        rndNum();
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
                    ctextview.setText("오류");
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
        String str=calc.getText().toString();
        int res=Integer.parseInt(str);
        if(res==a+b){
            current_number++;
            rndNum();
        }
        if(current_number == 5) {
            IsRunning = false;
            AlertDialog mDialog = createDialogBox();
            mDialog.show();
        }
    } // 버튼 클릭할 때 색 바뀌기 & 다 누르면 점수 창 뜨기

    public AlertDialog createDialogBox(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("게임 종료");
        mBuilder.setMessage("걸린시간: "+(mProgressBar.getProgress()));

        mBuilder.setPositiveButton("등수 확인", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

            }
        });

        AlertDialog mAlertDialog = mBuilder.create();
        return mAlertDialog;
    } // 점수 창 뜨기 구현

    public void rndNum(){
        rnd=new Random();
        a=rnd.nextInt(100);
        b=rnd.nextInt(100);
        ctextview.setText(a+"+"+b);
    }

}