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
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.InputPigeonViewModel;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.foot.SelectCountyFragment;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.BaseImgUploadFragment;
import com.cpigeon.book.module.photo.ImgUploadFragment;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;
import com.cpigeon.book.widget.selectImagesView.SelectImageAdapter2;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/11.
 */

public class InputPigeonFragment extends BaseBookFragment {

    private static final String KEY_SON_FOOT_ID = "KEY_SON_FOOT_ID";
    private static final String KEY_SON_PIGEON_ID = "KEY_SON_PIGEON_ID";
    private static final String KEY_PIGEON_SEX_TYPE = "KEY_PIGEON_SEX_TYPE";

    public static final String TYPE_SEX_MALE = "TYPE_SEX_MALE";
    public static final String TYPE_SEX_FEMALE = "TYPE_SEX_FEMALE";

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
    private LineInputView mLlFootVice;
    private LineInputView mLlFootSource;
    private LineInputView mLvFatherFoot;
    private LineInputView mLvMotherFoot;
    private LineInputView mLvEyeSand;
    private LineInputView mLvBirthTime;
    private LineInputView mLlHangingRingDate;
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
    protected String sexType;


    public static void start(Activity activity, @Nullable String pigeonId, String sonFootId, String sonPigeonId, String sex, int requestCode) {
        IntentBuilder builder = IntentBuilder.Builder();
        builder.putExtra(IntentBuilder.KEY_DATA, pigeonId);
        builder.putExtra(KEY_SON_FOOT_ID, sonFootId);
        builder.putExtra(KEY_SON_PIGEON_ID, sonPigeonId);
        builder.putExtra(KEY_PIGEON_SEX_TYPE, sex);
        builder.startParentActivity(activity, InputPigeonFragment.class, requestCode);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new InputPigeonViewModel();
        mSelectTypeViewModel = new SelectTypeViewModel();
        sexType = getBaseActivity().getIntent().getStringExtra(KEY_PIGEON_SEX_TYPE);
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

        setExpandAnim();
        setClick();

        mSelectTypeViewModel.getSelectType_FeatherColor();
        mSelectTypeViewModel.getSelectType_lineage();
        mSelectTypeViewModel.getSelectType_eyeSand();
        mSelectTypeViewModel.getSelectType_State();
        mSelectTypeViewModel.getSelectType_PigeonSource();
        mSelectTypeViewModel.getPigeonType();
    }

    private void setExpandAnim() {
        mLlAddition.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        ViewWrapper viewWrapper = new ViewWrapper(mLlAddition);
        mOpenAnim = ObjectAnimator.ofInt(viewWrapper, "trueWidth", 0
                , ScreenTool.dip2px(448)).setDuration(300);
        mCloseAnim = ObjectAnimator.ofInt(viewWrapper, "trueWidth"
                , ScreenTool.dip2px(448), 0).setDuration(300);
    }

    private void setClick() {

        mLvPigeonType.setOnRightClickListener(lineInputView -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes_PigeonType), p -> {
                mLvPigeonType.setRightText(mViewModel.mSelectTypes_PigeonType.get(p).getTypeName());
                mViewModel.pigeonType = mViewModel.mSelectTypes_PigeonType.get(p).getTypeID();
            });
        });

        mLvCountries.setOnRightClickListener(v -> {
            SearchFragmentParentActivity.start(getBaseActivity(), SelectCountyFragment.class, SelectCountyFragment.CODE_SELECT_COUNTY, null);
        });

        mLvRing.setOnRightClickListener(lineInputView -> {
            InputSingleFootDialog.show(getFragmentManager(), mLvRing.getContent(), mViewModel.isChina(), true, foot -> {

            });
        });

        mRlAddition.setOnClickListener(v -> {
            if (mIsAdditionOpen) {
                mImgExpand.setRotation(0);
                mCloseAnim.start();
                mIsAdditionOpen = false;
            } else {
                mImgExpand.setRotation(180);
                mOpenAnim.start();
                mIsAdditionOpen = true;
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
        mLlFootVice = findViewById(R.id.ll_foot_vice);
        mLlFootSource = findViewById(R.id.ll_foot_source);
        mLvFatherFoot = findViewById(R.id.lvFatherFoot);
        mLvMotherFoot = findViewById(R.id.lvMotherFoot);
        mLvEyeSand = findViewById(R.id.lvEyeSand);
        mLvBirthTime = findViewById(R.id.lvBirthTime);
        mLlHangingRingDate = findViewById(R.id.ll_hanging_ring_date);
        mLlImage = findViewById(R.id.llImage);
        mList = findViewById(R.id.list);
        mTvNextStep = findViewById(R.id.tv_next_step);
        mLlAddition = findViewById(R.id.llAddition);
        mImgExpand = findViewById(R.id.imgExpand);

    }

    @Override
    protected void initObserve() {
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
        });
    }

    private static class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View view) {
            mTarget = view;
        }

        public void setTrueWidth(int height) {
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
                    mViewModel.countryId = entity.getSort();
                    mLvCountries.setRightText(entity.getCode());
                } catch (Exception e) {
                    CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                    mViewModel.countryId = entity.getFootCodeID();
                    mLvCountries.setRightText(entity.getCode());
                }

                break;

            case ImgUploadFragment.CODE_SELECT_COUNTY:
                ImgTypeEntity mImgTypeEntity = (ImgTypeEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);

                List<ImgTypeEntity> imgs = Lists.newArrayList();
                imgs.add(0, mImgTypeEntity);
                mAdapter.addImage(imgs);

                mViewModel.phototypeid = mImgTypeEntity.getImgTypeId();
                mViewModel.images.addAll(Lists.newArrayList(mImgTypeEntity.getImgPath()));

                break;
        }
    }
}
