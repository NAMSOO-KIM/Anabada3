package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 초기화 되는 시점. 스레드 실행
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
    }

    public void button(View view) {
        ClientThread thread = new ClientThread();
        thread.start();
    }

    class ClientThread extends Thread {
        public void run() {
            String host = "localhost";
            int port = 5001;

            try {
                Socket socket = new Socket(host, port);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject("안녕!");
                outputStream.flush();
                Log.d("ClientThread", "서버로 보냄.");
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                final String input = (String) inputStream.readObject(); // Object로 받아도 무방
                Log.d("ClientThread","받은 데이터 : "+input);

                Toast.makeText(getApplicationContext(),input,Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

