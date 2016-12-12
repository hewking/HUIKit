package com.example.administrator.customviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.customviewtest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DialogFragment dialogFragment = new ImageDialogFragment();
//        dialogFragment.show(getSupportFragmentManager(),"mainactivity");
    }
}
