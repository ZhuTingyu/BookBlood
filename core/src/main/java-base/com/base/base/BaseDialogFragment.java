package com.base.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.base.http.R;
import com.base.util.system.ScreenTool;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public abstract class BaseDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(getLayoutRes());
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawableResource(R.drawable.shape_bg_corner_5);
        //window.setWindowAnimations(R.style.AnimBottomDialog);
        final WindowManager.LayoutParams lp = window.getAttributes();
        initLayout(window, lp);

        initView(dialog);

        return dialog;
    }

    @LayoutRes
    protected abstract int  getLayoutRes();

    protected abstract void initView(Dialog dialog);

    protected void initLayout(Window window, WindowManager.LayoutParams lp){
        lp.gravity = Gravity.CENTER;
        lp.width = (ScreenTool.getScreenWidth() / 4) * 3;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void show(FragmentManager manager){
        show(manager, "dialog");
    }
}
