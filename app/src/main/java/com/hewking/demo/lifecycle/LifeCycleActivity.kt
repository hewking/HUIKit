package com.hewking.demo.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/4/2
 * 修改人员：hewking
 * 修改时间：2018/4/2
 * 修改备注：
 * Version: 1.0.0
 */
class LifeCycleActivity : AppCompatActivity(){

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}
