package com.example.anabada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

        private ArrayAdapter adapter;
        private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner=(Spinner)findViewById(R.id.majorSpinner);
        adapter= ArrayAdapter.createFromResource(this,R.array.major,android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

    }
}
