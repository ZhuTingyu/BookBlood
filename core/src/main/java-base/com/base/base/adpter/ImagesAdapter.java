package com.base.base.adpter;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.base.base.BaseViewHolder;
import com.base.base.ShowImageActivity;
import com.base.event.ShowImagePositionEvent;
import com.base.http.R;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.system.ScreenTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhu TingYu on 2018/1/15.
 */

public class ImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    int position = 0;
    RecyclerView recyclerView;

    public ImagesAdapter(RecyclerView recyclerView) {
        super(R.layout.item_one_image_layout, Lists.newArrayList());
        this.recyclerView = recyclerView;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        AppCompatImageView view = holder.getView(R.id.icon);
        holder.setGlideImageView(mContext,R.id.icon, item);

        view.setOnClickListener(v -> {
            setPosition(holder.getAdapterPosition());
            setBackImage();
            PictureSelectUtil.showImagePhoto((Activity) mContext,view, getData(), position);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShowImagePositionEvent event) {
        position = event.position;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setBackImage(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) mContext).setExitSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    sharedElements.put(ShowImageActivity.TRANSITION, getViewByPosition(recyclerView, position, R.id.icon));
                    super.onMapSharedElements(names, sharedElements);
                }
            });
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
