package com.cpigeon.book.module.feedpigeon.childfragment;

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
import com.base.util.RxUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.map.LocationLiveData;
import com.base.util.map.WeatherLiveData;
import com.base.util.picker.PickerUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.model.entity.StatusIllnessRecordEntity;
import com.cpigeon.book.module.feedpigeon.viewmodel.DrugUseCaseViewModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 用药情况
 * Created by Zhu TingYu on 2018/9/8.
 */

public class DrugUseCaseFragment extends BaseBookFragment {

    public static final int CODE_ILLNESS_RECORD = 0x123;
    //    @BindView(R.id.lvIllnessRecord)
//    LineInputView lvIllnessRecord;
    @BindView(R.id.lvDrugName)
    LineInputView lvDrugName;
    @BindView(R.id.lvDrugUseTime)
    LineInputView lvDrugUseTime;
    @BindView(R.id.lvDiseaseName)
    LineInputView lvDiseaseName;
    //    @BindView(R.id.lvRecordTime)
//    LineInputView lvRecordTime;
    @BindView(R.id.lvDrugAfterStatus)
    LineInputView lvDrugAfterStatus;
    //    @BindView(R.id.lvIsHaveAfterResult)
//    LineInputView lvIsHaveAfterResult;
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

        mDrugUseCaseViewModel.setmBaseFragment(this);
    }

    public static void start(Activity activity, PigeonEntity mPigeonEntity, FeedPigeonEntity mFeedPigeonEntity, int type) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                .putExtra(IntentBuilder.KEY_DATA_2, mFeedPigeonEntity)
                .putExtra(IntentBuilder.KEY_TYPE, type)//类型
                .startParentActivity(activity, DrugUseCaseFragment.class);
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

        mDrugUseCaseViewModel.mFeedPigeonEntity = getBaseActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA_2);
        mDrugUseCaseViewModel.typePag = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE, 0);
        if (mDrugUseCaseViewModel.typePag == 1) {
            //修改  删除
            setTitle(getString(R.string.str_drug_use_case_details));

            setProgressVisible(true);
            mDrugUseCaseViewModel.getTXGP_PigeonDrug_SelectData();
            tvOk.setVisibility(View.GONE);

            setToolbarRight(getString(R.string.text_delete), item -> {
                //删除
                getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), getString(R.string.text_delete_warning_hint), sweetAlertDialog -> {
                    //确定
                    sweetAlertDialog.dismiss();
                    setProgressVisible(true);
                    mDrugUseCaseViewModel.getTXGP_PigeonDrug_DeleteData();
                }, sweetAlertDialog -> {
                    //取消
                    sweetAlertDialog.dismiss();
                });

                return true;
            });
        }

        inputRemark.getEditText().setCanEdit(false);//不可编辑
        mSelectTypeViewModel.getSelectTypem_Medicate();//获取用药后的状态

        mSelectTypeViewModel.getLiness_Name();//病症名称
        mSelectTypeViewModel.getDrug_Name();//药品名称
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

        if (mDrugUseCaseViewModel.typePag == 0) {
            LocationLiveData.get(true).observe(this, aMapLocation -> {
                WeatherLiveData.get(aMapLocation.getCity()).observe(this, localWeatherLive -> {
                    mDrugUseCaseViewModel.weather = localWeatherLive.getWeather();//天气
                    mDrugUseCaseViewModel.temper = localWeatherLive.getTemperature();//气温
                    mDrugUseCaseViewModel.hum = localWeatherLive.getHumidity();//湿度
                    mDrugUseCaseViewModel.dir = localWeatherLive.getWindDirection();//风向
                });
            });
        }

        //用药后的状态
        mSelectTypeViewModel.mSelectType_Medicate.observe(this, selectTypeEntities -> {
            setProgressVisible(false);
            mDrugUseCaseViewModel.mSelectTypes_drugAfterStatus = selectTypeEntities;
        });

        mDrugUseCaseViewModel.mDrugUseCaseDetails.observe(this, datas -> {
            setProgressVisible(false);

//            mDrugUseCaseViewModel.illnessRecord = String.valueOf(datas.getPigeonDiseaseID());//病情记录id
            mDrugUseCaseViewModel.illnessName = datas.getPigeonDiseaseName();//疾病名称
            mDrugUseCaseViewModel.drugName = String.valueOf(datas.getDrugNameID());//药品名称
            mDrugUseCaseViewModel.drugUseTime = datas.getUseDrugTime();//用药日期
//            mDrugUseCaseViewModel.recordTime = datas.getRecordTime();//记录日期
            mDrugUseCaseViewModel.drugAfterStatus = String.valueOf(datas.getEffectStateID());//用药后状态
//            mDrugUseCaseViewModel.isHaveAfterResult = String.valueOf(datas.getBitEffect());//有无副作用
            mDrugUseCaseViewModel.bodyTemp = datas.getBodytemper();
            mDrugUseCaseViewModel.remark = datas.getRemark();//备注


            lvDiseaseName.setRightText(mDrugUseCaseViewModel.illnessName);//疾病名称
//            lvIllnessRecord.setContent(datas.getPigeonDiseaseName());//病情记录
            lvDrugName.setRightText(datas.getPigeonDrugName());//药品名称
            lvDrugUseTime.setContent(mDrugUseCaseViewModel.drugUseTime);//用药日期
//            lvRecordTime.setContent(mDrugUseCaseViewModel.recordTime);//记录日期
            lvDrugAfterStatus.setContent(datas.getEffectStateName());//用药后状态
//            if (datas.getBitEffect() == 1) {
//                lvIsHaveAfterResult.setContent(Utils.getString(R.string.text_side_effects_y));//有副作用
//            } else {
//                lvIsHaveAfterResult.setContent(Utils.getString(R.string.text_side_effects_n));//无副作用
//            }
            lvBodyTemp.setRightText(mDrugUseCaseViewModel.bodyTemp);//体温
            inputRemark.setText(mDrugUseCaseViewModel.remark);//备注


            mDrugUseCaseViewModel.weather = datas.getWeather();//天气
            mDrugUseCaseViewModel.temper = datas.getTemperature();//气温
            mDrugUseCaseViewModel.hum = datas.getHumidity();//湿度
            mDrugUseCaseViewModel.dir = datas.getDirection();//风向

        });

        mSelectTypeViewModel.mLinessName.observe(this, datas -> {
            //疾病名称
            mDrugUseCaseViewModel.mIllnessNameData = datas;
        });

        mSelectTypeViewModel.mDrugNameData.observe(this, datas -> {
            //药品名称
            mDrugUseCaseViewModel.mDrugNameData = datas;
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            StatusIllnessRecordEntity mStatusIllnessRecordEntity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
//            mDrugUseCaseViewModel.illnessRecord = String.valueOf(mStatusIllnessRecordEntity.getPigeonDiseaseId());

//            lvIllnessRecord.setContent(mStatusIllnessRecordEntity.getPigeonDiseaseName());
            mDrugUseCaseViewModel.isCanCommit();
        }
    }

    private BaseInputDialog mInputDialog;

    @OnClick({R.id.lvDiseaseName, R.id.lvDrugName, R.id.lvDrugUseTime, R.id.lvDrugAfterStatus, R.id.lvBodyTemp, R.id.lvWeather, R.id.lvTemp, R.id.lvWindAngle, R.id.lvHumidity, R.id.inputRemark, R.id.llRoot, R.id.scrollView, R.id.tvOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.lvIllnessRecord:
//                //病情记录
//                IntentBuilder.Builder()
//                        .putExtra(IntentBuilder.KEY_DATA, mDrugUseCaseViewModel.mPigeonEntity)
//                        .startParentActivity(getBaseActivity(), SelectIllnessRecordFragment.class, CODE_ILLNESS_RECORD);
//
//                break;

            case R.id.lvDiseaseName:
                //疾病名称
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_illness_name, lvDiseaseName.getContent(), 0, content -> {
                            mDrugUseCaseViewModel.illnessName = content;
                            lvDiseaseName.setRightText(content);
                            mDrugUseCaseViewModel.isCanCommit();
                            mInputDialog.hide();
                        }, () -> {
                            if (!Lists.isEmpty(mDrugUseCaseViewModel.mIllnessNameData)) {
                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mDrugUseCaseViewModel.mIllnessNameData), 0, new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index, String item) {
                                        mDrugUseCaseViewModel.illnessName = mDrugUseCaseViewModel.mIllnessNameData.get(index).getTypeName();
                                        lvDiseaseName.setRightText(mDrugUseCaseViewModel.mIllnessNameData.get(index).getTypeName());
                                        mDrugUseCaseViewModel.isCanCommit();
                                        mInputDialog.hide();
                                    }
                                });
                            } else {
                                mSelectTypeViewModel.getLiness_Name();
                            }
                        });

                break;
            case R.id.lvDrugName:
                //药品名称
                if (!Lists.isEmpty(mDrugUseCaseViewModel.mDrugNameData)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mDrugUseCaseViewModel.mDrugNameData), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mDrugUseCaseViewModel.drugName = mDrugUseCaseViewModel.mDrugNameData.get(index).getTypeID();
                            lvDrugName.setRightText(mDrugUseCaseViewModel.mDrugNameData.get(index).getTypeName());
                            mDrugUseCaseViewModel.isCanCommit();
                        }
                    });
                } else {
                    mSelectTypeViewModel.getDrug_Name();
                }

