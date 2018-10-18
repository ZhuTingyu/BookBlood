package com.cpigeon.book.module.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.http.HttpErrorException;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.butterknife.AntiShake;
import com.base.util.dialog.DialogUtils;
import com.base.util.file.FileUtils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.UpdateInfo;
import com.cpigeon.book.module.menu.AboutAsFragment;
import com.cpigeon.book.module.menu.LogbookFragment;
import com.cpigeon.book.module.menu.SettingFragment;
import com.cpigeon.book.module.menu.account_security.ReviseLoginPsdFragment;
import com.cpigeon.book.module.menu.mycurrency.MyPigeonCurrencyFragment;
import com.cpigeon.book.module.menu.service.OpenServiceFragment;
import com.cpigeon.book.module.menu.smalltools.recyclebin.RecycleBinFragment;
import com.cpigeon.book.module.menu.update.UpdateManager;
import com.cpigeon.book.module.menu.update.viewmodel.UpdateViewModel;
import com.cpigeon.book.module.menu.viewmodel.ShareViewModel;
import com.cpigeon.book.module.menu.viewmodel.UserInfoViewModel;
import com.cpigeon.book.module.order.OrderListActivity;
import com.cpigeon.book.module.order.balance.AccountBalanceFragment;
import com.cpigeon.book.module.pigeonhouse.PigeonHouseInfoFragment;
import com.cpigeon.book.util.CpigeonConfig;
import com.cpigeon.book.widget.mydialog.ShareDialogFragment;
import com.cpigeon.book.widget.mydialog.ViewControlShare;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 我的信息
 * Created by Administrator on 2018/8/8.
 */

public class UserInfoFragment extends BaseBookFragment {

    private ShareDialogFragment dialogFragment;

