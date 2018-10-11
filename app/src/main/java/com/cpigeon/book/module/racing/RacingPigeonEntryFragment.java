package com.cpigeon.book.module.racing;

import android.app.Activity;

import com.base.util.IntentBuilder;
import com.base.util.dialog.DialogUtils;
import com.cpigeon.book.module.basepigeon.BasePigeonEntryFragment;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.module.racing.viewmodel.RacingPigeonEntryViewModel;
import com.cpigeon.book.util.TextViewUtil;

/**
 * 赛鸽录入
 * Created by Administrator on 2018/9/8.
 */

public class RacingPigeonEntryFragment extends BasePigeonEntryFragment {


    private RacingPigeonEntryViewModel mRacingPigeonEntryViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, RacingPigeonEntryFragment.class);
    }

    @Override
    protected void onAttachs() {
        super.onAttachs();
        mBasePigeonViewModel = new RacingPigeonEntryViewModel();
        mRacingPigeonEntryViewModel = (RacingPigeonEntryViewModel) mBasePigeonViewModel;
        initViewModels(mSelectTypeViewModel, mRacingPigeonEntryViewModel);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("赛鸽录入");
//        llHangingRingDate.setVisibility(View.VISIBLE);
//        llState.setVisibility(View.GONE);

        mRacingPigeonEntryViewModel.isCanCommit();

        tvNextStep.setOnClickListener(v -> {
            //添加确定
            mRacingPigeonEntryViewModel.addRacingPigeonEntry();
        });
    }

    @Override
    protected void btnState() {
        super.btnState();
        mRacingPigeonEntryViewModel.isCanCommit();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mRacingPigeonEntryViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvNextStep, aBoolean);
        });

        mRacingPigeonEntryViewModel.mEntryData.observe(this, o -> {

            if (getBaseActivity().errorDialog != null && getBaseActivity().errorDialog.isShowing()) {
                getBaseActivity().errorDialog.dismiss();
            }

            String hintStr = "赛鸽录入成功，";
            if (Integer.valueOf(o.getPigeonMoney()) > 0) {
                hintStr += "获取" + o.getPigeonMoney() + "个鸽币，";
            }

            hintStr += "是否为该鸽子录入赛绩！";

            getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), hintStr, sweetAlertDialog -> {
                //确定
                sweetAlertDialog.dismiss();
                PlayAddFragment.start(getBaseActivity(), o, 0);
            }, sweetAlertDialog -> {
                //取消
                sweetAlertDialog.dismiss();
            });
        });
    }
}
