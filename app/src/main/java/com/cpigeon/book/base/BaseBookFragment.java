package com.cpigeon.book.base;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.BaseFragment;
import com.base.util.dialog.DialogUtils;
import com.base.util.system.AppManager;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.login.LoginActivity;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.service.SingleLoginService;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class BaseBookFragment extends BaseFragment {

    Unbinder unbinder;

    @Override
    public void error(int code, String error) {
        setProgressVisible(false);//加载框
        if (code == 90102 || code == 90100) {

            if (!StringUtil.isStringValid(error)) {
                return;
            }

            //保证界面只有一个错误提示
            if (getBaseActivity().errorDialog == null || !getBaseActivity().errorDialog.isShowing()) {
                getBaseActivity().errorDialog = DialogUtils.createHintDialog(getActivity(), error, SweetAlertDialog.ERROR_TYPE, false, dialog -> {
                    SingleLoginService.stopService();
                    UserModel.getInstance().cleanUserInfo();
                    dialog.dismiss();
                    AppManager.getAppManager().killAllActivity();
                    LoginActivity.start(getBaseActivity());
                });
            }

        } else {
            super.error(code, error);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);//在当前界面注册一个订阅者
    }


    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);//取消注册
    }


    protected void addItemDecorationLine(RecyclerView recyclerView) {
        addItemDecorationLine(recyclerView, R.color.color_line, ScreenTool.dip2px(0.5f));
    }

    protected void addItemDecorationLine(RecyclerView recyclerView, @ColorRes int color, int size) {
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(recyclerView.getContext())
                .colorResId(color).size(size).build());
    }

}
