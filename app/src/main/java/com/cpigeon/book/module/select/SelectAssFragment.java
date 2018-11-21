package com.cpigeon.book.module.select;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.base.pinyin.LetterSortModel;
import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.AssEntity;
import com.cpigeon.book.module.select.adpter.SelectAssAdapter;
import com.cpigeon.book.module.select.viewmodel.SelectAssViewModel;
import com.gjiazhe.wavesidebar.WaveSideBar;

/**
 * 详情选择协会
 */

public class SelectAssFragment extends BaseBookFragment {

    SelectAssAdapter mAdapter;

    XRecyclerView mRecyclerView;
    WaveSideBar mWaveSideBar;
    LetterSortModel<AssEntity> mModel = new LetterSortModel<>();
    SelectAssViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new SelectAssViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_with_wave_bar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("选择协会");

        mRecyclerView = findViewById(R.id.list);
        mWaveSideBar = findViewById(R.id.side_bar);

        mAdapter = new SelectAssAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView.getRecyclerView());

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                Intent intent = new Intent();
                intent.putExtra(IntentBuilder.KEY_DATA, mAdapter.getData().get(position));
                getBaseActivity().setResult(Activity.RESULT_OK, intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setProgressVisible(true);
        mViewModel.getAssList();

        SearchFragmentParentActivity activity = (SearchFragmentParentActivity) getBaseActivity();
        activity.setSearchHint(R.string.text_search_ass);
        activity.setSearchClickListener(v -> {
            SearchAssActivity.start(getBaseActivity(), SearchAssActivity.class, null);
        });
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.liveAss.observe(this, assEntities -> {
            setProgressVisible(false);
            mModel.setData(assEntities);
            mAdapter.initWave(mModel, mWaveSideBar);
            mAdapter.initHead(getBaseActivity());
            mRecyclerView.setRefreshing(false);
            mAdapter.setNewData(mModel.getData());
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == BaseSearchActivity.CODE_SEARCH) {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, (AssEntity) data.getParcelableExtra(IntentBuilder.KEY_DATA))
                        .finishForResult(getBaseActivity());
            }
        }
    }
}
