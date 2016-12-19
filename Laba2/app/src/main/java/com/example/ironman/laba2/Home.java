package com.example.ironman.laba2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    RelativeLayout home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        home = (RelativeLayout)findViewById(R.id.home);
         home.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(Home.this,AlaramControler.class);
                 startActivity(intent);
                 Toast.makeText(getApplicationContext(),"FFF",Toast.LENGTH_SHORT).show();
             }
         });
    }
}
