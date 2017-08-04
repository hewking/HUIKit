package com.hewking.customviewtest.coordinatorlayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by hewking on 2016/11/6.
 */

public class FollowFingerBehavior extends CoordinatorLayout.Behavior<Button> {

    private int width;

    public FollowFingerBehavior(Context ctx, AttributeSet attrs){
        super(ctx,attrs);
        width = ctx.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
        //当 dependency 移动时，都会回掉此方法
        int l = width - dependency.getLeft() - child.getWidth();
        int t = dependency.getTop();
        int r = l + child.getWidth();
        int b = t + child.getHeight();
        child.layout(l,t,r,b);
        return true;
    }



    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        //返回true 意味着 依赖，false 不依赖
        //如果 dependency 是 FollowTouchMoveView 就是我们所需要的 dependency
        return dependency instanceof FollowTcouchMoveView;
    }
}
