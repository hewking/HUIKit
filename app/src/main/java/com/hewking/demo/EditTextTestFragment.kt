<<<<<<< HEAD
package com.hewking.demo

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.hewking.custom.R
import kotlinx.android.synthetic.main.activity_audiorecorder.view.*

class EditTextTestFragment : androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edittext_demo,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.v<Button>(R.id.btn_test2)?.background.alpha = 10
    }
=======
package com.hewking.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.hewking.custom.R
import com.hewking.custom.util.v

class EditTextTestFragment : androidx.fragment.app.Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edittext_demo,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.v<Button>(R.id.btn_test2)?.background.alpha = 10
    }
>>>>>>> 29db5ff72117652c79e56c13f91005b1bfdcfd24
}