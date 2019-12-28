<<<<<<< HEAD
package com.hewking.base.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewking.base.DelayClickListener;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/10/25 0025
 * 修改人员：hewking
 * 修改时间：2018/10/25 0025
 * 修改备注：
 * Version: 1.0.0
 */
public class ComnViewHolder extends RecyclerView.ViewHolder {

    public ComnViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(){

    }

    public TextView tv(int resId) {
        return itemView.findViewById(resId);
    }

    public ImageView iv(int resId) {
        return itemView.findViewById(resId);
    }

    public <T extends View> T v(int resId) {
        return itemView.findViewById(resId);
    }

    public void click(int res, View.OnClickListener listener) {
        if (res == -1) {
            return;
        }
        v(res).setOnClickListener(new DelayClickListener(listener));
    }

    public void click(View.OnClickListener listener) {
        itemView.setOnClickListener(new DelayClickListener(listener));
    }

    public void longClick(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

    public void longClick(int res, View.OnLongClickListener listener) {
        if (res == -1) {
            return;
        }
        v(res).setOnLongClickListener(listener);
    }
}
=======
package com.hewking.base.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewking.base.DelayClickListener;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/10/25 0025
 * 修改人员：hewking
 * 修改时间：2018/10/25 0025
 * 修改备注：
 * Version: 1.0.0
 */
public class ComnViewHolder extends RecyclerView.ViewHolder {

    public ComnViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(){

    }

    /**
     * 可以缓存 不用每次都 findViewById
     *
     * @param resId
     * @return
     */
    public TextView tv(int resId) {
        return itemView.findViewById(resId);
    }

    public ImageView iv(int resId) {
        return itemView.findViewById(resId);
    }

    public <T extends View> T v(int resId) {
        return itemView.findViewById(resId);
    }

    public void click(int res, View.OnClickListener listener) {
        if (res == -1) {
            return;
        }
        v(res).setOnClickListener(new DelayClickListener(listener));
    }

    public void click(View.OnClickListener listener) {
        itemView.setOnClickListener(new DelayClickListener(listener));
    }

    public void longClick(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

    public void longClick(int res, View.OnLongClickListener listener) {
        if (res == -1) {
            return;
        }
        v(res).setOnLongClickListener(listener);
    }
}
>>>>>>> 29db5ff72117652c79e56c13f91005b1bfdcfd24
