package com.cpigeon.book.module.photo.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonPhotoEntity;


/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class PigeonPhotoHomeAdapter extends BaseQuickAdapter<PigeonPhotoEntity, BaseViewHolder> {

    int imgSize;
    RelativeLayout rlRoot;
    int marginB;

    public PigeonPhotoHomeAdapter() {
        super(R.layout.item_pigeon_photo_home, Lists.newArrayList());
        imgSize = (ScreenTool.getScreenWidth() - ScreenTool.dip2px(60)) / 3;
        marginB = ScreenTool.dip2px(114);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonPhotoEntity item) {
        ImageView imageView = helper.getView(R.id.img);
        Glide.with(getBaseActivity())
                .load(item.getPhotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_img_default2)
                .error(R.drawable.ic_img_default2)
                .dontAnimate()
                .into(imageView);



        rlRoot = helper.getView(R.id.rlRoot);
        RecyclerView.LayoutParams rootP = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imgSize);

        int dataSize = getData().size();
        int p = helper.getAdapterPosition();
        boolean isB = false;
        if (dataSize % 3 == 0) {
            if (p == dataSize - 3 || p == dataSize - 2 || p == dataSize - 1) {
                isB = true;
            }
        } else if (dataSize % 3 == 1) {
            if (p == dataSize - 1) {
                isB = true;
            }
        } else if (dataSize % 3 == 2) {
            if (p == dataSize - 2 || p == dataSize - 1) {
                isB = true;
            }
        }

        if(isB){
            rootP.setMargins(0, 0, 0, marginB);
        }

        rlRoot.setLayoutParams(rootP);

    }
}
