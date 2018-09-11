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
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.InputBreedInBookViewModel;
import com.cpigeon.book.widget.family.FamilyTreeView;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class InputBreedInBookFragment extends BaseBookFragment {

    public static final int CODE_ADD_PIGEON = 0x123;
    FamilyTreeView mFamilyTreeView;
    InputBreedInBookViewModel mViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder().startParentActivity(activity, InputBreedInBookFragment.class);
    }

    public static void start(Activity activity, int requestCode) {
        IntentBuilder.Builder().startParentActivity(activity, InputBreedInBookFragment.class, requestCode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new InputBreedInBookViewModel();
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
                BreedPigeonEntryFragment.start(getBaseActivity(), CODE_ADD_PIGEON);
            }

            @Override
            public void showInfo(BreedPigeonEntity entity) {
                ToastUtils.showLong(getBaseActivity(), entity.getClass().toString());
            }
        });
    }

    @Override
    protected void initObserve() {
        mViewModel.mBookLiveData.observe(this, bloodBookEntity -> {

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == CODE_ADD_PIGEON) {
            PigeonEntryEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            mViewModel.foodId = entity.getFootRingID();
            mViewModel.pigeonId = entity.getPigeonID();
            mViewModel.getBloodBook();
        }

    }
}
