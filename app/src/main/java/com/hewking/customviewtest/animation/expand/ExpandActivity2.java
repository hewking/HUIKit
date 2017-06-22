package com.example.administrator.customviewtest.animation.expand;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.administrator.customviewtest.R;
import com.example.administrator.customviewtest.notestick.NoteStickActivity;
import com.example.administrator.customviewtest.util.UiUtil;

import java.util.ArrayList;

public class ExpandActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppCompatCheckBox cb = (AppCompatCheckBox) findViewById(R.id.cb);

        final LinearLayout content = (LinearLayout) findViewById(R.id.content);



        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                animViews(content,isChecked);
            }
        });

    }

    private void animViews(View view, boolean show) {
        int itemSize = 100;
        int animTime = 300;
        int delayTime = 100;
//        if (show) {
//                ViewCompat.setTranslationY(view, itemSize);
//                ViewCompat.animate(view).translationY(0)
//                        .setInterpolator(new DecelerateInterpolator())
//                        .setDuration(animTime).start();
//        } else {
//                ViewCompat.setTranslationY(view, -itemSize);
//                ViewCompat.animate(view).translationY(0)
//                        .setInterpolator(new DecelerateInterpolator())
//                        .setDuration(animTime).start();
//        }

        float start = show? UiUtil.dipToPx(this,- 50):0;
        float end = show?0:UiUtil.dipToPx(this,- 50);
        ObjectAnimator animator = new ObjectAnimator().ofFloat(view,"translationY",start,end);
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();

    }

}