//                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
//                        , R.string.tv_drug_name, lvDrugName.getContent(), InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
//                            mDrugUseCaseViewModel.drugName = content;
//                            lvDrugName.setRightText(content);
//                            mDrugUseCaseViewModel.isCanCommit();
//                            mInputDialog.hide();
//                        }, null);
                break;
            case R.id.lvDrugUseTime:
                //用药日期
                PickerUtil.showTimeYMD(getActivity(), new Date().getTime(), (year, monthOfYear, dayOfMonth) -> {
                    lvDrugUseTime.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
                    mDrugUseCaseViewModel.drugUseTime = year + "-" + monthOfYear + "-" + dayOfMonth;
                    mDrugUseCaseViewModel.isCanCommit();
                });
                break;
//            case R.id.lvRecordTime:
//                //记录日期
//                PickerUtil.showTimeYMD(getActivity(), new Date().getTime(), (year, monthOfYear, dayOfMonth) -> {
//                    lvRecordTime.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
//                    mDrugUseCaseViewModel.recordTime = year + "-" + monthOfYear + "-" + dayOfMonth;
//                    mDrugUseCaseViewModel.isCanCommit();
//                });
//                break;
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
                    mSelectTypeViewModel.getSelectTypem_Medicate();//获取用药后的状态
                }
                break;
//            case R.id.lvIsHaveAfterResult:
//                //是否副作用
//
//                String[] chooseWays = getResources().getStringArray(R.array.array_side_effects);
//                BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
//                    String way = chooseWays[p];
//                    if (Utils.getString(R.string.text_side_effects_y).equals(way)) {
//                        //有副作用
//                        mDrugUseCaseViewModel.isHaveAfterResult = "1";
//                    } else if (Utils.getString(R.string.text_side_effects_n).equals(way)) {
//                        //无副作用
//                        mDrugUseCaseViewModel.isHaveAfterResult = "2";
//                    }
//
//                    lvIsHaveAfterResult.setContent(way);
//                    mDrugUseCaseViewModel.isCanCommit();
//                });
//
//                break;
            case R.id.lvBodyTemp:
                //体温
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_body_temperature, lvBodyTemp.getContent(), InputType.TYPE_CLASS_NUMBER, content -> {
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
                        , R.string.tv_input_remark, inputRemark.getText(), InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
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
                setProgressVisible(true);
                mDrugUseCaseViewModel.getTXGP_PigeonDrug_AddData();
                break;
        }
    }
}
