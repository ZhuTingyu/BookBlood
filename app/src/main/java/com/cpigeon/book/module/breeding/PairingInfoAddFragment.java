package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.map.LocationLiveData;
import com.base.util.map.WeatherLiveData;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.LogUtil;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PriringRecommendEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.basepigeon.SelectBloodFragment;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoAddViewModel;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.select.SelectFootRingFragment;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 添加配对信息
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoAddFragment extends BaseBookFragment {


    @BindView(R.id.tv_hint_foot)
    TextView tvHintFoot;
    @BindView(R.id.img_hint_sex)
    ImageView imgHintSex;
    @BindView(R.id.ll_pairing_foot)
    LineInputView llPairingFoot;
    @BindView(R.id.ll_pairing_time)
    LineInputView llPairingTime;
    @BindView(R.id.tv_next_step)
    TextView tvNextStep;

    @BindView(R.id.ll_lineage)
    LineInputView llLineage;
    @BindView(R.id.ll_feather_color)
    LineInputView llFeatherColor;

    private PairingInfoAddViewModel mPairingInfoAddViewModel;
    protected SelectTypeViewModel mSelectTypeViewModel;

    public static void start(Activity activity, PigeonEntity mBreedPigeonEntity, PriringRecommendEntity item) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity)
                .putExtra(IntentBuilder.KEY_DATA_2, item)
                .startParentActivity(activity, PairingInfoAddFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPairingInfoAddViewModel = new PairingInfoAddViewModel();
        mSelectTypeViewModel = new SelectTypeViewModel();
        initViewModels(mPairingInfoAddViewModel, mSelectTypeViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pairing_info_add, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(getString(R.string.array_pairing_add));

        setToolbarRight(getString(R.string.array_pairing_recommend), item -> {
            PairingInfoRecommendFragment.start(getBaseActivity(), mPairingInfoAddViewModel.mPigeonEntity);
            return true;
        });

        mPairingInfoAddViewModel.mPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
        PriringRecommendEntity item = (PriringRecommendEntity) getBaseActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA_2);

        if (item != null) {
            //足环号
            llPairingFoot.setContent(item.getFootRingNum());
            mPairingInfoAddViewModel.pairingFoot = item.getFootRingNum();

            //血统
            mPairingInfoAddViewModel.lineage = item.getPigeonBloodName();
            llLineage.setRightText(item.getPigeonBloodName());

            //雨色
            mPairingInfoAddViewModel.featherColor = item.getPigeonPlumeName();
            llFeatherColor.setContent(item.getPigeonPlumeName());
        } else {
            mSelectTypeViewModel.getSelectType_FeatherColor();
            mSelectTypeViewModel.getSelectType_lineage();

        }

        llPairingTime.setContent(TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDD));
        mPairingInfoAddViewModel.pairingTime = TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDD);


        tvHintFoot.setText(mPairingInfoAddViewModel.mPigeonEntity.getFootRingNum());

        if (mPairingInfoAddViewModel.mPigeonEntity.getPigeonSexName().equals("雌")) {
            imgHintSex.setImageResource(R.mipmap.ic_female);
            mPairingInfoAddViewModel.sex = "雄";
        } else if (mPairingInfoAddViewModel.mPigeonEntity.getPigeonSexName().equals("雄")) {
            imgHintSex.setImageResource(R.mipmap.ic_male);
            mPairingInfoAddViewModel.sex = "雌";
        } else {
            mPairingInfoAddViewModel.sex = "未知";
            imgHintSex.setImageResource(R.mipmap.ic_sex_no);
        }

        mPairingInfoAddViewModel.isCanCommit();
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mPairingInfoAddViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvNextStep, aBoolean);
        });


        mSelectTypeViewModel.mSelectType_FeatherColor.observe(this, selectTypeEntities -> {
            mPairingInfoAddViewModel.mSelectTypes_FeatherColor = selectTypeEntities;

            try {
                mPairingInfoAddViewModel.featherColor = mPairingInfoAddViewModel.mSelectTypes_FeatherColor.get(0).getTypeName();
                llFeatherColor.setContent(mPairingInfoAddViewModel.mSelectTypes_FeatherColor.get(0).getTypeName());
                mPairingInfoAddViewModel.isCanCommit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        mSelectTypeViewModel.mSelectType_Lineage.observe(this, selectTypeEntities -> {
            mPairingInfoAddViewModel.mSelectTypes_Lineage = selectTypeEntities;
            try {
                mPairingInfoAddViewModel.lineage = mPairingInfoAddViewModel.mSelectTypes_Lineage.get(0).getTypeName();
                llLineage.setContent(mPairingInfoAddViewModel.mSelectTypes_Lineage.get(0).getTypeName());
                mPairingInfoAddViewModel.isCanCommit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        LocationLiveData.get(true).observe(this, aMapLocation -> {
            Log.d("dingwei", "initObserve: 城市--》" + aMapLocation.getCity());
            LogUtil.print(aMapLocation);
            WeatherLiveData.get(aMapLocation.getCity()).observe(this, localWeatherLive -> {

                Log.d("dingwei", "initObserve: 天气" + localWeatherLive.getWeather());
                mPairingInfoAddViewModel.weather = localWeatherLive.getWeather();//天气
                mPairingInfoAddViewModel.temper = localWeatherLive.getTemperature();//气温
                mPairingInfoAddViewModel.hum = localWeatherLive.getHumidity();//湿度
                mPairingInfoAddViewModel.dir = localWeatherLive.getWindDirection();//风向
            });

        });

        mPairingInfoAddViewModel.isCanCommit();

    }

    private BaseInputDialog mDialogInput;

    @OnClick({R.id.ll_pairing_foot, R.id.ll_pairing_time, R.id.ll_feather_color, R.id.ll_lineage, R.id.tv_next_step, R.id.ll_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_pairing_foot:
                //配偶环号
                InputSingleFootDialog.show(getFragmentManager(), llPairingFoot.getContent(), true, dialog1 -> {
                    SelectFootRingFragment.start(getBaseActivity());
                }, foot -> {
                    llPairingFoot.setRightText(foot);
                    mPairingInfoAddViewModel.pairingFoot = foot;
                    mPairingInfoAddViewModel.isCanCommit();
                });

                break;
            case R.id.ll_pairing_time:
                //配对时间
                PickerUtil.showTimeYMD(getActivity(), new Date().getTime(), (year, monthOfYear, dayOfMonth) -> {
                    llPairingTime.setContent(year + "-" + (monthOfYear) + "-" + dayOfMonth);
                    mPairingInfoAddViewModel.pairingTime = year + "-" + (monthOfYear) + "-" + dayOfMonth;
                    mPairingInfoAddViewModel.isCanCommit();
                });
                break;
            case R.id.ll_feather_color:
                //羽色
                mDialogInput = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_feather_color, llFeatherColor.getContent(), 0, content -> {
                            mDialogInput.hide();
                            mPairingInfoAddViewModel.featherColor = content;
                            llFeatherColor.setContent(content);
                            mPairingInfoAddViewModel.isCanCommit();
                        }, () -> {
                            mDialogInput.hide();

                            if (!Lists.isEmpty(mPairingInfoAddViewModel.mSelectTypes_FeatherColor)) {
                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mPairingInfoAddViewModel.mSelectTypes_FeatherColor), 0, new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index, String item) {
                                        mPairingInfoAddViewModel.featherColor = mPairingInfoAddViewModel.mSelectTypes_FeatherColor.get(index).getTypeName();
                                        llFeatherColor.setContent(mPairingInfoAddViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
                                        mPairingInfoAddViewModel.isCanCommit();
                                    }
                                });
                            } else {
                                mSelectTypeViewModel.getSelectType_FeatherColor();
                            }
                        });

                break;

            case R.id.ll_lineage:
                //血统

                BaseInputDialog.show(getFragmentManager(), R.string.text_blood, R.string.text_blood_bank, 0, content -> {

                    mPairingInfoAddViewModel.lineage = content;
                    llLineage.setRightText(content);
                    mPairingInfoAddViewModel.isCanCommit();
                }, () -> {
                    SelectBloodFragment.start(getBaseActivity());
                });

//                mDialogInput = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
//                        , R.string.text_pigeon_lineage, llLineage.getContent(),0, content -> {
//                            mDialogInput.hide();
//                            mPairingInfoAddViewModel.lineage = content;
//                            llLineage.setRightText(content);
//                            mPairingInfoAddViewModel.isCanCommit();
//                        }, () -> {
//                            mDialogInput.hide();
//
//                            if (!Lists.isEmpty(mPairingInfoAddViewModel.mSelectTypes_Lineage)) {
//                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mPairingInfoAddViewModel.mSelectTypes_Lineage), 0, new OptionPicker.OnOptionPickListener() {
//                                    @Override
//                                    public void onOptionPicked(int index, String item) {
//                                        mPairingInfoAddViewModel.lineage = mPairingInfoAddViewModel.mSelectTypes_Lineage.get(index).getTypeName();
//                                        llLineage.setContent(mPairingInfoAddViewModel.mSelectTypes_Lineage.get(index).getTypeName());
//                                        mPairingInfoAddViewModel.isCanCommit();
//                                    }
//                                });
//                            } else {
//                                mSelectTypeViewModel.getSelectType_lineage();
//                            }
//                        });

                break;
            case R.id.tv_next_step:
                mPairingInfoAddViewModel.getTXGP_PigeonBreed_AddData();
                break;


            case R.id.ll_head:
                BreedPigeonDetailsFragment.start(getBaseActivity(),
                        mPairingInfoAddViewModel.mPigeonEntity.getPigeonID(),
                        mPairingInfoAddViewModel.mPigeonEntity.getFootRingID());
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == PairingInfoRecommendFragment.RECOMMEND_REQUEST) {

                try {
                    PriringRecommendEntity item = data.getParcelableExtra(IntentBuilder.KEY_DATA);

                    //足环号
                    llPairingFoot.setContent(item.getFootRingNum());
                    mPairingInfoAddViewModel.pairingFoot = item.getFootRingNum();

                    //血统
                    mPairingInfoAddViewModel.lineage = item.getPigeonBloodName();
                    llLineage.setRightText(item.getPigeonBloodName());

                    //雨色
                    mPairingInfoAddViewModel.featherColor = item.getPigeonPlumeName();
                    llFeatherColor.setContent(item.getPigeonPlumeName());

                    mPairingInfoAddViewModel.isCanCommit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //            switch (resultCode) {
                //                case PairingLineageFragment.resultCode:
                //                    //血统
                //
                //                    break;
                //                case PairingPlayFragment.resultCode:
                //                    //赛绩
                //
                //                    break;
                //                case PairingScoreFragment.resultCode:
                //                    //评分
                //
                //                    break;
                //            }

            } else if (requestCode == SelectBloodFragment.CODE_SELECT_BLOOD) {
                SelectTypeEntity blood = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mPairingInfoAddViewModel.lineage = blood.getTypeName();
                llLineage.setRightText(blood.getTypeName());
                mPairingInfoAddViewModel.isCanCommit();
            }
            if (SelectFootRingFragment.CODE_SELECT_FOOT == requestCode) {
                FootEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);

                //足环号
                llPairingFoot.setContent(entity.getFootRingNum());
                mPairingInfoAddViewModel.pairingFoot = entity.getFootRingNum();

                mPairingInfoAddViewModel.isCanCommit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
