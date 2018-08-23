package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.base.base.BaseWebViewActivity;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookActivity;
import com.cpigeon.book.module.pigeonhouse.PigeonHouseInfoFragment;
import com.cpigeon.book.widget.LineInputView;


/**
 * 我的信息
 * Created by Administrator on 2018/8/8.
 */

public class UserInfoActivity extends BaseBookActivity {

    private LineInputView mLlLoftInfo;
    private LineInputView mLlAccountSecurity;
    private LinearLayout mLlShareTxgp;
    private LineInputView mLlAccountBalance;
    private LineInputView mLlRenewal;
    private LineInputView mLlMyGebi;
    private LineInputView mLlMyOrder;
    private LineInputView mLlAboutUs;
    private LineInputView mLlSetting;
//    private LineInputView mLlFeedback;
//    private LineInputView mLlUseHelp;
    private LineInputView mLlLogbook;
//    private LineInputView mLlAnnouncementNotice;
//    private LineInputView mLlPigeonFriendMsg;
    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, UserInfoActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(com.base.http.R.anim.anim_left_in, com.base.http.R.anim.anim_right_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info_details);
        setTitle("我的");

        mLlLoftInfo = findViewById(R.id.ll_loft_info);
        mLlAccountSecurity = findViewById(R.id.ll_account_security);
        mLlShareTxgp = findViewById(R.id.ll_share_txgp);
        mLlAccountBalance = findViewById(R.id.ll_account_balance);
        mLlRenewal = findViewById(R.id.ll_renewal);
        mLlMyGebi = findViewById(R.id.ll_my_gebi);
        mLlMyOrder = findViewById(R.id.ll_my_order);
        mLlAboutUs = findViewById(R.id.ll_about_us);
        mLlSetting = findViewById(R.id.ll_setting);
//        mLlFeedback = findViewById(R.id.ll_feedback);
//        mLlUseHelp = findViewById(R.id.ll_use_help);
        mLlLogbook = findViewById(R.id.ll_logbook);
//        mLlAnnouncementNotice = findViewById(R.id.ll_announcement_notice);
//        mLlPigeonFriendMsg = findViewById(R.id.ll_pigeon_friend_msg);

        mLlLoftInfo.setOnClickListener(v -> {
            PigeonHouseInfoFragment.start(this, true);
        });

        mLlAccountSecurity.setOnClickListener(v -> {
            //账户安全
            String[] chooseWays = getResources().getStringArray(R.array.array_account_security);
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                String way = chooseWays[p];
                if (Utils.getString(R.string.text_renewal_login_psd).equals(way)) {
                    //修改登录密码
                    ReviseLoginPsdFragment.start(this);
                } else if (Utils.getString(R.string.text_renewal_play_psd).equals(way)) {
                    //修改支付密码
                    RevisePlayPsdFragment.start(this);
                }
            });
        });
        mLlShareTxgp.setOnClickListener(v -> {
            //分享天下鸽谱
            Intent intent1 = new Intent(getBaseActivity(), BaseWebViewActivity.class);
            intent1.putExtra(IntentBuilder.KEY_DATA, String.valueOf(getString(R.string.baseUrl) + getString(R.string.txgp_app_share)));
            startActivity(intent1);
        });

        mLlRenewal.setOnClickListener(v -> {
            //续费
            RenewalFragment.start(getBaseActivity());
        });
        mLlMyGebi.setOnClickListener(v -> {
            //我的鸽币
            MyPigeonCurrencyFragment.start(getBaseActivity());
        });
        mLlMyOrder.setOnClickListener(v -> {
            //我的订单
            OrderListFragment.start(getBaseActivity());
        });
        mLlAboutUs.setOnClickListener(v -> {
            //关于我们
            AboutAsFragment.start(getBaseActivity());
        });
        mLlSetting.setOnClickListener(v -> {
            SettingFragment.start(getBaseActivity());
        });
//        mLlFeedback.setOnClickListener(v -> {
//            //意见反馈
//            FeedbackListFragment.start(getBaseActivity());
//        });
//        mLlUseHelp.setOnClickListener(v -> {
//            //使用帮助
//            Intent intent2 = new Intent(getBaseActivity(), BaseWebViewActivity.class);
//            intent2.putExtra(IntentBuilder.KEY_DATA, String.valueOf(getString(R.string.baseUrl) + getString(R.string.txgp_use_help)));
//            startActivity(intent2);
//        });
        mLlLogbook.setOnClickListener(v -> {
            //操作日志
            LogbookFragment.start(this);
        });
//        mLlAnnouncementNotice.setOnClickListener(v -> {
//            //公告通知
//            AnnouncementNoticeFragment.start(getBaseActivity());
//        });
//
//        mLlPigeonFriendMsg.setOnClickListener(v -> {
//            //鸽友消息
//            PigeonFriendMsgFragment.start(getBaseActivity());
//        });

        mLlAccountBalance.setOnClickListener(v -> {
            //账户余额
            AccountBalanceFragment.start(getBaseActivity());
        });


        setToolbarRight("签到", item -> {
            //签到
            SignFragment.start(getBaseActivity());
            return true;
        });
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.base.http.R.anim.anim_right_in, com.base.http.R.anim.anim_left_out);

    }
}
