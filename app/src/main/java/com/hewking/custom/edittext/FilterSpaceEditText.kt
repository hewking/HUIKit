package com.hewking.custom.edittext

import android.content.Context
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.Toast

class FilterSpaceEditText(ctx : Context,attrs : AttributeSet) : FilterEmojiEditText(ctx,attrs){

    private var mInputBefore : Char? = null
    private var mInputBeforeText : String? = null

    private val TAG : String = FilterSpaceEditText::class.java.simpleName

    init {
        init()
    }

    private fun init() {
        addFilterTextWatcher(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
               Log.d(TAG,"beforeTextChanged s : ${s?.toString()}  start : $start count : $count after : $after")
                mInputBeforeText = s?.toString()
                if (after >= 1 && s?.length?:0 > 0) {
                    mInputBefore = s?.get(s?.lastIndex)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG,"onTextChanged s : ${s?.toString()}  start : $start count : $count before : $before")
                if (count >= 1) {
                    // 匹配两个空格
                    val lastCh = s?.get(s?.lastIndex)
                    if (mInputBefore == lastCh && mInputBefore == ' ') {
                        Toast.makeText(context,"不能连续空格",Toast.LENGTH_SHORT).show()
                        setText(mInputBeforeText)
                        if (text is Spannable) {
                            Selection.setSelection(text, text!!.length)
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG,"afterTextChanged s : ${s?.toString()}")

            }
        })
    }


}