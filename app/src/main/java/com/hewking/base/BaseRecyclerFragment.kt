package com.hewking.base

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.hewking.base.recyclerview.ComnBaseAdapter
import com.hewking.base.recyclerview.LoadState
import com.hewking.custom.R
import com.hewking.demo.RBaseItemDecoration
import kotlinx.android.synthetic.main.base_recycler_content.*

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/10/30 0030
 * 修改人员：hewking
 * 修改时间：2018/10/30 0030
 * 修改备注：
 * Version: 1.0.0
 */
abstract class BaseRecyclerFragment<T> : BaseFragment()
        ,ComnBaseAdapter.OnStateChangeListener{

    private val DEFAULT_PAGE_SIZE = 10

    open var mAdapter : ComnBaseAdapter<T>? = null

    open var mPage = 0

    override fun getContentLayoutId(): Int {
        return R.layout.base_recycler_content
    }

    override fun loadContentView() {
        super.loadContentView()
        refreshlayout.setOnRefreshListener {
            mPage = 0
            loadData()
        }
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.addItemDecoration(RBaseItemDecoration())
        recyclerview.itemAnimator = DefaultItemAnimator()
        mAdapter = buildAdapter()
        mAdapter?.setStateChangeListener(this)
        recyclerview.adapter = mAdapter

        refreshlayout.isRefreshing = true
        loadData()
    }

    abstract fun buildAdapter(): ComnBaseAdapter<T>

    open fun loadData() {
        onLoadData()
    }

    open fun onLoadData()  {

    }

    open fun onLoadEnd(datas : List<T>?= null) {
        if (datas?.isNotEmpty() == true) {
            mPage ++
            // 一下两行代码，顺序要一致
            mAdapter?.appendData(datas)
            mAdapter?.state = LoadState.NORMAL
        } else {
            mAdapter?.state = LoadState.NOMORE
        }

        if (refreshlayout.isRefreshing) {
            refreshlayout.isRefreshing = false
        }
    }

    override fun onLoadMore() {
        loadData()
    }

}