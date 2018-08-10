package com.cpigeon.book.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpigeon.book.R;

/**
 * 输入框
 * Created by Administrator on 2018/8/10.
 */

public class InputBoxView extends LinearLayout {


    private int textSize;
    private int hintColor;
    private int textColor;
    private int maxInputLine;
    private String text;
    private String textHint;
    private EditText input_box_editText;
    private TextView tv_hint;

    public InputBoxView(Context context) {
        this(context, null);
    }

    public InputBoxView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputBoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttr(attrs);
        initView();
    }

    @SuppressLint({"Recycle", "ResourceAsColor"})
    private void readAttr(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.InputBoxView);
        textSize = array.getInteger(R.styleable.InputBoxView_input_text_size, 14);
        hintColor = array.getColor(R.styleable.InputBoxView_text_hint_color, R.color.general_text_hint_color);
        textColor = array.getColor(R.styleable.InputBoxView_text_color, R.color.general_text_color);

        maxInputLine = array.getInteger(R.styleable.InputBoxView_edit_max_input_line, 100);

        text = array.getString(R.styleable.InputBoxView_text);
        textHint = array.getString(R.styleable.InputBoxView_text_hint);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_input_box, this, true);

        input_box_editText = view.findViewById(R.id.input_box_editText);
        tv_hint = view.findViewById(R.id.tv_hint);

        input_box_editText.setHintTextColor(getResources().getColor(hintColor));
        input_box_editText.setTextColor(getResources().getColor(textColor));
        input_box_editText.setTextSize(textSize);
        input_box_editText.setMaxLines(maxInputLine);
        input_box_editText.setText(text);

        input_box_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 0) {
                    tv_hint.setVisibility(VISIBLE);
                } else {
                    tv_hint.setVisibility(GONE);
                }
            }
        });


        tv_hint.setHint(textHint);


    }


    public String getText() {
        return input_box_editText.getText().toString();
    }

    public void setText(String textStr) {
        input_box_editText.setText(textStr);
    }

}
