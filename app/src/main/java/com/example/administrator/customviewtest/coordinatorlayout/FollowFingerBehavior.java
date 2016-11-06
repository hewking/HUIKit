package com.example.administrator.customviewtest.coordinatorlayout;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Button;

/**
 * Created by hewking on 2016/11/6.
 */

public class FollowFingerBehavior extends CoordinatorLayout.Behavior<Button> {

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }



    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        //返回true 意味着 依赖，false 不依赖
        return super.layoutDependsOn(parent, child, dependency);
    }
}
