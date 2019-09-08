package com.example.anabada;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private FloatingActionButton fab;
    Boolean logout=true;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        ListView listView;
        ListViewAdapter adapter;

        adapter=new ListViewAdapter();
        listView =(ListView)findViewById(R.id.ProductList);
        listView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_menu_camera),"camera","camera");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_menu_send),"mark","mark");

        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                ListViewItem item = (ListViewItem) adapterView.getItemAtPosition(i) ;

                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                Drawable image = item.getIcon() ;

                Intent intent =new Intent(getApplicationContext(),Product_description.class);
                intent.putExtra("title",titleStr);
                intent.putExtra("desc",descStr);
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

        if (id == R.id.nav_my_interest) {
            // Handle the camera action
            Intent intent =new Intent(getApplicationContext(),Product_interest.class);
            startActivity(intent);
        } else if (id == R.id.nav_my_product) {
            Intent intent =new Intent(getApplicationContext(),Product_my.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(this/* 해당 액티비티를 가르킴 */)
                    .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = auto.edit();
                            //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                            editor.clear();
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();
                            finish();
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
}
