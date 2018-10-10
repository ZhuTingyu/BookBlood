package com.cpigeon.book.module.breedpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BookViewModel;
import com.cpigeon.book.module.racing.BreedPigeonEntryFragment2;
import com.cpigeon.book.widget.family.FamilyTreeView;

import org.greenrobot.eventbus.EventBus;

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

                if (x == mFamilyTreeView.getStartGeneration()) {

                    BreedPigeonEntryFragment2.start(getBaseActivity()
                            , StringUtil.emptyString()
                            , StringUtil.emptyString()
                            , StringUtil.emptyString()
                            , StringUtil.emptyString()
                            , CODE_ADD_PIGEON);

                } else {
                    PigeonEntity breedPigeonEntity = null;
                    if (mFamilyTreeView.getSon(x, y) != null) {
                        breedPigeonEntity = mFamilyTreeView.getSon(x, y).getData();
                    }


                    BreedPigeonEntryFragment2.start(getBaseActivity()
                            , StringUtil.emptyString()
                            , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getFootRingID()
                            , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getPigeonID()
                            , FamilyTreeView.isMale(y) ? BreedPigeonEntryFragment2.TYPE_SEX_MALE : BreedPigeonEntryFragment2.TYPE_SEX_FEMALE
                            , CODE_ADD_PIGEON);
                }
            }

            @Override
            public void showInfo(int x, int y, PigeonEntity breedPigeonEntity) {
                BreedPigeonEntryFragment2.start(getBaseActivity()
                        , breedPigeonEntity != null ? breedPigeonEntity.getPigeonID() : StringUtil.emptyString()
                        , StringUtil.emptyString()
                        , StringUtil.emptyString()
                        , FamilyTreeView.isMale(y) ? BreedPigeonEntryFragment2.TYPE_SEX_MALE : BreedPigeonEntryFragment2.TYPE_SEX_FEMALE
                        , CODE_ADD_PIGEON);
            }
        });

        if (StringUtil.isStringValid(mViewModel.foodId) && StringUtil.isStringValid(mViewModel.pigeonId)) {
            setProgressVisible(true);
            mViewModel.getBloodBook();
        }
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
                PigeonEntryEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mViewModel.foodId = entity.getFootRingID();
                mViewModel.pigeonId = entity.getPigeonID();
            }
            setProgressVisible(true);
            mViewModel.getBloodBook();
        }
    }
}
