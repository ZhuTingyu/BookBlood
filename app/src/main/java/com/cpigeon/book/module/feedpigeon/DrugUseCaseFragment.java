package com.cpigeon.book.module.feedpigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.RxUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

/**
 * Created by Zhu TingYu on 2018/9/8.
 */

public class DrugUseCaseFragment extends BaseBookFragment {

    private LineInputListLayout mLlRoot;
    private LineInputView mLvIllnessRecord;
    private LineInputView mLvDrugName;
    private LineInputView mLvDrugUseTime;
    private LineInputView mLvRecordTime;
    private LineInputView mLvDrugAfterStatus;
    private LineInputView mLvIsHaveAfterResult;
    private LineInputView mLvBodyTemp;
    private LineInputView mLvWeather;
    private LineInputView mLvTemp;
    private LineInputView mLvWindAngle;
    private LineInputView mLvHumidity;
    private InputBoxView mInputVaccineReason;
    private TextView mTvOk;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_drug_use_case, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLlRoot = findViewById(R.id.llRoot);
        mLvIllnessRecord = findViewById(R.id.lvIllnessRecord);
        mLvDrugName = findViewById(R.id.lvDrugName);
        mLvDrugUseTime = findViewById(R.id.lvDrugUseTime);
        mLvRecordTime = findViewById(R.id.lvRecordTime);
        mLvDrugAfterStatus = findViewById(R.id.lvDrugAfterStatus);
        mLvIsHaveAfterResult = findViewById(R.id.lvIsHaveAfterResult);
        mLvBodyTemp = findViewById(R.id.lvBodyTemp);
        mLvWeather = findViewById(R.id.lvWeather);
        mLvTemp = findViewById(R.id.lvTemp);
        mLvWindAngle = findViewById(R.id.lvWindAngle);
        mLvHumidity = findViewById(R.id.lvHumidity);
        mInputVaccineReason = findViewById(R.id.inputVaccineReason);
        mTvOk = findViewById(R.id.tvOk);
        composite.add(RxUtils.delayed(50, aLong -> {
            mLlRoot.setLineInputViewState(false);
        }));
    }
}
