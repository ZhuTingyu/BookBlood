package com.cpigeon.book.module.feedpigeon;

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
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.module.feedpigeon.adapter.FeedPigeonDetailsAdapter;
import com.cpigeon.book.module.photo.ImgUploadFragment;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonDetailsFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    FeedPigeonDetailsAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
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
                            .startActivity();
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        setTitle(R.string.text_feed_pigeon_record);

        mRecyclerView = findViewById(R.id.list);
        mAdapter = new FeedPigeonDetailsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newArrayList("", "", "", "", ""));
        mAdapter.addHeaderView(initView());
    }

    private View initView() {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_feed_pigeon_details_head, null);

        CircleImageView mCircleImageView;
        TextView mTvFootNumber;
        ImageView mImgSex;
        TextView mTvStatus;
        TextView mTvRemark;

        mCircleImageView = view.findViewById(R.id.circleImageView);
        mTvFootNumber = view.findViewById(R.id.tvFootNumber);
        mImgSex = view.findViewById(R.id.imgSex);
        mTvStatus = view.findViewById(R.id.tvStatus);
        mTvRemark = view.findViewById(R.id.tvRemark);

        GlideUtil.setGlideImageView(getBaseActivity(), UserModel.getInstance().getUserData().touxiangurl, mCircleImageView);
        mTvFootNumber.setText("2018-12-224645");


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;

        if (requestCode == 1) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

            IntentBuilder.Builder().putExtra(IntentBuilder.KEY_TYPE, new ImgTypeEntity.Builder().imgPath(selectList.get(0).getCompressPath())
                    .imgType(ImgTypeEntity.TYPE_NEF)
                    .build())
                    .startParentActivity(getBaseActivity(), ImgUploadFragment.class, ImgUploadFragment.CODE_SELECT_COUNTY);

        }
    }
}
