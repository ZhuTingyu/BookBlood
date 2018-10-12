package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.util.map.LocationLiveData;
import com.base.util.map.WeatherLiveData;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.LogUtil;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breeding.adapter.OffspringInfoAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingNestAddViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加窝次
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestAddFragment extends BaseBookFragment {


    @BindView(R.id.ll_nest_num)
    LineInputView llNestNum;
    @BindView(R.id.ll_foot_father)
    LineInputView llFootFather;
    @BindView(R.id.ll_foot_mother)
    LineInputView llFootMother;
    @BindView(R.id.ll_pairing_time)
    LineInputView llPairingTime;

    @BindView(R.id.ll_lay_eggs)
    LinearLayout llLayEggs;
    @BindView(R.id.img_flashlight)
    ImageView img_flashlight;
    @BindView(R.id.img_right_lay_eggs)
    ImageView img_right_lay_eggs;

    @BindView(R.id.ll_lay_eggs_time)
    LineInputView llLayEggsTime;
    @BindView(R.id.ll_fertilized_egg)
    LineInputView llFertilizedEgg;
    @BindView(R.id.ll_fertilized_egg_no)
    LineInputView llFertilizedEggNo;
    @BindView(R.id.ll_hatches_info)
    LineInputView llHatchesInfo;
    @BindView(R.id.ll_hatches_time)
    LineInputView llHatchesTime;
    @BindView(R.id.ll_offspring_info)
    LineInputView llOffspringInfo;
    @BindView(R.id.ll_hatches_num)
    LineInputView llHatchesNum;
    @BindView(R.id.tv_next_step)
    TextView tvNextStep;

    @BindView(R.id.ll_lay_eggs_z)
    LinearLayout ll_lay_eggs_z;
    @BindView(R.id.ll_hatches_info_z)
    LinearLayout ll_hatches_info_z;
    @BindView(R.id.tv_giving_name)
    TextView tv_giving_name;//

    @BindView(R.id.rv_offspring_info)
    RecyclerView rv_offspring_info;

    @BindView(R.id.img_giving)
    ImageView img_giving;//

    private PairingNestAddViewModel mPairingNestAddViewModel;
    private OffspringInfoAdapter mOffspringInfoAdapter;

    private Camera mCamera;

    public static void start(Activity activity, PairingInfoEntity mPairingInfoEntity, PigeonEntity mBreedPigeonEntity, int maxNest) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPairingInfoEntity)
                .putExtra(IntentBuilder.KEY_DATA_2, mBreedPigeonEntity)
                .putExtra(IntentBuilder.KEY_DATA_3, maxNest)
                .startParentActivity(activity, PairingNestAddFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPairingNestAddViewModel = new PairingNestAddViewModel();
        initViewModels(mPairingNestAddViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pairing_nest_add, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(getString(R.string.str_pairing_nest_add));

        mPairingNestAddViewModel.mPairingInfoEntity = (PairingInfoEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
        mPairingNestAddViewModel.mBreedPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA_2);

        int nestNum = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_DATA_3, 0);

        //窝次
        llNestNum.setContent(String.valueOf(++nestNum));
        //父足环号码
        llFootFather.setContent(mPairingNestAddViewModel.mPairingInfoEntity.getMenFootRingNum());
        //母足环号码
        llFootMother.setContent(mPairingNestAddViewModel.mPairingInfoEntity.getWoFootRingNum());

        llPairingTime.setContent(TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDD));
        mPairingNestAddViewModel.pairingTime = TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDD);
        mPairingNestAddViewModel.isCanCommit();

        mOffspringInfoAdapter = new OffspringInfoAdapter();
        rv_offspring_info.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        rv_offspring_info.setAdapter(mOffspringInfoAdapter);
        mOffspringInfoAdapter.setOnItemClickListener((adapter, view1, position) -> {
            adapter.remove(position);
            mPairingNestAddViewModel.setIdStr(mOffspringInfoAdapter);
        });

        if (mCamera == null) {
            mCamera = Camera.open();
        }
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mPairingNestAddViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvNextStep, aBoolean);
        });

        LocationLiveData.get(true).observe(this, aMapLocation -> {
            Log.d("dingwei", "initObserve: 城市--》" + aMapLocation.getCity());
            LogUtil.print(aMapLocation);
            WeatherLiveData.get(aMapLocation.getCity()).observe(this, localWeatherLive -> {
                Log.d("dingwei", "initObserve: 天气" + localWeatherLive.getWeather());
                mPairingNestAddViewModel.weather = localWeatherLive.getWeather();//天气
                mPairingNestAddViewModel.temper = localWeatherLive.getTemperature();//气温
                mPairingNestAddViewModel.hum = localWeatherLive.getHumidity();//湿度
                mPairingNestAddViewModel.dir = localWeatherLive.getWindDirection();//风向
            });
        });
    }

    private BaseInputDialog mInputDialog;

    private boolean llLayEggsTag = false;
    private boolean llHatchesInfoTag = false;
    private boolean flashlightTag = false;

    @OnClick({R.id.ll_nest_num, R.id.ll_foot_father, R.id.ll_foot_mother, R.id.ll_pairing_time, R.id.ll_lay_eggs, R.id.img_flashlight, R.id.ll_lay_eggs_time, R.id.ll_fertilized_egg, R.id.ll_fertilized_egg_no, R.id.ll_fertilized_giving, R.id.ll_hatches_info, R.id.ll_hatches_time, R.id.ll_offspring_info, R.id.ll_hatches_num, R.id.tv_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_nest_num:
                //窝次
                break;
            case R.id.ll_foot_father:
                //父足环号码
                break;
            case R.id.ll_foot_mother:
                //母足环号码
                break;
            case R.id.ll_pairing_time:
                //配对时间
                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    llPairingTime.setContent(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mPairingNestAddViewModel.pairingTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    mPairingNestAddViewModel.isCanCommit();
                });
                break;
            case R.id.ll_lay_eggs:
                //产蛋信息
                if (llLayEggsTag) {
                    //未产蛋
                    llLayEggsTag = false;

                    img_right_lay_eggs.setRotation(0);

                    ll_lay_eggs_z.setVisibility(View.GONE);

                    mPairingNestAddViewModel.layEggs = Utils.getString(R.string.string_lay_eggs_no);
                    mPairingNestAddViewModel.isCanCommit();

                } else if (!llLayEggsTag) {
                    //已产蛋
                    llLayEggsTag = true;

                    img_right_lay_eggs.setRotation(90);

                    ll_lay_eggs_z.setVisibility(View.VISIBLE);

                    mPairingNestAddViewModel.layEggs = Utils.getString(R.string.string_lay_eggs_yes);
                    mPairingNestAddViewModel.isCanCommit();
                }
                break;

            case R.id.img_flashlight:
                // 打开关闭手电筒
                openFlashlight();
                break;
            case R.id.ll_lay_eggs_time:
                //产蛋时间
                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    llLayEggsTime.setContent(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mPairingNestAddViewModel.layEggsTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                });
                break;
            case R.id.ll_fertilized_egg:
                //产蛋 受精蛋
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_fertilized_egg, InputType.TYPE_CLASS_NUMBER, content -> {
                            mInputDialog.hide();

                            llFertilizedEgg.setContent(content + "个");
                            mPairingNestAddViewModel.fertilizedEgg = content;

                        }, null);

                break;
            case R.id.ll_fertilized_egg_no:
                //产蛋 无精蛋
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_fertilized_egg_no, InputType.TYPE_CLASS_NUMBER, content -> {
                            mInputDialog.hide();

                            if (!StringUtil.isStringValid(content)) {
                                return;
                            }
                            llFertilizedEggNo.setContent(content + "个");
                            mPairingNestAddViewModel.fertilizedEggNo = content;
                        }, null);

                break;
            case R.id.ll_fertilized_giving:
                //是否赠送
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_hatches_giving_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mInputDialog.hide();
                            tv_giving_name.setText(content);
                            mPairingNestAddViewModel.giveprson = content;
                            if (StringUtil.isStringValid(content)) {
                                img_giving.setImageResource(R.mipmap.giving_yes);
                            } else {
                                img_giving.setImageResource(R.mipmap.giving_no);
                            }
                        }, null);
                break;
            case R.id.ll_hatches_info:
                //出壳信息

                String lleggstr = llFertilizedEgg.getContent().replace("个", "");

                if (!StringUtil.isStringValid(lleggstr)) {
                    ToastUtils.showLong(getBaseActivity(), "请先填写产蛋信息下的受精蛋个数！");
                    return;
                }

                ImageView hatches_info_img = llHatchesInfo.getImgRight();

                if (llHatchesInfoTag) {
                    //未出壳
                    llHatchesInfoTag = false;

                    hatches_info_img.setRotation(0);

                    ll_hatches_info_z.setVisibility(View.GONE);

                    mPairingNestAddViewModel.hatchesInfo = Utils.getString(R.string.string_hatches_info_no);
                    mPairingNestAddViewModel.isCanCommit();

                } else if (!llHatchesInfoTag) {
                    //已出壳
                    llHatchesInfoTag = true;

                    hatches_info_img.setRotation(90);

                    ll_hatches_info_z.setVisibility(View.VISIBLE);

                    mPairingNestAddViewModel.hatchesInfo = Utils.getString(R.string.string_hatches_info_yes);
                    mPairingNestAddViewModel.isCanCommit();
                }

                break;
            case R.id.ll_hatches_time:
                //出壳时间
                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    llHatchesTime.setContent(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mPairingNestAddViewModel.hatchesTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                });
                break;
            case R.id.ll_hatches_num:
                //出壳个数
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_hatches_num, InputType.TYPE_CLASS_NUMBER, content -> {
                            mInputDialog.hide();
                            llHatchesNum.setContent(content + "个");
                            mPairingNestAddViewModel.hatchesNum = content;
                        }, null);
                break;
            case R.id.ll_offspring_info:
                //子代信息

                String hatchesNumStr = llHatchesNum.getContent().replace("个", "");

                if (!StringUtil.isStringValid(hatchesNumStr)) {
                    ToastUtils.showLong(getBaseActivity(), "请先填写出壳信息下的出壳个数！");
                    return;
                }

                OffspringChooseFragment.start(getBaseActivity(), PairingNestAddFragment.requestCode, mPairingNestAddViewModel.mPairingInfoEntity);
                break;
            case R.id.tv_next_step:
                //立即添加
                mPairingNestAddViewModel.getTXGP_PigeonBreedNest_Add();
                break;
        }
    }


    //打开关闭手电筒
    private void openFlashlight() {
        if (flashlightTag) {
            //手电筒关闭
            flashlightTag = false;
            img_flashlight.setImageResource(R.mipmap.flashlight_no);

            if (mCamera == null) {
                mCamera = Camera.open();
            }

            Camera.Parameters parameters = mCamera.getParameters();
            List<String> flashModes = parameters.getSupportedFlashModes();
            if (flashModes == null) {
                return;
            }
            String flashMode = parameters.getFlashMode();
            if (!flashMode.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
//                Toast.makeText(getBaseActivity(), "关闭手电筒成功", Toast.LENGTH_SHORT).show();
            }
        } else if (!flashlightTag) {
            //手电筒打开
            flashlightTag = true;
            img_flashlight.setImageResource(R.mipmap.flashlight_yes);

            if (mCamera == null) {
                mCamera = Camera.open();
            }
            mCamera.startPreview();
            Camera.Parameters parameters = mCamera.getParameters();
            List<String> flashModes = parameters.getSupportedFlashModes();
            if (flashModes == null) {
                return;
            }
            String flashMode = parameters.getFlashMode();
            if (!flashMode.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
//                Toast.makeText(getBaseActivity(), "打开手电筒成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PairingNestAddFragment.requestCode) {
            //选择子代后返回
            try {
                PigeonEntity mBreedPigeonEntity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
                Log.d("hehheheheh", "onActivityResult: " + mBreedPigeonEntity.getFootRingNum());

                PairingNestInfoEntity.PigeonListBean mOffspringInfo = new PairingNestInfoEntity.PigeonListBean.Builder()
                        .FootRingID(mBreedPigeonEntity.getFootRingID())
                        .FootRingNum(mBreedPigeonEntity.getFootRingNum())
                        .PigeonID(mBreedPigeonEntity.getPigeonID())
                        .PigeonPlumeName(mBreedPigeonEntity.getPigeonPlumeName())
                        .build();
                mOffspringInfoAdapter.addData(mOffspringInfo);
                mOffspringInfoAdapter.notifyDataSetChanged();

                mPairingNestAddViewModel.setIdStr(mOffspringInfoAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static final int requestCode = 0x0000201;


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.release();
    }
}
