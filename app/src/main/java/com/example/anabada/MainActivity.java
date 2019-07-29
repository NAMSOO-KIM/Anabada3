package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

<<<<<<< HEAD
import android.os.AsyncTask;
=======
import android.content.Intent;
>>>>>>> 4bca7f6fe5bc494fdefcefb1860a8eccaa339ca1
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
=======
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

>>>>>>> 4bca7f6fe5bc494fdefcefb1860a8eccaa339ca1
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();

        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        //noticeListView.setAdapter(adapter);
=======
        noticeListView =(ListView)findViewById(R.id.noticeListView);
        noticeList=new ArrayList<Notice>();
        noticeList.add(new Notice("공지사항입니다.","호감도상승","2019-07-18"));
        noticeList.add(new Notice("님들 하이욤.","호감도상승","2019-07-18"));
        noticeList.add(new Notice("발소리 작게좀요.","호감도상승","2019-07-18"));
        noticeList.add(new Notice("한이음 쪼아!.","호감도상승","2019-07-18"));
        noticeList.add(new Notice("공지사항입니다.","호감도상승","2019-07-18"));
        noticeList.add(new Notice("공지사항입니다.","호감도상승","2019-07-18"));
        adapter= new NoticeListAdapter(getApplicationContext(),noticeList);
        noticeListView.setAdapter(adapter);
>>>>>>> 4bca7f6fe5bc494fdefcefb1860a8eccaa339ca1


        final Button roomButton = (Button) findViewById(R.id.roomButton);
        final Button scheduleButton = (Button) findViewById(R.id.scheduleButton);
        final Button GameButton = (Button) findViewById(R.id.GameButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);


        roomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);//공지사항 부분 보이지 않도록
                roomButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark)); //클릭 된거는 색 어둡게
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); //클릭 안된거 밝게
                GameButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                FragmentManager fragmentManager = getSupportFragmentManager(); //Fragment 관리해주는 역할
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//Fragment트랜잭션은 Fragment 연산에 관련된 작업
                fragmentTransaction.replace(R.id.fragment, new RoomFragment()); //fragment 부분을 RoomFragment로 대체해주는것
                fragmentTransaction.commit();
            }
        });
        // new BackgroundTask().execute(); //DB 접근해서 찾아봄
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                roomButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                GameButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.commit();
            }
        });

        GameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);//공지사항 부분 보이지 않도록
                roomButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); //클릭 된거는 색 어둡게
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); //클릭 안된거 밝게
                GameButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                FragmentManager fragmentManager = getSupportFragmentManager(); //Fragment 관리해주는 역할
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//Fragment트랜잭션은 Fragment 연산에 관련된 작업
                fragmentTransaction.replace(R.id.fragment, new GameFragment()); //fragment 부분을 RoomFragment로 대체해주는것
                fragmentTransaction.commit();
            }
        });
        new BackgroundTask().execute();
    }

<<<<<<< HEAD
        class BackgroundTask extends AsyncTask<Void, Void, String>
        {
            String target;
           @Override
           protected void onPreExecute(){
               target = "http://gksdldma.cafe24.com/NoticeList.php";

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
                    return stringBuilder.toString().trim();

               }
               catch(Exception e){
            e.printStackTrace();
               }

                return null;
            }
            @Override
            public void onProgressUpdate(Void... values){
               super.onProgressUpdate();

            }
            @Override
            public void onPostExecute(String result){
               try{
                   JSONObject jsonObject = new JSONObject(result);
                   JSONArray jsonArray = jsonObject.getJSONArray("response");
                   int count =0;
                   String noticeContent, noticeName, noticeDate;
                   while(count < jsonArray.length()){
                       JSONObject object = jsonArray.getJSONObject(count);
                       noticeContent= object.getString("noticeContent");
                       noticeName= object.getString("noticeName");
                       noticeDate= object.getString("noticeDate");
                       Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                      noticeListView.setAdapter(adapter); //추가됨
                       noticeList.add(notice);
                       adapter.notifyDataSetChanged();

                       count++;


                   }
               }
               catch (Exception e){
                e.printStackTrace();

               }

            }
        }
    }


=======
    public void logout(View view) {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                final Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
>>>>>>> 4bca7f6fe5bc494fdefcefb1860a8eccaa339ca1
