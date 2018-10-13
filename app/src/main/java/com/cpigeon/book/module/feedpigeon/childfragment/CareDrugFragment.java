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
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.map.LocationLiveData;
import com.base.util.map.WeatherLiveData;
import com.base.util.picker.PickerUtil;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.feedpigeon.viewmodel.CareDrugViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 保健品
 * Created by Zhu TingYu on 2018/9/8.
 */

public class CareDrugFragment extends BaseBookFragment {
    @BindView(R.id.lvCareDrugName)
    LineInputView lvCareDrugName;
    @BindView(R.id.lvCareDrugFunction)
    LineInputView lvCareDrugFunction;
    @BindView(R.id.lvUserTime)
    LineInputView lvUserTime;

    @BindView(R.id.lvRecordTime)
    LineInputView lvRecordTime;

    @BindView(R.id.lvUserResult)
    LineInputView lvUserResult;
    @BindView(R.id.lvAfterResult)
    LineInputView lvAfterResult;
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

//    private LineInputListLayout mLlRoot;
//    private LineInputView lvCareDrugName;
//    private LineInputView mLvCareDrugFunction;
//    private LineInputView mLvUserTime;
//    private LineInputView mLvUserResult;
//    private LineInputView mLvAfterResult;
//    private LineInputView mLvWeather;
//    private LineInputView mLvTemp;
//    private LineInputView mLvWindAngle;
//    private LineInputView mLvHumidity;
//    private InputBoxView mInputVaccineReason;
//    private TextView mTvOk;


