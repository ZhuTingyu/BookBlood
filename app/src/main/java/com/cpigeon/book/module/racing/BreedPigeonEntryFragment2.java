package com.cpigeon.book.module.racing;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonEntryFragment;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonEntryViewModel;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.util.TextViewUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/9/29 0029.
 */

public class BreedPigeonEntryFragment2 extends BasePigeonEntryFragment {

    private static final int CODE_ADD_PLAY = 0x234;
    private static final String KEY_SON_FOOT_ID = "KEY_SON_FOOT_ID";
    private static final String KEY_SON_PIGEON_ID = "KEY_SON_PIGEON_ID";
    private static final String KEY_PIGEON_SEX_TYPE = "KEY_PIGEON_SEX_TYPE";

    public static final String TYPE_SEX_MALE = "TYPE_SEX_MALE";
    public static final String TYPE_SEX_FEMALE = "TYPE_SEX_FEMALE";

    private BreedPigeonEntryViewModel mBreedPigeonEntryViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, BreedPigeonEntryFragment2.class);
    }

    public static void start(Activity activity, @Nullable String pigeonId, String sonFootId, String sonPigeonId, String sex, int requestCode) {
        IntentBuilder builder = IntentBuilder.Builder();
        builder.putExtra(IntentBuilder.KEY_DATA, pigeonId);
        builder.putExtra(KEY_SON_FOOT_ID, sonFootId);
        builder.putExtra(KEY_SON_PIGEON_ID, sonPigeonId);
        builder.putExtra(KEY_PIGEON_SEX_TYPE, sex);
        builder.startParentActivity(activity, BreedPigeonEntryFragment2.class, requestCode);
    }

    @Override
    protected void onAttachs() {
        super.onAttachs();
        mBasePigeonViewModel = new BreedPigeonEntryViewModel();
        mBreedPigeonEntryViewModel = (BreedPigeonEntryViewModel) mBasePigeonViewModel;
        initViewModels(mSelectTypeViewModel, mBreedPigeonEntryViewModel);
        sexType = getBaseActivity().getIntent().getStringExtra(KEY_PIGEON_SEX_TYPE);
        mBreedPigeonEntryViewModel.sonFootId = getBaseActivity().getIntent().getStringExtra(KEY_SON_FOOT_ID);
        mBreedPigeonEntryViewModel.sonPigeonId = getBaseActivity().getIntent().getStringExtra(KEY_SON_PIGEON_ID);
        mBreedPigeonEntryViewModel.pigeonId = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("种鸽录入");
//        llHangingRingDate.setVisibility(View.VISIBLE);

        mBreedPigeonEntryViewModel.isCanCommit();

        tvNextStep.setOnClickListener(v -> {
            //添加确定
            setProgressVisible(true);
            if (mBreedPigeonEntryViewModel.isHavePigeonInfo()) {
                mBreedPigeonEntryViewModel.modifyBreedPigeonEntry();
            } else {
                mBreedPigeonEntryViewModel.addBreedPigeonEntry();
            }
        });

        bindUi(RxUtils.textChanges(llFoot.getEditText()), mBreedPigeonEntryViewModel.setFootNumber());//足环号


        if (TYPE_SEX_MALE.equals(sexType)) {
            mBreedPigeonEntryViewModel.mBreedPigeonEntity.setPigeonSexID(PigeonEntity.ID_MALE);
            mBreedPigeonEntryViewModel.sexId = (PigeonEntity.ID_MALE);
            llSex.setRightText(Utils.getString(R.string.text_male_a));
            llSex.setRightImageVisible(false);
        } else if (TYPE_SEX_FEMALE.equals(sexType)) {
            mBreedPigeonEntryViewModel.mBreedPigeonEntity.setPigeonSexID(PigeonEntity.ID_FEMALE);
            mBreedPigeonEntryViewModel.sexId = (PigeonEntity.ID_FEMALE);
            llSex.setRightText(Utils.getString(R.string.text_female_a));
            llSex.setRightImageVisible(false);
        }

        if (StringUtil.isStringValid(mBreedPigeonEntryViewModel.pigeonId)) {
            setProgressVisible(true);
            mBreedPigeonEntryViewModel.getPigeonDetails();
        }

    }

    @Override
    protected void btnState() {
        super.btnState();
        mBreedPigeonEntryViewModel.isCanCommit();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mBreedPigeonEntryViewModel.mPigeonDetailsData.observe(this, breedPigeonEntity -> {

            llCountries.setRightText(breedPigeonEntity.getFootCode());
            llFoot.setRightText(breedPigeonEntity.getFootRingNum());
            llFootVice.setRightText(breedPigeonEntity.getFootRingIDToNum());
            llFootSource.setRightText(breedPigeonEntity.getSourceName());
            llFootFather.setRightText(breedPigeonEntity.getMenFootRingNum());
            llFootMother.setRightText(breedPigeonEntity.getWoFootRingNum());
            llPigeonName.setRightText(breedPigeonEntity.getPigeonName());
            llSex.setRightText(breedPigeonEntity.getPigeonSexName());
            llSex.setRightImageVisible(false);
            llFeatherColor.setRightText(breedPigeonEntity.getPigeonPlumeName());
            llEyeSand.setRightText(breedPigeonEntity.getPigeonEyeName());
            //llTheirShellsDate.setRightText(breedPigeonEntity.getFootRingTime());
            llLineage.setRightText(breedPigeonEntity.getPigeonBloodName());
            llState.setRightText(breedPigeonEntity.getStateName());


            mBreedPigeonEntryViewModel.countryId = breedPigeonEntity.getFootCodeID();
            mBreedPigeonEntryViewModel.footVice = breedPigeonEntity.getFootRingIDToNum();
            mBreedPigeonEntryViewModel.sourceId = breedPigeonEntity.getSourceID();
            mBreedPigeonEntryViewModel.footFather = breedPigeonEntity.getMenFootRingNum();
            mBreedPigeonEntryViewModel.footMother = breedPigeonEntity.getWoFootRingNum();
            mBreedPigeonEntryViewModel.pigeonName = breedPigeonEntity.getPigeonName();
            mBreedPigeonEntryViewModel.sexId = breedPigeonEntity.getPigeonSexID();
            mBreedPigeonEntryViewModel.featherColor = breedPigeonEntity.getPigeonSexID();
            mBreedPigeonEntryViewModel.eyeSandId = breedPigeonEntity.getPigeonEyeID();
            mBreedPigeonEntryViewModel.theirShellsDate = breedPigeonEntity.getFootRingTimeTo();
            mBreedPigeonEntryViewModel.lineage = breedPigeonEntity.getPigeonBloodID();
            mBreedPigeonEntryViewModel.stateId = breedPigeonEntity.getStateID();

            if (StringUtil.isStringValid(breedPigeonEntity.getCoverPhotoUrl())) {
                List<ImgTypeEntity> imgs = Lists.newArrayList();
                ImgTypeEntity entity = new ImgTypeEntity.Builder()
                        .imgTypeId(breedPigeonEntity.getCoverPhotoTypeID())
                        .imgType(breedPigeonEntity.getCoverPhotoTypeName())
                        .imgPath(breedPigeonEntity.getCoverPhotoUrl())
                        .build();
                imgs.add(0, entity);
                mAdapter.addImage(imgs);

                mBreedPigeonEntryViewModel.phototypeid = breedPigeonEntity.getCoverPhotoID();
                mBreedPigeonEntryViewModel.images.addAll(Lists.newArrayList(breedPigeonEntity.getCoverPhotoUrl()));
            }
        });

        mBreedPigeonEntryViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvNextStep, aBoolean);
        });

        //种鸽录入
        mBreedPigeonEntryViewModel.mBreedPigeonData.observe(this, o -> {

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_ADD_PLAY) {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntryViewModel.mBreedPigeonData.getValue())
                    .finishForResult(getBaseActivity());
        }
    }


}
