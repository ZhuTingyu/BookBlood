package com.base.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.base.http.R;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.base.widget.LoadingView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    private List<BaseViewModel> viewModels = Lists.newArrayList();

    protected LoadingView progressView;


    public BaseDialogFragment(){}



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
        window.setBackgroundDrawableResource(R.drawable.shape_bg_corner_3);
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

    public void hide(){
       this.dismiss();
    }

    public BaseActivity getBaseActivity(){
        BaseActivity activity = null;
        if(getActivity() instanceof BaseActivity){
            activity = (BaseActivity) getActivity();
        }
        return activity;
    }

    protected void initViewModel(BaseViewModel viewModel) {
        this.viewModels.add(viewModel);
        viewModel.setBaseActivity(getBaseActivity());
        viewModel.getError().observe(this, restErrorInfo -> {
            if (restErrorInfo != null) {
                error(restErrorInfo.code, restErrorInfo.message);
            }
        });
    }

    protected void error(String message) {
        getBaseActivity().setProgressVisible(false);
        getBaseActivity().error(message);
    }

    protected void error(@StringRes int resId) {
        getBaseActivity().setProgressVisible(false);
        getBaseActivity().error(Utils.getString(resId));
    }

    public void error(int code, String error) {
        getBaseActivity().error(code, error);
    }

}
