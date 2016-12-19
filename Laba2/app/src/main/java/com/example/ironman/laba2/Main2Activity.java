package com.example.ironman.laba2;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    Button upadate;
    Button send;
    Button add;
    String tmp = "";
    final String LOG_TAG = "myLogs";
    static final int READ_BLOCK_SIZE = 100;


    ListView listView;
    ArrayList<Contacts> list;
    Contacts listCon;
    TestAdapter testAdapter;
    String t;
    String name;
    String time;
    int selectpos = -1;
    MyReceiver mTimeBroadCastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           mTimeBroadCastReceiver = new MyReceiver();
        send = (Button) findViewById(R.id.send);
        add = (Button) findViewById(R.id.add);
        upadate = (Button)findViewById(R.id.upadate);
        listView = (ListView) findViewById(R.id.listView);
        listView.setItemsCanFocus(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectpos = i;


            }
        });

        list = new ArrayList<>();
        listCon = new Contacts();
        Intent data = getIntent();
       /* if(data!=null) {
            name = data.getStringExtra("name");
            time = data.getStringExtra("time");
            t = data.getStringExtra("data1");
            init();
        }else {
            Toast.makeText(getApplicationContext(), "FF", Toast.LENGTH_SHORT).show();
        }*/




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,SetAlaram.class);
                startActivityForResult(intent,1);
            }
        });
        upadate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,AddActivity.class);
                startActivityForResult(intent,2);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "FF", Toast.LENGTH_SHORT).show();

                ReadBtn();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        name = data.getStringExtra("name");
        time = data.getStringExtra("time");
        t = data.getStringExtra("data1");

        if(requestCode ==1) {
            init();
            serach(time);

                WriteBtn(name + time + t);

            sendBroadcast(new Intent(this, MyReceiver.class));
           registerBroadcastReceiver();

        }
        if(requestCode == 2){
            SharedPreferences sharedPref = getSharedPreferences("Key",Context.MODE_PRIVATE);
            Contacts contacts = new Contacts(name + time + t,sharedPref.getString("image_data",""));
             list.set(selectpos,contacts);
            testAdapter.notifyDataSetChanged();
        }
    }

    public void serach(String ser) {
        SharedPreferences sharedPref = getSharedPreferences("Key",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("numberTime", Integer.parseInt(ser));
        editor.putString("filexml",ReadBtn());
        editor.commit();

    }
    public void registerBroadcastReceiver() {
        this.registerReceiver(mTimeBroadCastReceiver, new IntentFilter(
                "android.intent.action.TIME_TICK"));
        Toast.makeText(getApplicationContext(), "Приёмник включен",
                Toast.LENGTH_SHORT).show();

    }

    public void init() {
        SharedPreferences sharedPref = getSharedPreferences("Key",Context.MODE_PRIVATE);
        list.add(new Contacts(name + " " + " " + time + t,sharedPref.getString("image_data","")));
        testAdapter = new TestAdapter(Main2Activity.this, list);
        listView.setAdapter(testAdapter);
    }

    public String ReadBtn() {
        String s="";
        try {
            FileInputStream fileIn=openFileInput("userdata.xml");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];

            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Toast.makeText(getBaseContext(), s,Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void WriteBtn(String file) {
        // add-write text into file
        try {
            FileOutputStream fileout=openFileOutput("userdata.xml", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(file);
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
               testAdapter.filter(searchQuery.toString().trim());
                listView.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

