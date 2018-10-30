package com.hewking.base.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livestar.flowchat.wallet.ui.tron.LoadView;

import java.util.ArrayList;
import java.util.List;

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
public abstract class ComnBaseAdapter<T> extends RecyclerView.Adapter<ComnViewHolder> {

    private static final int STATE_TYPE = 0x10001;
    private static final int DEFAULT_LOAD_PREVIEW_SIZE = 4;// 默认距离最底部4个位置时候加载

    private List<T> mDatas;

    private LoadState mLoadState = LoadState.NORMAL;

    private OnStateChangeListener mStateChangeListener;

    public ComnBaseAdapter() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
    }

    public ComnBaseAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setStateChangeListener(OnStateChangeListener listener){
        this.mStateChangeListener = listener;
    }

    public void setData(List<T> datas) {
        if (datas == null) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void appendData(List<T> datas) {
        if (datas == null) {
            return;
        }

        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        int startPos = mDatas.size();
        int insertedItemCount = datas.size();
        mDatas.addAll(datas);
        notifyItemRangeInserted(startPos, insertedItemCount);
    }

    public void deleteItem(T item) {
        if (mDatas.contains(item)) {
            int index = mDatas.indexOf(item);
            mDatas.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void deleteItem(int index) {
        if (index >= 0 && index < mDatas.size()) {
            mDatas.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void deleteItems(List<T> items) {
        if (mDatas.containsAll(items)) {
            mDatas.removeAll(items);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == STATE_TYPE) {
            View stateView = new LoadView(parent.getContext());
            return new ComnViewHolder(stateView);
        }
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (getItemLayoutId(viewType) == -1) {
            throw new IllegalArgumentException("layout id error");
        }
        View itemView = inflater.inflate(getItemLayoutId(viewType), parent, false);
        return new ComnViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ComnViewHolder holder, int position) {
        if (getItemViewType(position) == STATE_TYPE && mDatas.size() == position) {
            onBindStateView(holder, mLoadState, position);
        } else {
            if (mDatas.size() - 1 - position <= DEFAULT_LOAD_PREVIEW_SIZE) {
                // 如果是normal 开始加载
                // 加载数据
                if (mStateChangeListener != null && mLoadState != LoadState.LOAD) {
                    mStateChangeListener.onLoadMore();
                    holder.itemView.post(new Runnable() {
                        @Override
                        public void run() {
                            setState(LoadState.LOAD);
                        }
                    });
                }
            }
            onBindComnViewHolder(holder, mDatas.get(position), position);
        }
    }

    public LoadState getState() {
        return mLoadState;
    }

    public void setState(LoadState state) {
        if( mLoadState == state) {
            return;
        }
        LoadState oldState = mLoadState;
        this.mLoadState = state;
        if (oldState == LoadState.NORMAL) {
            setEnableLoadMore(true);
        } else {
            setEnableLoadMore(false);
        }
    }

    public void setEnableLoadMore(boolean enable) {
        if (enable) {
            notifyItemInserted(getLast());
            notifyItemChanged(getLast());
        } else {
            notifyItemRemoved(getLast());
        }
    }

    private void onBindStateView(ComnViewHolder holder, LoadState mLoadState, int position) {

    }

    protected abstract void onBindComnViewHolder(ComnViewHolder holder, T t, int position);

    public int getLast(){
        return getItemCount() - 1;
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mLoadState == LoadState.NORMAL ? mDatas.size() : mDatas.size() + 1;
        }
        return 0;
    }

    protected abstract int getItemLayoutId(int type);

    @Override
    public int getItemViewType(int position) {
        if (position == mDatas.size() && mLoadState != LoadState.NORMAL) {
            return STATE_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    public interface OnStateChangeListener{
        void onLoadMore();
    }
}
