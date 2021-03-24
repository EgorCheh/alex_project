package com.example.myfat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle arguments = getIntent().getExtras();
        String name = arguments.get("name").toString();

        TextView tvname= findViewById(R.id.tvName);
        tvname.setText("Привет "+name);
    }
}