package com.base.base;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.base.event.ShowImagePositionEvent;
import com.base.util.BarUtils;
import com.base.widget.magicindicator.MagicIndicator;
import com.base.widget.magicindicator.ViewPagerHelper;
import com.base.widget.magicindicator.ext.navigator.ScaleCircleNavigator;
import com.base.widget.photoview.PhotoView;
import com.base.widget.videoplay.VideoPlayActivity;
import com.base.http.R;
import com.base.util.Lists;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.dialog.PictureDialog;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.widget.PreviewViewPager;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



/**
 * 大图浏览
 * Created by Zhu TingYu on 2018/3/21.
 */

public class ShowImageActivity extends BaseActivity {

    public final static String TRANSITION = "IMG_TRANSITION";

    PreviewViewPager viewPager;
    private LayoutInflater inflater;
    private List<LocalMedia> images = Lists.newArrayList();

    PictureDialog dialog;

    SimpleFragmentAdapter adapter;
    int position;

    public static void startActivity(Activity activity, View view, int position, List<LocalMedia> images) {
        Intent intent = new Intent(activity, ShowImageActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Pair pair = new Pair<>(view, TRANSITION);
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair);
            intent.putExtra("previewSelectList", (Serializable) images);
            intent.putExtra("position", position);
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarVisibility(getWindow(),false);
        BarUtils.setStatusBarAllAlpha(this);
        setContentView(R.layout.activity_show_image_layout);
        inflater = LayoutInflater.from(this);
        images = (List) this.getIntent().getSerializableExtra("previewSelectList");
        position = this.getIntent().getIntExtra("position", 0);
        viewPager = findViewById(R.id.preview_pager);
        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator3);


        adapter = new SimpleFragmentAdapter();
        this.viewPager.setAdapter(adapter);
        this.viewPager.setCurrentItem(position);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                EventBus.getDefault().post(new ShowImagePositionEvent(position));
            }

            public void onPageScrollStateChanged(int state) {
            }
        });

        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        scaleCircleNavigator.setCircleCount(images.size());
        scaleCircleNavigator.setNormalCircleColor(getResources().getColor(R.color.darker_gray));
        scaleCircleNavigator.setSelectedCircleColor(getResources().getColor(R.color.white));
        scaleCircleNavigator.onPageSelected(position);
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                viewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);

        if(images.size() == 1){
            magicIndicator.setVisibility(View.GONE);
        }

        //过渡动画
        initTransition();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    sharedElements.put(TRANSITION, viewPager.findViewWithTag(viewPager.getCurrentItem()));
                    super.onMapSharedElements(names, sharedElements);
                }
            });
        }
    }

    private void initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(viewPager, TRANSITION);
            startPostponedEnterTransition();
        }
    }

    protected void showPleaseDialog() {
        if (!this.isFinishing()) {
            this.dismissDialog();
            this.dialog = new PictureDialog(this);
            this.dialog.show();
        }

    }

    protected void dismissDialog() {
        try {
            if (this.dialog != null && this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public class SimpleFragmentAdapter extends PagerAdapter {
        public SimpleFragmentAdapter() {
        }

        public int getCount() {
            return ShowImageActivity.this.images.size();
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            View contentView = ShowImageActivity.this.inflater.inflate(R.layout.item_image_preview, container, false);
            final PhotoView imageView = (PhotoView) contentView.findViewById(com.luck.picture.lib.R.id.preview_image);
            LocalMedia media = (LocalMedia) ShowImageActivity.this.images.get(position);
            if (media != null) {
                String pictureType = media.getPictureType();
                final String path;
                if (media.isCut() && !media.isCompressed()) {
                    path = media.getCutPath();
                } else if (!media.isCompressed() && (!media.isCut() || !media.isCompressed())) {
                    path = media.getPath();
                } else {
                    path = media.getCompressPath();
                }

                boolean isHttp = PictureMimeType.isHttp(path);
                if (isHttp) {
                    ShowImageActivity.this.showPleaseDialog();
                }

                boolean isGif = PictureMimeType.isGif(pictureType);
                if(isGif && !media.isCompressed()) {
                    Glide.with(ShowImageActivity.this).load(path).asGif().override(480, 800).diskCacheStrategy(DiskCacheStrategy.SOURCE).priority(Priority.HIGH).into(imageView);
                    ShowImageActivity.this.dismissDialog();
                } else {
                    Glide.with(ShowImageActivity.this).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT).into(new SimpleTarget<Bitmap>(480, 800) {
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            imageView.setImageBitmap(resource);
                            ShowImageActivity.this.dismissDialog();
                        }
                    });
                }

                imageView.setOnViewTapListener((view, x, y) -> {
                    onBackPressed();

                });



            }
            imageView.setTag(position);
            container.addView(contentView, 0);
            return contentView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BarUtils.setStatusBarVisibility(getWindow(),true);
    }
}
