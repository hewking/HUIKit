package com.hewking.demo

import android.app.Application
import android.content.Context

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/9/14 16:53
 * 修改人员：hewking
 * 修改时间：2018/9/14 16:53
 * 修改备注：
 * Version: 1.0.0
 */
class DemoApplication : Application() {

   companion object {
       var context : Context? = null
   }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}