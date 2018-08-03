package com.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * hl 输入文本光标处于末尾位置
 * Created by Zhu TingYu on 2018/4/2.
 */

public class MyEditText extends EditText {

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        try {
            setSelection(getText().toString().length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
