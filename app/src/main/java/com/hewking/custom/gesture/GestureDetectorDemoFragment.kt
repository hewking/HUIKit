package com.hewking.custom.gesture

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GestureDetectorCompat
import android.view.*
import com.hewking.custom.R
import kotlinx.android.synthetic.main.fragment_gesture_detector_layout.*

class GestureDetectorDemoFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gesture_detector_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_touch.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
        }
    }

    val gestureDetector = GestureDetectorCompat(activity,object : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
        }
    })

}