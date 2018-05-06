package com.hewking.androidview.chronomoter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer

import com.hewking.custom.R

class ChronometerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chronomoter)

        val chronometer : Chronometer = findViewById(R.id.chronometer) as Chronometer
//        chronometer.text = "等待开始"

//        chronometer.text = getFilesDir().getAbsolutePath()

        val handler = Handler(Looper.getMainLooper())
        chronometer.base = SystemClock.elapsedRealtime() - 10000
        chronometer.start()
        Log.d("Chronometer" ,": duration : "+ chronometer.contentDescription.split(" ")[0])


        handler.postDelayed(Runnable {

            chronometer.stop()
            chronometer.text = "通话结束"

        },5000)
    }
}
