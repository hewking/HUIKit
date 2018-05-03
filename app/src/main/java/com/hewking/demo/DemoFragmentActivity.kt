package com.hewking.demo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.hewking.customviewtest.R

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/3
 * 修改人员：hewking
 * 修改时间：2018/5/3
 * 修改备注：
 * Version: 1.0.0
 */

class DemoFragmentActivity : AppCompatActivity() {

    companion object {
        val FRAGMENT = "fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_navi)

        val fragmentName = intent.getStringExtra(FRAGMENT)
        val fragment = Class.forName(fragmentName).newInstance() as Fragment
        supportFragmentManager.beginTransaction()
                .add(R.id.container,fragment,fragmentName)
                .commit()

    }

}