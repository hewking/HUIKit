package com.hewking.customviewtest.animation.imagedialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hewking.customviewtest.R;


/**
 * Created by Administrator on 2016/12/1.
 * <p>
 * 联系方式：。。。
 */
public class ImageDialogFragment extends DialogFragment implements View.OnClickListener{


    private ViewGroup viewGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_view_anim,container);
        viewGroup = (LinearLayout) view.findViewById(R.id.container);
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1, ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
//        LayoutAnimationController lac = new LayoutAnimationController(scaleAnimation);
//        viewGroup.setLayoutAnimation(lac);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        createTransition(imageView,0,imageView.getY()).start();

        Button addbtn = (Button) view.findViewById(R.id.add);
        addbtn.setOnClickListener(this);

        return view;
    }

    private Animator createTransition(View targetView,float startY,float endY){
        ObjectAnimator animator = new ObjectAnimator().ofFloat(targetView,"y",startY,endY);
        animator.setDuration(1000);
        animator.setInterpolator(new BounceInterpolator());
        return animator;
    }

    @Override
    public void onClick(View v) {
        View view = new View(getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(300, 300);
        view.setLayoutParams(lp);
        view.setBackgroundColor(Color.parseColor("#ffeebb"));
        viewGroup.addView(view);
        createTransition(view,0,500).start();
    }
}
