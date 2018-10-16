package com.cpigeon.book.module.breedpigeon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.PhoneUtils;
import com.base.util.utility.StringUtil;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.event.ShareHallEvent;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonDetailsFragment;
import com.cpigeon.book.module.basepigeon.InputPigeonFragment;
import com.cpigeon.book.module.basepigeon.SelectBloodFragment;
import com.cpigeon.book.module.breeding.PairingInfoListFragment;
import com.cpigeon.book.module.feedpigeon.GrowthReportFragment;
import com.cpigeon.book.module.makebloodbook.PreviewsBookFragment;
import com.cpigeon.book.module.photo.PigeonPhotoHomeActivity;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.module.score.ScoreFragment;
import com.cpigeon.book.widget.family.FamilyTreeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.OnClick;

/**
 * 种鸽详情
 * Created by Administrator on 2018/8/29.
 */

public class BreedPigeonDetailsFragment extends BasePigeonDetailsFragment {

    public static final int CODE_ADD_PIGEON = 0x123;


    public static void start(Activity activity, String pigeonId, String footId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }

    //血统书跳转
    public static void start(Activity activity, String pigeonId, String footId, String footNumber, int generationCount) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .putExtra(KEY_TITLE_FOOT_NUMBER, footNumber)
                .putExtra(IntentBuilder.KEY_TITLE, generationCount)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }

    public static void start(Activity activity, String pigeonId, String footId, String type) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .putExtra(IntentBuilder.KEY_TYPE, type)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }

    //共享厅
    public static void start(Activity activity, String pigeonId, String footId, String type, String userId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .putExtra(IntentBuilder.KEY_TYPE, type)
                .putExtra(IntentBuilder.KEY_DATA_3, userId)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }

    //铭鸽库
    public static void startGoodPigeon(Activity activity, String pigeonId, String footId, String userId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .putExtra(IntentBuilder.KEY_DATA_3, userId)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
        setToolbarRight("成长记录", item -> {
            if (mBreedPigeonModifyViewModel.mPigeonEntity == null) {
                return true;
            }
            GrowthReportFragment.start(getBaseActivity(), mBreedPigeonModifyViewModel.mPigeonEntity, mBreedPigeonDetailsViewModel.pUid);
            return true;
        });

        mFamilyTreeView.setOnFamilyClickListener(new FamilyTreeView.OnFamilyClickListener() {
            @Override
            public void add(int x, int y) {

                if (x == mFamilyTreeView.getStartGeneration()) {
                    InputPigeonFragment.start(getBaseActivity()
                            , StringUtil.emptyString()
                            , mBookViewModel.foodId
                            , mBookViewModel.pigeonId
                            , FamilyTreeView.isMale(y) ? InputPigeonFragment.TYPE_SEX_MALE : InputPigeonFragment.TYPE_SEX_FEMALE
                            , 0);
                } else {

                    PigeonEntity breedPigeonEntity = null;
                    if (mFamilyTreeView.getSon(x, y) != null) {
                        breedPigeonEntity = mFamilyTreeView.getSon(x, y).getData();
                    }

                    InputPigeonFragment.start(getBaseActivity()
                            , StringUtil.emptyString()
                            , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getFootRingID()
                            , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getPigeonID()
                            , FamilyTreeView.isMale(y) ? InputPigeonFragment.TYPE_SEX_MALE : InputPigeonFragment.TYPE_SEX_FEMALE
                            , 0);

                }
            }

            @Override
            public void showInfo(int x, int y, PigeonEntity breedPigeonEntity) {
                BreedPigeonDetailsFragment.start(getBaseActivity(), breedPigeonEntity.getPigeonID()
                        , breedPigeonEntity.getFootRingID()
                        , mFirstFootNumber
                        , mGenerationCount + x);
            }
        });

        if (!mBreedPigeonDetailsViewModel.pUid.equals(UserModel.getInstance().getUserId())) {
            mFamilyTreeView.setShowInfoModel(true);
        }

        if (StringUtil.isStringValid(mType)) {
            llButton.setVisibility(View.VISIBLE);
            if (TYPE_SHARE_PIGEON.equals(mType)) {
                tvLeft.setVisibility(View.GONE);
                tvRight.setText(R.string.text_sure_share);
                tvRight.setOnClickListener(v -> {
                    DialogUtils.createDialogWithLeft(getBaseActivity(), Utils.getString(R.string.text_is_share_pigeon), sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        setProgressVisible(true);
                        mBreedPigeonDetailsViewModel.addApplyShareHall();
                    });
                });
            } else if (TYPE_MY_SHARE.equals(mType)) {
                tvRight.setVisibility(View.GONE);
                tvLeft.setText(Utils.getString(R.string.text_cancel_share));
                tvLeft.setOnClickListener(v -> {
                    DialogUtils.createDialogWithLeft(getBaseActivity(), Utils.getString(R.string.text_is_cancel_share), sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        setProgressVisible(true);
                        mBreedPigeonDetailsViewModel.cancelApplyShareHall();
                    });
                });
            } else if (TYPE_HIS_SHARE.equals(mType)) {
                tvRight.setOnClickListener(v -> {
                    List<String> way = Lists.newArrayList(getResources().getStringArray(R.array.array_contact_way));
                    BottomSheetAdapter.createBottomSheet(getBaseActivity(), way, p -> {
                        if (p == 0) {
                            //打电话
                            PhoneUtils.dial(getBaseActivity(), mBreedPigeonDetailsViewModel.mPigeonEntity.getPigeonHomePhone());
                        } else {
                            //发短信
                            PhoneUtils.sms(getBaseActivity(), mBreedPigeonDetailsViewModel.mPigeonEntity.getPigeonHomePhone());
                        }
                    });
                });
            }
        }


        if (!mBreedPigeonDetailsViewModel.pUid.equals(UserModel.getInstance().getUserId())) {
            //当前为查看数据
            img_play_import.setVisibility(View.GONE);
            img_play_add.setVisibility(View.GONE);
            ll_details_other.setVisibility(View.GONE);
        }
    }


    @Override
    protected void initObserve() {
        super.initObserve();

        mBreedPigeonDetailsViewModel.mDataAddApplyR.observe(this, s -> {
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                finish();
                EventBus.getDefault().post(new ShareHallEvent());
            });
        });

        mBreedPigeonDetailsViewModel.mDataCancelShareR.observe(this, s -> {
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                finish();
                EventBus.getDefault().post(new ShareHallEvent());
            });
        });
    }

    private BaseInputDialog mInputDialog;

    @OnClick({R.id.img_pigeon, R.id.ll_info1, R.id.ll_their_shells_date, R.id.ll_foot_source, R.id.ll_score
            , R.id.tv_make_book, R.id.tv_lineage_analysis, R.id.tv_lineage_roots, R.id.tv_breed_info
            , R.id.img_play_import, R.id.img_play_add})
    public void onViewClicked(View view) {

        if (!mBreedPigeonDetailsViewModel.pUid.equals(UserModel.getInstance().getUserId())) return;

        switch (view.getId()) {

            case R.id.img_pigeon:
                //信鸽相册
                PigeonPhotoHomeActivity.start(getBaseActivity(), mBreedPigeonModifyViewModel.mPigeonEntity);
                break;

            case R.id.ll_info1:
            case R.id.ll_their_shells_date:
            case R.id.ll_foot_source:
                //修改
                InputPigeonFragment.start(getBaseActivity(),
                        mBreedPigeonModifyViewModel.mPigeonEntity.getPigeonID(),
                        "", "", "", -1);
                break;
            case R.id.ll_score:
                //评分
                ScoreFragment.start(getBaseActivity(), mBreedPigeonDetailsViewModel.mPigeonEntity);
                break;

            case R.id.tv_make_book:
                //血统书制作
                PreviewsBookFragment.start(getBaseActivity(), mBreedPigeonDetailsViewModel.mPigeonEntity);
                break;

            case R.id.tv_lineage_analysis:
                //血统分析

                break;
            case R.id.tv_lineage_roots:
                //血统寻根

                break;
            case R.id.tv_breed_info:
                //繁育信息
                PairingInfoListFragment.start(getBaseActivity(), mBreedPigeonModifyViewModel.mPigeonEntity);

                break;
            case R.id.img_play_import:
                //赛绩导入
                mAddPlayDialog.setFoot(mBreedPigeonDetailsViewModel.mPigeonEntity.getFootRingNum());
                mAddPlayDialog.show(getBaseActivity().getFragmentManager(), "");
//                LineInputView ll_foot = mAddPlayDialog.getDialog().findViewById(R.id.ll_foot);
//                ll_foot.setContent(mBreedPigeonDetailsViewModel.mPigeonEntity.getFootRingNum());
                break;
            case R.id.img_play_add:
                //手动添加赛绩
                try {
                    PigeonEntity mBreedPigeonEntity = mBreedPigeonDetailsViewModel.mBreedPigeonData.getValue();
                    PlayAddFragment.start(getBaseActivity(),
                            new PigeonEntryEntity.Builder()
                                    .FootRingID(mBreedPigeonEntity.getFootRingID())
                                    .FootRingNum(mBreedPigeonEntity.getFootRingNum())
                                    .PigeonID(mBreedPigeonEntity.getPigeonID())
                                    .build(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

//    @OnClick({R.id.img_pigeon, R.id.tv_foot, R.id.img_sex, R.id.ll_foot_vice, R.id.ll_lineage, R.id.ll_state, R.id.ll_eye_sand, R.id.ll_feather_color, R.id.ll_their_shells_date, R.id.ll_foot_source, R.id.ll_score
//            , R.id.tv_make_book, R.id.tv_lineage_analysis, R.id.tv_lineage_roots, R.id.tv_breed_info
//            , R.id.img_play_import, R.id.img_play_add})
//    public void onViewClicked(View view) {
//
//        if (!mBreedPigeonDetailsViewModel.pUid.equals(UserModel.getInstance().getUserId())) return;
//
//        switch (view.getId()) {
//
//            case R.id.img_pigeon:
//                //信鸽相册
//                PigeonPhotoHomeActivity.start(getBaseActivity(), mBreedPigeonModifyViewModel.mPigeonEntity);
//                break;
//
//            case R.id.tv_foot:
//                //足环号
//                InputSingleFootDialog.show(getFragmentManager(), tvFoot.getText().toString(), mBreedPigeonModifyViewModel.isChina(), null, foot -> {
//                    tvFoot.setText(foot);
//                    mBreedPigeonModifyViewModel.mPigeonEntity.setFootRingNum(foot);
//                    mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//                });
//
//                break;
//            case R.id.img_sex:
//                //性别
//                if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_Sex)) {
//                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
//                            , SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_Sex), p -> {
//                                mBreedPigeonModifyViewModel.sexId = mBreedPigeonModifyViewModel.mSelectTypes_Sex.get(p).getTypeID();
////                                llSex.setContent(mBreedPigeonModifyViewModel.mSelectTypes_Sex.get(p).getTypeName());
//
//
//                                mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonSexID(mBreedPigeonModifyViewModel.mSelectTypes_Sex.get(p).getTypeID());
//                                mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonSexName(mBreedPigeonModifyViewModel.mSelectTypes_Sex.get(p).getTypeName());
//
//                                mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//                            });
//                } else {
//                    mSelectTypeViewModel.getSelectType_Sex();
//                }
//
//                break;
//            case R.id.ll_foot_vice:
//                //副环
//                InputSingleFootDialog.show(getFragmentManager(), tvFootVice.getText().toString(), true, null, foot -> {
//                    tvFootVice.setText(foot);
//                    mBreedPigeonModifyViewModel.mPigeonEntity.setFootRingIDToNum(foot);
//                    mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//                });
//                break;
//            case R.id.ll_lineage:
//                //血统
//
//
//                BaseInputDialog.show(getFragmentManager(), R.string.text_blood, R.string.text_blood_bank, 0, content -> {
//                    tvLineage.setText(content);
//                    mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonBloodID("");
//                    mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonBloodName(content);
//                    mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//                }, () -> {
//                    SelectBloodFragment.start(getBaseActivity());
//                });
//
//
////                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
////                        , R.string.text_pigeon_lineage, tvLineage.getText().toString(), 0, content -> {
////                            mInputDialog.hide();
////                            tvLineage.setText(content);
////                            mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonBloodID("");
////                            mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonBloodName(content);
////                            mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
////                        }, () -> {
////                            mInputDialog.hide();
////                            if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_Lineage)) {
////                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_Lineage), 0, new OptionPicker.OnOptionPickListener() {
////                                    @Override
////                                    public void onOptionPicked(int index, String item) {
////                                        tvLineage.setText(mBreedPigeonModifyViewModel.mSelectTypes_Lineage.get(index).getTypeName());
////                                        mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonBloodID(mBreedPigeonModifyViewModel.mSelectTypes_Lineage.get(index).getTypeID());
////                                        mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonBloodName(mBreedPigeonModifyViewModel.mSelectTypes_Lineage.get(index).getTypeName());
////                                        mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
////
////                                    }
////                                });
////                            } else {
////                                mSelectTypeViewModel.getSelectType_lineage();
////                            }
////                        });
//                break;
//            case R.id.ll_state:
//                //状态
//                if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_State)) {
//                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_State), 0, new OptionPicker.OnOptionPickListener() {
//                        @Override
//                        public void onOptionPicked(int index, String item) {
//                            tvState.setText(mBreedPigeonModifyViewModel.mSelectTypes_State.get(index).getTypeName());
//
//                            mBreedPigeonModifyViewModel.mPigeonEntity.setStateID(mBreedPigeonModifyViewModel.mSelectTypes_State.get(index).getTypeID());
//                            mBreedPigeonModifyViewModel.mPigeonEntity.setStateName(mBreedPigeonModifyViewModel.mSelectTypes_State.get(index).getTypeName());
//                            mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//
//                        }
//                    });
//                } else {
//                    mSelectTypeViewModel.getSelectType_State();
//                }
//                break;
//            case R.id.ll_eye_sand:
//                //眼砂
//                if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand)) {
//                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand), 0, new OptionPicker.OnOptionPickListener() {
//                        @Override
//                        public void onOptionPicked(int index, String item) {
//                            tvEyeSand.setText(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand.get(index).getTypeName());
//
//                            mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonEyeID(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand.get(index).getTypeID());
//                            mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonEyeName(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand.get(index).getTypeName());
//                            mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//                        }
//                    });
//                } else {
//                    mSelectTypeViewModel.getSelectType_eyeSand();
//                }
//                break;
//            case R.id.ll_feather_color:
//                //羽色
//                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
//                        , R.string.text_feather_color, tvFeatherColor.getText().toString(), 0, content -> {
//                            mInputDialog.hide();
//                            tvFeatherColor.setText(content);
//
//                            mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonPlumeID("");
//                            mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonPlumeName(content);
//                            mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//
//                        }, () -> {
//                            mInputDialog.hide();
//
//                            if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor)) {
//                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor), 0, new OptionPicker.OnOptionPickListener() {
//                                    @Override
//                                    public void onOptionPicked(int index, String item) {
//                                        tvFeatherColor.setText(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
//                                        mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonPlumeID(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor.get(index).getTypeID());
//                                        mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonPlumeName(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
//                                        mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//
//                                    }
//                                });
//                            } else {
//                                mSelectTypeViewModel.getSelectType_FeatherColor();
//                            }
//                        });
//                break;
//            case R.id.ll_their_shells_date:
//                //出壳日期
//                PickerUtil.showTimeYMD(getActivity(), System.currentTimeMillis(), (year, month, day) -> {
//                    tvTheirShellsDate.setText(year + "-" + month + "-" + day);
//
//                    mBreedPigeonModifyViewModel.mPigeonEntity.setOutShellTime(year + "-" + month + "-" + day);
//                    mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//                });
//
//                break;
//            case R.id.ll_foot_source:
//                //来源
//                if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_Source)) {
//                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
//                            , SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_Source), p -> {
//                                tvFootSource.setText(mBreedPigeonModifyViewModel.mSelectTypes_Source.get(p).getTypeName());
//
//                                mBreedPigeonModifyViewModel.mPigeonEntity.setSourceID(mBreedPigeonModifyViewModel.mSelectTypes_Source.get(p).getTypeID());
//
//                                mBreedPigeonModifyViewModel.mPigeonEntity.setSourceName(mBreedPigeonModifyViewModel.mSelectTypes_Source.get(p).getTypeName());
//                                mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
//                            });
//                } else {
//                    mSelectTypeViewModel.getSelectType_PigeonSource();
//                }
//
//                break;
//            case R.id.ll_score:
//                //评分
//                ScoreFragment.start(getBaseActivity(), mBreedPigeonDetailsViewModel.mPigeonEntity);
//                break;
//
//            case R.id.tv_make_book:
//                //血统书制作
//                PreviewsBookFragment.start(getBaseActivity(), mBreedPigeonDetailsViewModel.mPigeonEntity);
//                break;
//
//            case R.id.tv_lineage_analysis:
//                //血统分析
//
//                break;
//            case R.id.tv_lineage_roots:
//                //血统寻根
//
//                break;
//            case R.id.tv_breed_info:
//                //繁育信息
//                PairingInfoListFragment.start(getBaseActivity(), mBreedPigeonModifyViewModel.mPigeonEntity);
//
//                break;
//            case R.id.img_play_import:
//                //赛绩导入
//                mAddPlayDialog.setFoot(mBreedPigeonDetailsViewModel.mPigeonEntity.getFootRingNum());
//                mAddPlayDialog.show(getBaseActivity().getFragmentManager(), "");
//                //                LineInputView ll_foot = mAddPlayDialog.getDialog().findViewById(R.id.ll_foot);
////                ll_foot.setContent(mBreedPigeonDetailsViewModel.mPigeonEntity.getFootRingNum());
//                break;
//            case R.id.img_play_add:
//                //手动添加赛绩
//                try {
//                    PigeonEntity mBreedPigeonEntity = mBreedPigeonDetailsViewModel.mBreedPigeonData.getValue();
//                    PlayAddFragment.start(getBaseActivity(),
//                            new PigeonEntryEntity.Builder()
//                                    .FootRingID(mBreedPigeonEntity.getFootRingID())
//                                    .FootRingNum(mBreedPigeonEntity.getFootRingNum())
//                                    .PigeonID(mBreedPigeonEntity.getPigeonID())
//                                    .build(), 0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (resultCode != Activity.RESULT_OK) return;

            if (requestCode == SelectBloodFragment.CODE_SELECT_BLOOD) {
                SelectTypeEntity blood = data.getParcelableExtra(IntentBuilder.KEY_DATA);

                tvLineage.setText(blood.getTypeName());
                mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonBloodID(blood.getTypeID());
                mBreedPigeonModifyViewModel.mPigeonEntity.setPigeonBloodName(blood.getTypeName());
                mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(PigeonAddEvent event) {
        mBreedPigeonDetailsViewModel.getPigeonDetails();//获取 鸽子  详情
        mBookViewModel.getBloodBook();
    }

}
