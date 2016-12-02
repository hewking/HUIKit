package com.example.administrator.customviewtest.activity;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.customviewtest.R;
import com.example.administrator.customviewtest.animation.ImageDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DialogFragment dialogFragment = new ImageDialogFragment();
        dialogFragment.show(getSupportFragmentManager(),"mainactivity");
    }
}
