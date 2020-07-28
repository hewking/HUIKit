package com.hewking.demo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.view.GestureDetectorCompat
import android.util.Log
import android.view.*
import com.hewking.custom.R
import kotlinx.android.synthetic.main.fragment_gesture_detector_layout.*

class GestureDetectorDemoFragment : androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gesture_detector_layout,container,false)
    }

    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_touch.setOnTouchListener { v, event ->
            Log.d("GestureDetectorDemoFragment","event x : ${event.x} y : ${event.y}")
            when (event.actionMasked) {
                MotionEvent.ACTION_UP -> {
                    flag = false
                    Log.d("GestureDetectorDemoFragment","ACTION_UP")
                }
            }
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    val gestureDetector = GestureDetectorCompat(activity,object : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            flag = true
            countDown()
        }

        @SuppressLint("LongLogTag")
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            Log.d("GestureDetectorDemoFragment","onSingleTapUp")
            flag = false
            return super.onSingleTapUp(e)
        }
    })

    var count = 1
    var flag = false

    private fun countDown() {
        if (!flag || !isVisible) {
            return
        }
        tv_time.postDelayed({
            tv_time.text = (count++).toString()
            countDown()
        },1000)
    }

}