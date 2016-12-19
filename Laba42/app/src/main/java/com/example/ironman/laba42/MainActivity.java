package com.example.ironman.laba42;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

    private AnimationDrawable mAnimationDrawable;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setBackgroundResource(R.drawable.catanimation);

        mAnimationDrawable = (AnimationDrawable) imageView.getBackground();

        final Button btnStart = (Button) findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAnimationDrawable.start();
            }
        });

        final Button btnStop = (Button) findViewById(R.id.buttonStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAnimationDrawable.stop();
            }
        });
    }
}
