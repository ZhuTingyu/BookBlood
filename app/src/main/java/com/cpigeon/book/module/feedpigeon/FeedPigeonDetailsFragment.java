package com.cpigeon.book.module.feedpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.glide.GlideUtil;
import com.base.widget.recyclerview.XRecyclerView;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonPhotoEntity;
import com.cpigeon.book.module.feedpigeon.adapter.FeedPigeonDetailsAdapter;
import com.cpigeon.book.module.feedpigeon.childfragment.CareDrugFragment;
import com.cpigeon.book.module.feedpigeon.childfragment.DrugUseCaseFragment;
import com.cpigeon.book.module.feedpigeon.childfragment.StatusIllnessRecordFragment;
import com.cpigeon.book.module.feedpigeon.childfragment.UseVaccineFragment;
import com.cpigeon.book.module.feedpigeon.viewmodel.FeedPigeonListViewModel;
import com.cpigeon.book.module.photo.BaseImgUploadFragment;
import com.cpigeon.book.module.photo.PigeonPhotoDetailsFragment;
import com.cpigeon.book.module.photo.SnapshotImgUploadFragment;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.util.PigeonPublicUtil;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 养鸽记录详情
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonDetailsFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    FeedPigeonDetailsAdapter mAdapter;

    private FeedPigeonListViewModel mFeedPigeonListViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mFeedPigeonListViewModel = new FeedPigeonListViewModel();
        initViewModels(mFeedPigeonListViewModel);
    }

    public static void start(Activity activity, PigeonEntity mBreedPigeonEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity)
                .startParentActivity(activity, FeedPigeonDetailsFragment.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mFeedPigeonListViewModel.mFeedPigeonListData.observe(this, datas -> {

            if (datas.isEmpty() || datas.size() == 0) {

            } else {
                if (mAdapter.getHeaderViewsCount() == 0) {
                    mAdapter.addHeaderView(initView());
                    mFeedPigeonListViewModel.getTXGP_Pigeon_SelectIDCountData();
                }
            }
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);

            setProgressVisible(false);
        });

        mFeedPigeonListViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        //养鸽记录统计
        mFeedPigeonListViewModel.mFeedPigeonStatistical.observe(this, datas -> {

            Glide.with(getBaseActivity())
                    .load(mFeedPigeonListViewModel.mPigeonEntity.getCoverPhotoUrl())
                    .placeholder(R.drawable.ic_img_default)
                    .into(mCircleImageView);

            mTvFootNumber.setText(mFeedPigeonListViewModel.mPigeonEntity.getFootRingNum());

            PigeonPublicUtil.setPigeonSexImg(mFeedPigeonListViewModel.mPigeonEntity.getPigeonSexName(), mImgSex);

            mTvStatus.setText(mFeedPigeonListViewModel.mPigeonEntity.getStateName());

            String remark = "注射疫苗" + datas.getVaccineCount() + "次," +
                    "保健" + datas.getHealthCount() + "次," +
                    "用药" + datas.getDrugCount() + "次," +
                    "病情" + datas.getDiseaseCount() + "次," +
                    "随拍" + datas.getPhotoCount() + "张";

            mTvRemark.setText(remark);

        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.getMenu().clear();
        toolbar.getMenu().add("")
                .setIcon(R.mipmap.ic_feed_record_photo)
                .setOnMenuItemClickListener(item -> {

                    PictureSelectUtil.openCamera(getBaseActivity());

                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        toolbar.getMenu().add("")
                .setIcon(R.mipmap.ic_feed_record_add)
                .setOnMenuItemClickListener(item -> {
                    IntentBuilder.Builder(getBaseActivity(), FeedPigeonRecordActivity.class)
                            .putExtra(IntentBuilder.KEY_DATA, mFeedPigeonListViewModel.mPigeonEntity)
                            .startActivity();
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        setTitle(R.string.text_feed_pigeon_record);


        mFeedPigeonListViewModel.mPigeonEntity = (PigeonEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);

        mRecyclerView = findViewById(R.id.list);
        mAdapter = new FeedPigeonDetailsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newArrayList());
        mAdapter.addHeaderView(initView());

        mRecyclerView.setRefreshListener(() -> {
            initData();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            mAdapter.setLoadMore(true);

//            setProgressVisible(true);
//            mFeedPigeonListViewModel.pi++;
//            mFeedPigeonListViewModel.getTXGP_Pigeon_SelectIDCountData();
//            mFeedPigeonListViewModel.getTXGP_Pigeon_SelectRecordData();
        }, mRecyclerView.getRecyclerView());


        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            try {
                FeedPigeonEntity item = mAdapter.getData().get(position);

                if (item.getTypeID() == 5) { //随拍
    //                PigeonPhotoDetailsFragment.start(getBaseActivity(), mFeedPigeonListViewModel.mPigeonEntity.getFootRingNum(), 0);

                    PigeonPhotoDetailsFragment.start(getBaseActivity(),
                            mFeedPigeonListViewModel.mPigeonEntity,
                            Lists.newArrayList(new PigeonPhotoEntity.Builder()
                                    .FootRingID(item.getFootRingID())
                                    .PigeonPhotoID(item.getViewID())
                                    .PhotoUrl(item.getName())
                                    .TypeName(getString(R.string.text_nef))
                                    .AddTime(item.getUseTime())
                                    .Remark(item.getListInfo())
                                    .build()),
                            0);
                } else if (item.getTypeID() == 3) {//用药
                    DrugUseCaseFragment.start(getBaseActivity(), mFeedPigeonListViewModel.mPigeonEntity, item, 1);
                } else if (item.getTypeID() == 1) {//保健
                    CareDrugFragment.start(getBaseActivity(), mFeedPigeonListViewModel.mPigeonEntity, item, 1);
                } else if (item.getTypeID() == 2) {//疫苗
                    UseVaccineFragment.start(getBaseActivity(), mFeedPigeonListViewModel.mPigeonEntity, item, 1);
                } else if (item.getTypeID() == 4) {//病情
                    StatusIllnessRecordFragment.start(getBaseActivity(), mFeedPigeonListViewModel.mPigeonEntity, item, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setProgressVisible(true);
        //获取养鸽记录 列表
        mFeedPigeonListViewModel.getTXGP_Pigeon_SelectRecordData();
        //获取养鸽记录统计
        mFeedPigeonListViewModel.getTXGP_Pigeon_SelectIDCountData();
    }


    CircleImageView mCircleImageView;
    TextView mTvFootNumber;
    ImageView mImgSex;
    TextView mTvStatus;
    TextView mTvRemark;

    private View initView() {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_feed_pigeon_details_head, null);

        mCircleImageView = view.findViewById(R.id.circleImageView);
        mTvFootNumber = view.findViewById(R.id.tvFootNumber);
        mImgSex = view.findViewById(R.id.imgSex);
        mTvStatus = view.findViewById(R.id.tvStatus);
        mTvRemark = view.findViewById(R.id.tvRemark);

        GlideUtil.setGlideImageView(getBaseActivity(), UserModel.getInstance().getUserData().touxiangurl, mCircleImageView);
        mTvFootNumber.setText("");


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;

        if (requestCode == 1) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

            BaseImgUploadFragment.start(getBaseActivity(),
                    SnapshotImgUploadFragment.class,
                    new ImgTypeEntity.Builder()
                            .foootId(mFeedPigeonListViewModel.mPigeonEntity.getFootRingID())
                            .pigeonId(mFeedPigeonListViewModel.mPigeonEntity.getPigeonID())
                            .imgTypeSpecified(getString(R.string.text_nef))
                            .imgPath(selectList.get(0).getCompressPath())
                            .build());
        }
    }

    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.FEED_PIGEON_DETAILS_REFRESH) || info.equals(EventBusService.PIGEON_PHOTO_REFRESH)) {
            initData();
        }
    }

    private void initData() {
        setProgressVisible(true);
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        mFeedPigeonListViewModel.pi = 1;
        mFeedPigeonListViewModel.getTXGP_Pigeon_SelectIDCountData();
        mFeedPigeonListViewModel.getTXGP_Pigeon_SelectRecordData();
    }
}
