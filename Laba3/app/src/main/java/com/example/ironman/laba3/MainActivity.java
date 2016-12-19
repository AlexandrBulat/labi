package com.example.ironman.laba3;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.cache.disk.DiskStorageCache;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Result;

public class MainActivity extends ActionBarActivity {

    public static String LOG_TAG = "my_log";
    ImageView imageView;
    EditText editText;
    Button button ;
    Element form;
    ProgressDialog mProgressDialog;
    private static String baseUrl = "http://www.moldcell.md/sendsms";
    HashMap<String, String> hashMap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText)findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);


        new FetchImageLogo().execute();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
                performPostCall("http://moldcell.md/sendsms",hashMap);
            }
        });





    }
    public static String performPostCall(String requestURL,
                                         HashMap<String, String> postDataParams) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-agent", System.getProperty("http.agent"));
            conn.setDoInput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.d("ffff",result.toString());
        return result.toString();
    }


    private class FetchImageLogo extends AsyncTask<Object, Object, Bitmap> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String res;


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(Object... objects) {
            try {
                URL url = new URL(baseUrl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
                Document document = Jsoup.parse(resultJson);
                form = document.getElementById("websms-main-form");
                res = "http://moldcell.md" + form.select("img").get(0).attr("src");
                init();



            } catch (Exception e) {
                e.printStackTrace();
            }
            return load(res);
        }

        public Bitmap load(String url) {
            BitmapFactory.Options bmOptions;
            bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
            Bitmap bm = loadBitmap(url, bmOptions);
            return bm;
        }

        private Bitmap loadBitmap(String URL, BitmapFactory.Options options) {
            Bitmap bitmap = null;
            InputStream in;
            try {
                in = OpenHttpConnection(URL);
                bitmap = BitmapFactory.decodeStream(in, null, options);
                in.close();
            } catch (Exception e) {
            }
            return bitmap;
        }

        private InputStream OpenHttpConnection(String strURL)
                throws IOException {
            InputStream inputStream = null;
            URL url = new URL(strURL);
            URLConnection conn = url.openConnection();

            try {
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setRequestMethod("GET");
                httpConn.connect();

                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpConn.getInputStream();
                }
            } catch (Exception ex) {
            }
            return inputStream;
        }
    }
    public void init(){
        hashMap = new HashMap<String, String>();
        hashMap.put("message","fgfg");
        hashMap.put("form_id", form.select("[name*=form_id]").val());
        hashMap.put("form_build_id", form.select("[name*=form_build_id]").val());
        hashMap.put("op", "Trimite");
        hashMap.put("captcha_response", editText.getText().toString());
        hashMap.put("captcha_token", form.select("[name*=captcha_token]").val());
        hashMap.put("captcha_sid", form.select("[name*=captcha_sid]").val());
        hashMap.put("name","Alex");
        hashMap.put("phone","79759697");
    }

    }




