package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.BreedEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PriringRecommendEntity;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.adapter.PairingInfoListAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoListViewModel;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.home.sharehall.ShareHallFragment;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.Subscribe;

/**
 * 配对信息
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoListFragment extends BaseListFragment {

    private PairingInfoListAdapter mPairingInfoListAdapter;
    private boolean IsMen = true;
    private boolean IsSingle = true;

    private PairingInfoListViewModel mPairingInfoListViewModel;

    public static void start(Activity activity, PigeonEntity mBreedPigeonEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity)
                .startParentActivity(activity, PairingInfoListFragment.class);

    }

    public static void start(Activity activity, BreedEntity mBreedEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mBreedEntity)
                .startParentActivity(activity, PairingInfoListFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPairingInfoListViewModel = new PairingInfoListViewModel( );
        initViewModels(mPairingInfoListViewModel);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mPairingInfoListViewModel.mBreedPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
            mPairingInfoListViewModel.PigeonID = mPairingInfoListViewModel.mBreedPigeonEntity.getPigeonID();
            mPairingInfoListViewModel.FootRingID = mPairingInfoListViewModel.mBreedPigeonEntity.getFootRingID();
            if (mPairingInfoListViewModel.mBreedPigeonEntity.getPigeonSexName().equals("雌")) {
                IsMen = false;
            }

        } catch (Exception e) {
            IsSingle = false;
            mPairingInfoListViewModel.mBreedPigeonEntity = new PigeonEntity();
        }
        try {

            mPairingInfoListViewModel.mBreedEntity = (BreedEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
            mPairingInfoListViewModel.PigeonID = mPairingInfoListViewModel.mBreedEntity.getMenPigeonID();
            mPairingInfoListViewModel.mBreedPigeonEntity.setPigeonID(mPairingInfoListViewModel.mBreedEntity.getMenPigeonID());
            mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingID(mPairingInfoListViewModel.mBreedEntity.getMenFootRingID());
            mPairingInfoListViewModel.mBreedPigeonEntity.setPigeonSexName("雄");
            mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingNum(mPairingInfoListViewModel.mBreedEntity.getMenFootRingNum());
            if(mPairingInfoListViewModel.mBreedEntity.isTogether())
            {
                mPairingInfoListViewModel.mBreedPigeonEntity.setBitTogether(1);
            }
            mPairingInfoListViewModel.FootRingID = mPairingInfoListViewModel.mBreedEntity.getMenFootRingID();

        } catch (Exception e) {

        }

        tvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        setTitle(getString(R.string.str_pairing_info));

        String[] chooseWays = getResources().getStringArray(R.array.text_breeding_info);
        setToolbarRightImage(R.drawable.svg_filtrate, item -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                if(mPairingInfoListViewModel.mBreedPigeonEntity.getBitTogether()==1) {
                    DialogUtils.createHintDialog(getBaseActivity(),"该鸽子"+mPairingInfoListViewModel.mBreedPigeonEntity.getFootRingNum()+"正处于“同居中”,不可再进行配对！");

                }else {
                    if (chooseWays[p].equals(Utils.getString(R.string.array_pairing_add))) {
                        //添加配对
                        PairingInfoAddFragment.start(getBaseActivity(), mPairingInfoListViewModel.mBreedPigeonEntity, null, 0);

                    } else if (chooseWays[p].equals(Utils.getString(R.string.array_blind_date))) {
                        //相亲配对
                        ShareHallFragment.start(getBaseActivity(), mPairingInfoListViewModel.mBreedPigeonEntity);
                    }
                }
            });
            return false;
        });

        mPairingInfoListAdapter = new PairingInfoListAdapter(mPairingInfoListViewModel.mBreedEntity, IsMen);

        list.setAdapter(mPairingInfoListAdapter);
        mPairingInfoListAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                PairingInfoEntity mPairingInfoEntity = (PairingInfoEntity) adapter.getData().get(position);
                PairingNestInfoListFragment.start(getBaseActivity(), mPairingInfoEntity, mPairingInfoListViewModel.mBreedPigeonEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        list.setRefreshListener(() -> {
            initData();
        });

        mPairingInfoListAdapter.setOnLoadMoreListener(() -> {
            setProgressVisible(true);
            mPairingInfoListViewModel.pi++;
            mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectPigeonAllData();
        }, list.getRecyclerView());
        mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectPigeonAllData();

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mPairingInfoListViewModel.mPairingInfoListData.observe(this, breedPigeonEntities -> {


            if (breedPigeonEntities.isEmpty() || breedPigeonEntities.size() == 0) {
            } else {
                if (mPairingInfoListAdapter.getHeaderViewsCount() == 0) {
                    if (IsSingle) {
                        mPairingInfoListAdapter.addHeaderView(initHeadView2());
                    } else {
                        mPairingInfoListAdapter.addHeaderView(initHeadView(breedPigeonEntities.size()));
                    }
                }
            }

            RecyclerViewUtils.setLoadMoreCallBack(list, mPairingInfoListAdapter, breedPigeonEntities);
            mPairingInfoListAdapter.getData().size();
            setProgressVisible(false);
        });
        mPairingInfoListViewModel.listEmptyMessage.observe(this, s -> {
            mPairingInfoListAdapter.setEmptyText(s);
        });

    }

    //初始化头部视图
    private View initHeadView(int count) {
        LinearLayout mHeadView = (LinearLayout) LayoutInflater.from(getBaseActivity()).inflate(R.layout.breed_detil, list, false);
        try {
//            CardView mHeadView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.layout_pairing_info_head, list, null);
            TextView manfoot = mHeadView.findViewById(R.id.man_foot);
            TextView womanfoot = mHeadView.findViewById(R.id.woman_foot);
            TextView footcount = mHeadView.findViewById(R.id.footring_count);
            //ImageView img_hint_sex = mHeadView.findViewById(R.id.img_hint_sex);
            LinearLayout ll_men = mHeadView.findViewById(R.id.men_linear);
            LinearLayout ll_women = mHeadView.findViewById(R.id.woman_ll);
            manfoot.setText(mPairingInfoListViewModel.mBreedEntity.getMenFootRingNum());
            womanfoot.setText(mPairingInfoListViewModel.mBreedEntity.getWoFootRingNum());

            if (IsMen) {
                mPairingInfoListViewModel.mBreedPigeonEntity.setPigeonID(mPairingInfoListViewModel.mBreedEntity.getMenPigeonID());
                mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingID(mPairingInfoListViewModel.mBreedEntity.getMenFootRingID());
                mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingID(mPairingInfoListViewModel.mBreedEntity.getMenFootRingNum());
                footcount.setText(mPairingInfoListViewModel.mBreedEntity.getMenFootRingNum() + "配偶" + count + "羽");
            } else {
                mPairingInfoListViewModel.mBreedPigeonEntity.setPigeonID(mPairingInfoListViewModel.mBreedEntity.getWoPigeonID());
                mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingID(mPairingInfoListViewModel.mBreedEntity.getWoFootRingID());
                mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingID(mPairingInfoListViewModel.mBreedEntity.getWoFootRingNum());
                footcount.setText(mPairingInfoListViewModel.mBreedEntity.getWoFootRingNum() + "配偶" + count + "羽");

            }


//            if (mPairingInfoListViewModel.mBreedPigeonEntity.getPigeonSexName().equals("雌")) {
//                img_hint_sex.setImageResource(R.mipmap.ic_female);
//            } else if (mPairingInfoListViewModel.mBreedPigeonEntity.getPigeonSexName().equals("雄")) {
//                img_hint_sex.setImageResource(R.mipmap.ic_male);
//            } else {
//                img_hint_sex.setImageResource(R.mipmap.ic_sex_no);
//            }

            ll_men.setOnClickListener(v -> {
                IsMen = true;
                mPairingInfoListAdapter.setIsMen(true);
                mPairingInfoListViewModel.PigeonID = mPairingInfoListViewModel.mBreedEntity.getMenPigeonID();
                mPairingInfoListViewModel.FootRingID = mPairingInfoListViewModel.mBreedEntity.getMenFootRingID();
                mPairingInfoListViewModel.mBreedPigeonEntity.setPigeonID(mPairingInfoListViewModel.mBreedEntity.getMenPigeonID());
                mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingID(mPairingInfoListViewModel.mBreedEntity.getMenFootRingID());
                mPairingInfoListViewModel.mBreedPigeonEntity.setPigeonSexName("雄");
                mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingNum(mPairingInfoListViewModel.mBreedEntity.getMenFootRingNum());
                initData();
//                BreedPigeonDetailsFragment.start(getBaseActivity(),
//                        mPairingInfoListViewModel.mBreedEntity.getMenPigeonID(),
//                        mPairingInfoListViewModel.mBreedEntity.getMenFootRingID());
            });

            ll_women.setOnClickListener(v -> {
                IsMen = false;
                mPairingInfoListAdapter.setIsMen(false);
                mPairingInfoListViewModel.PigeonID = mPairingInfoListViewModel.mBreedEntity.getWoPigeonID();
                mPairingInfoListViewModel.FootRingID = mPairingInfoListViewModel.mBreedEntity.getWoFootRingID();
                mPairingInfoListViewModel.mBreedPigeonEntity.setPigeonID(mPairingInfoListViewModel.mBreedEntity.getWoPigeonID());
                mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingID(mPairingInfoListViewModel.mBreedEntity.getWoFootRingID());
                mPairingInfoListViewModel.mBreedPigeonEntity.setFootRingNum(mPairingInfoListViewModel.mBreedEntity.getWoFootRingNum());
                mPairingInfoListViewModel.mBreedPigeonEntity.setPigeonSexName("雌");
                initData();
//                BreedPigeonDetailsFragment.start(getBaseActivity(),
//                        mPairingInfoListViewModel.mBreedEntity.getWoPigeonID(),
//                        mPairingInfoListViewModel.mBreedEntity.getWoFootRingID());
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return mHeadView;
    }


    private View initHeadView2() {
        LinearLayout mHeadView = (LinearLayout) LayoutInflater.from(getBaseActivity()).inflate(R.layout.layout_pairing_info_head, list, false);
        try {


            TextView tv_hint_foot = mHeadView.findViewById(R.id.tv_hint_foot);
            ImageView img_hint_sex = mHeadView.findViewById(R.id.img_hint_sex);
            LinearLayout ll_head = mHeadView.findViewById(R.id.ll_head);

            tv_hint_foot.setText(mPairingInfoListViewModel.mBreedPigeonEntity.getFootRingNum());

            if (mPairingInfoListViewModel.mBreedPigeonEntity.getPigeonSexName().equals("雌")) {
                img_hint_sex.setImageResource(R.mipmap.ic_female);
            } else if (mPairingInfoListViewModel.mBreedPigeonEntity.getPigeonSexName().equals("雄")) {
                img_hint_sex.setImageResource(R.mipmap.ic_male);
            } else {
                img_hint_sex.setImageResource(R.mipmap.ic_sex_no);
            }

            ll_head.setOnClickListener(v -> {
                BreedPigeonDetailsFragment.start(getBaseActivity(),
                        mPairingInfoListViewModel.mBreedPigeonEntity.getPigeonID(),
                        mPairingInfoListViewModel.mBreedPigeonEntity.getFootRingID());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mHeadView;
    }

    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PAIRING_INFO_REFRESH)) {
            initData();
        }
    }

    private void initData() {
        setProgressVisible(true);
        mPairingInfoListAdapter.getData().clear();
        mPairingInfoListAdapter.removeAllHeaderView();
        mPairingInfoListAdapter.notifyDataSetChanged();
        mPairingInfoListViewModel.pi = 1;
        mPairingInfoListViewModel.getTXGP_PigeonBreed_SelectPigeonAllData();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PairingInfoRecommendFragment.RECOMMEND_REQUEST) {
            try {
                PriringRecommendEntity item = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                PairingInfoAddFragment.start(getBaseActivity(), mPairingInfoListViewModel.mBreedPigeonEntity, item, 0);
                } catch (Exception e) {
                e.printStackTrace();
            }

//            switch (resultCode) {
//                case PairingLineageFragment.resultCode:
//                    //血统
//
//                    break;
//                case PairingPlayFragment.resultCode:
//                    //赛绩
//
//                    break;
//                case PairingScoreFragment.resultCode:
//                    //评分
//
//                    break;
//            }

        }
    }

}
