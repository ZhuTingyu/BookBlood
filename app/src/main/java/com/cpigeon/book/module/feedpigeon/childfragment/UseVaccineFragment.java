package com.cpigeon.book.module.feedpigeon.childfragment;

import android.app.Activity;
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
import com.cpigeon.book.module.feedpigeon.viewmodel.UseVaccineViewModel;
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
 * 疫苗注射
 * Created by Zhu TingYu on 2018/9/8.
 */

public class UseVaccineFragment extends BaseBookFragment {

    @BindView(R.id.lvVaccine)
    LineInputView lvVaccine;
    @BindView(R.id.lvTime)
    LineInputView lvTime;
    @BindView(R.id.lvWeather)
    LineInputView lvWeather;
    @BindView(R.id.lvTemp)
    LineInputView lvTemp;
    @BindView(R.id.lvWindAngle)
    LineInputView lvWindAngle;
    @BindView(R.id.lvHumidity)
    LineInputView lvHumidity;
    @BindView(R.id.lvBodyTemp)
    LineInputView lvBodyTemp;//体温（舍弃）
    @BindView(R.id.lvVaccineReason)
    LineInputView lvVaccineReason;//注射原因
    //    @BindView(R.id.inputVaccineReason)
//    InputBoxView inputVaccineReason;
    @BindView(R.id.inputRemark)
    InputBoxView inputRemark;
    @BindView(R.id.llRoot)
    LineInputListLayout llRoot;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.tvOk)
    TextView tvOk;

    private UseVaccineViewModel mUseVaccineViewModel;
    private SelectTypeViewModel mSelectTypeViewModel;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUseVaccineViewModel = new UseVaccineViewModel();
        mUseVaccineViewModel.setmBaseFragment(this);
        mSelectTypeViewModel = new SelectTypeViewModel();
        initViewModels(mUseVaccineViewModel, mSelectTypeViewModel);
    }


    public static void start(Activity activity, PigeonEntity mPigeonEntity, FeedPigeonEntity mFeedPigeonEntity, int type) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                .putExtra(IntentBuilder.KEY_DATA_2, mFeedPigeonEntity)
                .putExtra(IntentBuilder.KEY_TYPE, type)//类型
                .startParentActivity(activity, UseVaccineFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_use_vaccine, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUseVaccineViewModel.mPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

        mUseVaccineViewModel.mFeedPigeonEntity = getBaseActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA_2);
        mUseVaccineViewModel.typePag = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE, 0);

        if (mUseVaccineViewModel.typePag == 1) {
            //修改  删除
            setTitle(getString(R.string.str_use_vaccine_details));
            setProgressVisible(true);
            mUseVaccineViewModel.getTXGP_PigeonVaccine_Select();
            tvOk.setVisibility(View.GONE);

            setToolbarRight(getString(R.string.text_delete), item -> {
                //删除
                getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), getString(R.string.text_delete_warning_hint), sweetAlertDialog -> {
                    //确定
                    sweetAlertDialog.dismiss();
                    setProgressVisible(true);
                    mUseVaccineViewModel.getTXGP_PigeonVaccine_DeleteData();
                }, sweetAlertDialog -> {
                    //取消
                    sweetAlertDialog.dismiss();
                });

                return true;
            });
        }

