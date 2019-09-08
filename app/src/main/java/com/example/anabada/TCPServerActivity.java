package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerActivity extends AppCompatActivity {
    private TextView status;
    private EditText trans_server_text;
    private Button serverTransButton;//서버텍스트전송
    private Button clientTransButton;//클라전송
    private Button serverOutButton;

    private TextView serverIpText;//서버아이피확인
    private TextView serverText;//서버채팅창
    private TextView clientText;//클라채팅창
    private EditText joinIpText;//클라접속아이피
    private EditText transServerText;
    private EditText transClientText;
    private ServerSocket serverSocket;
    private Socket socket_cli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpserver);

        status=(TextView)findViewById(R.id.status);
        serverTransButton = (Button) findViewById(R.id.trans_server_button);
        clientTransButton = (Button) findViewById(R.id.trans_client_button);

        serverText = (TextView) findViewById(R.id.server_text);
        clientText = (TextView) findViewById(R.id.client_text);
        transServerText = (EditText) findViewById(R.id.trans_server_text);
        transClientText = (EditText) findViewById(R.id.client_server_text);

        //serverTransButton.setOnClickListener(this);
       // clientTransButton.setOnClickListener(this);


        ServerThread thread = new ServerThread();
        thread.start();
    }



    public void button(View view) {
        ClientThread thread_ = new ClientThread();
        thread_.start();
        //status.setText("test");
    }

    class ServerThread extends Thread {
        @Override
        public void run() {
            int port = 5001;
            try {
                ServerSocket server = new ServerSocket(port);
                serverSocket =server;
                status.setText("서버1");

                while(true){
                    Socket socket = server.accept(); // server 대기상태. 클라이언트 접속 시 소켓 객체 리턴

                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()); // 들어오는 데이터 처리
                    //Object input = inputStream.readObject();
                    final String get  = (String) inputStream.readObject();
                    serverText.setText("Client : "+get);

                    Object input = (Object)transServerText.getText().toString();
                    //Object input="fff";
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(input); // 서버에서 보낸 데이터
                    outputStream.flush();
                    Log.d("ServerThread","output 보냄");

                    socket.close(); // 연결을 유지할 필요 없으면 끊어줌.
                }
            } catch (Exception e){
                status.setText("오류");
                e.printStackTrace();
            }
        }
    }

    class ClientThread extends Thread {
        public void run() {
            String host = "localhost";
            String send;
            int port = 5001;

            try {

                Socket socket = new Socket(host, port);
                socket_cli=socket;
                status.setText("서버연결");

                Object msg = (Object)transClientText.getText().toString();
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(msg);
                outputStream.flush();
                Log.d("ClientThread", "서버로 보냄.");

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                final String input = (String) inputStream.readObject(); // Object로 받아도 무방
                Log.d("ClientThread","받은 데이터 : "+input);

                clientText.setText("Server : "+input);

            } catch (Exception e) {
                //status.setText("오류");
                e.printStackTrace();
            }
        }
    }


}
