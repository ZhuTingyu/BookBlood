package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.base.http.ApiResponse;
import com.base.util.IntentBuilder;
import com.base.util.dialog.DialogUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.InputPigeonFragment;

/**
 * Created by Zhu TingYu on 2018/11/8.
 */

public class OffspringAddFragment2 extends InputPigeonFragment {

    public static void start(Activity activity, String nestTimeId, PairingInfoEntity mPairingInfoEntity, int requestCode) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPairingInfoEntity)
                .putExtra(IntentBuilder.KEY_DATA_2, nestTimeId)
                .startParentActivity(activity, OffspringAddFragment2.class, requestCode);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.str_offspring_add);

        mViewModel.mPairingInfoEntity = (PairingInfoEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);


        if (mViewModel.mPairingInfoEntity != null) {
            mLvFatherFoot.setContent(mViewModel.mPairingInfoEntity.getMenFootRingNum());
            mLvMotherFoot.setContent(mViewModel.mPairingInfoEntity.getWoFootRingNum());
            mLvFatherFootState.setContent(mViewModel.mPairingInfoEntity.getMenPigeonStateName());
            mLvMotherFootState.setContent(mViewModel.mPairingInfoEntity.getWoPigeonStateName());


            mLvFatherFoot.setRightImageVisible(false);
            mLvMotherFoot.setRightImageVisible(false);
            mLvFatherFootState.setRightImageVisible(false);
            mLvMotherFootState.setRightImageVisible(false);

            mLvFatherFoot.setEnabled(false);
            mLvMotherFoot.setEnabled(false);
            mLvFatherFootState.setEnabled(false);
            mLvMotherFootState.setEnabled(false);

            mViewModel.footFatherId = mViewModel.mPairingInfoEntity.getMenFootRingID();
            mViewModel.footMotherId = mViewModel.mPairingInfoEntity.getWoFootRingID();

            mViewModel.pigeonFatherId = mViewModel.mPairingInfoEntity.getMenPigeonID();
            mViewModel.pigeonMotherId = mViewModel.mPairingInfoEntity.getWoPigeonID();

            mViewModel.footMother=mViewModel.mPairingInfoEntity.getWoFootRingNum();
            mViewModel.footFather=mViewModel.mPairingInfoEntity.getMenFootRingNum();

            mViewModel.pigeonFatherStateId = mViewModel.mPairingInfoEntity.getMenPigeonStateID();
            mViewModel.pigeonMotherStateId = mViewModel.mPairingInfoEntity.getWoPigeonStateID();
        }
    }

    @Override
    protected void mDataPigeonResult(ApiResponse<PigeonEntity> datas) {

        PigeonEntity o = datas.getData();

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
