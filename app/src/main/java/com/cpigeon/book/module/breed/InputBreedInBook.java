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
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.widget.FamilyTreeView;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class InputBreedInBook extends BaseBookFragment {
    FamilyTreeView mFamilyTreeView;

    public static void start(Activity activity){
        IntentBuilder.Builder().startParentActivity(activity, InputBreedInBook.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFamilyTreeView = new FamilyTreeView(getBaseActivity());
        return mFamilyTreeView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(R.string.text_breed_pigeon_input);
        mFamilyTreeView.setHaveCurrentGeneration(true);

        composite.add(RxUtils.delayed(100,aLong -> {
            mFamilyTreeView.setData();
        }));
    }
}
