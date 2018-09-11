package com.cpigeon.book.module.breedpigeon;

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
import com.base.util.dialog.DialogUtils;
import com.base.util.picker.PickerUtil;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonEntryViewModel;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.foot.SelectCountyFragment;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.ImgUploadFragment;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;
import com.cpigeon.book.widget.selectImagesView.SelectImageAdapter2;
import com.kyleduo.switchbutton.SwitchButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 种鸽录入
 * Created by Administrator on 2018/8/28.
 */

public class BreedPigeonEntryFragment extends BaseBookFragment {

    private static final int CODE_ADD_PLAY = 0x234;

    @BindView(R.id.llz)
    LineInputListLayout mLlRoot;

    @BindView(R.id.list)
    RecyclerView list;

    //    FootAddMultiViewModel mViewModel;
    SelectTypeViewModel mSelectTypeViewModel;
    BreedPigeonEntryViewModel mBreedPigeonEntryViewModel;

    SelectImageAdapter2 mAdapter;
    @BindView(R.id.ll_countries)
    LineInputView llCountries;
    @BindView(R.id.ll_foot)
    LineInputView llFoot;
    @BindView(R.id.ll_foot_vice)
    LineInputView llFootVice;
    @BindView(R.id.ll_foot_source)
    LineInputView llFootSource;
    @BindView(R.id.ll_foot_father)
    LineInputView llFootFather;
    @BindView(R.id.ll_foot_mother)
    LineInputView llFootMother;
    @BindView(R.id.ll_pigeon_name)
    LineInputView llPigeonName;
    @BindView(R.id.ll_sex)
    LineInputView llSex;
    @BindView(R.id.ll_feather_color)
    LineInputView llFeatherColor;
    @BindView(R.id.ll_eye_sand)
    LineInputView llEyeSand;
    @BindView(R.id.ll_their_shells_date)
    LineInputView llTheirShellsDate;
    @BindView(R.id.ll_lineage)
    LineInputView llLineage;
    @BindView(R.id.ll_state)
    LineInputView llState;
    @BindView(R.id.sb_dont_disturb)
    SwitchButton sbDontDisturb;
    @BindView(R.id.ll_deal_price)
    LineInputView llDealPrice;
    @BindView(R.id.tv_next_step)
    TextView tvNextStep;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, BreedPigeonEntryFragment.class);
    }

    public static void start(Activity activity, int requestCode) {
        IntentBuilder.Builder()
                .startParentActivity(activity, BreedPigeonEntryFragment.class, requestCode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mViewModel = new FootAddMultiViewModel(getBaseActivity());
        mSelectTypeViewModel = new SelectTypeViewModel();
        mBreedPigeonEntryViewModel = new BreedPigeonEntryViewModel();
//        initViewModels(mViewModel, mPublicViewModel, mBreedPigeonEntryViewModel);
        initViewModels(mSelectTypeViewModel, mBreedPigeonEntryViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breed_pigeon_entry, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("种鸽录入");

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

        bindUi(RxUtils.textChanges(llFoot.getEditText()), mBreedPigeonEntryViewModel.setFootNumber());//足环号


        mSelectTypeViewModel.getSelectType_Sex();
        mSelectTypeViewModel.getSelectType_FeatherColor();
        mSelectTypeViewModel.getSelectType_eyeSand();
        mSelectTypeViewModel.getSelectType_lineage();
        mSelectTypeViewModel.getSelectType_State();
        mSelectTypeViewModel.getSelectType_PigeonSource();
    }

    @Override
    protected void initObserve() {

        mBreedPigeonEntryViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvNextStep, aBoolean);
        });

        mSelectTypeViewModel.mSelectType_Sex.observe(this, selectTypeEntities -> {
            mBreedPigeonEntryViewModel.mSelectTypes_Sex = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_FeatherColor.observe(this, selectTypeEntities -> {
            mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_EyeSand.observe(this, selectTypeEntities -> {
            mBreedPigeonEntryViewModel.mSelectTypes_EyeSand = selectTypeEntities;
        });


        mSelectTypeViewModel.mSelectType_Lineage.observe(this, selectTypeEntities -> {
            mBreedPigeonEntryViewModel.mSelectTypes_Lineage = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_State.observe(this, selectTypeEntities -> {
            mBreedPigeonEntryViewModel.mSelectTypes_State = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_Pigeon_Source.observe(this, selectTypeEntities -> {
            setProgressVisible(false);
            mBreedPigeonEntryViewModel.mSelectTypes_Source = selectTypeEntities;
        });

        //种鸽录入
        mBreedPigeonEntryViewModel.mBreedPigeonData.observe(this, o -> {
            //保证界面只有一个提示
            setProgressVisible(false);
            if (getBaseActivity().errorDialog != null && getBaseActivity().errorDialog.isShowing()) {
                getBaseActivity().errorDialog.dismiss();
            }

            String hintStr = "种鸽录入成功，";
            if (Integer.valueOf(o.getPigeonMoney()) > 0) {
                hintStr += "获取" + o.getPigeonMoney() + "个鸽币，";
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CODE_ADD_PLAY){
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntryViewModel.mBreedPigeonData.getValue())
                    .finishForResult(getBaseActivity());
        }

        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == PictureMimeType.ofImage()) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

            IntentBuilder.Builder().putExtra(IntentBuilder.KEY_DATA, new ImgTypeEntity.Builder().imgPath(selectList.get(0).getCompressPath()).build())
                    .startParentActivity(getBaseActivity(), ImgUploadFragment.class, ImgUploadFragment.CODE_SELECT_COUNTY);

        }

        switch (requestCode) {
            case SelectCountyFragment.CODE_SELECT_COUNTY:
                try {
                    CountyEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                    mBreedPigeonEntryViewModel.countryId = entity.getSort();
                    llCountries.setRightText(entity.getCode());
                } catch (Exception e) {
                    CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                    mBreedPigeonEntryViewModel.countryId = entity.getFootCodeID();
                    llCountries.setRightText(entity.getCode());
                }

                break;

            case ImgUploadFragment.CODE_SELECT_COUNTY:
                ImgTypeEntity mImgTypeEntity = (ImgTypeEntity) data.getSerializableExtra(IntentBuilder.KEY_TYPE);

                List<ImgTypeEntity> imgs = Lists.newArrayList();
                imgs.add(0, mImgTypeEntity);
                mAdapter.addImage(imgs);

                mBreedPigeonEntryViewModel.phototypeid = mImgTypeEntity.getImgTypeId();
                mBreedPigeonEntryViewModel.images.addAll(Lists.newArrayList(mImgTypeEntity.getImgPath()));

                break;
        }
    }

    private BaseInputDialog mDialogLineage;
    private BaseInputDialog mDialogMoney;

    @OnClick({R.id.ll_countries, R.id.ll_foot, R.id.ll_foot_vice, R.id.ll_foot_source, R.id.ll_foot_father, R.id.ll_foot_mother, R.id.ll_pigeon_name, R.id.ll_sex, R.id.ll_feather_color, R.id.ll_eye_sand, R.id.ll_their_shells_date, R.id.ll_lineage, R.id.ll_state, R.id.sb_dont_disturb, R.id.ll_deal_price, R.id.tv_next_step, R.id.llz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_countries:
                //国家
                SearchFragmentParentActivity.start(getBaseActivity(), SelectCountyFragment.class, SelectCountyFragment.CODE_SELECT_COUNTY, null);
                break;
            case R.id.ll_foot:
                //足环号
//                List<String> foots = mViewModel.getFoots();
                List<String> foots = new ArrayList<>();
                InputSingleFootDialog dialog = new InputSingleFootDialog();
                dialog.setFoots(foots);
                dialog.setOnFootStringFinishListener(foot -> {
                    llFoot.setRightText(foot);
                    mBreedPigeonEntryViewModel.foot = foot;
                });
                dialog.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.ll_foot_vice:
                //副环
                List<String> foots2 = new ArrayList<>();
                InputSingleFootDialog dialog2 = new InputSingleFootDialog();
                dialog2.setFoots(foots2);
                dialog2.setOnFootStringFinishListener(foot -> {
                    llFootVice.setRightText(foot);
                    mBreedPigeonEntryViewModel.footVice = foot;
                });
                dialog2.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.ll_foot_source:
                //来源
                if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_Source)) {
                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
                            , SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_Source), p -> {
                                mBreedPigeonEntryViewModel.sourceId = mBreedPigeonEntryViewModel.mSelectTypes_Source.get(p).getTypeID();
                                llFootSource.setContent(mBreedPigeonEntryViewModel.mSelectTypes_Source.get(p).getTypeName());
                                mBreedPigeonEntryViewModel.isCanCommit();
                            });
                } else {
                    mSelectTypeViewModel.getSelectType_PigeonSource();
                }
                break;
            case R.id.ll_foot_father:
                //父足环
                List<String> foots3 = new ArrayList<>();
                InputSingleFootDialog dialog3 = new InputSingleFootDialog();
                dialog3.setFoots(foots3);
                dialog3.setOnFootStringFinishListener(foot -> {
                    llFootFather.setRightText(foot);
                    mBreedPigeonEntryViewModel.footFather = foot;
                });
                dialog3.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.ll_foot_mother:
                //母足环
                List<String> foots4 = new ArrayList<>();
                InputSingleFootDialog dialog4 = new InputSingleFootDialog();
                dialog4.setFoots(foots4);
                dialog4.setOnFootStringFinishListener(foot -> {
                    llFootMother.setRightText(foot);
                    mBreedPigeonEntryViewModel.footMother = foot;
                });
                dialog4.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.ll_pigeon_name:
                //鸽名
                mDialogMoney = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_pigeon_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mBreedPigeonEntryViewModel.pigeonName = content;
                            llPigeonName.setRightText(content);
                            mDialogMoney.hide();
                        }, null);

                break;
            case R.id.ll_sex:
                //性别
                if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_Sex)) {
                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
                            , SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_Sex), p -> {
                                mBreedPigeonEntryViewModel.sexId = mBreedPigeonEntryViewModel.mSelectTypes_Sex.get(p).getTypeID();
                                llSex.setContent(mBreedPigeonEntryViewModel.mSelectTypes_Sex.get(p).getTypeName());
                                mBreedPigeonEntryViewModel.isCanCommit();
                            });
                } else {
                    mSelectTypeViewModel.getSelectType_Sex();
                }

                break;
            case R.id.ll_feather_color:
                //羽色
                mDialogLineage = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_feather_color, 0, content -> {
                            mDialogLineage.hide();
                            mBreedPigeonEntryViewModel.featherColor = content;
                            llFeatherColor.setContent(content);
                            mBreedPigeonEntryViewModel.isCanCommit();
                        }, () -> {
                            mDialogLineage.hide();

                            if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor)) {
                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor), 0, new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index, String item) {
                                        mBreedPigeonEntryViewModel.featherColor = mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor.get(index).getTypeName();
                                        llFeatherColor.setContent(mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
                                        mBreedPigeonEntryViewModel.isCanCommit();
                                    }
                                });
                            } else {
                                mSelectTypeViewModel.getSelectType_FeatherColor();
                            }
                        });

                break;
            case R.id.ll_eye_sand:
                //眼砂
                if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_EyeSand)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_EyeSand), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mBreedPigeonEntryViewModel.eyeSandId = mBreedPigeonEntryViewModel.mSelectTypes_EyeSand.get(index).getTypeID();
                            llEyeSand.setContent(mBreedPigeonEntryViewModel.mSelectTypes_EyeSand.get(index).getTypeName());
                            mBreedPigeonEntryViewModel.isCanCommit();
                        }
                    });
                } else {
                    mSelectTypeViewModel.getSelectType_eyeSand();
                }
                break;
            case R.id.ll_their_shells_date:
                //出壳日期
                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    llTheirShellsDate.setContent(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mBreedPigeonEntryViewModel.theirShellsDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                });
                break;
            case R.id.ll_lineage:
                //血统
                mDialogLineage = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_pigeon_lineage, 0, content -> {
                            mDialogLineage.hide();
                            mBreedPigeonEntryViewModel.lineage = content;
                            llLineage.setRightText(content);
                        }, () -> {
                            mDialogLineage.hide();

                            if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_Lineage)) {
                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_Lineage), 0, new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index, String item) {
                                        mBreedPigeonEntryViewModel.lineage = mBreedPigeonEntryViewModel.mSelectTypes_Lineage.get(index).getTypeName();
                                        llLineage.setContent(mBreedPigeonEntryViewModel.mSelectTypes_Lineage.get(index).getTypeName());
                                        mBreedPigeonEntryViewModel.isCanCommit();
                                    }
                                });
                            } else {
                                mSelectTypeViewModel.getSelectType_lineage();
                            }
                        });

                break;
            case R.id.ll_state:
                //状态
                if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_State)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_State), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mBreedPigeonEntryViewModel.stateId = mBreedPigeonEntryViewModel.mSelectTypes_State.get(index).getTypeID();
                            llState.setContent(mBreedPigeonEntryViewModel.mSelectTypes_State.get(index).getTypeName());
                            mBreedPigeonEntryViewModel.isCanCommit();
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
                setProgressVisible(true);
                mBreedPigeonEntryViewModel.addBreedPigeonEntry();
                break;
            case R.id.llz:
                break;
        }
    }

}
