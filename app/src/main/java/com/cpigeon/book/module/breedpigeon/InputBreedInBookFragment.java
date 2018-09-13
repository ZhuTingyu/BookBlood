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
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.PigeonUpdateEvent;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BookViewModel;
import com.cpigeon.book.widget.family.FamilyTreeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    public static void start(Activity activity, int requestCode) {
        IntentBuilder.Builder().startParentActivity(activity, InputBreedInBookFragment.class, requestCode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new BookViewModel();
        initViewModel(mViewModel);
        EventBus.getDefault().register(this);
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

                BreedPigeonEntity.Builder builder = new BreedPigeonEntity.Builder();
                if (x != mFamilyTreeView.getStartGeneration()) {
                    if (FamilyTreeView.isMale(x, y)) {
                        builder.PigeonSexID(BreedPigeonEntity.ID_MALE)
                                .PigeonSexName(Utils.getString(R.string.text_male_a));
                    } else {
                        builder.PigeonSexID(BreedPigeonEntity.ID_FEMALE)
                                .PigeonSexName(Utils.getString(R.string.text_female_a));
                    }
                }

                BreedPigeonEntity breedPigeonEntity = null;
                if (mFamilyTreeView.getSon(x, y) != null) {
                    breedPigeonEntity = mFamilyTreeView.getSon(x, y).getData();
                }

                if (breedPigeonEntity != null) {
                    BreedPigeonEntryFragment.start(getBaseActivity(), builder.build()
                            , breedPigeonEntity.getFootRingID()
                            , breedPigeonEntity.getPigeonID()
                            , CODE_ADD_PIGEON);
                } else {
                    BreedPigeonEntryFragment.start(getBaseActivity(), builder.build(), CODE_ADD_PIGEON);

                }

            }

            @Override
            public void showInfo(BreedPigeonEntity entity) {
                if (!entity.isHaveDetailsInfo()) {
                    BreedPigeonEntryFragment.start(getBaseActivity(), entity, CODE_ADD_PIGEON);
                } else {
                    BreedPigeonDetailsFragment.start(getBaseActivity(), entity.getPigeonID(), entity.getFootRingID());
                }
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
                PigeonEntryEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mViewModel.foodId = entity.getFootRingID();
                mViewModel.pigeonId = entity.getPigeonID();
            }
            setProgressVisible(true);
            mViewModel.getBloodBook();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(PigeonUpdateEvent event) {
        mViewModel.getBloodBook();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
