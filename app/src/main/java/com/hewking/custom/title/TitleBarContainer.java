package com.hewking.custom.title;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class TitleBarContainer extends FrameLayout {



    public TitleBarContainer(@NonNull Context context) {
        this(context,null);
    }

    public TitleBarContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBarContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


}
