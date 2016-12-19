package com.example.ironman.laba2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AlaramControler extends AppCompatActivity {
    RelativeLayout setAlram;
    RelativeLayout profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaram_controler);
        setAlram = (RelativeLayout)findViewById(R.id.setAlaram);
        profile = (RelativeLayout)findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlaramControler.this,YourProfile.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"FFF",Toast.LENGTH_SHORT).show();
            }
        });
        setAlram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlaramControler.this,Main2Activity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"FFF",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
