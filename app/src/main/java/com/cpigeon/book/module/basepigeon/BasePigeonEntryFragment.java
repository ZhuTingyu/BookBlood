package com.cpigeon.book.module.basepigeon;

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
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BasePigeonViewModel;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.foot.SelectCountyFragment;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.BaseImgUploadFragment;
import com.cpigeon.book.module.photo.ImgUploadFragment;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;
import com.cpigeon.book.widget.selectImagesView.SelectImageAdapter2;
import com.kyleduo.switchbutton.SwitchButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 鸽子录入fragment
 * Created by Administrator on 2018/9/8.
 */

public class BasePigeonEntryFragment extends BaseBookFragment {

    @BindView(R.id.llz)
    LineInputListLayout mLlRoot;
    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.ll_pigeon_type)
    protected LineInputView llPigeonType;//鸽子类型  ：种鸽 赛鸽
    @BindView(R.id.ll_countries)
    protected LineInputView llCountries;
    @BindView(R.id.ll_foot)
    protected LineInputView llFoot;
    @BindView(R.id.ll_foot_vice)
    protected LineInputView llFootVice;
    @BindView(R.id.ll_foot_source)
    protected LineInputView llFootSource;
    @BindView(R.id.ll_foot_father)
    protected LineInputView llFootFather;
    @BindView(R.id.ll_foot_mother)
    protected LineInputView llFootMother;
    @BindView(R.id.ll_pigeon_name)
    protected LineInputView llPigeonName;
    @BindView(R.id.ll_sex)
    protected LineInputView llSex;
    @BindView(R.id.ll_feather_color)
    protected LineInputView llFeatherColor;
    @BindView(R.id.ll_eye_sand)
    protected LineInputView llEyeSand;
    //    @BindView(R.id.ll_their_shells_date)
//    protected LineInputView llTheirShellsDate;//出壳日期
    //    @BindView(R.id.ll_hanging_ring_date)
//    protected LineInputView llHangingRingDate;//挂环日期
    @BindView(R.id.ll_lineage)
    protected LineInputView llLineage;
    @BindView(R.id.ll_state)
    protected LineInputView llState;//状态
    @BindView(R.id.sb_dont_disturb)
    SwitchButton sbDontDisturb;
    @BindView(R.id.ll_deal_price)
    LineInputView llDealPrice;
    @BindView(R.id.tv_next_step)
    protected TextView tvNextStep;

    protected SelectImageAdapter2 mAdapter;
    protected SelectTypeViewModel mSelectTypeViewModel;
    protected BasePigeonViewModel mBasePigeonViewModel;
    protected String sexType;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breed_pigeon_entry, container, false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mViewModel = new FootAddMultiViewModel(getBaseActivity());
        mSelectTypeViewModel = new SelectTypeViewModel();
        onAttachs();
    }

    protected void onAttachs() {


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        composite.add(RxUtils.delayed(50, aLong -> {
            mLlRoot.setLineInputViewState(false);
        }));

        list.setLayoutManager(new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new SelectImageAdapter2(getBaseActivity());
        list.setAdapter(mAdapter);
        mAdapter.setOnSelectImageClickListener(new SelectImageAdapter2.OnSelectImageClickListener() {
            @Override
            public void onAddImage() {
                String[] chooseWays = getResources().getStringArray(R.array.array_choose_photo);
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                    String way = chooseWays[p];
                    if (Utils.getString(R.string.text_open_gallery).equals(way)) {
//                        PictureSelectUtil.showChooseImage(getBaseActivity(), PictureMimeType.ofImage(), SelectImageAdapter.MAX_NUMBER - mBreedPigeonEntryViewModel.images.size());
                        PictureSelectUtil.showChooseImage(getBaseActivity(), PictureMimeType.ofImage(), 1);
                    } else if (Utils.getString(R.string.text_open_camera).equals(way)) {
                        PictureSelectUtil.openCamera(getBaseActivity(), false);
                    }
                });
            }

            @Override
            public void onImageDelete(int position) {
//                mBreedPigeonEntryViewModel.images.remove(position);
            }
        });


        llCountries.setRightText("CHN");


        mSelectTypeViewModel.getSelectType_FeatherColor();
        mSelectTypeViewModel.getSelectType_lineage();
        mSelectTypeViewModel.getSelectType_eyeSand();
        mSelectTypeViewModel.getSelectType_State();
        mSelectTypeViewModel.getSelectType_PigeonSource();


        // 初始化出壳日期
        //llTheirShellsDate.setContent(TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDD));
        mBasePigeonViewModel.theirShellsDate = TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDD);

        //初始化 挂环日期
