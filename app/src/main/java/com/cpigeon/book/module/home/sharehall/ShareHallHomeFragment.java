package com.cpigeon.book.module.home.sharehall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.home.sharehall.adpter.ShareHallHomeAdapter;
import com.cpigeon.book.widget.FiltrateListView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class ShareHallHomeFragment extends BaseBookFragment {

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mRlSearch;
    private TextView mTvSearch;
    private LinearLayout mLlMyShare;
    private RelativeLayout mMenuLayoutLeft;
    private FiltrateListView mFiltrate;
    private XRecyclerView mRecyclerView;
    private ShareHallHomeAdapter mAdapter;
    private SelectTypeViewModel mSelectTypeViewModel;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSelectTypeViewModel = new SelectTypeViewModel();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share_hall, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mRlSearch = findViewById(R.id.rlSearch);
        mTvSearch = findViewById(R.id.tvSearch);
        mLlMyShare = findViewById(R.id.llMyShare);
        mMenuLayoutLeft = findViewById(R.id.menuLayoutLeft);
        mFiltrate = findViewById(R.id.filtrate);
        mRecyclerView = findViewById(R.id.list);
        mTvSearch.setText(R.string.text_input_foot_number_search);

        setToolbarRightImage(R.drawable.svg_filtrate, item -> {
            if (mDrawerLayout != null) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
            return false;
        });

        mAdapter = new ShareHallHomeAdapter(false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());

        mSelectTypeViewModel.setSelectType(SelectTypeViewModel.TYPE_SEX, SelectTypeViewModel.STATE_STATE, SelectTypeViewModel.TYPE_PIGEON_BLOOD);
        mSelectTypeViewModel.getSelectTypes();

        mLlMyShare.setOnClickListener(v -> {
            SearchFragmentParentActivity.start(getBaseActivity(), MySharePigeonFragment.class, false);
        });

    }

    @Override
    protected void initObserve() {
        mSelectTypeViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            List<String> titles = Lists.newArrayList(Utils.getString(R.string.text_sex)
                    , Utils.getString(R.string.text_pigeon_status), Utils.getString(R.string.text_pigeon_blood));

            if (mFiltrate != null) {
                mFiltrate.setData(true, selectTypeEntities, titles, mSelectTypeViewModel.whichIds);
            }
        });
    }
}
