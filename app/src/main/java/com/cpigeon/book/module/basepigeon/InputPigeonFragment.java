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
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BookViewModel;
import com.cpigeon.book.module.breedpigeon.viewmodel.InputPigeonViewModel;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.foot.SelectCountyFragment;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.BaseImgUploadFragment;
import com.cpigeon.book.module.photo.ImgUploadFragment;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.module.select.SelectFootRingFragment;
import com.cpigeon.book.module.select.SetPigeonDeathDialog;
import com.cpigeon.book.util.TextViewUtil;
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

    public static final String KEY_SON_FOOT_ID = "KEY_SON_FOOT_ID";
    public static final String KEY_SON_PIGEON_ID = "KEY_SON_PIGEON_ID";
    public static final String KEY_PIGEON_SEX_TYPE = "KEY_PIGEON_SEX_TYPE";
    public static final String KEY_PIGEON_TYPE = "KEY_PIGEON_TYPE";

    public static final String TYPE_SEX_MALE = "TYPE_SEX_MALE";
    public static final String TYPE_SEX_FEMALE = "TYPE_SEX_FEMALE";
    private static final int CODE_ADD_PLAY = 0x321;
    private static final int CODE_SELECT_FOOT = 0x123;

    public static final int CODE_SELECT_FATHER = 0x2341;
    public static final int CODE_SELECT_MATHER = 0x3451;

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
    BookViewModel mBookViewModel;
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
        mBookViewModel = new BookViewModel();
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

        if (StringUtil.isStringValid(mViewModel.pigeonId)) {
            setTitle(R.string.text_pigeon_edit);
        } else {
            setTitle(R.string.text_pigeon_input);
        }

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

        mViewModel.isCanCommit();
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
            try {
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_PigeonType), p -> {
                    mLvPigeonType.setRightText(mViewModel.mSelectTypes_PigeonType.get(p).getTypeName());
                    mViewModel.pigeonType = mViewModel.mSelectTypes_PigeonType.get(p).getTypeID();
                    mViewModel.isCanCommit();
                });
            } catch (Exception e) {
                mSelectTypeViewModel.getPigeonType();
            }
        });

        //国家
        mLvCountries.setOnRightClickListener(v -> {
            SearchFragmentParentActivity.start(getBaseActivity(), SelectCountyFragment.class, SelectCountyFragment.CODE_SELECT_COUNTY, null);
            mViewModel.isCanCommit();
        });

        //足环
        mLvRing.setOnRightClickListener(lineInputView -> {
            InputSingleFootDialog.show(getFragmentManager(), mLvRing.getContent(), mViewModel.isChina(), dialog -> {
                SelectFootRingFragment.start(getBaseActivity(), true);
            }, foot -> {
                mLvRing.setRightText(foot);
                mViewModel.foot = foot;
                mViewModel.isCanCommit();
                setProgressVisible(true);
                mViewModel.getFootRingState();
            });
        });

        //性别
        mLvSex.setOnRightClickListener(lineInputView -> {

            if (!StringUtil.isStringValid(mSexType)) {
                try {
                    BottomSheetAdapter.createBottomSheet(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_Sex), p -> {
                        mLvSex.setRightText(mViewModel.mSelectTypes_Sex.get(p).getTypeName());
                        mViewModel.sexId = mViewModel.mSelectTypes_Sex.get(p).getTypeID();
                        mViewModel.isCanCommit();
                    });
                } catch (Exception e) {
                    mSelectTypeViewModel.getSelectType_Sex();
                }
            }

        });

        //血统
        mLvBlood.setOnRightClickListener(lineInputView -> {
            BaseInputDialog.show(getFragmentManager(), R.string.text_blood, R.string.text_blood_bank, 0, content -> {
                mViewModel.lineage = content;
                mLvBlood.setRightText(content);
            }, () -> {
                SelectBloodFragment.start(getBaseActivity());
            });

        });

        mLvFeatherColor.setOnRightClickListener(lineInputView -> {
            mBaseInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                    , R.string.text_feather_color, mLvFeatherColor.getContent(), 0, content -> {
                        mBaseInputDialog.hide();
                        mViewModel.featherColor = content;
                        mLvFeatherColor.setRightText(content);
                    }, () -> {
                        mBaseInputDialog.hide();
                        if (!Lists.isEmpty(mViewModel.mSelectTypes_FeatherColor)) {
                            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_FeatherColor), 0, new OptionPicker.OnOptionPickListener() {
                                @Override
                                public void onOptionPicked(int index, String item) {
                                    mViewModel.featherColor = mViewModel.mSelectTypes_FeatherColor.get(index).getTypeName();
                                    mLvFeatherColor.setContent(mViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
                                    mViewModel.isCanCommit();
                                }
                            });
                        } else {
                            mSelectTypeViewModel.getSelectType_FeatherColor();
                        }
                    });
        });

        mLvState.setOnRightClickListener(lineInputView -> {
            try {
                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_State), 0, new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        mViewModel.stateId = mViewModel.mSelectTypes_State.get(index).getTypeID();
                        mLvState.setContent(mViewModel.mSelectTypes_State.get(index).getTypeName());
                        mViewModel.isCanCommit();
                    }
                });
            } catch (Exception e) {
               mSelectTypeViewModel.getSelectType_State();
            }
        });

        mLvPigeonName.setOnRightClickListener(lineInputView -> {
            mBaseInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                    , R.string.text_pleas_input_pigeon_name, mLvPigeonName.getContent(), InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                        mViewModel.pigeonName = content;
                        mLvPigeonName.setRightText(content);
                        mBaseInputDialog.hide();
                    }, null);
        });

        mLvFootVice.setOnRightClickListener(lineInputView -> {
            InputSingleFootDialog.show(getFragmentManager(), mLvFootVice.getContent(), mViewModel.isChina(), null, foot -> {
                mLvFootVice.setRightText(foot);
                mViewModel.footVice = foot;
            });
        });

        mLvFootSource.setOnRightClickListener(lineInputView -> {
            try {
                BottomSheetAdapter.createBottomSheet(getBaseActivity()
                        , SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_Source), p -> {
                            mViewModel.sourceId = mViewModel.mSelectTypes_Source.get(p).getTypeID();
                            mLvFootSource.setContent(mViewModel.mSelectTypes_Source.get(p).getTypeName());
                        });
            } catch (Exception e) {
                mSelectTypeViewModel.getSelectType_Source();
            }
        });

        mLvFatherFoot.setOnRightClickListener(lineInputView -> {
            InputSingleFootDialog.show(getFragmentManager(), mLvFatherFoot.getContent(), mViewModel.isChina(), dialog -> {
                SelectFootRingFragment.start(getBaseActivity(), false
                        , SelectFootRingFragment.CODE_SELECT_FATHER_FOOT, PigeonEntity.ID_MALE, PigeonEntity.ID_NONE_SEX);
            }, foot -> {
                mLvFatherFoot.setRightText(foot);
                mViewModel.footFather = foot;
            });
        });

        mLvMotherFoot.setOnRightClickListener(lineInputView -> {
            InputSingleFootDialog.show(getFragmentManager(), mLvMotherFoot.getContent(), mViewModel.isChina(), dialog -> {
                SelectFootRingFragment.start(getBaseActivity(), false
                        , SelectFootRingFragment.CODE_SELECT_MATHER_FOOT, PigeonEntity.ID_FEMALE, PigeonEntity.ID_NONE_SEX);
            }, foot -> {
                mLvMotherFoot.setRightText(foot);
                mViewModel.footMother = foot;
            });
        });
        //眼砂
        mLvEyeSand.setOnRightClickListener(lineInputView -> {
            try {
                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_EyeSand), 0, new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        mViewModel.eyeSandId = mViewModel.mSelectTypes_EyeSand.get(index).getTypeID();
                        mLvEyeSand.setContent(mViewModel.mSelectTypes_EyeSand.get(index).getTypeName());
                    }
                });
            } catch (Exception e) {
                mSelectTypeViewModel.getSelectType_eyeSand();
            }
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
                mLvHangingRingDate.setRightText(mViewModel.llHangingRingDate);
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
            if (!StringUtil.isStringValid(mViewModel.pigeonId)) {
                mViewModel.addPigeon();
            } else {
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

        mViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(mTvNextStep, aBoolean);
        });

        //详情
        mViewModel.mDataPigeonDetails.observe(this, breedPigeonEntity -> {
            setProgressVisible(false);
            mLvPigeonType.setRightText(breedPigeonEntity.getTypeName());
            mLvCountries.setRightText(breedPigeonEntity.getFootCode());
            mLvRing.setRightText(breedPigeonEntity.getFootRingNum());
            mLvFootVice.setRightText(breedPigeonEntity.getFootRingIDToNum());
            mLvFootSource.setRightText(breedPigeonEntity.getSourceName());
            mLvFatherFoot.setRightText(breedPigeonEntity.getMenFootRingNum());
            mLvMotherFoot.setRightText(breedPigeonEntity.getWoFootRingNum());
            mLvPigeonName.setRightText(breedPigeonEntity.getPigeonName());
            mLvSex.setRightText(breedPigeonEntity.getPigeonSexName());
            mLvFeatherColor.setRightText(breedPigeonEntity.getPigeonPlumeName());
            mLvEyeSand.setRightText(breedPigeonEntity.getPigeonEyeName());
            mLvBirthTime.setRightText(breedPigeonEntity.getFootRingTime());
            mLvBlood.setRightText(breedPigeonEntity.getPigeonBloodName());
            mLvState.setRightText(breedPigeonEntity.getStateName());

            mViewModel.pigeonType = breedPigeonEntity.getTypeID();
            mViewModel.countryId = breedPigeonEntity.getFootCodeID();
            mViewModel.foot = breedPigeonEntity.getFootRingNum();
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

            mViewModel.isCanCommit();
        });

        //种鸽录入、修改
        mViewModel.mDataPigeon.observe(this, datas -> {

            PigeonEntity o = datas.data;

            EventBus.getDefault().post(new PigeonAddEvent());

            //保证界面只有一个提示
            setProgressVisible(false);
            if (getBaseActivity().errorDialog != null && getBaseActivity().errorDialog.isShowing()) {
                getBaseActivity().errorDialog.dismiss();
            }

            String hintStr = datas.getMsg();

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
            if (PigeonEntity.ID_BREED_PIGEON.equals(mPigeonType)) {
                mViewModel.pigeonType = mViewModel.mSelectTypes_PigeonType.get(0).getTypeID();
                mLvPigeonType.setRightText(mViewModel.mSelectTypes_PigeonType.get(0).getTypeName());
            }
        });

        mViewModel.mDataFootRingState.observe(this, footRingStateEntity -> {
            setProgressVisible(false);
            if(StringUtil.isStringValid(footRingStateEntity.getFootId())
                    && StringUtil.isStringValid(footRingStateEntity.getPigeonID())){
                FootEntity footEntity = new FootEntity();
                footEntity.setPigeonID(footRingStateEntity.getPigeonID());
                footEntity.setFootRingID(Integer.parseInt(footRingStateEntity.getFootId()));
                SetPigeonDeathDialog.show(getFragmentManager(), footEntity, new SetPigeonDeathDialog.OnPigeonDeathClickListener() {
                    @Override
                    public void cancel() {
                        PigeonEntity pigeonEntity = new PigeonEntity();
                        pigeonEntity.setPigeonID(footEntity.getPigeonID());
                        pigeonEntity.setFootRingID(String.valueOf(footEntity.getFootRingID()));
                        IntentBuilder.Builder()
                                .putExtra(IntentBuilder.KEY_DATA, pigeonEntity)
                                .finishForResult(getBaseActivity());
                    }

                    @Override
                    public void setDeathFinish(FootEntity footEntity) {

                    }
                });
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

        if (requestCode == CODE_ADD_PLAY) {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mViewModel.mDataPigeonDetails.getValue())
                    .finishForResult(getBaseActivity());
        }

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == PictureMimeType.ofImage()) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            BaseImgUploadFragment.start(getBaseActivity(),
                    ImgUploadFragment.class,
                    new ImgTypeEntity.Builder()
                            .imgPath(selectList.get(0).getCompressPath())
                            .build(),
                    ImgUploadFragment.CODE_SELECT_COUNTY);
        } else if (requestCode == SelectCountyFragment.CODE_SELECT_COUNTY) {
            try {
                CountyEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mViewModel.countryId = entity.getFootCodeID();
                mLvCountries.setRightText(entity.getCode());
            } catch (Exception e) {
                CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mViewModel.countryId = entity.getFootCodeID();
                mLvCountries.setRightText(entity.getCode());
            }
        } else if (requestCode == ImgUploadFragment.CODE_SELECT_COUNTY) {
            ImgTypeEntity mImgTypeEntity = (ImgTypeEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            List<ImgTypeEntity> imgs = Lists.newArrayList();
            imgs.add(0, mImgTypeEntity);
            mAdapter.addImage(imgs);
            mViewModel.phototypeid = mImgTypeEntity.getImgTypeId();
            mViewModel.images.addAll(Lists.newArrayList(mImgTypeEntity.getImgPath()));
        } else if (requestCode == SelectBloodFragment.CODE_SELECT_BLOOD) {
            SelectTypeEntity blood = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            mViewModel.lineage = blood.getTypeName();
            mLvBlood.setRightText(blood.getTypeName());
        } else if (CODE_SELECT_FATHER == requestCode) {
            PigeonEntity pigeonEntity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            mLvFatherFoot.setRightText(pigeonEntity.getFootRingNum());
            mViewModel.footFather = pigeonEntity.getFootRingNum();
        } else if (CODE_SELECT_MATHER == requestCode) {
            PigeonEntity pigeonEntity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            mLvMotherFoot.setRightText(pigeonEntity.getFootRingNum());
            mViewModel.footMother = pigeonEntity.getFootRingNum();
        } else if (SelectFootRingFragment.CODE_SELECT_FOOT == requestCode) {
            FootEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            mLvRing.setRightText(entity.getFootRingNum());
            mViewModel.foot = entity.getFootRingNum();
        } else if (SelectFootRingFragment.CODE_SELECT_FATHER_FOOT == requestCode) {
            FootEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            mLvFatherFoot.setRightText(entity.getFootRingNum());
            mViewModel.footFather = entity.getFootRingNum();
        } else if (SelectFootRingFragment.CODE_SELECT_MATHER_FOOT == requestCode) {
            FootEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            mLvMotherFoot.setRightText(entity.getFootRingNum());
            mViewModel.footMother = entity.getFootRingNum();
        }
        mViewModel.isCanCommit();
    }
}
