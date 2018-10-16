package com.cpigeon.book.module.menu.feedback.adpter;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.base.base.BaseViewHolder;
import com.base.base.ShowImageActivity;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.event.ShowImagePositionEvent;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.system.ScreenTool;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.ImageEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Zhu TingYu on 2018/8/24.
 */

public class FeedbackDetailsImageAdapter extends BaseQuickAdapter<ImageEntity, BaseViewHolder> {

    int w;
    RecyclerView mRecyclerView;
    int position;

    public FeedbackDetailsImageAdapter(RecyclerView recyclerView) {
        super(R.layout.item_selecte_image_show, Lists.newArrayList());
        w = ScreenTool.dip2px(261) / 4;
        mRecyclerView = recyclerView;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageEntity item) {
//        helper.setGlideImageViewHaveRound(mContext, R.id.img, item.getImgurl());

        Glide.with(mContext)
                .load(item.getImgurl())
                .bitmapTransform(new RoundedCornersTransformation(mContext,13, 0, RoundedCornersTransformation.CornerType.ALL))
                .placeholder(mContext.getResources().getDrawable(R.mipmap.ic_default_head))
                .into((ImageView) helper.getView(R.id.img));

        RelativeLayout rlRoot = helper.getView(R.id.rlRoot);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (helper.getAdapterPosition() > 3) {
            layoutParams.setMargins(0, ScreenTool.dip2px(10), 0, 0);
        } else {
            layoutParams.setMargins(0, 0, 0, 0);
        }
        rlRoot.setLayoutParams(layoutParams);

        rlRoot.setOnClickListener(v -> {
            setPosition(helper.getAdapterPosition());
            PictureSelectUtil.showImagePhoto(getBaseActivity(), helper.getView(R.id.img)
                    , ImageEntity.getUrls(getData()), position);
            setBackImage();
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShowImagePositionEvent event) {
        setPosition(event.position);
    }

    public void setBackImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) mContext).setExitSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    sharedElements.put(ShowImageActivity.TRANSITION, getViewByPosition(mRecyclerView, position, R.id.img));
                    super.onMapSharedElements(names, sharedElements);
                }
            });
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
