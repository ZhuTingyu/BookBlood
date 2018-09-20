package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.adapter.PairingInfoListAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoListViewModel;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 配对信息
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoListFragment extends BaseListFragment {

    private PairingInfoListAdapter mPairingInfoListAdapter;

    private PairingInfoListViewModel mPairingInfoListViewModel;

    public static void start(Activity activity, PigeonEntity mBreedPigeonEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity)
                .startParentActivity(activity, PairingInfoListFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPairingInfoListViewModel = new PairingInfoListViewModel();
        initViewModels(mPairingInfoListViewModel);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);//在当前界面注册一个订阅者

        mPairingInfoListViewModel.mBreedPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

        tvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        setTitle("配对信息");

        String[] chooseWays = getResources().getStringArray(R.array.text_breeding_info);
        setToolbarRightImage(R.drawable.svg_filtrate, item -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                if (chooseWays[p].equals(Utils.getString(R.string.array_pairing_add))) {
                    //添加配对
                    PairingInfoAddFragment.start(getBaseActivity(), mPairingInfoListViewModel.mBreedPigeonEntity);
                } else if (chooseWays[p].equals(Utils.getString(R.string.array_pairing_recommend))) {
                    //推荐配对
                    PairingInfoRecommendFragment.start(getBaseActivity(), mPairingInfoListViewModel.mBreedPigeonEntity);
                } else if (chooseWays[p].equals(Utils.getString(R.string.array_blind_date))) {
                    //相亲配对

                }
            });
            return false;
        });

        mPairingInfoListAdapter = new PairingInfoListAdapter(mPairingInfoListViewModel.mBreedPigeonEntity);

        initHeadView();

        list.setAdapter(mPairingInfoListAdapter);
        mPairingInfoListAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PairingInfoEntity mPairingInfoEntity = (PairingInfoEntity) adapter.getData().get(position);
            PairingNestInfoListFragment.start(getBaseActivity(), mPairingInfoEntity, mPairingInfoListViewModel.mBreedPigeonEntity);
        });

        list.setRefreshListener(() -> {
            setProgressVisible(true);
            initData();
        });

        mPairingInfoListAdapter.setOnLoadMoreListener(() -> {
            setProgressVisible(true);
            mPairingInfoListViewModel.pi++;
            mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectPigeonAllData();
        }, list.getRecyclerView());

        setProgressVisible(true);
        mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectPigeonAllData();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mPairingInfoListViewModel.mPairingInfoListData.observe(this, breedPigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(list, mPairingInfoListAdapter, breedPigeonEntities);
        });

        mPairingInfoListViewModel.listEmptyMessage.observe(this, s -> {
            mPairingInfoListAdapter.setEmptyText(s);
        });

    }

    //初始化头部视图
    private void initHeadView() {
        try {
//            CardView mHeadView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.layout_pairing_info_head, list, null);

            View mHeadView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.layout_pairing_info_head, list, false);

            TextView tv_hint_foot = mHeadView.findViewById(R.id.tv_hint_foot);
            ImageView img_hint_sex = mHeadView.findViewById(R.id.img_hint_sex);

            tv_hint_foot.setText(mPairingInfoListViewModel.mBreedPigeonEntity.getFootRingNum());

            if (mPairingInfoListViewModel.mBreedPigeonEntity.getPigeonSexName().equals("雌")) {
                img_hint_sex.setImageResource(R.mipmap.ic_female);
            } else if (mPairingInfoListViewModel.mBreedPigeonEntity.getPigeonSexName().equals("雄")) {
                img_hint_sex.setImageResource(R.mipmap.ic_male);
            } else {
                img_hint_sex.setImageResource(R.mipmap.ic_sex_no);
            }

            mPairingInfoListAdapter.addHeaderView(mHeadView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PAIRING_INFO_REFRESH)) {
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }

    private void initData() {
        setProgressVisible(true);
        mPairingInfoListAdapter.getData().clear();
        mPairingInfoListAdapter.notifyDataSetChanged();
        mPairingInfoListViewModel.pi = 1;
        mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectPigeonAllData();
    }
}
