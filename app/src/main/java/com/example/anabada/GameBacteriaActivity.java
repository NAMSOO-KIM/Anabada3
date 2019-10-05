package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Random;

public class GameBacteriaActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    //MyView m ;
    int heigth = 480,width = 800;
    //ArrayList<Insect> unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_bacteria);
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display display =  wm.getDefaultDisplay();

        width = display.getWidth();
        heigth = display.getHeight();
        //linearLayout = findViewById(R.id.back_linear);

        //m = new MyView(this);
        //linearLayout.addView(m);
    }

    /*class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            for(int i=0;i<unit.size();i++)
            {
                //좌표                가속도
                unit.get(i).setX(unit.get(i).getX() + unit.get(i).getAx()) ;
                unit.get(i).setY(unit.get(i).getY() + unit.get(i).getAy()) ;

                //x축 범위 벗어났을때
                if(unit.get(i).getX() > width-45 || unit.get(i).getX()<0)
                {
                    unit.get(i).setAx(-unit.get(i).getAx());
                }

                //y축 범위 벗어났을때
                if(unit.get(i).getY() > heigth-300 || unit.get(i).getY() < 0)
                {
                    unit.get(i).setAy(-unit.get(i).getAy());
                }
            }

            m.invalidate();
            sendEmptyMessageDelayed(0,20);
        }
    }

    MyHandler handler = new MyHandler();

    public class MyView extends View {

        Bitmap image,image2;
        Random random;
        int x=0,y=0;

        public MyView(Context context) {
            super(context);
            unit = new ArrayList<Insect>();
            random = new Random();

            for(int i=0 ; i < 50 ;i++)
                unit.add(new Insect(100,30+random.nextInt(width-100),30+random.nextInt(heigth-330),5,5));

            image = BitmapFactory.decodeResource(getResources(),R.drawable.monster);
            image2 = BitmapFactory.decodeResource(getResources(),R.drawable.monster2);
            handler.sendEmptyMessage(0);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            for (int i = 0; i < unit.size(); i++) {
                if(unit.get(i).getEnergy()<100 && unit.get(i).getEnergy()>0)
                    canvas.drawBitmap(image2, unit.get(i).getX(), unit.get(i).getY(), null);
                else if(unit.get(i).getEnergy()<=0) {
                    unit.remove(i);
                }
                else
                    canvas.drawBitmap(image, unit.get(i).getX(), unit.get(i).getY(), null);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            int x2 = (int)event.getX();
            int y2 = (int)event.getY();

            for(int i=0;i<unit.size();i++)
            {
                //밑변길이
                int a = x2 - unit.get(i).getX();

                //높이
                int c = y2 - unit.get(i).getY();

                //빗변의 길이
                int b = (int)Math.sqrt(Math.pow(a,2)+c*c);

                if(b<30)
                    unit.get(i).setEnergy(unit.get(i).getEnergy()-20);
            }

            return super.onTouchEvent(event);
        }

    }*/
}
