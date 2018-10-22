package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.picker.SelectTimeHaveHMSDialog;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.FlyBackAddRecordEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.viewmodel.AddFlyBackRecordViewModel;
import com.cpigeon.book.util.MathUtil;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class AddFlyBackRecordFragment extends BaseBookFragment {

    private static final int CODE_FOOT_NUMBER = 0x123;

    private LineInputListLayout mLlRoot;
    private LineInputView mLvFoot;
    private LineInputView mLvTime;
    private LineInputView mLvSpeed;
    private TextView mTvOk;

    AddFlyBackRecordViewModel mViewModel;

    public static void start(Activity activity, TrainEntity entity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, entity)
                .startParentActivity(activity, AddFlyBackRecordFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_homing_record, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_add_homing_recode);
        mLlRoot = findViewById(R.id.llRoot);
        mLvFoot = findViewById(R.id.lvFoot);
        mLvTime = findViewById(R.id.lvTime);
        mLvSpeed = findViewById(R.id.lvSpeed);
        mTvOk = findViewById(R.id.tvOk);

        composite.add(RxUtils.delayed(50, aLong -> {
            mLlRoot.setLineInputViewState(false);
        }));

        bindUi(RxUtils.textChanges(mLvTime.getEditText()), s -> {

            if(!StringUtil.isStringValid(s)){
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(TimeUtil.format(System.currentTimeMillis(), TimeUtil.FORMAT_YYYYMMDD));
            sb.append(" ");
            sb.append(s);
            mViewModel.endTime = sb.toString();

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
                mLvSpeed.setRightText(Utils.getString(R.string.text_speed_content, speed));
            }

            mViewModel.isCanCommit();
        });

        mLvFoot.setOnClickListener(v -> {
            SearchPigeonToFlyBackActivity.start(getBaseActivity(), mViewModel.mTrainEntity);
        });

        mLvTime.setOnClickListener(v -> {
            SelectTimeHaveHMSDialog dialog = new SelectTimeHaveHMSDialog();
            dialog.setOnTimeSelectListener((hours, minute, second, time) -> {
                mLvTime.setRightText(Utils.getString(R.string.text_time_h_m_s, hours, minute, second));
            });
            dialog.show(getBaseActivity().getSupportFragmentManager());
        });

        mTvOk.setOnClickListener(v -> {
            setProgressVisible(true);
            mViewModel.addFlyBackRecord();
        });

    }

    @Override
    protected void initObserve() {
        mViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(mTvOk, aBoolean);
        });

        mViewModel.normalResult.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new FlyBackAddRecordEvent());
                finish();
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == CODE_FOOT_NUMBER) {
            PigeonEntity pigeonEntity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            mLvFoot.setRightText(pigeonEntity.getFootRingNum());
            mViewModel.footId = pigeonEntity.getFootRingID();
            mViewModel.pigeonId = pigeonEntity.getPigeonID();
        }
    }
}
