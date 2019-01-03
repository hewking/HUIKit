package com.hewking.base.recyclerview;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/3 0003
 * 修改人员：hewking
 * 修改时间：2019/1/3 0003
 * 修改备注：
 * Version: 1.0.0
 */
public class MultiTypeAdapter extends RecyclerView.Adapter<ComnViewHolder> {

    private List<Object> mDatas = new ArrayList<>();

    private Map<Class, Class> itemTypes = new HashMap<>();
    private Map<Class, Integer> viewTypes = new HashMap<>();

    public void register(Class itemClazz, Class vhClazz) {
        itemTypes.put(itemClazz, vhClazz);
        viewTypes.put(itemClazz, viewTypes.size() + 1);
    }

    public void append(Object item) {
        mDatas.add(item);
    }

    @NonNull
    @Override
    public ComnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Class<? extends ComnViewHolder> vh = getVH(viewType);
        try {
            return vh.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ComnViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemViewType(int position) {
        Object bean = mDatas.get(position);
        return viewTypes.get(bean.getClass());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public Class<? extends ComnViewHolder> getVH(Integer type) {
        Set<Map.Entry<Class, Integer>> entries = viewTypes.entrySet();
        for (Map.Entry entry : entries) {
            if (entry.getValue() == type) {
                return itemTypes.get(entry.getKey());
            }
        }
        return null;
    }
}
