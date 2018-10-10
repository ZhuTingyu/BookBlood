package com.cpigeon.book.module.trainpigeon;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.event.FlyBackAddRecordEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.viewmodel.AddFlyBackRecordViewModel;
import com.cpigeon.book.util.MathUtil;
import com.cpigeon.book.widget.LineInputView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zhu TingYu on 2018/10/10.
 */

public class AddBackFlyRecordDialog extends BaseDialogFragment {

    private TextView mTvFootNumber;
    private LineInputView mLvTime;
    private LineInputView mLvDis;
    private LineInputView mLvSpeed;
    private TextView mTvCancel;
    private TextView mTvOk;
    private PigeonEntity mPigeonEntity;
    private TrainEntity mTrainEntity;
    private String mEndTime;

    AddFlyBackRecordViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new AddFlyBackRecordViewModel();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_input_back_fly_record;
    }

    @Override
    protected void initView(Dialog dialog) {

        mPigeonEntity = (PigeonEntity) getArguments().getSerializable(IntentBuilder.KEY_DATA);
        mTrainEntity = getArguments().getParcelable(IntentBuilder.KEY_DATA_2);
        mEndTime = getArguments().getString(IntentBuilder.KEY_DATA_3);

        mViewModel.footNumber = mPigeonEntity.getFootRingNum();
        mViewModel.footId = mPigeonEntity.getFootRingID();
        mViewModel.pigeonId = mPigeonEntity.getPigeonID();
        mViewModel.mTrainEntity = mTrainEntity;

        mTvFootNumber = dialog.findViewById(R.id.tvFootNumber);
        mLvTime = dialog.findViewById(R.id.lvTime);
        mLvDis = dialog.findViewById(R.id.lvDis);
        mLvSpeed = dialog.findViewById(R.id.lvSpeed);
        mTvCancel = dialog.findViewById(R.id.tvCancel);
        mTvOk = dialog.findViewById(R.id.tvOk);

        mTvFootNumber.setText(mPigeonEntity.getFootRingNum());
        mLvDis.setRightText(String.valueOf(mTrainEntity.getDistance()));
        mLvTime.setRightText(mEndTime);

        mLvTime.setOnClickListener(v -> {
            SelectTimeHaveHMSDialog selectTimeHaveHMSDialog = new SelectTimeHaveHMSDialog();
            selectTimeHaveHMSDialog.setOnTimeSelectListener((hours, minute, second, time) -> {
                mViewModel.endTime = time;
                mLvTime.setRightText(time);

                long flyTime = TimeUtil.parse(mViewModel.mTrainEntity.getFromFlyTime(), TimeUtil.FORMAT_YYYYMMDDHHMMSS);
                long backTime = TimeUtil.parse(mViewModel.endTime, TimeUtil.FORMAT_YYYYMMDDHHMMSS);

                long userTime = backTime - flyTime;

                if (userTime < 0) {
                    DialogUtils.createErrorDialog(getBaseActivity()
                            , Utils.getString(R.string.text_select_back_fly_time_error));
                } else {
                    double userTimeM = userTime / 1000 / 60;
                    String speed = MathUtil.doubleformat(mViewModel.mTrainEntity.getDistance() / userTimeM, 3);
                    mViewModel.speed = speed;
                    mLvSpeed.setRightText(Utils.getString(R.string.text_speed_content_1, speed));
                }

            });
            selectTimeHaveHMSDialog.show(getFragmentManager());
        });

        mTvCancel.setOnClickListener(v -> {
            dismiss();
        });

        mTvOk.setOnClickListener(v -> {
            getBaseActivity().setProgressVisible(true);
            mViewModel.addFlyBackRecord();
        });

        mViewModel.normalResult.observe(this, s -> {
            getBaseActivity().setProgressVisible(false);
            EventBus.getDefault().post(new FlyBackAddRecordEvent());
            dismiss();
        });

    }

    public static void show(FragmentManager fragmentManager, PigeonEntity pigeonEntity, TrainEntity trainEntity, String endTime){
        AddBackFlyRecordDialog inputBackFlyRecordDialog = new AddBackFlyRecordDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentBuilder.KEY_DATA, pigeonEntity);
        bundle.putParcelable(IntentBuilder.KEY_DATA_2, trainEntity);
        bundle.putString(IntentBuilder.KEY_DATA_3, endTime);
        inputBackFlyRecordDialog.setArguments(bundle);
        inputBackFlyRecordDialog.show(fragmentManager);
    }

}
