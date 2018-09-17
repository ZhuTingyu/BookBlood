package com.cpigeon.book.module.feedpigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.map.LocationLiveData;
import com.base.util.map.WeatherLiveData;
import com.base.util.picker.PickerUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.feedpigeon.viewmodel.StatusIllnessRecordAddViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 病情记录
 * Created by Zhu TingYu on 2018/9/8.
 */

public class StatusIllnessRecordFragment extends BaseBookFragment {


    @BindView(R.id.lvIllnessName)
    LineInputView lvIllnessName;
    @BindView(R.id.lvIllnessSymptom)
    LineInputView lvIllnessSymptom;
    @BindView(R.id.lvIllTime)
    LineInputView lvIllTime;
    @BindView(R.id.lvBodyTemp)
    LineInputView lvBodyTemp;
    @BindView(R.id.lvWeather)
    LineInputView lvWeather;
    @BindView(R.id.lvTemp)
    LineInputView lvTemp;
    @BindView(R.id.lvWindAngle)
    LineInputView lvWindAngle;
    @BindView(R.id.lvHumidity)
    LineInputView lvHumidity;
    @BindView(R.id.inputRemark)
    InputBoxView inputRemark;
    @BindView(R.id.llRoot)
    LineInputListLayout llRoot;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.tvOk)
    TextView tvOk;

    private StatusIllnessRecordAddViewModel mStatusIllnessRecordAddViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStatusIllnessRecordAddViewModel = new StatusIllnessRecordAddViewModel();
        initViewModels(mStatusIllnessRecordAddViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_status_illness_record, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStatusIllnessRecordAddViewModel.mPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

    }

    @Override
    protected void initObserve() {
        super.initObserve();
        composite.add(RxUtils.delayed(50, aLong -> {
            llRoot.setLineInputViewState(false);
        }));

        mStatusIllnessRecordAddViewModel.isCanCommit();

        mStatusIllnessRecordAddViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvOk, aBoolean);
        });

        LocationLiveData.get(true).observe(this, aMapLocation -> {
            WeatherLiveData.get(aMapLocation.getCity()).observe(this, localWeatherLive -> {
                mStatusIllnessRecordAddViewModel.weather = localWeatherLive.getWeather();//天气
                mStatusIllnessRecordAddViewModel.temper = localWeatherLive.getTemperature();//气温
                mStatusIllnessRecordAddViewModel.hum = localWeatherLive.getHumidity();//湿度
                mStatusIllnessRecordAddViewModel.dir = localWeatherLive.getWindDirection();//风向
            });
        });
    }


    private BaseInputDialog mInputDialog;

    @OnClick({R.id.lvIllnessName, R.id.lvIllnessSymptom, R.id.lvIllTime, R.id.lvBodyTemp, R.id.lvWeather, R.id.lvTemp, R.id.lvWindAngle, R.id.lvHumidity, R.id.inputRemark, R.id.llRoot, R.id.tvOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lvIllnessName:
                //疾病名称
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_illness_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mStatusIllnessRecordAddViewModel.illnessName = content;
                            lvIllnessName.setRightText(content);
                            mInputDialog.hide();
                        }, null);

                break;
            case R.id.lvIllnessSymptom:
                //症状
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_illness_symptom, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mStatusIllnessRecordAddViewModel.illnessSymptom = content;
                            lvIllnessSymptom.setRightText(content);
                            mInputDialog.hide();
                        }, null);

                break;
            case R.id.lvIllTime:
                //生病日期
                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    lvIllTime.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
                    mStatusIllnessRecordAddViewModel.illnessTime = year + "-" + monthOfYear + "-" + dayOfMonth;
                    mStatusIllnessRecordAddViewModel.isCanCommit();
                });

                break;
            case R.id.lvBodyTemp:
                //体温
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_body_temperature, InputType.TYPE_CLASS_NUMBER, content -> {
                            mStatusIllnessRecordAddViewModel.bodyTemperature = content;
                            lvBodyTemp.setRightText(content);
                            mInputDialog.hide();
                            mStatusIllnessRecordAddViewModel.isCanCommit();
                        }, null);

                break;
            case R.id.lvWeather:
                break;
            case R.id.lvTemp:
                break;
            case R.id.lvWindAngle:
                break;
            case R.id.lvHumidity:
                break;
            case R.id.inputRemark:
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_input_remark, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mStatusIllnessRecordAddViewModel.remark = content;
                            inputRemark.getEditText().setText(content);
                            mInputDialog.hide();
                            mStatusIllnessRecordAddViewModel.isCanCommit();
                        }, null);


                break;
            case R.id.llRoot:
                break;
            case R.id.tvOk:
                mStatusIllnessRecordAddViewModel.getTXGP_PigeonVaccine_AddData();
                break;
        }
    }
}