//        llHangingRingDate.setContent(TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDD));
        mBasePigeonViewModel.llHangingRingDate = TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDD);
        btnState();

        initData();

        if (!StringUtil.isStringValid(sexType)) {
            mSelectTypeViewModel.getSelectType_Sex();

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == PictureMimeType.ofImage()) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            BaseImgUploadFragment.start(getBaseActivity(),
                    ImgUploadFragment.class,
                    new ImgTypeEntity.Builder()
                            .imgPath(selectList.get(0).getCompressPath())
                            .build(),
                    ImgUploadFragment.CODE_SELECT_COUNTY);
        }

        switch (requestCode) {
            case SelectCountyFragment.CODE_SELECT_COUNTY:
                try {
                    CountyEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                    mBasePigeonViewModel.countryId = entity.getSort();
                    llCountries.setRightText(entity.getCode());
                } catch (Exception e) {
                    CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                    mBasePigeonViewModel.countryId = entity.getFootCodeID();
                    llCountries.setRightText(entity.getCode());
                }

                break;

            case ImgUploadFragment.CODE_SELECT_COUNTY:
                ImgTypeEntity mImgTypeEntity = (ImgTypeEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);

                List<ImgTypeEntity> imgs = Lists.newArrayList();
                imgs.add(0, mImgTypeEntity);
                mAdapter.addImage(imgs);

                mBasePigeonViewModel.phototypeid = mImgTypeEntity.getImgTypeId();
                mBasePigeonViewModel.images.addAll(Lists.newArrayList(mImgTypeEntity.getImgPath()));

                break;
        }
    }


    @Override
    protected void initObserve() {


        //性别
        mSelectTypeViewModel.mSelectType_Sex.observe(this, selectTypeEntities -> {
            mBasePigeonViewModel.mSelectTypes_Sex = selectTypeEntities;
            try {
                mBasePigeonViewModel.sexId = mBasePigeonViewModel.mSelectTypes_Sex.get(0).getTypeID();
                llSex.setContent(mBasePigeonViewModel.mSelectTypes_Sex.get(0).getTypeName());
                btnState();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        //雨色
        mSelectTypeViewModel.mSelectType_FeatherColor.observe(this, selectTypeEntities -> {
            mBasePigeonViewModel.mSelectTypes_FeatherColor = selectTypeEntities;
            try {
                mBasePigeonViewModel.featherColor = mBasePigeonViewModel.mSelectTypes_FeatherColor.get(0).getTypeName();
                llFeatherColor.setContent(mBasePigeonViewModel.mSelectTypes_FeatherColor.get(0).getTypeName());
                btnState();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //眼沙
        mSelectTypeViewModel.mSelectType_EyeSand.observe(this, selectTypeEntities -> {
            mBasePigeonViewModel.mSelectTypes_EyeSand = selectTypeEntities;
            try {
                mBasePigeonViewModel.eyeSandId = mBasePigeonViewModel.mSelectTypes_EyeSand.get(0).getTypeID();
                llEyeSand.setContent(mBasePigeonViewModel.mSelectTypes_EyeSand.get(0).getTypeName());
                btnState();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        //血统
        mSelectTypeViewModel.mSelectType_Lineage.observe(this, selectTypeEntities -> {
            mBasePigeonViewModel.mSelectTypes_Lineage = selectTypeEntities;

            try {
                mBasePigeonViewModel.lineage = mBasePigeonViewModel.mSelectTypes_Lineage.get(0).getTypeName();
                llLineage.setContent(mBasePigeonViewModel.mSelectTypes_Lineage.get(0).getTypeName());
                btnState();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //状态
        mSelectTypeViewModel.mSelectType_State.observe(this, selectTypeEntities -> {
            mBasePigeonViewModel.mSelectTypes_State = selectTypeEntities;
            try {
                mBasePigeonViewModel.stateId = mBasePigeonViewModel.mSelectTypes_State.get(0).getTypeID();
                llState.setContent(mBasePigeonViewModel.mSelectTypes_State.get(0).getTypeName());
                btnState();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //来源
        mSelectTypeViewModel.mSelectType_Pigeon_Source.observe(this, selectTypeEntities -> {
            setProgressVisible(false);
            mBasePigeonViewModel.mSelectTypes_Source = selectTypeEntities;
            try {
                mBasePigeonViewModel.sourceId = mBasePigeonViewModel.mSelectTypes_Source.get(0).getTypeID();
                llFootSource.setContent(mBasePigeonViewModel.mSelectTypes_Source.get(0).getTypeName());
                btnState();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private BaseInputDialog mDialogLineage;
    private BaseInputDialog mDialogMoney;

    @OnClick({R.id.ll_countries, R.id.ll_foot, R.id.ll_foot_vice, R.id.ll_foot_source, R.id.ll_foot_father, R.id.ll_foot_mother, R.id.ll_pigeon_name, R.id.ll_sex, R.id.ll_feather_color, R.id.ll_eye_sand, R.id.ll_lineage, R.id.ll_state, R.id.sb_dont_disturb, R.id.ll_deal_price, R.id.tv_next_step, R.id.llz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_countries:
                //国家
                SearchFragmentParentActivity.start(getBaseActivity(), SelectCountyFragment.class, SelectCountyFragment.CODE_SELECT_COUNTY, null);
                break;
            case R.id.ll_foot:
                //足环号
//                List<String> foots = mViewModel.getFoots();
                InputSingleFootDialog dialog = new InputSingleFootDialog();
                dialog.setFootNumber(llFoot.getContent());
                dialog.setOnFootStringFinishListener(foot -> {
                    llFoot.setRightText(foot);
                    mBasePigeonViewModel.foot = foot;
                    btnState();
                });
                dialog.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.ll_foot_vice:
                //副环
                InputSingleFootDialog dialog2 = new InputSingleFootDialog();
                dialog2.setFootNumber(llFootVice.getContent());
                dialog2.setOnFootStringFinishListener(foot -> {
                    llFootVice.setRightText(foot);
                    mBasePigeonViewModel.footVice = foot;
                });
                dialog2.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.ll_foot_source:
                //来源
                if (!Lists.isEmpty(mBasePigeonViewModel.mSelectTypes_Source)) {
                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
                            , SelectTypeEntity.getTypeNames(mBasePigeonViewModel.mSelectTypes_Source), p -> {
                                mBasePigeonViewModel.sourceId = mBasePigeonViewModel.mSelectTypes_Source.get(p).getTypeID();
                                llFootSource.setContent(mBasePigeonViewModel.mSelectTypes_Source.get(p).getTypeName());
                                btnState();
                            });
                } else {
                    mSelectTypeViewModel.getSelectType_PigeonSource();
                }


                break;
            case R.id.ll_foot_father:
                //父足环
                InputSingleFootDialog dialog3 = new InputSingleFootDialog();
                dialog3.setFootNumber(llFootFather.getContent());
                dialog3.setOnFootStringFinishListener(foot -> {
                    llFootFather.setRightText(foot);
                    mBasePigeonViewModel.footFather = foot;
                });
                dialog3.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.ll_foot_mother:
                //母足环
                InputSingleFootDialog dialog4 = new InputSingleFootDialog();
                dialog4.setFootNumber(llFootMother.getContent());
                dialog4.setOnFootStringFinishListener(foot -> {
                    llFootMother.setRightText(foot);
                    mBasePigeonViewModel.footMother = foot;
                });
                dialog4.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.ll_pigeon_name:
                //鸽名
                mDialogMoney = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_pleas_input_pigeon_name, llPigeonName.getContent(), InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mBasePigeonViewModel.pigeonName = content;
                            llPigeonName.setRightText(content);
                            mDialogMoney.hide();
                        }, null);

                break;
            case R.id.ll_sex:

                if (StringUtil.isStringValid(sexType)) return;
                //性别
                if (!Lists.isEmpty(mBasePigeonViewModel.mSelectTypes_Sex)) {
                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
                            , SelectTypeEntity.getTypeNames(mBasePigeonViewModel.mSelectTypes_Sex), p -> {
                                mBasePigeonViewModel.sexId = mBasePigeonViewModel.mSelectTypes_Sex.get(p).getTypeID();
                                llSex.setContent(mBasePigeonViewModel.mSelectTypes_Sex.get(p).getTypeName());
                                btnState();
                            });
                } else {
                    mSelectTypeViewModel.getSelectType_Sex();
                }

                break;
            case R.id.ll_feather_color:
                //羽色
                mDialogLineage = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_feather_color, llFeatherColor.getContent(),0, content -> {
                            mDialogLineage.hide();
                            mBasePigeonViewModel.featherColor = content;
                            llFeatherColor.setContent(content);
                            btnState();
                        }, () -> {
                            mDialogLineage.hide();

                            if (!Lists.isEmpty(mBasePigeonViewModel.mSelectTypes_FeatherColor)) {
                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBasePigeonViewModel.mSelectTypes_FeatherColor), 0, new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index, String item) {
                                        mBasePigeonViewModel.featherColor = mBasePigeonViewModel.mSelectTypes_FeatherColor.get(index).getTypeName();
                                        llFeatherColor.setContent(mBasePigeonViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
                                        btnState();
                                    }
                                });
                            } else {
                                mSelectTypeViewModel.getSelectType_FeatherColor();
                            }
                        });

                break;
            case R.id.ll_eye_sand:
                //眼砂
                if (!Lists.isEmpty(mBasePigeonViewModel.mSelectTypes_EyeSand)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBasePigeonViewModel.mSelectTypes_EyeSand), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mBasePigeonViewModel.eyeSandId = mBasePigeonViewModel.mSelectTypes_EyeSand.get(index).getTypeID();
                            llEyeSand.setContent(mBasePigeonViewModel.mSelectTypes_EyeSand.get(index).getTypeName());
                            btnState();
                        }
                    });
                } else {
                    mSelectTypeViewModel.getSelectType_eyeSand();
                }
                break;
//            case R.id.ll_their_shells_date:
//                //出壳日期
//                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
//                    //llTheirShellsDate.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
//                    mBasePigeonViewModel.theirShellsDate = year + "-" + monthOfYear + "-" + dayOfMonth;
//                    btnState();
//                });
//                break;

//            case R.id.ll_hanging_ring_date:
//                //挂环日期
//                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
//                    llHangingRingDate.setContent(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                    mBasePigeonViewModel.llHangingRingDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//                    btnState();
//                });
//                break;
            case R.id.ll_lineage:
                //血统
                mDialogLineage = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_pigeon_lineage,llLineage.getContent(), 0, content -> {
                            mDialogLineage.hide();
                            mBasePigeonViewModel.lineage = content;
                            llLineage.setRightText(content);
                            btnState();
                        }, () -> {
                            mDialogLineage.hide();

                            if (!Lists.isEmpty(mBasePigeonViewModel.mSelectTypes_Lineage)) {
                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBasePigeonViewModel.mSelectTypes_Lineage), 0, new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index, String item) {
                                        mBasePigeonViewModel.lineage = mBasePigeonViewModel.mSelectTypes_Lineage.get(index).getTypeName();
                                        llLineage.setContent(mBasePigeonViewModel.mSelectTypes_Lineage.get(index).getTypeName());
                                        btnState();
                                    }
                                });
                            } else {
                                mSelectTypeViewModel.getSelectType_lineage();
                            }
                        });

                break;
            case R.id.ll_state:
                //状态
                if (!Lists.isEmpty(mBasePigeonViewModel.mSelectTypes_State)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBasePigeonViewModel.mSelectTypes_State), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mBasePigeonViewModel.stateId = mBasePigeonViewModel.mSelectTypes_State.get(index).getTypeID();
                            llState.setContent(mBasePigeonViewModel.mSelectTypes_State.get(index).getTypeName());
                            btnState();
                        }
                    });
                } else {
                    mSelectTypeViewModel.getSelectType_State();
                }

                break;
            case R.id.sb_dont_disturb:
                break;
            case R.id.ll_deal_price:
                //数据交易价格
                break;
            case R.id.tv_next_step:
//                mBreedPigeonEntryViewModel.addBreedPigeonEntry();
                break;
            case R.id.llz:
                break;
        }
    }


    protected void btnState() {

    }

    protected void initData() {


    }

}
