package com.hewking.customviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hewking.customviewtest.R;


public class MainActivity extends AppCompatActivity {

    public static final int NESTED_SCROLL = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = NESTED_SCROLL;
//        if (type == NESTED_SCROLL) {
//            setContentView(R.layout.view_nested_view_parent);
//        } else {
            setContentView(R.layout.activity_main);
//        }

//        DialogFragment dialogFragment = new ImageDialogFragment();
//        dialogFragment.show(getSupportFragmentManager(),"mainactivity");
//        findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StickyTopLayout topLayout = (StickyTopLayout) findViewById(R.id.sticktop);
//                expand = !expand;
//                topLayout.top(expand);
//            }
//        });
    }

    boolean expand = false;
}
