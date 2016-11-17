package com.example.administrator.customviewtest.notestick;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.administrator.customviewtest.R;
import com.example.administrator.customviewtest.UiUtil;

/**
 * Created by Administrator on 2016/11/17.
 * <p>
 * 联系方式：。。。
 */
public class NoteStickActivity extends Activity {

    private RelativeLayout parentlayout;
    private View view;
    private boolean isExpand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_stick);
        setUpViews();
    }

    private void setUpViews() {
        parentlayout = (RelativeLayout) findViewById(R.id.parentPanel);
        view = findViewById(R.id.note);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    isExpand = !isExpand;
                    float start = isExpand? UiUtil.dipToPx(NoteStickActivity.this,300):0;
                    float end = isExpand?0:UiUtil.dipToPx(NoteStickActivity.this,300);
                    ObjectAnimator animator = new ObjectAnimator().ofFloat(parentlayout,"translationY",start,end);
                    animator.setDuration(1000);
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.start();
                }
                return true;
            }
        });
    }
}
