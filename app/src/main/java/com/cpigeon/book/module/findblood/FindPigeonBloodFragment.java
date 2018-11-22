package com.cpigeon.book.module.findblood;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.widget.family.FamilyTreeView;

/**
 * Created by Zhu TingYu on 2018/11/22.
 */

public class FindPigeonBloodFragment extends BaseBookFragment {

    private RecyclerView mHeadList;
    private FamilyTreeView mFamilyTreeView;
    private FindBloodHeadAdapter mAdapter;


    public static void start(Activity activity, PigeonEntity entity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, entity)
                .startParentActivity(activity, FindPigeonBloodFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_pigeon_blood, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_blood_find);
        mFamilyTreeView = findViewById(R.id.familyTreeView);
        mHeadList = findViewById(R.id.headList);
        mHeadList.setLayoutManager(new LinearLayoutManager(getBaseActivity(),LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new FindBloodHeadAdapter();
        mHeadList.setAdapter(mAdapter);
    }
}
