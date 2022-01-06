package com.hewking.uikit.softkeyboard

import android.annotation.SuppressLint
import android.app.Activity
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Build
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.lang.reflect.Method

/**
 * @author: jianhao
 * @create: 2019/9/11
 * @description:
 */
class CustomKeyboard(
  private val mActivity: Activity,
  private val mKeyboardView: KeyboardView, keyBoardXmlResId: Int
) {

  init {
    val mKeyboard = Keyboard(mActivity, keyBoardXmlResId)
    // Attach the keyboard to the view
    mKeyboardView.keyboard = mKeyboard
    // Do not show the preview balloons
    mKeyboardView.isPreviewEnabled = false
    val keyboardListener = onKeyboardActionListener
    mKeyboardView.setOnKeyboardActionListener(keyboardListener)
  }

  // Get the EditText and its Editable
  private val onKeyboardActionListener: KeyboardView

  // Handle key
  .OnKeyboardActionListener
    private get() = object : KeyboardView.OnKeyboardActionListener {
      override fun onKey(primaryCode: Int, keyCodes: IntArray) {
        // Get the EditText and its Editable
        val focusCurrent = mActivity.window.currentFocus
        if (focusCurrent == null || focusCurrent !is EditText) {
          return
        }
        val editable = focusCurrent.text
        val start = focusCurrent.selectionStart
        // Handle key
        if (primaryCode == Keyboard.KEYCODE_CANCEL) {
          hideCustomKeyboard()
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
          if (editable != null && start > 0) {
            editable.delete(start - 1, start)
          }
        } else if (primaryCode == Keyboard.KEYCODE_DONE) { // 隐藏键盘
          hideCustomKeyboard()
        } else {
          // Insert character
          editable?.insert(start, primaryCode.toChar().toString())
        }
      }

      override fun onPress(arg0: Int) {
        //empty
      }

      override fun onRelease(primaryCode: Int) {
        //empty
      }

      override fun onText(text: CharSequence) {
        //empty
      }

      override fun swipeDown() {
        //empty
      }

      override fun swipeLeft() {
        //empty
      }

      override fun swipeRight() {
        //empty
      }

      override fun swipeUp() {
        //empty
      }
    }

  @SuppressLint("ClickableViewAccessibility")
  fun setupEditText(editText: EditText) {
    // Make the custom keyboard appear
    disableShowSoftInput(editText)
    editText.onFocusChangeListener = View.OnFocusChangeListener { v: View?, hasFocus: Boolean ->
      if (hasFocus) {
        showCustomKeyboard(v)
      } else {
        hideCustomKeyboard()
      }
    }

//    with(editText) {
//      setOnTouchListener { v, event ->
//        val inType = editText.inputType
//        inputType = InputType.TYPE_NULL
//        onTouchEvent(event)
//        inputType = inType
////        setSelection(text.length)
//        true
//      }
//    }

    editText.setOnClickListener { v: View? -> showCustomKeyboard(v) }
    editText.isLongClickable = false
  }

  fun hideCustomKeyboard() {
    mKeyboardView.visibility = View.GONE
    mKeyboardView.isEnabled = false
  }

  fun showCustomKeyboard(v: View?) {
    mKeyboardView.visibility = View.VISIBLE
    mKeyboardView.isEnabled = true
    if (v != null) {
      (mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(v.windowToken, 0)
    }
  }

  /**
   * 禁止Edittext弹出软件盘，光标依然正常显示。
   */
  fun disableShowSoftInput(editText: EditText) {
    val cls = EditText::class.java
    var method: Method
    try {
      method = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
      method.isAccessible = true
      method.invoke(editText, false)
    } catch (e: Exception) {
      Log.e("CustomKeyBoard", e.message.orEmpty())
    }
    try {
      method = cls.getMethod("setSoftInputShownOnFocus", Boolean::class.javaPrimitiveType)
      method.isAccessible = true
      method.invoke(editText, false)
    } catch (e: Exception) {
      Log.e("CustomKeyBoard", e.message.orEmpty())
    }
  }

  val isCustomKeyboardVisible: Boolean
    get() = mKeyboardView.visibility == View.VISIBLE

}