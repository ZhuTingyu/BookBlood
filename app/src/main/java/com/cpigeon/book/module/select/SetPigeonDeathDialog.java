package com.cpigeon.book.module.select;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.picker.PickerUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.select.viewmodel.SetPigeonDeathViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputView;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by Zhu TingYu on 2018/10/15.
 */

public class SetPigeonDeathDialog extends BaseDialogFragment {

    private LineInputView mLvDeathReason;
    private TextView mTvCancel;
    private TextView mTvOk;
    private TextView mTvDirectUse;

    SetPigeonDeathViewModel mViewModel;
    SelectTypeViewModel mSelectTypeViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new SetPigeonDeathViewModel();
        mSelectTypeViewModel = new SelectTypeViewModel();
        initViewModel(mViewModel);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_set_pigeon_death;
    }

    @Override
    protected void initView(Dialog dialog) {

        if (getArguments() != null) {
            FootEntity footEntity = getArguments().getParcelable(IntentBuilder.KEY_DATA);
            mViewModel.mFootEntity = footEntity;
        }

        mLvDeathReason = dialog.findViewById(R.id.lvDeathReason);
        RxUtils.delayed(100, aLong -> {
            mLvDeathReason.setIsLookState(true);
        });
        mTvCancel = dialog.findViewById(R.id.tvCancel);
        mTvOk = dialog.findViewById(R.id.tvOk);
        mTvDirectUse = dialog.findViewById(R.id.tvDirectUse);


        mLvDeathReason.setOnRightClickListener(lineInputView -> {
            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mDeathReason)
                    , 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mViewModel.deathId = mViewModel.mDeathReason.get(index).getTypeID();
                            mLvDeathReason.setRightText(mViewModel.mDeathReason.get(index).getTypeName());
                            mViewModel.isCanCommit();
                        }
                    });
        });

        mTvOk.setOnClickListener(v -> {
            getBaseActivity().setProgressVisible(true);
            mViewModel.setPigeonDeath();
        });

        mTvDirectUse.setOnClickListener(v -> {
            if(mOnPigeonDeathClickListener != null){
                PigeonEntity pigeonEntity = new PigeonEntity();
                pigeonEntity.setPigeonID(mViewModel.mFootEntity.getPigeonID());
                pigeonEntity.setFootRingID(String.valueOf(mViewModel.mFootEntity.getFootRingID()));
                mOnPigeonDeathClickListener.userFootRing(pigeonEntity);
            }
        });

        mTvCancel.setOnClickListener(v -> {
            dismiss();
        });
        mSelectTypeViewModel.getDeathReason();

        mViewModel.normalResult.observe(this, s -> {
            getBaseActivity().setProgressVisible(false);
            dismiss();
            if (mOnPigeonDeathClickListener != null) {
                mOnPigeonDeathClickListener.setDeathFinish(mViewModel.mFootEntity);
            } else {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, mViewModel.mFootEntity)
                        .finishForResult(getBaseActivity());
            }
        });

        mViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(mTvOk, aBoolean);
        });

        mSelectTypeViewModel.mDeathReason.observe(this, selectTypeEntities -> {
            mViewModel.mDeathReason = selectTypeEntities;
        });

        mViewModel.isCanCommit();

    }

    public static void show(FragmentManager fragmentManager, String footId, String pigeonId, OnPigeonDeathClickListener listener) {
        SetPigeonDeathDialog setPigeonDeathDialog = new SetPigeonDeathDialog();
        Bundle bundle = new Bundle();
        FootEntity footEntity = new FootEntity();
        footEntity.setFootRingID(Integer.parseInt(footId));
        footEntity.setPigeonID(pigeonId);
        bundle.putParcelable(IntentBuilder.KEY_DATA, footEntity);
        setPigeonDeathDialog.setArguments(bundle);
        setPigeonDeathDialog.setOnPigeonDeathClickListener(listener);
        setPigeonDeathDialog.show(fragmentManager);
    }

    public static void show(FragmentManager fragmentManager, FootEntity pigeonEntity) {
        SetPigeonDeathDialog setPigeonDeathDialog = new SetPigeonDeathDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentBuilder.KEY_DATA, pigeonEntity);
        setPigeonDeathDialog.setArguments(bundle);
        setPigeonDeathDialog.show(fragmentManager);
    }

    public interface OnPigeonDeathClickListener {
        void userFootRing(PigeonEntity entity);
        void setDeathFinish(FootEntity footEntity);
    }

    private OnPigeonDeathClickListener mOnPigeonDeathClickListener;

    public void setOnPigeonDeathClickListener(OnPigeonDeathClickListener onPigeonDeathClickListener) {
        mOnPigeonDeathClickListener = onPigeonDeathClickListener;
    }
}
