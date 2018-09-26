package com.cpigeon.book.module.home.sharehall;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.ShareHallEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.home.sharehall.adpter.SearchPigeonToShareAdapter;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Zhu TingYu on 2018/9/26.
 */

public class SearchPigeonToShareFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    SearchPigeonToShareAdapter mAdapter;
    BreedPigeonListModel mViewModel;
    SearchFragmentParentActivity mActivity;

    public static void start(Activity activity){
        SearchFragmentParentActivity.start(activity, SearchPigeonToShareFragment.class, false, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new BreedPigeonListModel();
        initViewModel(mViewModel);
        mActivity = (SearchFragmentParentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mRecyclerView = findViewById(R.id.list);
        mAdapter = new SearchPigeonToShareAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            mViewModel.pi = 1;
            mViewModel.getPigeonList();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity pigeonEntity = mAdapter.getItem(position);
            BreedPigeonDetailsFragment.start(getBaseActivity(),pigeonEntity.getPigeonID()
                    , pigeonEntity.getFootRingID(), BreedPigeonDetailsFragment.TYPE_SHARE_PIGEON, pigeonEntity.getUserID());
        });

        setProgressVisible(true);
        mViewModel.bitshare = BreedPigeonListModel.CODE_IN_NOT_SHARE_HALL;
        mViewModel.getPigeonList();


    }


    @Override
    protected void initObserve() {
        mViewModel.mPigeonListData.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, pigeonEntities);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(ShareHallEvent event){
        mAdapter.cleanList();
        mViewModel.pi = 1;
        mViewModel.getPigeonList();
    }
}
