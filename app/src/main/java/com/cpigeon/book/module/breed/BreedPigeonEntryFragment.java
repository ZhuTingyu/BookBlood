package com.cpigeon.book.module.breed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breed.viewmodel.BreedPigeonEntryViewModel;
import com.cpigeon.book.module.foot.SelectCountyFragment;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
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
 * 种鸽录入
 * Created by Administrator on 2018/8/28.
 */

public class BreedPigeonEntryFragment extends BaseBookFragment {


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
                        PictureSelectUtil.showChooseImage(getBaseActivity(), PictureMimeType.ofImage(), 10000);
                    } else if (Utils.getString(R.string.text_open_camera).equals(way)) {
                        PictureSelectUtil.openCamera(getBaseActivity(), false);
                    }
                });
            }

            @Override
            public void onImageDelete(int position) {
                mBreedPigeonEntryViewModel.images.remove(position);
            }
        });


        mSelectTypeViewModel.getSelectType_Sex();
        mSelectTypeViewModel.getSelectType_FeatherColor();
        mSelectTypeViewModel.getSelectType_eyeSand();
    }

    @Override
    protected void initObserve() {
        mSelectTypeViewModel.mSelectType_Sex.observe(this, selectTypeEntities -> {
            mBreedPigeonEntryViewModel.mSelectTypes_Sex = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_FeatherColor.observe(this, selectTypeEntities -> {
            mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_EyeSand.observe(this, selectTypeEntities -> {
            mBreedPigeonEntryViewModel.mSelectTypes_EyeSand = selectTypeEntities;
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == PictureMimeType.ofImage()) {
            List<String> imgs = Lists.newArrayList();
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            for (int i = 0; i < selectList.size(); i++) {
                imgs.add(0, selectList.get(i).getCompressPath());
            }
            mAdapter.addImage(imgs);
            mBreedPigeonEntryViewModel.images.addAll(imgs);
        }

        switch (requestCode) {
            case SelectCountyFragment.CODE_SELECT_COUNTY:
                try {
                    CountyEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
//                    mViewModel.countryId = entity.getSort();
                    llCountries.setRightText(entity.getCode());
                } catch (Exception e) {
                    CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
//                    mViewModel.countryId = entity.getFootCodeID();
                    llCountries.setRightText(entity.getCode());
                }

                break;
        }
    }

    @OnClick({R.id.ll_countries, R.id.ll_foot, R.id.ll_foot_vice, R.id.ll_foot_source, R.id.ll_foot_father, R.id.ll_foot_mother, R.id.ll_pigeon_name, R.id.ll_sex, R.id.ll_feather_color, R.id.ll_eye_sand, R.id.ll_their_shells_date, R.id.ll_lineage, R.id.ll_state, R.id.sb_dont_disturb, R.id.ll_deal_price, R.id.tv_next_step, R.id.llz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_countries:
                SearchFragmentParentActivity.start(getBaseActivity(), SelectCountyFragment.class, SelectCountyFragment.CODE_SELECT_COUNTY);
                break;
            case R.id.ll_foot:
                break;
            case R.id.ll_foot_vice:
                break;
            case R.id.ll_foot_source:
                break;
            case R.id.ll_foot_father:
                break;
            case R.id.ll_foot_mother:
                break;
            case R.id.ll_pigeon_name:
                break;
            case R.id.ll_sex:

                if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_Sex)) {
                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
                            , SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_Sex), p -> {
                                mBreedPigeonEntryViewModel.sexId = mBreedPigeonEntryViewModel.mSelectTypes_Sex.get(p).getTypeID();
                                llSex.setContent(mBreedPigeonEntryViewModel.mSelectTypes_Sex.get(p).getTypeName());
                                mBreedPigeonEntryViewModel.isCanCommit();
                            });
                }

                break;
            case R.id.ll_feather_color:

                if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mBreedPigeonEntryViewModel.featherColorId = mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor.get(index).getTypeID();
                            llFeatherColor.setContent(mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
                            mBreedPigeonEntryViewModel.isCanCommit();
                        }
                    });

//                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
//                            , SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor), p -> {
//                                mBreedPigeonEntryViewModel.featherColorId = mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor.get(p).getTypeID();
//                                llSex.setContent(mBreedPigeonEntryViewModel.mSelectTypes_FeatherColor.get(p).getTypeName());
//                                mBreedPigeonEntryViewModel.isCanCommit();
//                            });
                }

                break;
            case R.id.ll_eye_sand:
                if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_EyeSand)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_EyeSand), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mBreedPigeonEntryViewModel.eyeSandId = mBreedPigeonEntryViewModel.mSelectTypes_EyeSand.get(index).getTypeID();
                            llEyeSand.setContent(mBreedPigeonEntryViewModel.mSelectTypes_EyeSand.get(index).getTypeName());
                            mBreedPigeonEntryViewModel.isCanCommit();
                        }
                    });
                }
                break;
            case R.id.ll_their_shells_date:

                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {

                });
                break;
            case R.id.ll_lineage:
                break;
            case R.id.ll_state:
                break;
            case R.id.sb_dont_disturb:
                break;
            case R.id.ll_deal_price:
                break;
            case R.id.tv_next_step:
                break;
            case R.id.llz:
                break;
        }
    }

}
