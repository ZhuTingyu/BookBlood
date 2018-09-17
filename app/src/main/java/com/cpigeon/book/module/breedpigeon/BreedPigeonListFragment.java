package com.cpigeon.book.module.breedpigeon;

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
import android.widget.TextView;

import com.base.BaseFragment;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.LogUtil;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.racing.RacingPigeonEntryFragment;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.FiltrateListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class BreedPigeonListFragment extends BaseFragment {

    XRecyclerView mRecyclerView;
    BreedPigeonListAdapter mAdapter;
    SearchFragmentParentActivity mActivity;
    DrawerLayout mDrawerLayout;
    FiltrateListView mFiltrate;
    TextView mTvOk;

    SelectTypeViewModel mSelectTypeViewModel;

    private BreedPigeonListModel mBreedPigeonListModel;

    public static void start(Activity activity, String pigeonType) {

        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, pigeonType);
        SearchFragmentParentActivity.
                start(activity, BreedPigeonListFragment.class, true, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
        mActivity = (SearchFragmentParentActivity) context;
        mSelectTypeViewModel = new SelectTypeViewModel();
        mBreedPigeonListModel = new BreedPigeonListModel();
        initViewModels(mSelectTypeViewModel, mBreedPigeonListModel);
    }

    private String pigeonType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pigeonType = getBaseActivity().getIntent().getExtras().getString(IntentBuilder.KEY_TYPE);

        mBreedPigeonListModel.typeid = pigeonType;

        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            //搜索
            Bundle bundle = new Bundle();
            bundle.putString(IntentBuilder.KEY_TYPE, pigeonType);

            BaseSearchActivity.start(getBaseActivity(), SearchBreedPigeonActivity.class, bundle);
        });
        mDrawerLayout = mActivity.getDrawerLayout();
        mFiltrate = mActivity.getFiltrate();


        setToolbarRightImage(R.drawable.svg_filtrate, item -> {
            if (mDrawerLayout != null) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
            return false;
        });

        mFiltrate.setOnSureClickListener(selectItems -> {
            LogUtil.print(selectItems);
            mDrawerLayout.closeDrawer(Gravity.RIGHT);

            setProgressVisible(true);
            mBreedPigeonListModel.pi = 1;
            mBreedPigeonListModel.isSearch = false;
            mAdapter.cleanList();

            //年份
            List<SelectTypeEntity> mSelectTypeYear = selectItems.get(0);
            mBreedPigeonListModel.year = SelectTypeEntity.getTypeName(mSelectTypeYear);

            //性别
            List<SelectTypeEntity> mSelectTypeSex = selectItems.get(1);
            mBreedPigeonListModel.sexid = SelectTypeEntity.getTypeIds(mSelectTypeSex);

            //状态
            List<SelectTypeEntity> mSelectTypeStatus = selectItems.get(2);
            mBreedPigeonListModel.stateid = SelectTypeEntity.getTypeIds(mSelectTypeStatus);

            //血统
            List<SelectTypeEntity> mSelectTypeLineage = selectItems.get(3);
            mBreedPigeonListModel.bloodid = SelectTypeEntity.getTypeIds(mSelectTypeLineage);

            mBreedPigeonListModel.getPigeonList();

        });

        mRecyclerView = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);
        mAdapter = new BreedPigeonListAdapter();
//        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
//            GrowthReportFragment.start(getBaseActivity(), "2018-22-1234567");
//        });
        mRecyclerView.setAdapter(mAdapter);

        mTvOk.setText(R.string.text_add_breed_pigeon);
        mTvOk.setOnClickListener(v -> {
            if (pigeonType.equals(PigeonEntity.ID_MATCH_PIGEON)) {
                RacingPigeonEntryFragment.start(getBaseActivity());
            } else {
                InputBreedInBookFragment.start(getBaseActivity());
            }
        });

//        mAdapter.setNewData(Lists.newTestArrayList());

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            BreedPigeonDetailsFragment.start(getBaseActivity(),
                    mBreedPigeonEntity.getPigeonID(),
                    mBreedPigeonEntity.getFootRingID());
        });

        mSelectTypeViewModel.setSelectType(SelectTypeViewModel.TYPE_SEX, SelectTypeViewModel.STATE_STATE, SelectTypeViewModel.TYPE_PIGEON_BLOOD);
        mSelectTypeViewModel.getSelectTypes();

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.getData().clear();
            mBreedPigeonListModel.pi = 1;
            mBreedPigeonListModel.getPigeonList();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            mBreedPigeonListModel.pi++;
            mBreedPigeonListModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());

        setProgressVisible(true);
        mBreedPigeonListModel.getPigeonList();
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

        mBreedPigeonListModel.mPigeonListData.observe(this, datas -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);
        });

        mBreedPigeonListModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(PigeonAddEvent event) {
        mAdapter.cleanList();
        mBreedPigeonListModel.pi = 1;
        mBreedPigeonListModel.getPigeonList();
    }
}
