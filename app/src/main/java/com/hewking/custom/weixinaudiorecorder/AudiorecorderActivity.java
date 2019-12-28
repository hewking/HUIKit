package com.hewking.custom.weixinaudiorecorder;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.hewking.custom.R;

public class AudiorecorderActivity extends AppCompatActivity {

    private Button start;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiorecorder);

        start = (Button) findViewById(R.id.recorder);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        chronometer.start();

        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN :

                        play();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);

//                        if(event.getRawX() < location[0] || event.getRawX() > location[0] + v.getWidth()
//                                || event.getRawY() < location[1]) {

                        Log.i("AudioOntouch" , "x : " + event.getRawX() + "  y : " + event.getRawY());

                            int[] location2 = new int[2];
                            chronometer.getLocationOnScreen(location2);
                            RectF rectf = new RectF(location2[0],location2[1],location2[0] + chronometer.getWidth()
                                    , location2[1] + chronometer.getHeight());
                            if (rectf.contains(event.getRawX(),event.getRawY())) {
                                stop();
                            }
//                        }

                        break;
                    case MotionEvent.ACTION_UP:

                        stop();

                        break;
                }

                return false;
            }
        });
    }

    private void play() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void stop() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
    }
}
