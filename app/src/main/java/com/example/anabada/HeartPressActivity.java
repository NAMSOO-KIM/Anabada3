package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HeartPressActivity extends AppCompatActivity implements View.OnClickListener{
    ProgressBar mProgressBar;
    TextView mTextView_progress;
    Boolean IsRunning;
    ProgressHandler mHandler_progress;

    private ImageView[] mButton = new ImageView[20];
    private int[] health=new int[20];
    private int current_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_press);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar_bc);
        mTextView_progress = (TextView) findViewById(R.id.textView_progress_bc);
        mHandler_progress = new ProgressHandler();

        mButton[0] = (ImageView) findViewById(R.id.imageView1);
        mButton[1] = (ImageView) findViewById(R.id.imageView2);
        mButton[2] = (ImageView) findViewById(R.id.imageView3);
        mButton[3] = (ImageView) findViewById(R.id.imageView4);
        mButton[4] = (ImageView) findViewById(R.id.imageView5);
        mButton[5] = (ImageView) findViewById(R.id.imageView6);
        mButton[6] = (ImageView) findViewById(R.id.imageView7);
        mButton[7] = (ImageView) findViewById(R.id.imageView8);
        mButton[8] = (ImageView) findViewById(R.id.imageView9);
        mButton[9] = (ImageView) findViewById(R.id.imageView10);
        mButton[10] = (ImageView) findViewById(R.id.imageView11);
        mButton[11] = (ImageView) findViewById(R.id.imageView12);
        mButton[12] = (ImageView) findViewById(R.id.imageView13);
        mButton[13] = (ImageView) findViewById(R.id.imageView14);
        mButton[14] = (ImageView) findViewById(R.id.imageView15);
        mButton[15] = (ImageView) findViewById(R.id.imageView16);
        mButton[16] = (ImageView) findViewById(R.id.imageView17);
        mButton[17] = (ImageView) findViewById(R.id.imageView18);
        mButton[18] = (ImageView) findViewById(R.id.imageView19);
        mButton[19] = (ImageView) findViewById(R.id.imageView20);

        for (int i = 0; i < 20; i++){
            health[i]=0;
        }

        for (int i = 0; i < 20; i++) {
            mButton[i].setOnClickListener(this);
        }
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
        current_number = 20;
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
        /*Button mButton_Click = (Button) v;
        if(current_number == Integer.parseInt(String.valueOf(mButton_Click.getText()))){
            current_number--;
        }*/
        for (int i = 0; i < 20; i++){
            if (v.equals(mButton[i])){
                health[i]+=1;
                if (health[i]==3){
                    mButton[i].setVisibility(View.GONE);
                    current_number--;
                }
            }
        }
        if(current_number == 0) {
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
}
