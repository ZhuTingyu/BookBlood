package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseActivity;
import com.base.util.BarUtils;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookActivity;
import com.cpigeon.book.module.photo.adpter.PigeonPhotoHomeAdapter;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class PigeonPhotoHomeActivity extends BaseBookActivity{

    private CollapsingToolbarLayout mCoordinator;
    private ImageView mImgHead;
    private TextView mTvFootNumber;
    private TextView mTvPhotoCount;
    private AppBarLayout mAppBar;
    PigeonPhotoHomeAdapter mAdapter;
    RecyclerView mRecyclerView;


    public static void start(Activity activity){
        IntentBuilder.Builder(activity, PigeonPhotoHomeActivity.class)
                .putExtra(BaseActivity.IS_BAR_IMMERSIVE, false)
                .startActivity();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pigeon_photo_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoordinator = findViewById(R.id.coordinator);
        mImgHead = findViewById(R.id.imgHead);
        mTvFootNumber = findViewById(R.id.tvFootNumber);
        mTvPhotoCount = findViewById(R.id.tvPhotoCount);
        mAppBar = findViewById(R.id.appbar);
        mRecyclerView = findViewById(R.id.list);
        mAdapter = new PigeonPhotoHomeAdapter();


        mCoordinator.setTitle("2018-22-123456");
        //通过CollapsingToolbarLayout修改字体颜色
        mCoordinator.setExpandedTitleColor(Color.TRANSPARENT);//设置还没收缩时状态下字体颜色
        mCoordinator.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        mAppBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mCoordinator.setTitle(null);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mCoordinator.setTitle("2018-22-123456");
                } else {
                    //中间状态
                    mCoordinator.setTitle("2018-22-123456");
                }
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(getBaseActivity(), 3));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());

    }

    private void initHead(){

    }
}
