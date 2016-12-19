package com.example.ironman.laba2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetAlaram extends AppCompatActivity {
    RelativeLayout setContent;
    RelativeLayout rev;
    TextView timeclock;
    TextView data;
    TextView clockcontent;
    String t;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alaram);
        setContent = (RelativeLayout)findViewById(R.id.setcontent);
        rev = (RelativeLayout)findViewById(R.id.relativeLayout);
        timeclock = (TextView)findViewById(R.id.timeclock);
        data = (TextView)findViewById(R.id.data);
        clockcontent = (TextView)findViewById(R.id.clock);
        setContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.activity_add, null);
                Button back = (Button)popupView.findViewById(R.id.back);
                final EditText editText = (EditText)popupView.findViewById(R.id.name);
                final TimePicker timePicker = (TimePicker)popupView.findViewById(R.id.timePicker);
                final CalendarView calendarView = (CalendarView)popupView.findViewById(R.id.calendarView);
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                        t = String.valueOf(i + " " + i1 + " " + " " +i2);
                        Toast.makeText(getApplicationContext(),"FFF",Toast.LENGTH_SHORT).show();
                    }
                });

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        600,
                        1000);
                popupWindow.showAtLocation(rev, Gravity.CENTER, 0, 0);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timeclock.setText(String.valueOf(timePicker.getCurrentHour()+ " " + timePicker.getCurrentMinute()));
                        data.setText( "Data"+ t);

                        clockcontent.setText("event is" + editText.getText().toString());
                        Toast.makeText(getApplicationContext(),"FFF",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SetAlaram.this,Main2Activity.class);
                        intent.putExtra("name", "Clos is"+ clockcontent.getText().toString());
                        intent.putExtra("time",String.valueOf(timePicker.getCurrentHour()));
                        intent.putExtra("data1",t);
                        setResult(RESULT_OK,intent);
                        finish();
                       // startActivity(intent);
                        popupWindow.dismiss();


                    }
                });

            }
        });
    }
}
