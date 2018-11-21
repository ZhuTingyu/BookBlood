package com.cpigeon.book.module.breedpigeon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
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
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonDetailsFragment;
import com.cpigeon.book.module.basepigeon.InputPigeonFragment;
import com.cpigeon.book.module.basepigeon.SelectBloodFragment;
import com.cpigeon.book.module.breeding.PairingInfoListFragment;
import com.cpigeon.book.module.feedpigeon.GrowthReportFragment;
import com.cpigeon.book.module.makebloodbook.PreviewsBookActivity;
import com.cpigeon.book.module.menu.service.OpenServiceFragment;
import com.cpigeon.book.module.photo.PigeonPhotoHomeActivity;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.module.score.ScoreFragment;
import com.cpigeon.book.service.EventBusService;
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
    private boolean mIsGoodPigeon = false;

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

    //血统书跳转
    public static void start(Activity activity, String pigeonId, String footId, String footNumber, int generationCount, String userId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .putExtra(IntentBuilder.KEY_DATA_3, userId)
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
                .putExtra(IntentBuilder.KEY_DATA_3, userId)
                .putExtra(IntentBuilder.KEY_TYPE, type)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }

    //铭鸽库
    public static void start(Activity activity, String pigeonId, String footId, String userId, boolean isGoodPigeon) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .putExtra(IntentBuilder.KEY_DATA_3, userId)
                .putExtra(IntentBuilder.KEY_BOOLEAN, isGoodPigeon)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
        mIsGoodPigeon = getBaseActivity().getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);
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

                boolean isMan = FamilyTreeView.isMale(y);

                if (x == mFamilyTreeView.getStartGeneration()) {
                    if (isMan) {
                        SelectPigeonToAddBreedFragment.start(getBaseActivity()
                                , mBookViewModel.foodId
                                , mBookViewModel.pigeonId
                                , 0
                                , PigeonEntity.ID_MALE, PigeonEntity.ID_NONE_SEX);
                    } else {
                        SelectPigeonToAddBreedFragment.start(getBaseActivity()
                                , mBookViewModel.foodId
                                , mBookViewModel.pigeonId
                                , 0
                                , PigeonEntity.ID_FEMALE, PigeonEntity.ID_NONE_SEX);
                    }
                } else {
                    PigeonEntity breedPigeonEntity = null;
                    if (mFamilyTreeView.getSon(x, y) != null) {
                        breedPigeonEntity = mFamilyTreeView.getSon(x, y).getData();
                    }

                    if (isMan) {
                        SelectPigeonToAddBreedFragment.start(getBaseActivity()
                                , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getFootRingID()
                                , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getPigeonID()
                                , 0
                                , PigeonEntity.ID_MALE, PigeonEntity.ID_NONE_SEX);
                    } else {
                        SelectPigeonToAddBreedFragment.start(getBaseActivity()
                                , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getFootRingID()
                                , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getPigeonID()
                                , 0
                                , PigeonEntity.ID_FEMALE, PigeonEntity.ID_NONE_SEX);
                    }
                }
            }

            @Override
            public void showInfo(int x, int y, PigeonEntity breedPigeonEntity) {
                BreedPigeonDetailsFragment.start(getBaseActivity(), breedPigeonEntity.getPigeonID()
                        , breedPigeonEntity.getFootRingID()
                        , mFirstFootNumber
                        , mGenerationCount + x
                        , mBreedPigeonDetailsViewModel.pUid);
            }
        });

        if (!mBreedPigeonDetailsViewModel.pUid.equals(UserModel.getInstance().getUserId())) {
            mFamilyTreeView.setShowInfoModel(true);
        }

        composite.add(RxUtils.delayed(50, aLong -> {
            mFamilyTreeView.initView();
        }));

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
                    if (mBreedPigeonDetailsViewModel.mPigeonEntity.isOpenShare()) {
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
                    } else {
                        DialogUtils.createDialogWithLeft(getBaseActivity(), Utils.getString(R.string.text_hint_not_open_share)
                                , sweetAlertDialog -> {
                                    sweetAlertDialog.dismiss();
                                    OpenServiceFragment.start(getBaseActivity());
                                });
                    }
                });
            }
        }

        mBookViewModel.isGoodPigeon = mIsGoodPigeon;

        if (!mBreedPigeonDetailsViewModel.pUid.equals(UserModel.getInstance().getUserId())) {
            //当前为查看数据
            img_play_import.setVisibility(View.GONE);
            img_play_add.setVisibility(View.GONE);
            ll_details_other.setVisibility(View.GONE);
        }

        mBreedPigeonDetailsViewModel.getPigeonDetails();//获取 鸽子  详情
        mBookViewModel.getBloodBook();// //获取 血统书  四代

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
                PreviewsBookActivity.start(getBaseActivity(), mBreedPigeonDetailsViewModel.mPigeonEntity);
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
                mAddPlayDialog.setPigeon(mBreedPigeonDetailsViewModel.mPigeonEntity);
                mAddPlayDialog.show(getBaseActivity().getFragmentManager(), "");
//                LineInputView ll_foot = mAddPlayDialog.getDialog().findViewById(R.id.ll_foot);
//                ll_foot.setContent(mBreedPigeonDetailsViewModel.mPigeonEntity.getFootRingNum());
                break;
            case R.id.img_play_add:
                //手动添加赛绩
                try {
                    PigeonEntity mBreedPigeonEntity = mBreedPigeonDetailsViewModel.mBreedPigeonData.getValue();
                    PlayAddFragment.start(getBaseActivity(),
                            new PigeonEntity.Builder()
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

    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PIGEON_PHOTO_REFRESH)) {
            mBreedPigeonDetailsViewModel.getPigeonDetails();
        }
    }
}
