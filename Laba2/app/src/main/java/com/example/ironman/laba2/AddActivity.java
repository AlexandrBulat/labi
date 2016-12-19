package com.example.ironman.laba2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddActivity extends AppCompatActivity {
     Button back;
     EditText name ;
     TextView textView;
    TimePicker timePicker;
    CalendarView calendarView;
    String t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        back = (Button)findViewById(R.id.back);
        name = (EditText)findViewById(R.id.name);
        textView = (TextView)findViewById(R.id.textView4);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                t = String.valueOf(i + " " + i1 + " " + " " +i2);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this,Main2Activity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("time",String.valueOf(timePicker.getCurrentHour()));
                intent.putExtra("data1",t);
                setResult(RESULT_OK,intent);
              finish();
                textView.setText(String.valueOf(timePicker.getCurrentHour()+ " " + timePicker.getCurrentMinute()));
            }
        });
    }
}
