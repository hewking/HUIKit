package com.example.administrator.customviewtest;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/11/14.
 * <p>
 * 联系方式：。。。
 */
public class UiUtil {
    public static int getScreenWidthPixels(Context ctx){
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int dipToPx(Context ctx,int dip){
        return (int) (getScreenDensity(ctx) * dip + 0.5);
    }

    public static float getScreenDensity(Context ctx){
        try{
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            return dm.density;
        }catch (Exception e){
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }
}
