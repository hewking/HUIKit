package com.hewking.customviewtest.viewlifecircle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.hewking.customviewtest.R
import com.hewking.customviewtest.util.v

class LifeCircleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_circle)
        v<Button>(R.id.btn).setOnClickListener({
            v<View>(R.id.tv_lifecircle).apply {
//                setOnClickListener {
                    if (visibility == View.VISIBLE) {

                        visibility = View.GONE
                    } else {
                        visibility = View.VISIBLE
                    }
//                }
            }
        })
    }
}
