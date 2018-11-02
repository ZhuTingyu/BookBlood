package com.cpigeon.book.module.breedpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.InputPigeonFragment;
import com.cpigeon.book.module.breedpigeon.viewmodel.BookViewModel;
import com.cpigeon.book.widget.family.FamilyTreeView;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class InputBreedInBookFragment extends BaseBookFragment {

    public static final int CODE_ADD_PIGEON = 0x123;
    FamilyTreeView mFamilyTreeView;
    BookViewModel mViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder().startParentActivity(activity, InputBreedInBookFragment.class);
    }

    public static void start(Activity activity, String foodId, String pigeonId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, foodId)
                .putExtra(IntentBuilder.KEY_DATA_2, pigeonId)
                .startParentActivity(activity, InputBreedInBookFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new BookViewModel(getBaseActivity());
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFamilyTreeView = new FamilyTreeView(getBaseActivity());
        mFamilyTreeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFamilyTreeView.setTypeMove(FamilyTreeView.TYPE_IS_CAN_MOVE_H);
        return mFamilyTreeView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_breed_pigeon_input);
        mFamilyTreeView.setOnFamilyClickListener(new FamilyTreeView.OnFamilyClickListener() {

            @Override
            public void add(int x, int y) {



                Log.d("songshuaishuai", "add if: ");
                if (x == mFamilyTreeView.getStartGeneration()) {
                    SelectPigeonToAddBreedFragment.start(getBaseActivity()
                            , StringUtil.emptyString()
                            , StringUtil.emptyString()
                            , CODE_ADD_PIGEON
                            , PigeonEntity.ID_FEMALE, PigeonEntity.ID_MALE, PigeonEntity.ID_NONE_SEX);
                } else {
                    Log.d("songshuaishuai", "add else: ");
                    PigeonEntity breedPigeonEntity = null;
                    if (mFamilyTreeView.getSon(x, y) != null) {
                        Log.d("songshuaishuai", "add: if get son");
                        breedPigeonEntity = mFamilyTreeView.getSon(x, y).getData();
                    }
                    boolean isMan = FamilyTreeView.isMale(y);
                    if (isMan) {
                        Log.d("songshuaishuai", "add: if is man");
                        SelectPigeonToAddBreedFragment.start(getBaseActivity()
                                , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getFootRingID()
                                , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getPigeonID()
                                , CODE_ADD_PIGEON
                                , PigeonEntity.ID_MALE, PigeonEntity.ID_NONE_SEX);
                    }else {
                        Log.d("songshuaishuai", "add: if is man else");
                        SelectPigeonToAddBreedFragment.start(getBaseActivity()
                                , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getFootRingID()
                                , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getPigeonID()
                                , CODE_ADD_PIGEON
                                , PigeonEntity.ID_FEMALE, PigeonEntity.ID_NONE_SEX);
                    }
                }
            }

            @Override
            public void showInfo(int x, int y, PigeonEntity breedPigeonEntity) {
                String sex = StringUtil.emptyString();
                Log.d("songshuaishuai", "add: showinfo");
                if (x != mFamilyTreeView.getStartGeneration()) {
                    sex = FamilyTreeView.isMale(y) ? InputPigeonFragment.TYPE_SEX_MALE : InputPigeonFragment.TYPE_SEX_FEMALE;
                }

                InputPigeonFragment.start(getBaseActivity()
                        , breedPigeonEntity != null ? breedPigeonEntity.getPigeonID() : StringUtil.emptyString()
                        , StringUtil.emptyString()
                        , StringUtil.emptyString()
                        , sex
                        , PigeonEntity.ID_BREED_PIGEON
                        , CODE_ADD_PIGEON);
            }
        });

        if (StringUtil.isStringValid(mViewModel.foodId) && StringUtil.isStringValid(mViewModel.pigeonId)) {
            setProgressVisible(true);
            mViewModel.getBloodBook();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }

    @Override
    protected void initObserve() {
        mViewModel.mBookLiveData.observe(this, bloodBookEntity -> {
            setProgressVisible(false);
            mFamilyTreeView.setData(bloodBookEntity);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == CODE_ADD_PIGEON) {
            if (!StringUtil.isStringValid(mViewModel.foodId)) {
                PigeonEntity entity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA) ;
                mViewModel.foodId = entity.getFootRingID();
                mViewModel.pigeonId = entity.getPigeonID();
            }
            setProgressVisible(true);
            mViewModel.getBloodBook();
        }
    }

    @Override
    public boolean OnBackClick() {

        exit();

        return true;
    }

    public void exit()
    {
        if (getBaseActivity().errorDialog != null && getBaseActivity().errorDialog.isShowing()) {
            getBaseActivity().errorDialog.dismiss();
        }

        String hintStr = "确定退出种鸽录入吗？";
        getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), hintStr, sweetAlertDialog -> {
            finish();
            sweetAlertDialog.dismiss();

        }, sweetAlertDialog -> {
            sweetAlertDialog.dismiss();
        });
    }
}
