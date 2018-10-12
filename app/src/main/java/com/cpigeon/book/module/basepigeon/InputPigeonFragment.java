package com.cpigeon.book.module.basepigeon;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.LogUtil;
import com.base.util.utility.StringUtil;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.InputPigeonViewModel;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.foot.SelectCountyFragment;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.BaseImgUploadFragment;
import com.cpigeon.book.module.photo.ImgUploadFragment;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;
import com.cpigeon.book.widget.selectImagesView.SelectImageAdapter2;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by Zhu TingYu on 2018/10/11.
 */

public class InputPigeonFragment extends BaseBookFragment {

    private static final String KEY_SON_FOOT_ID = "KEY_SON_FOOT_ID";
    private static final String KEY_SON_PIGEON_ID = "KEY_SON_PIGEON_ID";
    private static final String KEY_PIGEON_SEX_TYPE = "KEY_PIGEON_SEX_TYPE";
    private static final String KEY_PIGEON_TYPE = "KEY_PIGEON_TYPE";

    public static final String TYPE_SEX_MALE = "TYPE_SEX_MALE";
    public static final String TYPE_SEX_FEMALE = "TYPE_SEX_FEMALE";
    private static final int CODE_ADD_PLAY = 0x321;

    private LineInputListLayout mLlBase;
    private LineInputView mLvPigeonType;
    private LineInputView mLvCountries;
    private LineInputView mLvRing;
    private LineInputView mLvSex;
    private LineInputView mLvBlood;
    private LineInputView mLvFeatherColor;
    private LineInputView mLvState;
    private RelativeLayout mRlAddition;
    private LineInputView mLvPigeonName;
    private LineInputView mLvFootVice;
    private LineInputView mLvFootSource;
    private LineInputView mLvFatherFoot;
    private LineInputView mLvMotherFoot;
    private LineInputView mLvEyeSand;
    private LineInputView mLvBirthTime;
    private LineInputView mLvHangingRingDate;
    private LinearLayout mLlImage;
    private RecyclerView mList;
    private TextView mTvNextStep;
    private ImageView mImgExpand;
    private LineInputListLayout mLlAddition;

    InputPigeonViewModel mViewModel;
    SelectTypeViewModel mSelectTypeViewModel;
    SelectImageAdapter2 mAdapter;


    ObjectAnimator mOpenAnim;
    ObjectAnimator mCloseAnim;
    boolean mIsAdditionOpen = false;
    protected String mSexType;
    protected String mPigeonType;
    private BaseInputDialog mBaseInputDialog;


    public static void start(Activity activity, @Nullable String pigeonId, String sonFootId, String sonPigeonId, String sex, String pigeonType, int requestCode) {
        IntentBuilder builder = IntentBuilder.Builder();
        builder.putExtra(IntentBuilder.KEY_DATA, pigeonId);
        builder.putExtra(KEY_SON_FOOT_ID, sonFootId);
        builder.putExtra(KEY_SON_PIGEON_ID, sonPigeonId);
        builder.putExtra(KEY_PIGEON_SEX_TYPE, sex);
        builder.putExtra(KEY_PIGEON_TYPE, pigeonType);
        builder.startParentActivity(activity, InputPigeonFragment.class, requestCode);
    }

    public static void start(Activity activity, @Nullable String pigeonId, String sonFootId, String sonPigeonId, String sex, int requestCode) {
        start(activity, pigeonId, sonFootId, sonPigeonId, sex, null, requestCode);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new InputPigeonViewModel();
        mSelectTypeViewModel = new SelectTypeViewModel();
        mSexType = getBaseActivity().getIntent().getStringExtra(KEY_PIGEON_SEX_TYPE);
        mPigeonType = getBaseActivity().getIntent().getStringExtra(KEY_PIGEON_TYPE);
        mViewModel.sonFootId = getBaseActivity().getIntent().getStringExtra(KEY_SON_FOOT_ID);
        mViewModel.sonPigeonId = getBaseActivity().getIntent().getStringExtra(KEY_SON_PIGEON_ID);
        mViewModel.pigeonId = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_DATA);
        initViewModels(mViewModel, mSelectTypeViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_pigeon, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_pigeon_input);
        findView();
        composite.add(RxUtils.delayed(200, aLong -> {
            if (StringUtil.isStringValid(mViewModel.pigeonId)) {
                mLlBase.setLineInputViewState(true);
            } else {
                mLlBase.setLineInputViewState(false);
            }
        }));

