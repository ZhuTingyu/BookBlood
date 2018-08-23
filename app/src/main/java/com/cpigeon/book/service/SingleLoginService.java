package com.cpigeon.book.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.base.base.BaseViewModel;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.system.AppManager;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2018/8/6.
 */

public class SingleLoginService extends Service {

    private SingleLoginViewModel mViewModel;
    public static final String ACTION_CHECK_SINGLE_LOGIN = "com.cpigeon.book.service.SingleLoginService";
    private SweetAlertDialog dialogPrompt;
    private Timer timer;

    protected void initViewModel(BaseViewModel viewModel) {
        this.mViewModel = (SingleLoginViewModel) viewModel;
        this.mViewModel.setBaseActivity(this);
    }

    public static void start(Activity context) {
        Intent intent = new Intent(context, SingleLoginService.class);
        intent.setAction(ACTION_CHECK_SINGLE_LOGIN);
        context.startService(intent);
    }

    public static void stopService() {
        Intent intent = new Intent(Utils.getApp(), SingleLoginService.class);
        intent.setAction(ACTION_CHECK_SINGLE_LOGIN);
        Utils.getApp().stopService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_SINGLE_LOGIN.equals(action)) {
                checkLogin();
            }
        }
        return START_STICKY_COMPATIBILITY;
    }

    private void checkLogin() {

        mViewModel = new SingleLoginViewModel();
        initViewModel(mViewModel);

        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    mViewModel.getSingleLogin(o -> {
                        Log.d("xiaohls", "onHandleIntent: 2 --->" + o);
                        if (!o.isEmpty()) {
                            if (dialogPrompt == null || !dialogPrompt.isShowing()) {
                                dialogPrompt = DialogUtils.createDialogWithLeft2(AppManager.getAppManager().getTopActivity(), o, true,dialog1 -> {
                                    //结束所有页面
                                    UserModel.getInstance().cleanUserInfo();

                                    SingleLoginService.stopService();
                                    AppManager.getAppManager().killAllActivity();
                                    dialog1.dismiss();
                                }, dialog2 -> {
                                    SingleLoginService.stopService();
                                    UserModel.getInstance().cleanUserInfo();
                                    //结束所有页面，跳转到登录页
                                    dialog2.dismiss();
                                    LoginActivity.start(AppManager.getAppManager().getTopActivity());
                                    AppManager.getAppManager().killAllToLoginActivity(LoginActivity.class);
                                });
                            }
                        }
                    });
                }
            }, 0, 1000 * 10);// 这里设定将延时每天固定执行
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.onDestroy();
            mViewModel = null;
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        Log.d("xiaohls", "onHandleIntent: 10");
    }
}
