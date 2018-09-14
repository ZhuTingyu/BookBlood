package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.adapter.PairingInfoListAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoListViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

/**
 * 配对信息
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoListFragment extends BaseListFragment {

    private PairingInfoListAdapter mPairingInfoListAdapter;

    private PairingInfoListViewModel mPairingInfoListViewModel;

    public static void start(Activity activity, BreedPigeonEntity mBreedPigeonEntity) {
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

        mPairingInfoListViewModel.mBreedPigeonEntity = (BreedPigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

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
                    PairingInfoRecommendFragment.start(getBaseActivity());
                } else if (chooseWays[p].equals(Utils.getString(R.string.array_blind_date))) {
                    //相亲配对

                }
            });
            return false;
        });

        mPairingInfoListAdapter = new PairingInfoListAdapter();

        initHeadView();

        list.setAdapter(mPairingInfoListAdapter);
        mPairingInfoListAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PairingNestInfoListFragment.start(getBaseActivity());
        });

        list.setRefreshListener(() -> {
            mPairingInfoListAdapter.getData().clear();
            mPairingInfoListAdapter.notifyDataSetChanged();
            mPairingInfoListViewModel.pi = 1;
            mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectPigeonAllData();
        });

        mPairingInfoListAdapter.setOnLoadMoreListener(() -> {
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
            RecyclerViewUtils.setLoadMoreCallBack2(list, mPairingInfoListAdapter, breedPigeonEntities);
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
}
