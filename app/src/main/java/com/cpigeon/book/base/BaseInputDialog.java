package com.cpigeon.book.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.util.utility.KeyboardUtils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class BaseInputDialog extends BaseDialogFragment {

    private ImageView mImgClose;
    private TextView mTvTitle;
    protected TextView mTvFinish;
    private EditText mEdContent;
    private TextView mTvChoose;
    private int mEditInputType;
    private String mContent;
    private boolean mIsOpen;

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_base_input_fragment;
    }

    @Override
    protected void initView(Dialog dialog) {
        KeyboardUtils.registerSoftInputChangedListener(getActivity(), isOpen -> {
            mIsOpen = isOpen;
        });
        mImgClose = dialog.findViewById(R.id.imgClose);
        mTvTitle = dialog.findViewById(R.id.tvTitle);
        mTvFinish = dialog.findViewById(R.id.tvFinish);
        mEdContent = dialog.findViewById(R.id.edContent);
        mTvChoose = dialog.findViewById(R.id.tvChoose);

        if (getArguments() != null) {
            String title = getArguments().getString(IntentBuilder.KEY_TITLE);
            mEditInputType = getArguments().getInt(IntentBuilder.KEY_TYPE);
            mContent = getArguments().getString(IntentBuilder.KEY_DATA);

            mEdContent.setText(mContent);

            mTvTitle.setText(title);
            if (mEditInputType != 0) {
                mEdContent.setInputType(mEditInputType);
            }
        }

        mImgClose.setOnClickListener(v -> {
            hide();
        });

        mTvFinish.setOnClickListener(v -> {
            if (mOnFinishListener != null) {
                mOnFinishListener.finish(mEdContent.getText().toString());
                hide();
            }
        });

        if (mOnChooseListener != null) {
            mTvChoose.setVisibility(View.VISIBLE);
            mTvChoose.setOnClickListener(v -> {
                mOnChooseListener.choose();
                dismiss();
            });
        }

        mEdContent.requestFocus();
        KeyboardUtils.toggleSoftInput();

    }

    @Override
    protected void initLayout(Window window, WindowManager.LayoutParams lp) {
        window.setWindowAnimations(R.style.AnimBottomDialog);
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setTitle(@StringRes int title) {
        mTvTitle.setText(Utils.getString(title));
    }

    public interface OnFinishListener {
        void finish(String content);
    }

    public interface OnChooseClickListener {
        void choose();
    }

    public EditText getEditText() {
        return mEdContent;
    }

    private OnFinishListener mOnFinishListener;
    private OnChooseClickListener mOnChooseListener;

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        mOnFinishListener = onFinishListener;
    }

    public void setOnChooseClickListener(OnChooseClickListener onChooseClickListener) {
        mOnChooseListener = onChooseClickListener;
    }

    public void setContent(String content) {
        mEdContent.setText(content);
    }

    public static BaseInputDialog show(FragmentManager fragmentManager
            , @StringRes int resId, String content, int editInputType, OnFinishListener onFinishListener, @Nullable OnChooseClickListener onChooseClickListener) {
        BaseInputDialog dialog = new BaseInputDialog();
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TITLE, Utils.getString(resId));
        if (editInputType != 0) {
            bundle.putInt(IntentBuilder.KEY_TYPE, editInputType);
        }

        if (StringUtil.isStringValid(content)) {
            bundle.putString(IntentBuilder.KEY_DATA, content);
        } else {
            bundle.putString(IntentBuilder.KEY_DATA, "");
        }


        dialog.setArguments(bundle);
        dialog.setOnFinishListener(onFinishListener);
        dialog.setOnChooseClickListener(onChooseClickListener);
        dialog.show(fragmentManager);
        return dialog;
    }


    public static BaseInputDialog show(FragmentManager fragmentManager
            , @StringRes int resId, int editInputType, OnFinishListener onFinishListener, @Nullable OnChooseClickListener onChooseClickListener) {
        BaseInputDialog dialog = new BaseInputDialog();
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TITLE, Utils.getString(resId));
        if (editInputType != 0) {
            bundle.putInt(IntentBuilder.KEY_TYPE, editInputType);
        }

        dialog.setArguments(bundle);
        dialog.setOnFinishListener(onFinishListener);
        dialog.setOnChooseClickListener(onChooseClickListener);
        dialog.show(fragmentManager);


        return dialog;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mIsOpen) {
            KeyboardUtils.toggleSoftInput();
        }
    }
}
