package com.cpigeon.book.module.breedpigeon;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.utility.LogUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonSexCountEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.widget.stats.StatView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class BreedPigeonListFragment extends BaseFootListFragment {


    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, PigeonEntity.ID_BREED_PIGEON);
        SearchFragmentParentActivity.
                start(activity, BreedPigeonListFragment.class, true, bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecorationLine();

    }

    @Override
    protected void initData() {
        super.initData();
        mTvOk.setText(R.string.text_add_breed_pigeon);
        mTvOk.setOnClickListener(v -> {
            //种鸽录入
            InputBreedInBookFragment.start(getBaseActivity());
        });

        mAdapter = new BreedPigeonListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            BreedPigeonDetailsFragment.start(getBaseActivity(),
                    mBreedPigeonEntity.getPigeonID(),
                    mBreedPigeonEntity.getFootRingID());
        });

        mSelectTypeViewModel.setSelectType(SelectTypeViewModel.TYPE_SEX, SelectTypeViewModel.STATE_STATE, SelectTypeViewModel.TYPE_PIGEON_BLOOD);
        mSelectTypeViewModel.getSelectTypes();

        mBreedPigeonListModel.getPigeonCount();

        mDrawerLayout = mActivity.getDrawerLayout();
        mFiltrate = mActivity.getFiltrate();

        if (mDrawerLayout == null || mFiltrate == null) {
            return;
        }

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
            mBreedPigeonListModel.getPigeonCount();

        });
    }


    @Override
    protected void initObserve() {
        super.initObserve();
        mBreedPigeonListModel.mLivePigeonSexCount.observe(this, pigeonSexCountEntity -> {
            mAdapter.addHeaderView(initHeadView(pigeonSexCountEntity));
        });

    }

    private View initHeadView(PigeonSexCountEntity countEntity) {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_stat_list_head, null);
        StatView mStat1;
        StatView mStat2;
        StatView mStat3;

        mStat1 = view.findViewById(R.id.stat1);
        mStat2 = view.findViewById(R.id.stat2);
        mStat3 = view.findViewById(R.id.stat3);

        mStat1.bindData(countEntity.xiongCount + countEntity.ciCount
                , countEntity.xiongCount + countEntity.ciCount);
        mStat2.bindData(countEntity.xiongCount, countEntity.allCount);
        mStat3.bindData(countEntity.ciCount, countEntity.allCount);

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(PigeonAddEvent event) {
        dataRefresh();
    }

    private void dataRefresh() {
        mAdapter.cleanList();
        mBreedPigeonListModel.pi = 1;
        mBreedPigeonListModel.getPigeonList();
    }


}