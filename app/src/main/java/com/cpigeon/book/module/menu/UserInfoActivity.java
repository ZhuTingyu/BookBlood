package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookActivity;
import com.cpigeon.book.module.menu.account_security.ReviseLoginPsdFragment;
import com.cpigeon.book.module.menu.balance.AccountBalanceFragment;
import com.cpigeon.book.module.menu.mycurrency.MyPigeonCurrencyFragment;
import com.cpigeon.book.module.menu.service.RenewalFragment;
import com.cpigeon.book.module.menu.viewmodel.ShareViewModel;
import com.cpigeon.book.module.menu.viewmodel.UserInfoViewModel;
import com.cpigeon.book.module.order.OrderListActivity;
import com.cpigeon.book.module.pigeonhouse.PigeonHouseInfoFragment;
import com.cpigeon.book.widget.mydialog.ShareDialogFragment;
import com.cpigeon.book.widget.mydialog.ViewControlShare;
import com.umeng.socialize.UMShareAPI;

import butterknife.OnClick;


/**
 * 我的信息
 * Created by Administrator on 2018/8/8.
 */

public class UserInfoActivity extends BaseBookActivity {


    private ShareDialogFragment dialogFragment;

    private ShareViewModel mShareViewModel;
    private UserInfoViewModel mUserInfoViewModel;

    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, UserInfoActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(com.base.http.R.anim.anim_left_in, com.base.http.R.anim.anim_right_out);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mShareViewModel = new ShareViewModel();
        mUserInfoViewModel = new UserInfoViewModel();
        initViewModels(mShareViewModel, mUserInfoViewModel);

        mShareViewModel.mInviteCodeData.observe(this, inviteCodeEntity -> {

            try {
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

        setTitle("我的");
        setToolbarRight("签到", item -> {
            //签到
            SignFragment.start(getBaseActivity());
            return true;
        });

        dialogFragment = new ShareDialogFragment();

        mUserInfoViewModel.getTXGP_GetUserInfoData();
        initObserve();
    }

    protected void initObserve() {
        super.initObserve();

        mUserInfoViewModel.mUserInfoData.observe(this, data -> {
            //获取用户信息回调

        });
    }

    @OnClick({R.id.ll_loft_info, R.id.ll_account_security, R.id.ll_logbook, R.id.ll_about_us, R.id.ll_setting, R.id.ll_my_order,
            R.id.ll_my_gebi, R.id.ll_renewal, R.id.ll_account_balance, R.id.ll_share_txgp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_loft_info:
                //鸽舍信息
                PigeonHouseInfoFragment.start(this, true);
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
                                .startParentActivity(this, ReviseLoginPsdFragment.class);

//                        ReviseLoginPsdFragment.start(this);
                    } else if (Utils.getString(R.string.text_renewal_play_psd).equals(way)) {
                        //修改支付密码

                        IntentBuilder.Builder()
                                .putExtra(IntentBuilder.KEY_DATA, 2)
                                .startParentActivity(this, ReviseLoginPsdFragment.class);

//                        RevisePlayPsdFragment.start(this);
                    }
                });

                break;
            case R.id.ll_logbook:
                //操作日志
                LogbookFragment.start(this);
                break;
            case R.id.ll_about_us:
                //关于我们
                AboutAsFragment.start(this);
                break;
            case R.id.ll_setting:
                //设置
                SettingFragment.start(this);
                break;
            case R.id.ll_my_order:
                //我的订单
                OrderListActivity.start(this);
                break;
            case R.id.ll_my_gebi:
                //我的鸽币
                MyPigeonCurrencyFragment.start(this);
                break;
            case R.id.ll_renewal:
                //续费
                RenewalFragment.start(this);
                break;
            case R.id.ll_account_balance:
                //账户余额
                AccountBalanceFragment.start(this);
                break;
            case R.id.ll_share_txgp:
                //分享天下鸽谱
                mShareViewModel.getZGW_Users_SignGuiZeData();

//                } catch (Exception e) {
//                    Log.d("sharemyxiaohl", "onViewClicked: 弹出异常"+e.getLocalizedMessage());
//                }

//                Intent intent1 = new Intent(this, BaseWebViewActivity.class);
//                intent1.putExtra(IntentBuilder.KEY_DATA, String.valueOf(getString(R.string.baseUrl) + getString(R.string.txgp_app_share)));
//                intent1.putExtra(IntentBuilder.KEY_TITLE, getString(R.string.web_title_share_txgp));
//                startActivity(intent1);
                break;
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_info_details;
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.base.http.R.anim.anim_right_in, com.base.http.R.anim.anim_left_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
