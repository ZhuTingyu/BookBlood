package com.cpigeon.book.base;

import android.app.Dialog;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class BaseInputDialog extends BaseDialogFragment {

    private ImageButton mImgClose;
    private TextView mTvTitle;
    protected TextView mTvFinish;
    private EditText mEdContent;
    private TextView mTvChoose;

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_base_input_fragment;
    }

    @Override
    protected void initView(Dialog dialog) {
        mImgClose = dialog.findViewById(R.id.imgClose);
        mTvTitle = dialog.findViewById(R.id.tvTitle);
        mTvFinish = dialog.findViewById(R.id.tvFinish);
        mEdContent = dialog.findViewById(R.id.edContent);
        mTvChoose = dialog.findViewById(R.id.tvChoose);

        mImgClose.setOnClickListener(v -> {
            hide();
        });

        mTvFinish.setOnClickListener(v -> {
            if(mOnFinishListener != null){
                mOnFinishListener.finish(mEdContent.getText().toString());
            }
        });

    }
    @Override
    protected void initLayout(Window window, WindowManager.LayoutParams lp) {
        window.setWindowAnimations(R.style.AnimBottomDialog);
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (ScreenTool.getScreenHeight() / 4) * 3;
        window.setAttributes(lp);
    }

    public void setTitle(String title){
        mTvTitle.setText(title);
    }

    public void setTitle(@StringRes int title){
        mTvTitle.setText(Utils.getString(title));
    }

    public interface  OnFinishListener{
        void finish(String content);
    }

    private OnFinishListener mOnFinishListener;

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        mOnFinishListener = onFinishListener;
    }

    public void setOnChooseClickListener(View.OnClickListener onClickListener) {
        mTvChoose.setVisibility(View.VISIBLE);
        mTvChoose.setOnClickListener(onClickListener);
    }
}
