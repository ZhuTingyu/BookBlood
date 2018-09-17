package com.cpigeon.book.module.feedpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.map.LocationLiveData;
import com.base.util.map.WeatherLiveData;
import com.base.util.picker.PickerUtil;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.feedpigeon.viewmodel.DrugUseCaseViewModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;
import com.luck.picture.lib.config.PictureMimeType;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 用药情况
 * Created by Zhu TingYu on 2018/9/8.
 */

public class DrugUseCaseFragment extends BaseBookFragment {

    public static final int CODE_ILLNESS_RECORD = 0x123;
    @BindView(R.id.lvIllnessRecord)
    LineInputView lvIllnessRecord;
    @BindView(R.id.lvDrugName)
    LineInputView lvDrugName;
    @BindView(R.id.lvDrugUseTime)
    LineInputView lvDrugUseTime;
    @BindView(R.id.lvRecordTime)
    LineInputView lvRecordTime;
    @BindView(R.id.lvDrugAfterStatus)
    LineInputView lvDrugAfterStatus;
    @BindView(R.id.lvIsHaveAfterResult)
    LineInputView lvIsHaveAfterResult;
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


    private DrugUseCaseViewModel mDrugUseCaseViewModel;
    private SelectTypeViewModel mSelectTypeViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mDrugUseCaseViewModel = new DrugUseCaseViewModel();
        mSelectTypeViewModel = new SelectTypeViewModel();
        initViewModels(mDrugUseCaseViewModel, mSelectTypeViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_drug_use_case, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDrugUseCaseViewModel.mPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

        mSelectTypeViewModel.getSelectTypem__Medicate();//获取用药后的状态
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        composite.add(RxUtils.delayed(50, aLong -> {
            llRoot.setLineInputViewState(false);
        }));

        mDrugUseCaseViewModel.isCanCommit();

        mDrugUseCaseViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvOk, aBoolean);
        });

        LocationLiveData.get(true).observe(this, aMapLocation -> {
            WeatherLiveData.get(aMapLocation.getCity()).observe(this, localWeatherLive -> {
                mDrugUseCaseViewModel.weather = localWeatherLive.getWeather();//天气
                mDrugUseCaseViewModel.temper = localWeatherLive.getTemperature();//气温
                mDrugUseCaseViewModel.hum = localWeatherLive.getHumidity();//湿度
                mDrugUseCaseViewModel.dir = localWeatherLive.getWindDirection();//风向
            });
        });


        //用药后的状态
        mSelectTypeViewModel.mSelectType_Pigeon_Source.observe(this, selectTypeEntities -> {
            setProgressVisible(false);
            mDrugUseCaseViewModel.mSelectTypes_drugAfterStatus = selectTypeEntities;
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            lvIllnessRecord.setRightText(data.getStringExtra(IntentBuilder.KEY_DATA));
        }
    }

    private BaseInputDialog mInputDialog;

    @OnClick({R.id.lvIllnessRecord, R.id.lvDrugName, R.id.lvDrugUseTime, R.id.lvRecordTime, R.id.lvDrugAfterStatus, R.id.lvIsHaveAfterResult, R.id.lvBodyTemp, R.id.lvWeather, R.id.lvTemp, R.id.lvWindAngle, R.id.lvHumidity, R.id.inputRemark, R.id.llRoot, R.id.scrollView, R.id.tvOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lvIllnessRecord:
                //病情记录
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA,mDrugUseCaseViewModel.mPigeonEntity)
                        .startParentActivity(getBaseActivity(), SelectIllnessRecordFragment.class, CODE_ILLNESS_RECORD);

                break;
            case R.id.lvDrugName:
                //药品名称
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_drug_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mDrugUseCaseViewModel.drugName = content;
                            lvDrugName.setRightText(content);
                            mDrugUseCaseViewModel.isCanCommit();
                            mInputDialog.hide();
                        }, null);
                break;
            case R.id.lvDrugUseTime:
                //用药日期
                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    lvDrugUseTime.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
                    mDrugUseCaseViewModel.drugUseTime = year + "-" + monthOfYear + "-" + dayOfMonth;
                    mDrugUseCaseViewModel.isCanCommit();
                });
                break;
            case R.id.lvRecordTime:
                //记录日期
                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    lvRecordTime.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
                    mDrugUseCaseViewModel.recordTime = year + "-" + monthOfYear + "-" + dayOfMonth;
                    mDrugUseCaseViewModel.isCanCommit();
                });
                break;
            case R.id.lvDrugAfterStatus:
                //用药后状态
                if (!Lists.isEmpty(mDrugUseCaseViewModel.mSelectTypes_drugAfterStatus)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mDrugUseCaseViewModel.mSelectTypes_drugAfterStatus), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mDrugUseCaseViewModel.drugAfterStatus = mDrugUseCaseViewModel.mSelectTypes_drugAfterStatus.get(index).getTypeID();
                            lvDrugAfterStatus.setContent(mDrugUseCaseViewModel.mSelectTypes_drugAfterStatus.get(index).getTypeName());
                            mDrugUseCaseViewModel.isCanCommit();
                        }
                    });
                } else {
                    mSelectTypeViewModel.getSelectTypem__Medicate();//获取用药后的状态
                }
                break;
            case R.id.lvIsHaveAfterResult:
                //是否副作用

                String[] chooseWays = getResources().getStringArray(R.array.array_side_effects);
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                    String way = chooseWays[p];
                    if (Utils.getString(R.string.text_side_effects_y).equals(way)) {
                        //有副作用
                        mDrugUseCaseViewModel.isHaveAfterResult = "1";
                    } else if (Utils.getString(R.string.text_side_effects_n).equals(way)) {
                        //无副作用
                        mDrugUseCaseViewModel.isHaveAfterResult = "2";
                    }

                    mDrugUseCaseViewModel.isCanCommit();
                });

                break;
            case R.id.lvBodyTemp:
                //体温
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_body_temperature, InputType.TYPE_CLASS_NUMBER, content -> {
                            mDrugUseCaseViewModel.bodyTemp = content;
                            lvBodyTemp.setRightText(content);
                            mInputDialog.hide();
                            mDrugUseCaseViewModel.isCanCommit();
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
                //备注
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_input_remark, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mDrugUseCaseViewModel.remark = content;
                            inputRemark.getEditText().setText(content);
                            mInputDialog.hide();
                            mDrugUseCaseViewModel.isCanCommit();
                        }, null);

                break;
            case R.id.llRoot:
                break;
            case R.id.scrollView:
                break;
            case R.id.tvOk:
                mDrugUseCaseViewModel.getTXGP_PigeonDrug_AddData();
                break;
        }
    }
}
