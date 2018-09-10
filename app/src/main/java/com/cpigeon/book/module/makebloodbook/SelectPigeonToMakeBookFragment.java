package com.cpigeon.book.module.makebloodbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;

/**
 * Created by Zhu TingYu on 2018/9/10.
 */

public class SelectPigeonToMakeBookFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    BreedPigeonListAdapter mAdapter;
    private TextView mTvUserCount;

    SearchFragmentParentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_pigeon_to_make_book, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(),SearchBreedPigeonToMakeBookActivity.class,null);
        });
        mRecyclerView = findViewById(R.id.list);
        mTvUserCount = findViewById(R.id.tvUserCount);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new BreedPigeonListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PreviewsBookFragment.start(getBaseActivity(),"");
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newArrayList(new BreedPigeonEntity(),new BreedPigeonEntity(),new BreedPigeonEntity()));

    }
}
