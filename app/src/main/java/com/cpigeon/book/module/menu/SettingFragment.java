package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.base.BaseWebViewActivity;
import com.base.util.IntentBuilder;
import com.base.util.dialog.DialogUtils;
import com.base.util.glide.GlideCacheUtil;
import com.base.util.system.AppManager;
import com.base.util.utility.PhoneUtils;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.login.LoginActivity;
import com.cpigeon.book.module.login.viewmodel.LoginViewModel;
import com.cpigeon.book.module.menu.feedback.FeedbackListFragment;
import com.cpigeon.book.service.SingleLoginService;
import com.cpigeon.book.widget.LineInputView;
import com.shuyu.gsyvideoplayer.utils.StorageUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * hl 设置
 * Created by Administrator on 2018/8/8.
 */

public class SettingFragment extends BaseBookFragment {

    @BindView(R.id.ll_current_version)
    LineInputView ll_current_version;

    @BindView(R.id.ll_clear_cache)
    LineInputView ll_clear_cache;
    private LoginViewModel mLoginViewModel;

    @BindView(R.id.out_login)
    TextView out_login;//退出登录状态

    private File mFile;//缓存文件
    private long cacheSize;//缓存内存大小


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, SettingFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLoginViewModel = new LoginViewModel();
        initViewModel(mLoginViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setTitle("设置");

        ll_current_version.getEditText().setText("v " + PhoneUtils.getVersionName(getActivity()));//设置当前app的版本号

        mFile = StorageUtils.getIndividualCacheDirectory(getActivity());
        try {
            cacheSize = GlideCacheUtil.getInstance().getFolderSize(mFile) + GlideCacheUtil.getInstance().getFolderSize(new File(getActivity().getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR));
            ll_clear_cache.getEditText().setText(String.valueOf(GlideCacheUtil.getFormatSize(cacheSize)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        //hl  退出登录
        mLoginViewModel.outMsg.observe(this, s -> {
            SingleLoginService.stopService();
            UserModel.getInstance().cleanUserInfo();
            setProgressVisible(false);
            getBaseActivity().errorDialog = DialogUtils.createHintDialog(getActivity(), s, SweetAlertDialog.SUCCESS_TYPE, false, dialog -> {
                dialog.dismiss();
                //结束所有页面，跳转到登录页
                LoginActivity.start(getBaseActivity());
                AppManager.getAppManager().killAllToLoginActivity(LoginActivity.class);
            });
        });
    }

    @OnClick({R.id.ll_clear_cache, R.id.ll_applied_scores, R.id.ll_current_version, R.id.ll_push_set, R.id.out_login, R.id.ll_feedback, R.id.ll_use_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_clear_cache:
                //清除缓存
                getBaseActivity().errorDialog = DialogUtils.createDialogWithLeft2(getActivity(), "是否清除缓存", false, sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                }, sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();

                    removeCache();
                });

                break;
            case R.id.ll_applied_scores:
                //应用评分
                try {
                    String mAddress = "market://details?id=" + getActivity().getPackageName();
                    Intent marketIntent = new Intent("android.intent.action.VIEW");
                    marketIntent.setData(Uri.parse(mAddress));
                    startActivity(marketIntent);
                } catch (Exception e) {
                    error("未找到应用商城");
                }

                break;
            case R.id.ll_current_version:
                //当前版本

                break;
            case R.id.ll_push_set:
                //推送设置
                PushSetFragment.start(getActivity());
                break;

            case R.id.ll_feedback:
                //意见反馈
                FeedbackListFragment.start(getActivity());
                break;
            case R.id.ll_use_help:
                //使用帮助
                Intent intent2 = new Intent(getActivity(), BaseWebViewActivity.class);
                intent2.putExtra(IntentBuilder.KEY_DATA, String.valueOf(getString(R.string.baseUrl) + getString(R.string.txgp_use_help)));
                intent2.putExtra(IntentBuilder.KEY_TITLE, getString(R.string.web_title_use_help));
                startActivity(intent2);
                break;

            case R.id.out_login:
                //退出登录
                getBaseActivity().errorDialog = DialogUtils.createDialogWithLeft2(getActivity(), "确定退出登录", false, sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                }, sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();

                    setProgressVisible(true);

                    mLoginViewModel.outLogin();
                });

                break;
        }
    }

    /**
     * 清除缓存
     */
    private void removeCache() {
        GlideCacheUtil.clearImageDiskCache(getActivity());//清除Glide图片加载缓存
        GlideCacheUtil.deleteFolderFile(mFile.getPath(), false);//清除视频播放缓存
        ll_clear_cache.getEditText().setText(String.valueOf(0 + "KB"));
    }

}
