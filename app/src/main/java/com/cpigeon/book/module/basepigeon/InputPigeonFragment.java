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

import com.base.http.ApiResponse;
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
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.InputPigeonViewModel;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.foot.SelectCountyFragment;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.BaseImgUploadFragment;
import com.cpigeon.book.module.photo.ImgUploadFragment;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.module.select.SelectFootRingFragment;
import com.cpigeon.book.module.select.SelectParentFootRingDialog;
import com.cpigeon.book.module.select.SetPigeonDeathDialog;
import com.cpigeon.book.module.select.viewmodel.SelectParentFootRingViewModel;
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
    private boolean IsClick;
    private LineInputListLayout mLlBase;
    private LineInputView mLvPigeonType;
    private LineInputView mLvCountries;
    private LineInputView mLvRing;
    private LineInputView mLvSex;
    private LineInputView mLvBlood;
    private LineInputView mLvFeatherColor;
    private LineInputView mLvState;
    private RelativeLayout mRlAddition, parentRlAddition;
    private LineInputView mLvPigeonName;
    private LineInputView mLvFootVice;
    private LineInputView mLvFootSource;
    protected LineInputView mLvFatherFoot;
    protected LineInputView mLvMotherFoot;
    protected LineInputView mLvFatherFootState;
    protected LineInputView mLvMotherFootState;
    private LineInputView mLvEyeSand;
    private LineInputView mLvBirthTime;
    private LineInputView mLvHangingRingDate;
    private LinearLayout mLlImage;
    private RecyclerView mList;
    private TextView mTvNextStep;
    private ImageView parentImgExpand;
    private ImageView mImgExpand;
    private LineInputListLayout mLlAddition;
    private LineInputListLayout parentLlAddition;

    protected InputPigeonViewModel mViewModel;
    SelectTypeViewModel mSelectTypeViewModel;
    SelectParentFootRingViewModel mSelectParentFootRingViewModel;
    SelectImageAdapter2 mAdapter;


    ObjectAnimator mOpenAnim;
    ObjectAnimator mCloseAnim;
    boolean mIsAdditionOpen = false;
    boolean parentIsAdditionOpen = false;
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
        mSelectParentFootRingViewModel = new SelectParentFootRingViewModel();
        mSexType = getBaseActivity().getIntent().getStringExtra(KEY_PIGEON_SEX_TYPE);
        mPigeonType = getBaseActivity().getIntent().getStringExtra(KEY_PIGEON_TYPE);
        mViewModel.sonFootId = getBaseActivity().getIntent().getStringExtra(KEY_SON_FOOT_ID);
        mViewModel.sonPigeonId = getBaseActivity().getIntent().getStringExtra(KEY_SON_PIGEON_ID);
        mViewModel.pigeonId = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_DATA);
        initViewModels(mViewModel, mSelectTypeViewModel, mSelectParentFootRingViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_pigeon, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findView();

        if (StringUtil.isStringValid(mViewModel.pigeonId)) {
            //修改
            setTitle(R.string.text_pigeon_edit);

            mLlImage.setVisibility(View.GONE);
        } else {
            //添加
            setTitle(R.string.text_pigeon_input);
        }

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
            mViewModel.mPigeonEntity.setPigeonSexID(PigeonEntity.ID_MALE);
            mViewModel.sexId = (PigeonEntity.ID_MALE);
            mLvSex.setRightText(Utils.getString(R.string.text_male_a));
            mLvSex.setRightImageVisible(false);
        } else if (TYPE_SEX_FEMALE.equals(mSexType)) {
            mViewModel.mPigeonEntity.setPigeonSexID(PigeonEntity.ID_FEMALE);
            mViewModel.sexId = (PigeonEntity.ID_FEMALE);
            mLvSex.setRightText(Utils.getString(R.string.text_female_a));
            mLvSex.setRightImageVisible(false);
        }

        if (StringUtil.isStringValid(mViewModel.pigeonId)) {
            setProgressVisible(true);
            mViewModel.getPigeonDetails();
        } else {
            mViewModel.stateId = "35";
            mLvState.setRightText("现役在棚");
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
        IsCanCommit();
    }

    private void setExpandAnim() {
        mLlAddition.setVisibility(View.GONE);
        parentLlAddition.setVisibility(View.GONE);
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


                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_PigeonType), 0, new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {

                        mLvPigeonType.setRightText(mViewModel.mSelectTypes_PigeonType.get(index).getTypeName());
                        mViewModel.pigeonType = mViewModel.mSelectTypes_PigeonType.get(index).getTypeID();
                        IsCanCommit();
                    }
                });

            } catch (Exception e) {
                mSelectTypeViewModel.getPigeonType();
            }
        });

        //国家
        mLvCountries.setOnRightClickListener(v -> {
            SearchFragmentParentActivity.start(getBaseActivity(), SelectCountyFragment.class, SelectCountyFragment.CODE_SELECT_COUNTY, null);
            IsCanCommit();
        });

        //足环
        mLvRing.setOnRightClickListener(lineInputView -> {

            InputSingleFootDialog.show(getFragmentManager(), mLvRing.getContent(), mViewModel.isChina(), null, foot -> {
                mLvRing.setRightText(foot);
                mViewModel.foot = foot;
                IsCanCommit();
                setProgressVisible(true);
                mViewModel.getFootRingState();


            });
        });

        //性别
        mLvSex.setOnRightClickListener(lineInputView -> {

            if (!StringUtil.isStringValid(mSexType)) {
                try {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_Sex), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mLvSex.setRightText(mViewModel.mSelectTypes_Sex.get(index).getTypeName());
                            mViewModel.sexId = mViewModel.mSelectTypes_Sex.get(index).getTypeID();
                            IsCanCommit();
                        }
                    });
                } catch (Exception e) {
                    mSelectTypeViewModel.getSelectType_Sex();
                }
            }

        });

        //血统
        mLvBlood.setOnRightClickListener(lineInputView -> {

            BaseInputDialog.show(getFragmentManager(), R.string.text_blood, R.string.text_blood_bank, mLvBlood.getContent(), 0, content -> {
                mViewModel.lineage = content;
                mLvBlood.setRightText(content);
                IsCanCommit();
            }, () -> {
                SelectBloodFragment.start(getBaseActivity());
            });
        });


        mLvFeatherColor.setOnRightClickListener(lineInputView -> {
            mBaseInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                    , R.string.text_feather_color, R.string.text_feather_color_bank, mLvFeatherColor.getContent(), 0, content -> {
                        mBaseInputDialog.hide();
                        mViewModel.featherColor = content;
                        mLvFeatherColor.setRightText(content);
                        IsCanCommit();
                    }, () -> {
                        mBaseInputDialog.hide();

                        if (!Lists.isEmpty(mViewModel.mSelectTypes_FeatherColor)) {
                            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_FeatherColor), 0, new OptionPicker.OnOptionPickListener() {
                                @Override
                                public void onOptionPicked(int index, String item) {
                                    mViewModel.featherColor = mViewModel.mSelectTypes_FeatherColor.get(index).getTypeName();
                                    mLvFeatherColor.setContent(mViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
                                    IsCanCommit();
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
                        IsCanCommit();
                    }
                });
            } catch (Exception e) {
                mSelectTypeViewModel.getSelectType_State();
            }
        });
        mLvFatherFootState.setOnRightClickListener(lineInputView -> {
            try {
                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_State), 0, new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        mViewModel.pigeonFatherStateName = mViewModel.mSelectTypes_State.get(index).getTypeName();
                        mViewModel.pigeonFatherStateId = mViewModel.mSelectTypes_State.get(index).getTypeID();
                        mLvFatherFootState.setContent(mViewModel.mSelectTypes_State.get(index).getTypeName());
                        mViewModel.isFatherCanCommit();
                    }
                });
            } catch (Exception e) {
                mSelectTypeViewModel.getSelectType_State();
            }
        });

        mLvMotherFootState.setOnRightClickListener(lineInputView -> {
            try {
                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_State), 0, new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        mViewModel.pigeonMotherStateName = mViewModel.mSelectTypes_State.get(index).getTypeName();
                        mViewModel.pigeonMotherStateId = mViewModel.mSelectTypes_State.get(index).getTypeID();
                        mLvMotherFootState.setContent(mViewModel.mSelectTypes_State.get(index).getTypeName());
                        mViewModel.isMotherCanCommit();
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
                if (!foot.equals(StringUtil.emptyString())) {
                    setProgressVisible(true);
                    mViewModel.pigeonFatherId= StringUtil.emptyString();
                    mViewModel.footFatherId = StringUtil.emptyString();
                    mViewModel.footFather = foot;
                    mSelectParentFootRingViewModel.mSexId = PigeonEntity.ID_MALE;
                    mSelectParentFootRingViewModel.mFootNumber = foot;
                    mSelectParentFootRingViewModel.getPgieon();
                } else {
                    mLvFatherFoot.setRightText(StringUtil.emptyString());
                    mLvFatherFootState.setRightText(StringUtil.emptyString());
                    mViewModel.footFather = StringUtil.emptyString();
                    mViewModel.footFatherId = StringUtil.emptyString();
                    mViewModel.pigeonFatherStateId = StringUtil.emptyString();
                    mViewModel.pigeonFatherStateName = StringUtil.emptyString();
                    mLvFatherFootState.setClickable(false);
                    IsCanCommit();
                }

            });
        });

        mLvMotherFoot.setOnRightClickListener(lineInputView -> {

            InputSingleFootDialog.show(getFragmentManager(), mLvMotherFoot.getContent(), mViewModel.isChina(), dialog -> {
                SelectFootRingFragment.start(getBaseActivity(), false
                        , SelectFootRingFragment.CODE_SELECT_MATHER_FOOT, PigeonEntity.ID_FEMALE, PigeonEntity.ID_NONE_SEX);
            }, foot -> {
                if (!foot.equals(StringUtil.emptyString())) {
                    mViewModel.footMother = foot;
                    setProgressVisible(true);
                    mViewModel.pigeonMotherId= StringUtil.emptyString();
                    mViewModel.footMotherId = StringUtil.emptyString();
                    mSelectParentFootRingViewModel.mSexId = PigeonEntity.ID_FEMALE;
                    mSelectParentFootRingViewModel.mFootNumber = foot;
                    mSelectParentFootRingViewModel.getPgieon();
                } else {

                    mLvMotherFoot.setRightText(StringUtil.emptyString());
                    mLvMotherFootState.setRightText(StringUtil.emptyString());
                    mViewModel.footMother = StringUtil.emptyString();
                    mViewModel.footMotherId = StringUtil.emptyString();
                    mViewModel.pigeonMotherStateId = StringUtil.emptyString();
                    mViewModel.pigeonMotherStateName = StringUtil.emptyString();
                    mLvMotherFootState.setClickable(false);
                    IsCanCommit();
                }


            });
        });
        //眼砂
        mLvEyeSand.setOnRightClickListener(lineInputView -> {
            try {
                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_EyeSand), 0, new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        mViewModel.eyeSandId = mViewModel.mSelectTypes_EyeSand.get(index).getTypeID();
                        IsCanCommit();
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

        parentRlAddition.setOnClickListener(v -> {
            if (parentIsAdditionOpen) {
                parentImgExpand.setRotation(0);
                parentLlAddition.setVisibility(View.GONE);
                parentIsAdditionOpen = false;
            } else {
                parentImgExpand.setRotation(180);
                parentLlAddition.setVisibility(View.VISIBLE);
                parentIsAdditionOpen = true;
            }
        });
        mTvNextStep.setOnClickListener(v -> {
            if (IsClick) {
                setProgressVisible(true);
                if (!StringUtil.isStringValid(mViewModel.pigeonId)) {

                    mViewModel.addPigeon();
                } else {
                    mViewModel.modifyBreedPigeonEntry();
                }
            } else {
                TextViewUtil.DialogShowNullMsg(getBaseActivity(),mLvPigeonType,mLvCountries,mLvRing,mLvSex,mLvBlood,mLvEyeSand,mLvFeatherColor,mLvState);
                   String Msg;
                    if(StringUtil.isStringValid(mLvFatherFoot.getContent())&&!StringUtil.isStringValid(mLvFatherFootState.getContent()))
                {
                    Msg="请输入"+mLvFatherFootState.getTitle()+"!";
                    DialogUtils.createHintDialog(getBaseActivity(), Msg);
                    return;
                }
                if(StringUtil.isStringValid(mLvMotherFoot.getContent())&&!StringUtil.isStringValid(mLvMotherFootState.getContent()))
                {
                    Msg="请输入"+mLvMotherFootState.getTitle()+"!";
                    DialogUtils.createHintDialog(getBaseActivity(), Msg);
                    return;
                }
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
        parentRlAddition = findViewById(R.id.rlAddition2);
        mRlAddition = findViewById(R.id.rlAddition);
        mLvPigeonName = findViewById(R.id.lvPigeonName);
        mLvFootVice = findViewById(R.id.lv_foot_vice);
        mLvFootSource = findViewById(R.id.lv_foot_source);
        mLvFatherFoot = findViewById(R.id.lvFatherFoot);
        mLvFatherFootState = findViewById(R.id.lvFatherState);

        mLvMotherFoot = findViewById(R.id.lvMotherFoot);
        mLvMotherFootState = findViewById(R.id.lvMotherState);

        mLvFatherFootState = findViewById(R.id.lvFatherState);
        mLvMotherFoot = findViewById(R.id.lvMotherFoot);
        mLvMotherFootState = findViewById(R.id.lvMotherState);
        mLvEyeSand = findViewById(R.id.lvEyeSand);
        mLvBirthTime = findViewById(R.id.lvBirthTime);
        mLvHangingRingDate = findViewById(R.id.lv_hanging_ring_date);
        mLlImage = findViewById(R.id.llImage);
        mList = findViewById(R.id.list);
        mTvNextStep = findViewById(R.id.tv_next_step);
        mLlAddition = findViewById(R.id.llAddition);
        parentLlAddition = findViewById(R.id.llAddition2);
        mImgExpand = findViewById(R.id.imgExpand);
        parentImgExpand = findViewById(R.id.imgExpand2);
    }

    @Override
    protected void initObserve() {

        mViewModel.isCanCommit.observe(this, aBoolean -> {

            IsClick = aBoolean;
        });

        //详情
        mViewModel.mDataPigeonDetails.observe(this, breedPigeonEntity -> {

            mLvPigeonType.setRightText(breedPigeonEntity.getTypeName());
            mLvCountries.setRightText(breedPigeonEntity.getFootCode());
            mLvRing.setRightText(breedPigeonEntity.getFootRingNum());
            mLvFootVice.setRightText(breedPigeonEntity.getFootRingIDToNum());
            mLvFootSource.setRightText(breedPigeonEntity.getSourceName());
            mLvFatherFoot.setRightText(breedPigeonEntity.getMenFootRingNum());
            mLvMotherFoot.setRightText(breedPigeonEntity.getWoFootRingNum());
            mLvFatherFootState.setRightText(breedPigeonEntity.getMenPigeonStateName());
            mLvMotherFootState.setRightText(breedPigeonEntity.getWoPigeonStateName());
            mLvPigeonName.setRightText(breedPigeonEntity.getPigeonName());
            mLvSex.setRightText(breedPigeonEntity.getPigeonSexName());
            mLvFeatherColor.setRightText(breedPigeonEntity.getPigeonPlumeName());
            mLvEyeSand.setRightText(breedPigeonEntity.getPigeonEyeName());
            mLvBirthTime.setRightText(breedPigeonEntity.getOutShellTime());
            mLvHangingRingDate.setRightText(breedPigeonEntity.getFootRingTime());
            mLvBlood.setRightText(breedPigeonEntity.getPigeonBloodName());

            mViewModel.pigeonMotherStateId = breedPigeonEntity.getWoPigeonStateID();
            mViewModel.pigeonFatherStateId = breedPigeonEntity.getMenPigeonStateID();
            mViewModel.llHangingRingDate = breedPigeonEntity.getFootRingTime();
            mViewModel.pigeonMotherStateName = breedPigeonEntity.getWoPigeonStateName();
            mViewModel.pigeonFatherStateName = breedPigeonEntity.getMenPigeonStateName();
            mViewModel.pigeonType = breedPigeonEntity.getTypeID();
            mViewModel.countryId = breedPigeonEntity.getFootCodeID();
            mViewModel.foot = breedPigeonEntity.getFootRingNum();
            mViewModel.footVice = breedPigeonEntity.getFootRingIDToNum();
            mViewModel.sourceId = breedPigeonEntity.getSourceID();
            mViewModel.footFather = breedPigeonEntity.getMenFootRingNum();
            mViewModel.footFatherId = breedPigeonEntity.getMenFootRingID();
            mViewModel.pigeonFatherId = breedPigeonEntity.getMenPigeonID();
            mViewModel.pigeonFatherStateId = breedPigeonEntity.getMenPigeonStateID();

            mViewModel.footMother = breedPigeonEntity.getWoFootRingNum();
            mViewModel.footMotherId = breedPigeonEntity.getWoFootRingID();
            mViewModel.pigeonMotherId = breedPigeonEntity.getWoPigeonID();
            mViewModel.pigeonMotherStateId = breedPigeonEntity.getWoPigeonStateID();
            mViewModel.pigeonName = breedPigeonEntity.getPigeonName();
            mViewModel.sexId = breedPigeonEntity.getPigeonSexID();
            mViewModel.featherColor = breedPigeonEntity.getPigeonPlumeName();
            mViewModel.eyeSandId = breedPigeonEntity.getPigeonEyeID();
            mViewModel.theirShellsDate = breedPigeonEntity.getOutShellTime();
            mViewModel.lineage = breedPigeonEntity.getPigeonBloodName();
            mViewModel.stateId = breedPigeonEntity.getStateID();
            mLvState.setRightText(breedPigeonEntity.getStateName());
            if (mViewModel.footMother.equals(StringUtil.emptyString())) {
                mLvMotherFootState.setClickable(false);

            }
            if (mViewModel.footFather.equals(StringUtil.emptyString())) {
                mLvFatherFootState.setClickable(false);
            }
//        if (   mViewModel.pigeonMotherStateId.equals(StringUtil.emptyString()))
//        {
//            mViewModel.pigeonMotherStateId="36";
//        }
//            if (   mViewModel.pigeonFatherStateId.equals(StringUtil.emptyString()))
//            {
//                mViewModel.pigeonMotherStateId="36";
//            }
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

            IsCanCommit();
            setProgressVisible(false);
        });

        //种鸽录入、修改
        mViewModel.mDataPigeon.observe(this, data -> {
            setProgressVisible(false);


            mDataPigeonResult(data);
        });

        //性别
        mSelectTypeViewModel.mSelectType_Sex.observe(this, selectTypeEntities -> {

            mViewModel.mSelectTypes_Sex = selectTypeEntities;
            mViewModel.mSelectTypes_Sex = selectTypeEntities;
            if (StringUtil.isStringValid(mSexType)) {
                mViewModel.mSelectTypes_Sex.remove(0);
            }
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

            if (footRingStateEntity.getFootRingID() != 0 && footRingStateEntity.getPigeonID() != 0) {
                SetPigeonDeathDialog.show(getFragmentManager(), String.valueOf(footRingStateEntity.getFootRingID())
                        , String.valueOf(footRingStateEntity.getPigeonID()), new SetPigeonDeathDialog.OnPigeonDeathClickListener() {
                            @Override
                            public void cancel() {
                                mLvRing.setRightText(StringUtil.emptyString());
                            }

                            @Override
                            public void userFootRing(PigeonEntity pigeonEntity) {
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

        mSelectParentFootRingViewModel.mDataPigeon.observe(this, pigeonEntities -> {
            setProgressVisible(false);

            if (Lists.isEmpty(pigeonEntities)) {
                if (PigeonEntity.ID_MALE.equals(mSelectParentFootRingViewModel.mSexId)) {
                    mLvFatherFoot.setRightText(mSelectParentFootRingViewModel.mFootNumber);
                    mViewModel.footFather = mSelectParentFootRingViewModel.mFootNumber;
                    mViewModel.pigeonFatherStateId=StringUtil.emptyString();
                    mLvFatherFootState.setRightText(StringUtil.emptyString());
                    mLvFatherFootState.setClickable(true);
                } else {
                    mLvMotherFoot.setRightText(mSelectParentFootRingViewModel.mFootNumber);
                    mViewModel.footMother = mSelectParentFootRingViewModel.mFootNumber;
                    mViewModel.pigeonMotherStateId=StringUtil.emptyString();
                    mLvMotherFootState.setRightText(StringUtil.emptyString());
                    mLvMotherFootState.setClickable(true);
                }
                IsCanCommit();
                return;
            }


            SelectParentFootRingDialog.show(getFragmentManager(), pigeonEntities, mSelectParentFootRingViewModel.mSexId, pigeonEntity -> {
                if (PigeonEntity.ID_MALE.equals(mSelectParentFootRingViewModel.mSexId)) {
//                    mLvFatherFoot.setRightText(pigeonEntity.getFootRingNum());
                    mLvFatherFootState.setClickable(true);
                    mViewModel.footFatherId = pigeonEntity.getFootRingID();
                    mViewModel.footFather = pigeonEntity.getFootRingNum();
                    mViewModel.pigeonFatherId = pigeonEntity.getPigeonID();
                    mViewModel.pigeonFatherStateName = pigeonEntity.getStateName();
                    mViewModel.pigeonFatherStateId = String.valueOf(pigeonEntity.getStateID());
                    mLvFatherFootState.setRightText(pigeonEntity.getStateName());
                    mLvFatherFoot.setRightText(pigeonEntity.getFootRingNum());
                } else {
                    mLvMotherFootState.setClickable(true);
                    mLvMotherFoot.setRightText(pigeonEntity.getFootRingNum());
                    mViewModel.footMotherId = pigeonEntity.getFootRingID();
                    mViewModel.footMother = pigeonEntity.getFootRingNum();
                    mViewModel.pigeonMotherId = pigeonEntity.getPigeonID();
                    mViewModel.pigeonMotherStateId = String.valueOf(pigeonEntity.getStateID());
                    mViewModel.pigeonMotherStateName = pigeonEntity.getStateName();
                    mLvMotherFootState.setRightText(pigeonEntity.getStateName());
                    mLvMotherFoot.setRightText(pigeonEntity.getFootRingNum());
                }
                IsCanCommit();
            });

        });
    }

    protected void mDataPigeonResult(ApiResponse<PigeonEntity> datas) {
        EventBus.getDefault().post(new PigeonAddEvent());
        PigeonEntity o = datas.data;
        String msg = datas.msg;
        DialogUtils.createDialogReturn(getBaseActivity(), msg, sweetAlertDialog -> {
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
        setProgressVisible(false);
        if (requestCode == CODE_ADD_PLAY) {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mViewModel.mPigeonEntity)
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
                mLvRing.setRightText(StringUtil.emptyString());
            } catch (Exception e) {
                CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mViewModel.countryId = entity.getFootCodeID();
                mLvCountries.setRightText(entity.getCode());
                mLvRing.setRightText(StringUtil.emptyString());
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
            mLvMotherFootState.setRightText(pigeonEntity.getStateName());
            mViewModel.footMother = pigeonEntity.getFootRingNum();
        } else if (SelectFootRingFragment.CODE_SELECT_FOOT == requestCode) {
            FootEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            mLvRing.setRightText(entity.getFootRingNum());
            mViewModel.foot = entity.getFootRingNum();
            setProgressVisible(false);
            mViewModel.getFootRingState();
        } else if (SelectFootRingFragment.CODE_SELECT_FATHER_FOOT == requestCode) {
            PigeonEntity entity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            mLvFatherFoot.setRightText(entity.getFootRingNum());
            mLvFatherFootState.setRightText(entity.getStateName());

            mViewModel.footFather = entity.getFootRingNum();
            mViewModel.footFatherId = String.valueOf(entity.getFootRingID());
            mViewModel.pigeonFatherId = entity.getPigeonID();
            mViewModel.pigeonFatherStateId = String.valueOf(entity.getStateID());
            mViewModel.pigeonFatherStateName = entity.getStateName();

        } else if (SelectFootRingFragment.CODE_SELECT_MATHER_FOOT == requestCode) {
            PigeonEntity entity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            mLvMotherFoot.setRightText(entity.getFootRingNum());
            mLvMotherFootState.setRightText(entity.getStateName());
            mViewModel.footMother = entity.getFootRingNum();
            mViewModel.footMotherId = String.valueOf(entity.getFootRingID());
            mViewModel.pigeonMotherId = entity.getPigeonID();
            mViewModel.pigeonMotherStateId = String.valueOf(entity.getStateID());
            mViewModel.pigeonMotherStateName = entity.getStateName();

        }
        IsCanCommit();
    }


    protected void IsCanCommit() {
        if (mLvMotherFoot.getContent().equals(StringUtil.emptyString()) && mLvFatherFoot.getContent().equals(StringUtil.emptyString())) {
            mViewModel.isCanCommit();
        } else {
            if (!mLvMotherFoot.getContent().equals(StringUtil.emptyString()) && !mLvFatherFoot.getContent().equals(StringUtil.emptyString())) {
                mViewModel.isallCanCommit();
            } else {
                if (!mLvMotherFoot.getContent().equals(StringUtil.emptyString())) {
                    mViewModel.isMotherCanCommit();
                }
                if (!mLvFatherFoot.getContent().equals(StringUtil.emptyString())) {
                    mViewModel.isFatherCanCommit();
                }
            }
        }
    }

}
