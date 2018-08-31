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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpigeon.book.R;

/**
 * hl 输入框
 * Created by Administrator on 2018/8/10.
 */

public class InputBoxView extends LinearLayout {


    private int textSize;
    private int hintColor;
    private int textColor;
    private int maxInputLine;
    private int inputHigh;
    private String text;
    private String textHint;
    private String title;
    private EditText input_box_editText;
    private TextView tv_hint;
    private TextView mTvTitle;
    private RelativeLayout rlz_input;
    private LinearLayout layout_z;

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
        inputHigh = (int) array.getDimension(R.styleable.InputBoxView_input_high, 117);

        text = array.getString(R.styleable.InputBoxView_text);
        textHint = array.getString(R.styleable.InputBoxView_text_hint);
        title = array.getString(R.styleable.InputBoxView_inputBoxView_tittle);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_input_box, this, true);

        input_box_editText = view.findViewById(R.id.input_box_editText);
        tv_hint = view.findViewById(R.id.tv_hint);
        mTvTitle = view.findViewById(R.id.tvTitle);
        rlz_input = view.findViewById(R.id.rlz_input);

        layout_z = view.findViewById(R.id.layout_z);

        rlz_input.getLayoutParams().height = inputHigh;

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
        mTvTitle.setText(title);

    }


    public String getText() {
        return input_box_editText.getText().toString();
    }

    public void setText(String textStr) {
        input_box_editText.setText(textStr);
    }


    public EditText getEditText() {
        return input_box_editText;
    }
}