    private CareDrugViewModel mCareDrugViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCareDrugViewModel = new CareDrugViewModel();
        initViewModels(mCareDrugViewModel);
        mCareDrugViewModel.setmBaseFragment(this);
    }

    public static void start(Activity activity, PigeonEntity mPigeonEntity, FeedPigeonEntity mFeedPigeonEntity, int type) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                .putExtra(IntentBuilder.KEY_DATA_2, mFeedPigeonEntity)
                .putExtra(IntentBuilder.KEY_TYPE, type)//类型
                .startParentActivity(activity, CareDrugFragment.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_care_drug, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        mLlRoot = findViewById(R.id.llRoot);
//        lvCareDrugName = findViewById(R.id.lvCareDrugName);
//        mLvCareDrugFunction = findViewById(R.id.lvCareDrugFunction);
//        mLvUserTime = findViewById(R.id.lvUserTime);
//        mLvUserResult = findViewById(R.id.lvUserResult);
//        mLvAfterResult = findViewById(R.id.lvAfterResult);
//        mLvWeather = findViewById(R.id.lvWeather);
//        mLvTemp = findViewById(R.id.lvTemp);
//        mLvWindAngle = findViewById(R.id.lvWindAngle);
//        mLvHumidity = findViewById(R.id.lvHumidity);
//        mInputVaccineReason = findViewById(R.id.inputVaccineReason);
//        mTvOk = findViewById(R.id.tvOk);


        mCareDrugViewModel.mPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

        mCareDrugViewModel.mFeedPigeonEntity = getBaseActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA_2);
        mCareDrugViewModel.typePag = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE, 0);

        if (mCareDrugViewModel.typePag == 1) {
            //修改  删除

            setTitle(getString(R.string.str_care_drug_details));


            setProgressVisible(true);
            mCareDrugViewModel.getTXGP_PigeonHealth_SelectData();
            tvOk.setVisibility(View.GONE);

            setToolbarRight(getString(R.string.text_delete), item -> {
                //删除
                getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), getString(R.string.text_delete_warning_hint), sweetAlertDialog -> {
                    //确定
                    sweetAlertDialog.dismiss();
                    setProgressVisible(true);
                    mCareDrugViewModel.getTXGP_PigeonHealth_DeleteData();
                }, sweetAlertDialog -> {
                    //取消
                    sweetAlertDialog.dismiss();
                });

                return true;
            });
        }

        inputRemark.getEditText().setCanEdit(false);//不可编辑
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        composite.add(RxUtils.delayed(50, aLong -> {
            llRoot.setLineInputViewState(false);
        }));

        mCareDrugViewModel.isCanCommit();

        mCareDrugViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvOk, aBoolean);
        });

        if (mCareDrugViewModel.typePag == 0) {
            LocationLiveData.get(true).observe(this, aMapLocation -> {
                WeatherLiveData.get(aMapLocation.getCity()).observe(this, localWeatherLive -> {
                    mCareDrugViewModel.weather = localWeatherLive.getWeather();//天气
                    mCareDrugViewModel.temper = localWeatherLive.getTemperature();//气温
                    mCareDrugViewModel.hum = localWeatherLive.getHumidity();//湿度
                    mCareDrugViewModel.dir = localWeatherLive.getWindDirection();//风向
                });
            });
        }


        mCareDrugViewModel.mCareDrugDetails.observe(this, datas -> {
            setProgressVisible(false);

            mCareDrugViewModel.careDrugName = datas.getPigeonHealthName();//保健品名称
            mCareDrugViewModel.careDrugFunction = datas.getPigeonHealthType();//保健品功能
            mCareDrugViewModel.useTime = datas.getUseHealthTime();//使用时间
            mCareDrugViewModel.recordTime = datas.getRecordTime();//记录时间
            mCareDrugViewModel.useEffect = datas.getUseEffect();//使用效果id
            mCareDrugViewModel.isHaveAfterResult = datas.getBitEffect();//副作用
            mCareDrugViewModel.bodyTemp = datas.getBodytemper();//体温


            lvCareDrugName.setRightText(mCareDrugViewModel.careDrugName);//保健品名称
            lvCareDrugFunction.setRightText(mCareDrugViewModel.careDrugFunction);//保健品功能
            lvUserTime.setContent(mCareDrugViewModel.useTime);//使用时间
            lvRecordTime.setContent(mCareDrugViewModel.recordTime);//记录时间

            if (mCareDrugViewModel.useEffect.equals("1")) {
                lvUserResult.setContent(Utils.getString(R.string.text_use_effect_y));//有效果
            } else {
                lvUserResult.setContent(Utils.getString(R.string.text_use_effect_n));//无效果
            }

            if (mCareDrugViewModel.isHaveAfterResult.equals("1")) {
                lvAfterResult.setContent(Utils.getString(R.string.text_side_effects_y));//有副作用
            } else {
                lvAfterResult.setContent(Utils.getString(R.string.text_side_effects_n));//无副作用
            }

            lvBodyTemp.setRightText(mCareDrugViewModel.bodyTemp);//体温

            mCareDrugViewModel.weather = datas.getWeather();//天气
            mCareDrugViewModel.temper = datas.getTemperature();//气温
            mCareDrugViewModel.hum = datas.getHumidity();//湿度
            mCareDrugViewModel.dir = datas.getDirection();//风向
        });

    }

    private BaseInputDialog mInputDialog;

    @OnClick({R.id.lvCareDrugName, R.id.lvCareDrugFunction, R.id.lvUserTime, R.id.lvRecordTime, R.id.lvUserResult, R.id.lvAfterResult, R.id.lvBodyTemp, R.id.lvWeather, R.id.lvTemp, R.id.lvWindAngle, R.id.lvHumidity, R.id.inputRemark, R.id.llRoot, R.id.tvOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lvCareDrugName:
                //保健品名称
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_care_drug_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mCareDrugViewModel.careDrugName = content;
                            lvCareDrugName.setRightText(content);
                            mInputDialog.hide();
                            mCareDrugViewModel.isCanCommit();
                        }, null);
                break;
            case R.id.lvCareDrugFunction:
                //保健品功能
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_care_drug_function, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mCareDrugViewModel.careDrugFunction = content;
                            lvCareDrugFunction.setRightText(content);
                            mCareDrugViewModel.isCanCommit();
                            mInputDialog.hide();
                        }, null);

                break;
            case R.id.lvUserTime:
                //使用时间
                PickerUtil.showTimeYMD(getActivity(), new Date().getTime(), ( year, monthOfYear, dayOfMonth) -> {
                    lvUserTime.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
                    mCareDrugViewModel.useTime = year + "-" + monthOfYear + "-" + dayOfMonth;
                    mCareDrugViewModel.isCanCommit();
                });
                break;

            case R.id.lvRecordTime:
                //记录时间
                PickerUtil.showTimeYMD(getActivity(), new Date().getTime(), ( year, monthOfYear, dayOfMonth) -> {
                    lvRecordTime.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
                    mCareDrugViewModel.recordTime = year + "-" + monthOfYear + "-" + dayOfMonth;
                    mCareDrugViewModel.isCanCommit();
                });
                break;
            case R.id.lvUserResult:
                //使用效果 useEffect
                String[] chooseWays2 = getResources().getStringArray(R.array.array_use_effect);
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays2), p -> {
                    String way = chooseWays2[p];
                    if (Utils.getString(R.string.text_use_effect_y).equals(way)) {
                        //有效果
                        mCareDrugViewModel.useEffect = "1";
                    } else if (Utils.getString(R.string.text_use_effect_n).equals(way)) {
                        //无效果
                        mCareDrugViewModel.useEffect = "2";
                    }
                    lvUserResult.setContent(way);
                    mCareDrugViewModel.isCanCommit();
                });

                break;
            case R.id.lvAfterResult:
                //有无副作用
                String[] chooseWays = getResources().getStringArray(R.array.array_side_effects);
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                    String way = chooseWays[p];
                    if (Utils.getString(R.string.text_side_effects_y).equals(way)) {
                        //有副作用
                        mCareDrugViewModel.isHaveAfterResult = "1";
                    } else if (Utils.getString(R.string.text_side_effects_n).equals(way)) {
                        //无副作用
                        mCareDrugViewModel.isHaveAfterResult = "2";
                    }

                    lvAfterResult.setContent(way);
                    mCareDrugViewModel.isCanCommit();
                });

                break;
            case R.id.lvBodyTemp:
                //体温
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_body_temperature, InputType.TYPE_CLASS_NUMBER, content -> {
                            mCareDrugViewModel.bodyTemp = content;
                            lvBodyTemp.setRightText(content);
                            mInputDialog.hide();
                            mCareDrugViewModel.isCanCommit();
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
                            mCareDrugViewModel.remark = content;
                            inputRemark.getEditText().setText(content);
                            mInputDialog.hide();
                            mCareDrugViewModel.isCanCommit();
                        }, null);
                break;
            case R.id.llRoot:
                break;
            case R.id.tvOk:
                mCareDrugViewModel.getTXGP_PigeonHealth_AddData();
                break;
        }
    }
}
