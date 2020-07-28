package com.hewking.demo.viewlifecircle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.hewking.custom.R
import com.hewking.utils.v

class LifeCircleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_circle)
        v<Button>(R.id.btn).setOnClickListener {
            v<View>(R.id.tv_lifecircle).apply {
//                setOnClickListener {
                visibility = if (visibility == View.VISIBLE) {

                    View.GONE
                } else {
                    View.VISIBLE
                }
//                }
            }
        }
    }
}
