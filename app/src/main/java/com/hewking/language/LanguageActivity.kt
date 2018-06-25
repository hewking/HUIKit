package com.hewking.language

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/5/9
 * 修改人员：hewking
 * 修改时间：2018/5/9
 * 修改备注：
 * Version: 1.0.0
 */
open class LanguageActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase,LanguageUtils.curLanguage))
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("LanguageActivity","onNewIntent")
    }

}