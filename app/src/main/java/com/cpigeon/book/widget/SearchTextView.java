package com.cpigeon.book.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class SearchTextView extends RelativeLayout {

    private EditText mEdSearch;
    private TextView mTvCancel;
    private OnSearchTextClickListener listener;


    public SearchTextView(Context context) {
        super(context);
        initView(context);
    }

    public SearchTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_search_text, this);

        mEdSearch = view.findViewById(R.id.edSearch);
        mTvCancel = view.findViewById(R.id.tvCancel);

        mEdSearch.setOnKeyListener((View v, int keyCode, KeyEvent event) -> {
            if ((keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER)
                    && event.getAction() == KeyEvent.ACTION_UP) {
                v.clearFocus();
                if(listener != null){
                    listener.search(mEdSearch.getText().toString());
                }
            }
            return false;
        });

        mTvCancel.setOnClickListener(v -> {
            if(listener != null){
                listener.cancel();
            }
        });
    }

    public interface OnSearchTextClickListener{
        void search(String key);
        void cancel();
    }

    public void setListener(OnSearchTextClickListener listener) {
        this.listener = listener;
    }
}
