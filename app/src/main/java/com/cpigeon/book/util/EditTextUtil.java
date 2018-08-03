package com.cpigeon.book.util;

import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cpigeon.book.R;

/**
 * Created by Administrator on 2018/8/3.
 */

public class EditTextUtil {

    /**
     * 显示隐藏密码
     */
    public static void setPasHint(EditText editText, ImageButton imageButton) {
        if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            //默认状态显示密码--设置文本 要一起写才能起作用  InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageButton.setImageResource(R.mipmap.hide2x);//设置图片
        } else {
            //选择状态 显示明文--设置为可见的密码
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageButton.setImageResource(R.mipmap.show2x);//设置图片
        }
        editText.setSelection(editText.getText().length());//光标移动到文本框末尾
    }

}