    private ShareViewModel mShareViewModel;
    private UserInfoViewModel mUserInfoViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mShareViewModel = new ShareViewModel();
        mUserInfoViewModel = new UserInfoViewModel();
        initViewModels(mShareViewModel, mUserInfoViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShareViewModel.mInviteCodeData.observe(this, inviteCodeEntity -> {
            try {
                setProgressVisible(false);
                if (dialogFragment != null && dialogFragment.getDialog() != null && dialogFragment.getDialog().isShowing()) {
                    dialogFragment.getDialog().dismiss();
                    dialogFragment.dismiss();
                }

                if (dialogFragment != null) {
                    dialogFragment.setShareTitle("注册邀请");
                    dialogFragment.setShareContentString("分享天下鸽谱，注册邀请得鸽币!!!");
                    dialogFragment.setShareContent(inviteCodeEntity.getUrl());
                    dialogFragment.setShareListener(ViewControlShare.getShareResultsDown(getBaseActivity(), dialogFragment, ""));
                    dialogFragment.setShareType(1);
                    dialogFragment.show(getBaseActivity().getFragmentManager(), "share");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        setToolbarNotBack();
        setTitle("我的");

//        checkNewVersion(1);//版本更新检查

        dialogFragment = new ShareDialogFragment();

        mUserInfoViewModel.getTXGP_GetUserInfoData();

    }

    protected void initObserve() {
        mUserInfoViewModel.mUserInfoData.observe(this, data -> {
            //获取用户信息回调
        });
    }

    @OnClick({R.id.ll_loft_info, R.id.ll_account_security, R.id.ll_logbook, R.id.ll_about_us, R.id.ll_setting, R.id.ll_my_order,
            R.id.ll_my_gebi, R.id.ll_renewal, R.id.ll_account_balance, R.id.ll_share_txgp
            , R.id.ll_test, R.id.ll_test2})
    public void onViewClicked(View view) {

        if (AntiShake.getInstance().check()) {//防止点击过快
            return;
        }

        switch (view.getId()) {
            case R.id.ll_loft_info:
                //鸽舍信息
                PigeonHouseInfoFragment.start(getBaseActivity(), true);
                break;
            case R.id.ll_account_security:
                //账户安全
                String[] chooseWays = getResources().getStringArray(R.array.array_account_security);
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                    String way = chooseWays[p];
                    if (Utils.getString(R.string.text_renewal_login_psd).equals(way)) {
                        //修改登录密码

                        IntentBuilder.Builder()
                                .putExtra(IntentBuilder.KEY_DATA, 1)
                                .startParentActivity(getBaseActivity(), ReviseLoginPsdFragment.class);

//                        ReviseLoginPsdFragment.start(this);
                    } else if (Utils.getString(R.string.text_renewal_play_psd).equals(way)) {
                        //修改支付密码

                        IntentBuilder.Builder()
                                .putExtra(IntentBuilder.KEY_DATA, 2)
                                .startParentActivity(getBaseActivity(), ReviseLoginPsdFragment.class);

//                        RevisePlayPsdFragment.start(this);
                    }
                });

                break;
            case R.id.ll_logbook:
                //操作日志
                LogbookFragment.start(getBaseActivity());
                break;
            case R.id.ll_about_us:
                //关于我们
                AboutAsFragment.start(getBaseActivity());
                break;
            case R.id.ll_setting:
                //设置
                SettingFragment.start(getBaseActivity());
                break;
            case R.id.ll_my_order:
                //我的订单
                OrderListActivity.start(getBaseActivity());
                break;
            case R.id.ll_my_gebi:
                //我的鸽币
                MyPigeonCurrencyFragment.start(getBaseActivity());
                break;
            case R.id.ll_renewal:
                //续费
                OpenServiceFragment.start(getBaseActivity());
                break;
            case R.id.ll_account_balance:
                //账户余额
                AccountBalanceFragment.start(getBaseActivity());
                break;
            case R.id.ll_share_txgp:
                //分享天下鸽谱
                setProgressVisible(true);
                mShareViewModel.getZGW_Users_SignGuiZeData();
                break;

            case R.id.ll_test:

                checkNewVersion(2);
                break;

            case R.id.ll_test2:
                RecycleBinFragment.start(getBaseActivity());
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getBaseActivity()).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 检查新版本
     */
    private UpdateManager mUpdateManager;

    private void checkNewVersion(int tag) {

        mUpdateViewModel = new UpdateViewModel();
        //更新检查
        mUpdateManager = new UpdateManager(getBaseActivity());

        onCheckUpdateInfoListener = new UpdateManager.OnCheckUpdateInfoListener() {
            @Override
            public void onGetUpdateInfoStart() {
                Log.d("xiaohl", "onNotFoundUpdate: 检查更新中...");
            }

            @Override
            public boolean onGetUpdateInfoEnd(List<UpdateInfo> updateInfos) {
                return false;
            }

            @Override
            public void onNotFoundUpdate() {
                if (tag == 2) {
                    //点击弹出

                    getBaseActivity().errorDialog = DialogUtils.createHintDialog(getActivity(), "当前已是最新版本！！！", SweetAlertDialog.ERROR_TYPE, false, dialog -> {
                        dialog.dismiss();
                    });
                }

                String path = CpigeonConfig.UPDATE_SAVE_FOLDER + getActivity().getPackageName() + ".apk";
                FileUtils.deleteDirectory(path, false);
            }

            @Override
            public void onHasUpdate(UpdateInfo updateInfo) {

            }

            @Override
            public void onDownloadStart() {

            }
        };

        checkUpdates(mUpdateManager);
    }

    /**
     * 检查App更新(从服务器拉取数据)
     */
    private UpdateManager.OnCheckUpdateInfoListener onCheckUpdateInfoListener;
    private UpdateViewModel mUpdateViewModel;

    public void checkUpdates(UpdateManager mUpdateManager) {

        if (onCheckUpdateInfoListener != null) {
            onCheckUpdateInfoListener.onGetUpdateInfoStart();
        }

        mUpdateViewModel.getUpdate_InfoData();
        mUpdateViewModel.getUpdateInfoData.observe(this, data -> {
            if (data.isOk()) {
                Log.d("xiazaidizhi", "checkUpdate: " + data.getData().get(0).getUrl());

//                List<UpdateInfo> mDatas = new ArrayList<>();
//                mDatas.add(new UpdateInfo.Builder()
//                        .verCode(20020)
//                        .updateTime("2018-22-22")
//                        .verName("3.4.5")
//                        .packageName("com.cpigeon.book")
//                        .appName("天下鸽谱")
//                        .updateExplain("1.新增用户个人信息中心，可进行个人信息的设置\n2.新增用户个人信息中心，可进行个人信息的设置\n3.新增用户个人信息中心，可进行个人信息的设置\n4.新增用户个人信息中心，可进行个人信息的设置\n")
//                        .url("http://www.cpigeon.com:818/APP/Download?type=androidzgzs")
//                        .force(true)
//                        .build());
                mUpdateManager.checkUpdate(data.data);

//                if (onCheckUpdateInfoListener != null) {
//                    new Handler(Looper.getMainLooper())
//                            .postDelayed(new Runnable() {
//                                             @Override
//                                             public void run() {
//                                                 if (!onCheckUpdateInfoListener.onGetUpdateInfoEnd(data.getData()))
//                                                     mUpdateManager.checkUpdate(data.getData());
//                                             }
//                                         }
//                                    , 500);
//                } else {
//                    mUpdateManager.checkUpdate(data.getData());
//                }

            } else {
                if (onCheckUpdateInfoListener != null) {
                    onCheckUpdateInfoListener.onGetUpdateInfoEnd(null);
                }
                throw new HttpErrorException(data);
            }

        });
    }
}
