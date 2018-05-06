package com.hewking.custom.design

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hewking.custom.R
import com.hewking.custom.util.v

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/4/4
 * 修改人员：hewking
 * 修改时间：2018/4/4
 * 修改备注：
 * Version: 1.0.0
 */
class DesignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design)

        val recyclerView = v<RecyclerView>(R.id.recyclerview)

        class VH(itemview : View) : RecyclerView.ViewHolder(itemview) {

            fun bindView() {

            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
                return VH(layoutInflater.inflate(R.layout.layout_recycler_item,null,false))
            }

            override fun getItemCount(): Int {
                return 200
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            }
        }


    }

}