        mList.setLayoutManager(new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new SelectImageAdapter2(getBaseActivity());
        mList.setAdapter(mAdapter);
        mAdapter.setOnSelectImageClickListener(new SelectImageAdapter2.OnSelectImageClickListener() {
            @Override
            public void onAddImage() {
                String[] chooseWays = getResources().getStringArray(R.array.array_choose_photo);
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                    String way = chooseWays[p];
                    if (Utils.getString(R.string.text_open_gallery).equals(way)) {
                        PictureSelectUtil.showChooseImage(getBaseActivity(), PictureMimeType.ofImage(), 1);
                    } else if (Utils.getString(R.string.text_open_camera).equals(way)) {
                        PictureSelectUtil.openCamera(getBaseActivity(), false);
                    }
                });
            }

            @Override
            public void onImageDelete(int position) {
            }
        });

        if (TYPE_SEX_MALE.equals(mSexType)) {
            mViewModel.mBreedPigeonEntity.setPigeonSexID(PigeonEntity.ID_MALE);
            mViewModel.sexId = (PigeonEntity.ID_MALE);
            mLvSex.setRightText(Utils.getString(R.string.text_male_a));
            mLvSex.setRightImageVisible(false);
        } else if (TYPE_SEX_FEMALE.equals(mSexType)) {
            mViewModel.mBreedPigeonEntity.setPigeonSexID(PigeonEntity.ID_FEMALE);
            mViewModel.sexId = (PigeonEntity.ID_FEMALE);
            mLvSex.setRightText(Utils.getString(R.string.text_female_a));
            mLvSex.setRightImageVisible(false);
        }


        if (StringUtil.isStringValid(mViewModel.pigeonId)) {
            setProgressVisible(true);
            mViewModel.getPigeonDetails();
        }

        setExpandAnim();
        setClick();
        mSelectTypeViewModel.getSelectType_Sex();
        mSelectTypeViewModel.getSelectType_FeatherColor();
        mSelectTypeViewModel.getSelectType_lineage();
        mSelectTypeViewModel.getSelectType_eyeSand();
        mSelectTypeViewModel.getSelectType_State();
        mSelectTypeViewModel.getSelectType_PigeonSource();
        mSelectTypeViewModel.getPigeonType();
    }

    private void setExpandAnim() {
        mLlAddition.setVisibility(View.GONE);
//        ViewWrapper viewWrapper = new ViewWrapper(mLlAddition);
//        mOpenAnim = ObjectAnimator.ofInt(viewWrapper, "trueWidth", 0
//                , ScreenTool.dip2px(448)).setDuration(300);
//        mCloseAnim = ObjectAnimator.ofInt(viewWrapper, "trueWidth"
//                , ScreenTool.dip2px(448), 0).setDuration(300);
    }

    private void setClick() {
        //信鸽类型
        mLvPigeonType.setOnRightClickListener(lineInputView -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_PigeonType), p -> {
                mLvPigeonType.setRightText(mViewModel.mSelectTypes_PigeonType.get(p).getTypeName());
                mViewModel.pigeonType = mViewModel.mSelectTypes_PigeonType.get(p).getTypeID();
                mViewModel.isCanCommit();
            });
        });

        //国家
        mLvCountries.setOnRightClickListener(v -> {
            SearchFragmentParentActivity.start(getBaseActivity(), SelectCountyFragment.class, SelectCountyFragment.CODE_SELECT_COUNTY, null);
            mViewModel.isCanCommit();
        });

        //足环
        mLvRing.setOnRightClickListener(lineInputView -> {
            InputSingleFootDialog.show(getFragmentManager(), mLvRing.getContent(), mViewModel.isChina(), true, foot -> {
                mLvRing.setRightText(foot);
                mViewModel.foot = foot;
                mViewModel.isCanCommit();
            });
        });

        //性别
        mLvSex.setOnRightClickListener(lineInputView -> {

            if(!StringUtil.isStringValid(mSexType)){
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_Sex), p -> {
                    mLvSex.setRightText(mViewModel.mSelectTypes_Sex.get(p).getTypeName());
                    mViewModel.sexId = mViewModel.mSelectTypes_Sex.get(p).getTypeID();
                    mViewModel.isCanCommit();
                });
            }

        });

        mLvBlood.setOnRightClickListener(lineInputView -> {
            SelectBloodFragment.start(getBaseActivity());
        });

        mLvFeatherColor.setOnRightClickListener(lineInputView -> {
            mBaseInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                    , R.string.text_pigeon_lineage, 0, content -> {
                        mBaseInputDialog.hide();
                        mViewModel.lineage = content;
                        mLvFeatherColor.setRightText(content);
                    }, () -> {
                        mBaseInputDialog.hide();
                        if (!Lists.isEmpty(mViewModel.mSelectTypes_Lineage)) {
                            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_Lineage), 0, new OptionPicker.OnOptionPickListener() {
                                @Override
                                public void onOptionPicked(int index, String item) {
                                    mViewModel.lineage = mViewModel.mSelectTypes_Lineage.get(index).getTypeName();
                                    mLvFeatherColor.setContent(mViewModel.mSelectTypes_Lineage.get(index).getTypeName());
                                    mViewModel.isCanCommit();
                                }
                            });
                        } else {
                            mSelectTypeViewModel.getSelectType_lineage();
                        }
                    });
        });

        mLvState.setOnRightClickListener(lineInputView -> {
            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_State), 0, new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    mViewModel.stateId = mViewModel.mSelectTypes_State.get(index).getTypeID();
                    mLvState.setContent(mViewModel.mSelectTypes_State.get(index).getTypeName());
                    mViewModel.isCanCommit();
                }
            });
        });

        mLvPigeonName.setOnRightClickListener(lineInputView -> {
            mBaseInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                    , R.string.text_pleas_input_pigeon_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                        mViewModel.pigeonName = content;
                        mLvPigeonName.setRightText(content);
                        mBaseInputDialog.hide();
                    }, null);
        });

        mLvFootVice.setOnRightClickListener(lineInputView -> {
            InputSingleFootDialog.show(getFragmentManager(), mLvFootVice.getContent(), mViewModel.isChina(), true, foot -> {
                mLvFootVice.setRightText(foot);
                mViewModel.footVice = foot;
            });
        });

        mLvFootSource.setOnRightClickListener(lineInputView -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity()
                    , SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_Source), p -> {
                        mViewModel.sourceId = mViewModel.mSelectTypes_Source.get(p).getTypeID();
                        mLvFootSource.setContent(mViewModel.mSelectTypes_Source.get(p).getTypeName());
                    });
        });

        mLvFatherFoot.setOnRightClickListener(lineInputView -> {
            InputSingleFootDialog.show(getFragmentManager(), mLvFatherFoot.getContent(), mViewModel.isChina(), true, foot -> {
                mLvFatherFoot.setRightText(foot);
                mViewModel.footFather = foot;
            });
        });

        mLvMotherFoot.setOnRightClickListener(lineInputView -> {
            InputSingleFootDialog.show(getFragmentManager(), mLvMotherFoot.getContent(), mViewModel.isChina(), true, foot -> {
                mLvMotherFoot.setRightText(foot);
                mViewModel.footFather = foot;
            });
        });
        //眼砂
        mLvEyeSand.setOnRightClickListener(lineInputView -> {
            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_EyeSand), 0, new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    mViewModel.eyeSandId = mViewModel.mSelectTypes_EyeSand.get(index).getTypeID();
                    mLvEyeSand.setContent(mViewModel.mSelectTypes_EyeSand.get(index).getTypeName());
                }
            });
        });

        mLvBirthTime.setOnRightClickListener(lineInputView -> {
            PickerUtil.showTimeYMD(getActivity(), System.currentTimeMillis(), (year, month, day) -> {
                mViewModel.theirShellsDate = Utils.getString(R.string.text_time_y_m_d, year, month, day);
                mLvBirthTime.setRightText(mViewModel.theirShellsDate);
            });
        });

        mLvHangingRingDate.setOnRightClickListener(lineInputView -> {
            PickerUtil.showTimeYMD(getActivity(), System.currentTimeMillis(), (year, month, day) -> {
                mViewModel.llHangingRingDate = Utils.getString(R.string.text_time_y_m_d, year, month, day);
                mLvHangingRingDate.setRightText(mViewModel.theirShellsDate);
            });
        });


        mRlAddition.setOnClickListener(v -> {
            if (mIsAdditionOpen) {
                mImgExpand.setRotation(0);
                mLlAddition.setVisibility(View.GONE);
                mIsAdditionOpen = false;
            } else {
                mImgExpand.setRotation(180);
                mLlAddition.setVisibility(View.VISIBLE);
                mIsAdditionOpen = true;
            }
        });

        mTvNextStep.setOnClickListener(v -> {
            setProgressVisible(true);
            if(!StringUtil.isStringValid(mViewModel.pigeonId)){
                mViewModel.addPigeon();
            }else {
                mViewModel.modifyBreedPigeonEntry();
            }
        });
    }

    private void findView() {
        mLlBase = findViewById(R.id.llBase);
        mLvPigeonType = findViewById(R.id.lvPigeonType);
        mLvCountries = findViewById(R.id.lvCountries);
        mLvRing = findViewById(R.id.lvRing);
        mLvSex = findViewById(R.id.lvSex);
        mLvBlood = findViewById(R.id.lvBlood);
        mLvFeatherColor = findViewById(R.id.lvFeatherColor);
        mLvState = findViewById(R.id.lvState);
        mRlAddition = findViewById(R.id.rlAddition);
        mLvPigeonName = findViewById(R.id.lvPigeonName);
        mLvFootVice = findViewById(R.id.lv_foot_vice);
        mLvFootSource = findViewById(R.id.lv_foot_source);
        mLvFatherFoot = findViewById(R.id.lvFatherFoot);
        mLvMotherFoot = findViewById(R.id.lvMotherFoot);
        mLvEyeSand = findViewById(R.id.lvEyeSand);
        mLvBirthTime = findViewById(R.id.lvBirthTime);
        mLvHangingRingDate = findViewById(R.id.lv_hanging_ring_date);
        mLlImage = findViewById(R.id.llImage);
        mList = findViewById(R.id.list);
        mTvNextStep = findViewById(R.id.tv_next_step);
        mLlAddition = findViewById(R.id.llAddition);
        mImgExpand = findViewById(R.id.imgExpand);
    }

    @Override
    protected void initObserve() {
        //详情
        mViewModel.mDataPigeonDetails.observe(this, breedPigeonEntity -> {
            mLvPigeonType.setRightText(breedPigeonEntity.getTypeName());
            mLvCountries.setRightText(breedPigeonEntity.getFootCode());
            mLvRing.setRightText(breedPigeonEntity.getFootRingNum());
            mLvFootVice.setRightText(breedPigeonEntity.getFootRingIDToNum());
            mLvFootSource.setRightText(breedPigeonEntity.getSourceName());
            mLvFatherFoot.setRightText(breedPigeonEntity.getMenFootRingNum());
            mLvMotherFoot.setRightText(breedPigeonEntity.getWoFootRingNum());
            mLvPigeonName.setRightText(breedPigeonEntity.getPigeonName());
            mLvSex.setRightText(breedPigeonEntity.getPigeonSexName());
            mLvSex.setRightImageVisible(false);
            mLvFeatherColor.setRightText(breedPigeonEntity.getPigeonPlumeName());
            mLvEyeSand.setRightText(breedPigeonEntity.getPigeonEyeName());
            mLvBirthTime.setRightText(breedPigeonEntity.getFootRingTime());
            mLvBlood.setRightText(breedPigeonEntity.getPigeonBloodName());
            mLvState.setRightText(breedPigeonEntity.getStateName());

            mViewModel.pigeonType = breedPigeonEntity.getTypeID();
            mViewModel.countryId = breedPigeonEntity.getFootCodeID();
            mViewModel.footVice = breedPigeonEntity.getFootRingIDToNum();
            mViewModel.sourceId = breedPigeonEntity.getSourceID();
            mViewModel.footFather = breedPigeonEntity.getMenFootRingNum();
            mViewModel.footMother = breedPigeonEntity.getWoFootRingNum();
            mViewModel.pigeonName = breedPigeonEntity.getPigeonName();
            mViewModel.sexId = breedPigeonEntity.getPigeonSexID();
            mViewModel.featherColor = breedPigeonEntity.getPigeonSexID();
            mViewModel.eyeSandId = breedPigeonEntity.getPigeonEyeID();
            mViewModel.theirShellsDate = breedPigeonEntity.getFootRingTimeTo();
            mViewModel.lineage = breedPigeonEntity.getPigeonBloodID();
            mViewModel.stateId = breedPigeonEntity.getStateID();

            if (StringUtil.isStringValid(breedPigeonEntity.getCoverPhotoUrl())) {
                List<ImgTypeEntity> imgs = Lists.newArrayList();
                ImgTypeEntity entity = new ImgTypeEntity.Builder()
                        .imgTypeId(breedPigeonEntity.getCoverPhotoTypeID())
                        .imgType(breedPigeonEntity.getCoverPhotoTypeName())
                        .imgPath(breedPigeonEntity.getCoverPhotoUrl())
                        .build();
                imgs.add(0, entity);
                mAdapter.addImage(imgs);

                mViewModel.phototypeid = breedPigeonEntity.getCoverPhotoID();
                mViewModel.images.addAll(Lists.newArrayList(breedPigeonEntity.getCoverPhotoUrl()));
            }
        });

        //种鸽录入、修改
        mViewModel.mDataPigeon.observe(this, o -> {

            EventBus.getDefault().post(new PigeonAddEvent());

            //保证界面只有一个提示
            setProgressVisible(false);
            if (getBaseActivity().errorDialog != null && getBaseActivity().errorDialog.isShowing()) {
                getBaseActivity().errorDialog.dismiss();
            }
            String hintStr = "种鸽录入成功，";
            if (o != null) {
                if (Integer.valueOf(o.getPigeonMoney()) > 0) {
                    hintStr += "获取" + o.getPigeonMoney() + "个鸽币，";
                }
            }

            hintStr += "是否为该鸽子录入赛绩！";

            getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), hintStr, sweetAlertDialog -> {
                //确定
                sweetAlertDialog.dismiss();
                PlayAddFragment.start(getBaseActivity(), o, 0, CODE_ADD_PLAY);
            }, sweetAlertDialog -> {
                //取消
                sweetAlertDialog.dismiss();
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, o)
                        .finishForResult(getBaseActivity());
            });
        });

        //性别
        mSelectTypeViewModel.mSelectType_Sex.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes_Sex = selectTypeEntities;
        });

        //雨色
        mSelectTypeViewModel.mSelectType_FeatherColor.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes_FeatherColor = selectTypeEntities;
        });

        //眼沙
        mSelectTypeViewModel.mSelectType_EyeSand.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes_EyeSand = selectTypeEntities;
        });


        //血统
        mSelectTypeViewModel.mSelectType_Lineage.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes_Lineage = selectTypeEntities;
        });

        //状态
        mSelectTypeViewModel.mSelectType_State.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes_State = selectTypeEntities;
        });

        //来源
        mSelectTypeViewModel.mSelectType_Pigeon_Source.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes_Source = selectTypeEntities;
        });

        //信鸽类型
        mSelectTypeViewModel.mSelectType_PigeonType.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes_PigeonType = selectTypeEntities;
            if(PigeonEntity.ID_BREED_PIGEON.equals(mPigeonType)){
                mViewModel.pigeonType = mViewModel.mSelectTypes_PigeonType.get(0).getTypeID();
                mLvPigeonType.setRightText(mViewModel.mSelectTypes_PigeonType.get(0).getTypeName());
            }
        });
    }

    private static class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View view) {
            mTarget = view;
        }

        public void setTrueWidth(int height) {
            LogUtil.print(height);
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();//必须调用，否则宽度改变但UI没有刷新
        }

        public int getTrueWidth() {
            return mTarget.getLayoutParams().height;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == CODE_ADD_PLAY) {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mViewModel.mDataPigeon.getValue())
                    .finishForResult(getBaseActivity());
        }else if (requestCode == PictureMimeType.ofImage()) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            BaseImgUploadFragment.start(getBaseActivity(),
                    ImgUploadFragment.class,
                    new ImgTypeEntity.Builder()
                            .imgPath(selectList.get(0).getCompressPath())
                            .build(),
                    ImgUploadFragment.CODE_SELECT_COUNTY);
        }else if(requestCode == SelectCountyFragment.CODE_SELECT_COUNTY){
            try {
                CountyEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mViewModel.countryId = entity.getFootCodeID();
                mLvCountries.setRightText(entity.getCode());
            } catch (Exception e) {
                CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mViewModel.countryId = entity.getFootCodeID();
                mLvCountries.setRightText(entity.getCode());
            }
        }else if(requestCode == ImgUploadFragment.CODE_SELECT_COUNTY){
            ImgTypeEntity mImgTypeEntity = (ImgTypeEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);

            List<ImgTypeEntity> imgs = Lists.newArrayList();
            imgs.add(0, mImgTypeEntity);
            mAdapter.addImage(imgs);

            mViewModel.phototypeid = mImgTypeEntity.getImgTypeId();
            mViewModel.images.addAll(Lists.newArrayList(mImgTypeEntity.getImgPath()));
        }else if(requestCode == SelectBloodFragment.CODE_SELECT_BLOOD){
            SelectTypeEntity blood = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            mViewModel.lineage = blood.getTypeID();
            mLvBlood.setRightText(blood.getTypeName());
        }

//        switch (requestCode) {
//
//            case CODE_ADD_PLAY:
//                break;
//
//            case SelectCountyFragment.CODE_SELECT_COUNTY:
//                try {
//                    CountyEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
//                    mViewModel.countryId = entity.getSort();
//                    mLvCountries.setRightText(entity.getCode());
//                } catch (Exception e) {
//                    CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
//                    mViewModel.countryId = entity.getFootCodeID();
//                    mLvCountries.setRightText(entity.getCode());
//                }
//
//                break;
//
//            case ImgUploadFragment.CODE_SELECT_COUNTY:
//                ImgTypeEntity mImgTypeEntity = (ImgTypeEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
//
//                List<ImgTypeEntity> imgs = Lists.newArrayList();
//                imgs.add(0, mImgTypeEntity);
//                mAdapter.addImage(imgs);
//
//                mViewModel.phototypeid = mImgTypeEntity.getImgTypeId();
//                mViewModel.images.addAll(Lists.newArrayList(mImgTypeEntity.getImgPath()));
//
//                break;
//        }
    }
}
