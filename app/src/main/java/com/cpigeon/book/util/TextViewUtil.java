package com.cpigeon.book.util;

import android.graphics.Color;
import android.widget.TextView;

import com.base.util.Utils;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/8/9.
 */

public class TextViewUtil {
    public static void setEnabled(TextView textView, boolean isEnabled){
        textView.setEnabled(isEnabled);
        if(isEnabled){
            textView.setTextColor(Utils.getColor(R.color.color_text_can_click));
        }else {
            textView.setTextColor(Color.WHITE);
        }
    }
}
