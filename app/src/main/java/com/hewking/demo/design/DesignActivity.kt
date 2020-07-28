package com.hewking.demo.design

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.hewking.custom.R
import com.hewking.util.v

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

        val recyclerView = v<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerview)

        class VH(itemview : View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemview) {

            fun bindView() {

            }
        }

        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = object : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>(){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
                return VH(layoutInflater.inflate(R.layout.layout_recycler_item,null,false))
            }

            override fun getItemCount(): Int {
                return 200
            }

            override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
            }
        }


    }

}