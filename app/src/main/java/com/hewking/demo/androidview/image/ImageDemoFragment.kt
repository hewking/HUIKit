package com.hewking.demo.androidview.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hewking.custom.databinding.FragmentImageDemoBinding

/**
 * @author: jianhao
 * @create: 2020/7/30
 * @description:
 */
class ImageDemoFragment : Fragment(){

    private lateinit var binding: FragmentImageDemoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentImageDemoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}