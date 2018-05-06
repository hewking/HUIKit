package com.hewking.androidview.edittext

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hewking.custom.R

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/3/4
 * 修改人员：hewking
 * 修改时间：2018/3/4
 * 修改备注：
 * Version: 1.0.0
 */
class FixSoftInputActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_softinput)
//        AndroidBug5497Workaround.assistActivity(this)
    }

}