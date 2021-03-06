package com.example.anabada;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG="MainActivity";
    private FloatingActionButton fab;
    Boolean logout=true;
    private FirebaseFirestore db;
    private static final String TAGDoc = "DocSnippets";
    ListView listView;
    ListViewAdapter adapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //파이어 베이스 유저
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db=FirebaseFirestore.getInstance();

        //유저 없으면 사용자 정보 등록화면으로 전환
        if(user == null){
            //myStartActivity(LoginActivity.class);
        }
        else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document != null){
                            if (document.exists()) {
                                Log.w(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.w(TAG, "No such document");
                                myStartActivity(Member_register.class);
                            }
                        }
                    } else {
                        Log.w(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        adapter=new ListViewAdapter();
        listView =(ListView)findViewById(R.id.ProductList);
        getAllUsers();


        //adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_menu_camera),"camera","camera");
        //adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_menu_send),"mark","mark");

        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                ListViewItem item = (ListViewItem) adapterView.getItemAtPosition(i) ;

                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                String image = item.getIcon() ;

                Intent intent =new Intent(getApplicationContext(),Product_description.class);
                intent.putExtra("title",titleStr);
                intent.putExtra("desc",descStr);
                intent.putExtra("image",image);
                startActivity(intent);
            }
        });
        /*final TextView username=(TextView) findViewById(R.id.username);
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        String user=auto.getString("inputname",null);
        username.setText(10);*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            new AlertDialog.Builder(this/* 해당 액티비티를 가르킴 */)
                    .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    })
                    .show();

        }
        else if (id==R.id.server){
            Intent intent =new Intent(getApplicationContext(),TCPServerActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.client){
            Intent intent =new Intent(getApplicationContext(),TCPClientActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.game1){
            Intent intent =new Intent(getApplicationContext(),PressGameActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.game2){
            Intent intent =new Intent(getApplicationContext(),CalculatorGameActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.game3){
            Intent intent =new Intent(getApplicationContext(),QuizGameActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.game4){
            Intent intent =new Intent(getApplicationContext(),HeartPressActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.fab:
                Intent intent =new Intent(getApplicationContext(),Product_regist.class);
                startActivity(intent);
                break;
        }
    }

    public void product(View view) {
        Intent intent =new Intent(getApplicationContext(),Product_description.class);
        startActivity(intent);
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }

    public void getAllUsers() {
        // [START get_all_users]
        listView.setAdapter(adapter);
        db.collection("board")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAGDoc, (String)document.get("photoUrl")+(String)document.get("boardTitle")+(String)document.get("boardDescription"));
                                    adapter.addItem(((String)document.get("photoUrl")),
                                            (String)document.get("boardTitle"),
                                            (String)document.get("boardDescription"));
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAGDoc, "Error getting documents.", task.getException());
                        }
                    }
                });
        // [END get_all_users]

    }

    private Drawable drawableFromUrl(String url)throws IOException {
        Bitmap x;

            HttpURLConnection connection =
                    (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();

            x = BitmapFactory.decodeStream(input);
            return new BitmapDrawable(getResources(),x);



    }

}
