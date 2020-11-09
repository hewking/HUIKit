package com.hewking.uikit.chart.depth

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/4/10
 * 修改人员：hewking
 * 修改时间：2018/4/10
 * 修改备注：
 * Version: 1.0.0
 */
class DepthDataManager {

    private var width : Int = 0
    private var height : Int = 0

    fun init(w : Int, h : Int){
        width = w
        height = h
    }

    fun getLeftData() : MutableList<ChartPoint>{
        val datas = mutableListOf<ChartPoint>()
        // [0,1)
        val gap = width / /*(Math.random() * 10 + 2)*/5
        val gapH = height / /*(Math.random() * 20 + 2).toFloat()*/5
        var prePoint : ChartPoint? = null
        for (i in 0..5) {
            prePoint?.let {
                datas.add(ChartPoint(gap.toFloat() * i, it.y, i.toFloat()))
            }
            val curPoint = ChartPoint(gap.toFloat() * i, round(0f, gapH * i.toFloat()), i.toFloat())
            datas.add(curPoint)
            prePoint = curPoint
        }
        datas.add(ChartPoint(prePoint?.x
            ?: 0f, height.toFloat(), 7f))
        return datas
    }

    fun round(left : Float , right : Float) : Float{
        //n >= 0.0 && n < 1.0
        return (left + Math.random() * (right - left)).toFloat()
    }

}
