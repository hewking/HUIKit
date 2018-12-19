package com.hewking.base.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livestar.flowchat.wallet.ui.tron.LoadView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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
public abstract class ComnBaseAdapter<T> extends RecyclerView.Adapter<ComnViewHolder> {

    private static final int STATE_TYPE = 0x10001;
    private static final int DEFAULT_LOAD_PREVIEW_SIZE = 4;// 默认距离最底部4个位置时候加载

    protected List<T> mDatas;

    private LoadState mLoadState = LoadState.NORMAL;

    private OnStateChangeListener mStateChangeListener;

    protected boolean enableStateView = true;

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

    public void setStateChangeListener(OnStateChangeListener listener) {
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

    public void addItem(T item) {
        if (item == null) return;
        int start = mDatas.size();
        mDatas.add(item);
        notifyItemRangeInserted(start, 1);
    }

    public void addItem(int pos, T item) {
        if (pos < 0 || pos > mDatas.size() - 1) {
            return;
        }
        if (item == null) return;
        mDatas.add(pos, item);
        notifyItemRangeInserted(pos, mDatas.size() - pos + 1);
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
//        notifyItemRangeChanged(startPos,getItemCount());
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
        return createViewHolder(itemView);
    }

    protected ComnViewHolder createViewHolder(View itemView) {
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
                if (mStateChangeListener != null && mLoadState == LoadState.NORMAL) {
                    holder.itemView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mLoadState == LoadState.LOAD) {
                                return;
                            }
                            mStateChangeListener.onLoadMore();
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
        if (mLoadState == state) {
            return;
        }
        LoadState oldState = mLoadState;
        this.mLoadState = state;

        if (!enableStateView) {
            return;
        }

        if (oldState == LoadState.NORMAL) {
            setEnableLoadMore(true);
        } else {
            if (state == LoadState.NORMAL) {
                setEnableLoadMore(false);
            } else {
                notifyItemChanged(getLast());
            }
        }
    }

    public void setEnableLoadMore(boolean enable) {
        if (enable) {
            notifyItemInserted(getLast());
            notifyItemChanged(getLast());
        } else {
            notifyItemRemoved(getLast());
            notifyItemChanged(getLast());
        }
    }

    private void onBindStateView(ComnViewHolder holder, LoadState mLoadState, int position) {
        if (mLoadState == LoadState.LOAD) {

        } else if (mLoadState == LoadState.NOMORE) {
            if (holder.itemView instanceof LoadView) {
                ((LoadView)holder.itemView).setState(mLoadState);
            }
        }
    }

    protected abstract void onBindComnViewHolder(ComnViewHolder holder, T t, int position);

    public int getLast() {
        return getItemCount() - 1;
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
//            return mLoadState != LoadState.LOAD ? mDatas.size() : mDatas.size() + 1;
            return mLoadState == LoadState.NORMAL || !enableStateView ? mDatas.size() : mDatas.size() + 1;
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

    public interface OnStateChangeListener {
        void onLoadMore();
    }

}
