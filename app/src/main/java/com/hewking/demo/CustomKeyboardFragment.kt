package com.hewking.demo

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.hewking.custom.R
import com.hewking.custom.databinding.FragmentCustomKeyboardBinding
import com.hewking.uikit.softkeyboard.CustomKeyboard

/**
 *@Description:
 *@Author: jianhao
 *@Date:   2021-12-29 15:18
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
class CustomKeyboardFragment: Fragment() {

  private lateinit var binding: FragmentCustomKeyboardBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCustomKeyboardBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    val keyboardHelper = CustomKeyboard(activity!!, binding.keyboardView.keyboardView, R.xml.keyboardnumber)
    keyboardHelper.setupEditText(binding.etInput)

    binding.etInput.setRawInputType(Configuration.KEYBOARD_QWERTY)
  }

}