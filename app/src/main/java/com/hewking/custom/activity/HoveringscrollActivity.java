//package com.hewking.customviewtest.activity;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//import com.example.administrator.customviewtest.HoveringScrollView;
//import com.hewking.customviewtest.R;
//
//
//public class HoveringscrollActivity extends AppCompatActivity {
//
//    private HoveringScrollView scrollView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_hoveringscroll);
//        scrollView = (HoveringScrollView) findViewById(R.id.scrollView);
//        scrollView.init();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        scrollView.setTopView(R.id.hover);
//    }
//}
