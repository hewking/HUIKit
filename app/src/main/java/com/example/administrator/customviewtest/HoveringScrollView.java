package com.example.administrator.customviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/11/18.
 * <p>
 * 联系方式：。。。
 */
public class HoveringScrollView extends FrameLayout{

    //需要悬停的view
    private ViewGroup hoverView;

    //伴随滚动的view
    private ViewGroup contentView;

    //悬停超过多少高度不悬停
    private int mTopHeight;

    public HoveringScrollView(Context context) {
        this(context,null);
    }

    public HoveringScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HoveringScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ViewGroup contentView = (ViewGroup) getChildAt(0);
        removeAllViews();
        MyScollView myScollView = new MyScollView(getContext(),this);
        myScollView.addView(contentView);
        addView(myScollView);
    }

    public void setTopView(int id){
        hoverView = (ViewGroup) contentView.findViewById(id);
        mTopHeight = hoverView.getMeasuredHeight();
        mTopHeight = 150;
    }

    private void onScroll(int scrollY) {
        if(scrollY > mTopHeight && hoverView.getParent() == contentView){
            contentView.removeView(hoverView);
            addView(hoverView,0);
        }else if(scrollY < mTopHeight && hoverView.getParent() != contentView){
            removeView(hoverView);
            contentView.addView(hoverView,0);
        }
    }

    public static class MyScollView extends ScrollView{

        HoveringScrollView hoveringScrollView;

        public MyScollView(Context context,HoveringScrollView hoveringScrollView) {
            super(context);
            this.hoveringScrollView = hoveringScrollView;
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            hoveringScrollView.onScroll(t);
        }
    }
}
