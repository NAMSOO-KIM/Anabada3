package com.example.anabada;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private ListView roomListView;
    private RoomListAdapter adapter;
    private List<Room> roomList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);


        roomListView = (ListView) findViewById(R.id.roomListView);
        roomList = new ArrayList<Room>();


        adapter = new RoomListAdapter(getApplicationContext(), roomList);

      final   Button makeroomButton = (Button)findViewById(R.id.makeroomButton);
        makeroomButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                makeroomIntent를 통해 버튼눌렀을 때 화면전환 가능

                Intent makeroomIntent = new Intent(RoomActivity.this, RoomRegisterActivity.class);
                RoomActivity.this.startActivity(makeroomIntent);

            }
        });

//        Button joinbutton = (Button)findViewById(R.id.joinButton);
//        joinbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent joinIntent = new Intent(RoomActivity.this,RoomJoin.class);
//
//            }
//        });




        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;
        @Override
        protected void onPreExecute(){
//               새롭게 추가함
            super.onPreExecute();
            target = "http://gksdldma.cafe24.com/RoomList.php";

        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) !=null){
                    stringBuilder.append(temp+"\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim(); //결과값이 여기에 리턴되면 이 값이 onPostExcute의 파라미터로 넘어감

            }
            catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        public void onProgressUpdate(Void... values){
            // super.onProgressUpdate();
            //변경됨.
            super.onProgressUpdate(values);
        }

        //가져온 데이터를 Room 객체에 넣은 뒤 리스트뷰 출력을 위한 Room 객체에 넣어주는 부분
        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count =0;
                String roomTitle;
                String roomPassword;
                //json타입의 값을 하나씩 빼서 Notice 객체에 저장후 리스트에 추가하는 부분
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    roomTitle= object.getString("roomTitle");
                    //roomPassword = object.getString("roomPassword");

                    Room room = new Room(roomTitle);
                 //   Room room = new Room(roomTitle,roomPassword);
                    roomListView.setAdapter(adapter); //추가됨
                    roomList.add(room);
                    // adapter.notifyDataSetChanged();//추가됨
                    count++;
                }
            }
            catch (Exception e){
                e.printStackTrace();

            }

        }
    }
}
