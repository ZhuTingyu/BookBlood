package com.cpigeon.book.module.home.sharehall;

import android.app.Activity;
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

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.ShareHallEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.home.sharehall.adpter.ShareHallHomeAdapter;
import com.cpigeon.book.module.home.sharehall.viewmodel.ShareHallViewModel;
import com.cpigeon.book.module.menu.service.OpenServiceFragment;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.FiltrateListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class ShareHallFragment extends BaseBookFragment {

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mRlSearch;
    private TextView mTvSearch;
    private LinearLayout mLlMyShare;
    private RelativeLayout mMenuLayoutLeft;
    private FiltrateListView mFiltrate;
    private XRecyclerView mRecyclerView;
    private ShareHallHomeAdapter mAdapter;
    private SelectTypeViewModel mSelectTypeViewModel;
    private ShareHallViewModel mViewModel;

    private boolean isShowToobar = true;

    public static void start(Activity activity, PigeonEntity mBreedPigeonEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity)
                .putExtra(IntentBuilder.KEY_TYPE, false)
                .putExtra(IntentBuilder.KEY_BOOLEAN, false)
                .startParentActivity(activity,false, ShareHallFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSelectTypeViewModel = new SelectTypeViewModel();
        mViewModel = new ShareHallViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share_hall, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        View appbar = view.findViewById(R.id.appbar);

        isShowToobar = getBaseActivity().getIntent().getBooleanExtra(IntentBuilder.KEY_TYPE, true);

        if (isShowToobar) {
            //首页
            setToolbarNotBack();
            setToolbarLeft(R.drawable.svg_open_share_hall, v -> {
                OpenServiceFragment.start(getBaseActivity());
            });

        } else {
            //配对信息   相亲配对

            setToolbarNotBack();
            setToolbarLeft(R.drawable.svg_back, v -> {
                getBaseActivity().finish();
            });
        }


        mDrawerLayout = findViewById(R.id.drawerLayout);
        mRlSearch = findViewById(R.id.rlSearch);
        mTvSearch = findViewById(R.id.tvSearch);
        mLlMyShare = findViewById(R.id.llMyShare);
        mMenuLayoutLeft = findViewById(R.id.menuLayoutLeft);
        mFiltrate = findViewById(R.id.filtrate);
        mRecyclerView = findViewById(R.id.shareList);
        mTvSearch.setText(R.string.text_input_foot_number_search);
        mRlSearch.setOnClickListener(v -> {
            SearchSharePigeonActivity.start(getBaseActivity(), false);
        });

        setToolbarRightImage(R.drawable.svg_filtrate, item -> {
            if (mDrawerLayout != null) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
            return false;
        });

        mFiltrate.setOnSureClickListener(selectItems -> {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);

            List<SelectTypeEntity> blood = selectItems.get(0);
            List<SelectTypeEntity> sex = selectItems.get(1);
            List<SelectTypeEntity> eye = selectItems.get(2);

            mViewModel.blood = SelectTypeEntity.getTypeIds(blood);
            mViewModel.sex = SelectTypeEntity.getTypeIds(sex);
            mViewModel.eye = SelectTypeEntity.getTypeIds(eye);

            mAdapter.cleanList();
            setProgressVisible(true);
            mViewModel.getSharePigeons();
        });

        mAdapter = new ShareHallHomeAdapter(false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getSharePigeons();
        }, mRecyclerView.getRecyclerView());

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            mViewModel.pi = 1;
            mViewModel.getSharePigeons();
        });

        mSelectTypeViewModel.setSelectType(SelectTypeViewModel.TYPE_PIGEON_BLOOD, SelectTypeViewModel.TYPE_SEX, SelectTypeViewModel.TYPE_EYE);
        mSelectTypeViewModel.getSelectTypes();

        mLlMyShare.setOnClickListener(v -> {
            SearchFragmentParentActivity.start(getBaseActivity(), MySharePigeonFragment.class, false);
        });

        mViewModel.getSharePigeons();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mSelectTypeViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            List<String> titles = Lists.newArrayList(Utils.getString(R.string.text_pigeon_blood), Utils.getString(R.string.text_sex)
                    , Utils.getString(R.string.text_eye_sand));

            if (mFiltrate != null) {
                mFiltrate.setData(false, selectTypeEntities, titles, mSelectTypeViewModel.whichIds);
            }
        });

        mViewModel.mDataSharePigeon.observe(this, sharePigeonEntities -> {
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, sharePigeonEntities);
            setProgressVisible(false);
        });
    }
    @Subscribe
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PIGEON_PHOTO_REFRESH)) {
            setProgressVisible(true);
            mAdapter.cleanList();
            mViewModel.pi = 1;
            mViewModel.getSharePigeons();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(ShareHallEvent event) {
        setProgressVisible(true);
        mAdapter.cleanList();
        mViewModel.pi = 1;
        mViewModel.getSharePigeons();
    }
}
