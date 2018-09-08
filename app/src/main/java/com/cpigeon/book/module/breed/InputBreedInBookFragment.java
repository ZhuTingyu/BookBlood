package com.cpigeon.book.module.breed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.widget.FamilyTreeView;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class InputBreedInBookFragment extends BaseBookFragment {
    FamilyTreeView mFamilyTreeView;

    public static void start(Activity activity) {
        IntentBuilder.Builder().startParentActivity(activity, InputBreedInBookFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        RxUtils.delayed(50, aLong -> {
            mFamilyTreeView.setData(new BreedPigeonEntity.Builder().build(), 0, 0);
        });

        mFamilyTreeView.setOnFamilyClickListener(new FamilyTreeView.OnFamilyClickListener() {
            @Override
            public void add(int x, int y) {
                mFamilyTreeView.setData(new BreedPigeonEntity.Builder().build(), x, y);
            }

            @Override
            public void showInfo(BreedPigeonEntity entity) {
                ToastUtils.showLong(getBaseActivity(), entity.getClass().toString());
            }
        });
    }
}
