package com.hewking.uikit.softkeyboard;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author: salmonzhg
 * @create: 2019/9/11
 * @description:
 */
public class CustomKeyboard {

  private static final String TAG = "KeyboardBuilder";
  private Activity mActivity;
  private KeyboardView mKeyboardView;

  public CustomKeyboard(Activity ac, KeyboardView keyboardView, int keyBoardXmlResId) {
    mActivity = ac;
    mKeyboardView = keyboardView;
    Keyboard mKeyboard = new Keyboard(mActivity, keyBoardXmlResId);
    // Attach the keyboard to the view
    mKeyboardView.setKeyboard(mKeyboard);
    // Do not show the preview balloons
    mKeyboardView.setPreviewEnabled(false);
    KeyboardView.OnKeyboardActionListener keyboardListener = getOnKeyboardActionListener();
    mKeyboardView.setOnKeyboardActionListener(keyboardListener);
  }

  private KeyboardView.OnKeyboardActionListener getOnKeyboardActionListener() {
    return new KeyboardView.OnKeyboardActionListener() {
      @Override
      public void onKey(int primaryCode, int[] keyCodes) {
        // Get the EditText and its Editable
        View focusCurrent = mActivity.getWindow().getCurrentFocus();
        if (focusCurrent == null || !(focusCurrent instanceof EditText)) {
          return;
        }
        EditText edittext = (EditText) focusCurrent;
        Editable editable = edittext.getText();
        int start = edittext.getSelectionStart();
        // Handle key
        if (primaryCode == Keyboard.KEYCODE_CANCEL) {
          hideCustomKeyboard();
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
          if (editable != null && start > 0) {
            editable.delete(start - 1, start);
          }
        } else if (primaryCode == Keyboard.KEYCODE_DONE) {// 隐藏键盘
          hideCustomKeyboard();
        } else {
          // Insert character
          editable.insert(start, Character.toString((char) primaryCode));
        }
      }

      @Override
      public void onPress(int arg0) {
        //empty
      }

      @Override
      public void onRelease(int primaryCode) {
        //empty
      }

      @Override
      public void onText(CharSequence text) {
        //empty
      }

      @Override
      public void swipeDown() {
        //empty
      }

      @Override
      public void swipeLeft() {
        //empty
      }

      @Override
      public void swipeRight() {
        //empty
      }

      @Override
      public void swipeUp() {
        //empty
      }
    };
  }

  //绑定一个EditText
  public void setupEditText(EditText editText) {
    // Make the custom keyboard appear
    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          showCustomKeyboard(v);
        } else {
          hideCustomKeyboard();
        }
      }
    });

    editText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showCustomKeyboard(v);
      }
    });

    editText.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        EditText edittext = (EditText) v;
        int inType = edittext.getInputType();    // Backup the input type
        edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
        edittext.onTouchEvent(event);        // Call native handler
        edittext.setInputType(inType);       // Restore input type
        edittext.setSelection(edittext.getText().length());
        return true;
      }
    });

    editText.setLongClickable(false);
  }

  public void hideCustomKeyboard() {
    mKeyboardView.setVisibility(View.GONE);
    mKeyboardView.setEnabled(false);
  }

  public void showCustomKeyboard(View v) {
    mKeyboardView.setVisibility(View.VISIBLE);
    mKeyboardView.setEnabled(true);
    if (v != null) {
      ((InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE))
          .hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
  }

  public boolean isCustomKeyboardVisible() {
    return mKeyboardView.getVisibility() == View.VISIBLE;
  }
}
