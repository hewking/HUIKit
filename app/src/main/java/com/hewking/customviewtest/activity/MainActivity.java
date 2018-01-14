package com.hewking.customviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hewking.customviewtest.R;
import com.hewking.customviewtest.stickytop.StickyTopLayout;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DialogFragment dialogFragment = new ImageDialogFragment();
//        dialogFragment.show(getSupportFragmentManager(),"mainactivity");
        findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StickyTopLayout topLayout = (StickyTopLayout) findViewById(R.id.sticktop);
                expand = !expand;
                topLayout.top(expand);
            }
        });
    }

    boolean expand = false;
}
