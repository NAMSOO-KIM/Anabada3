package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Product_interest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_interest);

        ListView listView;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listView =(ListView)findViewById(R.id.ProductList);
        listView.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_menu_camera),"camera","camera");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_menu_send),"mark","mark");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
    }

    public void product(View view) {
        Intent intent =new Intent(getApplicationContext(),Product_description.class);
        startActivity(intent);
    }
}
