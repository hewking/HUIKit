package com.hewking.demo

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hewking.custom.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.max

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/3
 * 修改人员：hewking
 * 修改时间：2018/5/3
 * 修改备注：
 * Version: 1.0.0
 */
class DemoListFragment : androidx.fragment.app.Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.activity_main,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val random = Random()
        val pointList = ArrayList<Int>()
        for (i in 0..20) {
            val nextInt = 15 + random.nextInt(15)
            pointList.add(nextInt)
        }

        chartView.brokenLineColor = Color.BLUE
        chartView.maxY=pointList.reduce { acc, i -> max(acc,i) } + 10
        chartView.setChartPoints(pointList)
    }

}