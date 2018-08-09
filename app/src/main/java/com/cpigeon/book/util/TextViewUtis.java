package com.cpigeon.book.util;

import android.graphics.Color;
import android.widget.TextView;

import com.base.util.Utils;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/8/9.
 */

public class TextViewUtis {
    public static void setEnabled(TextView textView, boolean isEnabled){
        textView.setEnabled(isEnabled);
        if(isEnabled){
            textView.setTextColor(Color.WHITE);
        }else {
            textView.setTextColor(Utils.getColor(R.color.color_text_not_can_click));
        }
    }
}