//        inputVaccineReason.getEditText().setCanEdit(false);//不可编辑
        inputRemark.getEditText().setCanEdit(false);//不可编辑

        mSelectTypeViewModel.getVaccineReason();//疫苗注射原因
        mSelectTypeViewModel.getVaccineName();//疫苗名称

    }

    @Override
    protected void initObserve() {
        super.initObserve();
        composite.add(RxUtils.delayed(50, aLong -> {
            llRoot.setLineInputViewState(false);
        }));
        mUseVaccineViewModel.isCanCommit();

        mUseVaccineViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvOk, aBoolean);
        });

        if (mUseVaccineViewModel.typePag == 0) {
            //添加才定位
            LocationLiveData.get(true).observe(this, aMapLocation -> {
                WeatherLiveData.get(aMapLocation.getCity()).observe(this, localWeatherLive -> {
                    mUseVaccineViewModel.weather = localWeatherLive.getWeather();//天气
                    mUseVaccineViewModel.temper = localWeatherLive.getTemperature();//气温
                    mUseVaccineViewModel.hum = localWeatherLive.getHumidity();//湿度
                    mUseVaccineViewModel.dir = localWeatherLive.getWindDirection();//风向
                });
            });
        }

        //详情
        mUseVaccineViewModel.mUseVaccineDetails.observe(this, datas -> {
            setProgressVisible(false);
            mUseVaccineViewModel.vaccineName = datas.getPigeonViccineName();//疫苗名称
            mUseVaccineViewModel.vaccineNameId = datas.getViccineNameID();//疫苗名称id
            mUseVaccineViewModel.injectionTiem = datas.getUseViccineTime();//注射日期
            mUseVaccineViewModel.bodyTemperature = datas.getBodyTemper();//体温
            mUseVaccineViewModel.injectionWhy = datas.getReason();//注射原因
            mUseVaccineViewModel.injectionWhyId = datas.getReasonID();//注射原因id
            mUseVaccineViewModel.remark = datas.getRemark();//备注

            lvVaccine.setRightText(mUseVaccineViewModel.vaccineName);
            lvTime.setContent(mUseVaccineViewModel.injectionTiem);
            lvBodyTemp.setRightText(mUseVaccineViewModel.bodyTemperature);
            lvVaccineReason.setRightText(mUseVaccineViewModel.injectionWhy);
            inputRemark.setText(mUseVaccineViewModel.remark);

            mUseVaccineViewModel.weather = datas.getWeather();//天气
            mUseVaccineViewModel.temper = datas.getTemperature();//气温
            mUseVaccineViewModel.hum = datas.getHumidity();//湿度
            mUseVaccineViewModel.dir = datas.getDirection();//风向

        });

        mSelectTypeViewModel.mVaccineReason.observe(this, datas -> {
            //疫苗注射原因
            mUseVaccineViewModel.mVaccineReasonSelect = datas;
        });

        mSelectTypeViewModel.mVaccineName.observe(this, datas -> {
            //疫苗名称
            mUseVaccineViewModel.mVaccineNameSelect = datas;
        });


        mUseVaccineViewModel.mVaccineAdd.observe(this, datas -> {

            //添加成功 后回调

            mUseVaccineViewModel.vaccineName = "";//疫苗名称
            mUseVaccineViewModel.vaccineNameId = "";//疫苗名称id
            mUseVaccineViewModel.injectionTiem = "";//注射日期
            mUseVaccineViewModel.bodyTemperature = "";//体温
            mUseVaccineViewModel.injectionWhy = "";//注射原因
            mUseVaccineViewModel.injectionWhyId = "";//注射原因id
            mUseVaccineViewModel.remark = "";//备注

            lvVaccine.setRightText(mUseVaccineViewModel.vaccineName);
            lvTime.setContent(mUseVaccineViewModel.injectionTiem);
            lvBodyTemp.setRightText(mUseVaccineViewModel.bodyTemperature);
            lvVaccineReason.setRightText(mUseVaccineViewModel.injectionWhy);
            inputRemark.setText(mUseVaccineViewModel.remark);

        });
        mUseVaccineViewModel.msg.observe(this,s ->
        {
            DialogUtils.createSuccessDialog(getBaseActivity(),s,sweetAlertDialog -> {
                finish();
            });
        });
    }

    private BaseInputDialog mInputDialog;

    @OnClick({R.id.lvVaccine, R.id.lvTime, R.id.lvWeather, R.id.lvTemp, R.id.lvWindAngle, R.id.lvHumidity, R.id.lvBodyTemp, R.id.lvVaccineReason, R.id.inputRemark, R.id.llRoot, R.id.scrollView, R.id.tvOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lvVaccine:
                //疫苗名称
                if (!Lists.isEmpty(mUseVaccineViewModel.mVaccineNameSelect)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mUseVaccineViewModel.mVaccineNameSelect), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mUseVaccineViewModel.vaccineName = mUseVaccineViewModel.mVaccineNameSelect.get(index).getTypeName();
                            mUseVaccineViewModel.vaccineNameId = mUseVaccineViewModel.mVaccineNameSelect.get(index).getTypeID();
                            lvVaccine.setContent(mUseVaccineViewModel.mVaccineNameSelect.get(index).getTypeName());

                            mUseVaccineViewModel.isCanCommit();

                        }
                    });
                } else {
                    mSelectTypeViewModel.getVaccineName();
                }

//                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
//                        , R.string.tv_vaccine_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
//                            mUseVaccineViewModel.vaccineName = content;
//                            lvVaccine.setRightText(content);
//                            mInputDialog.hide();
//                            mUseVaccineViewModel.isCanCommit();
//                        }, null);

                break;
            case R.id.lvTime:
                //注射日期
                PickerUtil.showTimeYMD(getActivity(), new Date().getTime(), (year, monthOfYear, dayOfMonth) -> {
                    lvTime.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
                    mUseVaccineViewModel.injectionTiem = year + "-" + monthOfYear + "-" + dayOfMonth;
                    mUseVaccineViewModel.isCanCommit();
                });

                break;
            case R.id.lvWeather:
                break;
            case R.id.lvTemp:
                break;
            case R.id.lvWindAngle:
                break;
            case R.id.lvHumidity:
                break;
            case R.id.lvBodyTemp:
                //体温
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_body_temperature, lvBodyTemp.getContent(), InputType.TYPE_CLASS_NUMBER, content -> {
                            mUseVaccineViewModel.bodyTemperature = content;
                            lvBodyTemp.setRightText(content);
                            mInputDialog.hide();
                            mUseVaccineViewModel.isCanCommit();
                        }, null);

                break;

            case R.id.lvVaccineReason:
                //注射原因
                if (!Lists.isEmpty(mUseVaccineViewModel.mVaccineReasonSelect)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mUseVaccineViewModel.mVaccineReasonSelect), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mUseVaccineViewModel.injectionWhy = mUseVaccineViewModel.mVaccineReasonSelect.get(index).getTypeName();
                            mUseVaccineViewModel.injectionWhyId = mUseVaccineViewModel.mVaccineReasonSelect.get(index).getTypeID();
                            lvVaccineReason.setContent(mUseVaccineViewModel.mVaccineReasonSelect.get(index).getTypeName());

                            mUseVaccineViewModel.isCanCommit();
                        }
                    });
                } else {
                    mSelectTypeViewModel.getVaccineReason();
                }

//                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
//                        , R.string.tv_input_vaccine_reason, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
//                            mUseVaccineViewModel.injectionWhy = content;
//                            inputVaccineReason.getEditText().setText(content);
//                            mInputDialog.hide();
//                            mUseVaccineViewModel.isCanCommit();
//                        }, null);

                break;
            case R.id.inputRemark:
                //备注
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_input_remark, inputRemark.getText(), InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mUseVaccineViewModel.remark = content;
                            inputRemark.getEditText().setText(content);
                            mInputDialog.hide();
                            mUseVaccineViewModel.isCanCommit();
                        }, null);

                break;
            case R.id.llRoot:
                break;
            case R.id.scrollView:
                break;
            case R.id.tvOk:
                setProgressVisible(true);
                mUseVaccineViewModel.getTXGP_PigeonVaccine_AddData();
                break;
        }
    }


}
