package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonEntryFragment;
import com.cpigeon.book.module.breeding.viewmodel.OffspringViewModel;
import com.cpigeon.book.util.TextViewUtil;

/**
 * 子代录入
 * Created by Administrator on 2018/9/19 0019.
 */

public class OffspringAddFragment extends BasePigeonEntryFragment {


    private OffspringViewModel mViewModel;

    public static void start(Activity activity, int requestCode, PairingInfoEntity mPairingInfoEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPairingInfoEntity)
                .startParentActivity(activity, OffspringAddFragment.class, requestCode);
    }

    @Override
    protected void onAttachs() {
        super.onAttachs();
        mBasePigeonViewModel = new OffspringViewModel();
        mViewModel = (OffspringViewModel) mBasePigeonViewModel;
        initViewModels(mSelectTypeViewModel, mViewModel);
    }


    @Override
    protected void initData() {
        super.initData();
        setTitle(getString(R.string.str_offspring_add));

//        llHangingRingDate.setVisibility(View.GONE);
        //选择子代类型
        llPigeonType.setVisibility(View.VISIBLE);
        llPigeonType.setOnClickListener(v -> {
            String[] chooseWays = getResources().getStringArray(R.array.array_pigeon_type);
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                String way = chooseWays[p];
                if (Utils.getString(R.string.text_breed_pigeon).equals(way)) {
                    //种鸽
                    llPigeonType.setContent(R.string.text_breed_pigeon);
//                    llHangingRingDate.setVisibility(View.GONE);
                    mViewModel.pigeonType = 1;

                } else if (Utils.getString(R.string.text_racing_pigeon).equals(way)) {
                    //赛鸽
                    llPigeonType.setContent(R.string.text_racing_pigeon);
//                    llHangingRingDate.setVisibility(View.VISIBLE);
                    mViewModel.pigeonType = 2;
                }
            });
        });

        //确定
        tvNextStep.setOnClickListener(v -> {
            mViewModel.addRacingPigeonEntry();
        });

        mViewModel.mPairingInfoEntity = (PairingInfoEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);


        if (mViewModel.mPairingInfoEntity != null) {
            llFootFather.setContent(mViewModel.mPairingInfoEntity.getMenFootRingNum());
            llFootMother.setContent(mViewModel.mPairingInfoEntity.getWoFootRingNum());

            llFootFather.setRightImageVisible(false);
            llFootMother.setRightImageVisible(false);

            llFootFather.setEnabled(false);
            llFootMother.setEnabled(false);
        }
    }

    @Override
    protected void btnState() {
        super.btnState();
        mViewModel.isCanCommit();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mViewModel.isCanCommit();
        mViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvNextStep, aBoolean);
        });

        mViewModel.mEntryData.observe(this, o -> {

            if (getBaseActivity().errorDialog != null && getBaseActivity().errorDialog.isShowing()) {
                getBaseActivity().errorDialog.dismiss();
            }
            String hintStr = "子代录入成功，";
            if (Integer.valueOf(o.getPigeonMoney()) > 0) {
                hintStr += "获取" + o.getPigeonMoney() + "个鸽币。";
            }
            getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), hintStr, sweetAlertDialog -> {
                //确定
                sweetAlertDialog.dismiss();
                setRequest(o);

            }, sweetAlertDialog -> {
                //取消
                sweetAlertDialog.dismiss();
                setRequest(o);
            });
        });
    }

    public void setRequest(PigeonEntity o) {
        PigeonEntity mBreedPigeonEntity = new PigeonEntity.Builder()
                .FootRingID(o.getFootRingID())
                .FootRingNum(o.getFootRingNum())
                .PigeonID(o.getPigeonID())
                .PigeonPlumeName(o.getPigeonPlumeName())
                .build();

        Intent intent = new Intent();
        intent.putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity);
        getBaseActivity().setResult(PairingNestAddFragment.requestCode, intent);
        getBaseActivity().finish();
    }
}
