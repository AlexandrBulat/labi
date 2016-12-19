package com.example.ironman.laba2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Unclocker extends AppCompatActivity {
    private AnimationDrawable mAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unclocker);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setBackgroundResource(R.drawable.uncloker);
        mAnimationDrawable = (AnimationDrawable) imageView.getBackground();
        mAnimationDrawable.start();

        // imageView.setBackgroundResource(R.drawable.uncloker);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimationDrawable.stop();
                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.custom_pop, null);
                Button back = (Button) popupView.findViewById(R.id.button2);
                final EditText editText = (EditText) popupView.findViewById(R.id.editque);

                final TextView calendarView = (TextView) popupView.findViewById(R.id.que);
                calendarView.setText("QUESTION \n" +
                        "how old are you?");
                  back.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          if(editText.getText().toString().equals("21")){
                              Intent intent1 = new Intent(Unclocker.this,AlaramControler.class);
                              startActivity(intent1);
                          }

                      }
                  });

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        600,
                        1000);
                popupWindow.showAtLocation(editText, Gravity.CENTER, 0, 0);
            }


        });
    }
}
