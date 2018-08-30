package com.cpigeon.book.module.photo.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;
import com.haibin.calendarview.BaseView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class PigeonPhotoHomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    int imgSize;
    RelativeLayout rlRoot;
    int marginB;

    public PigeonPhotoHomeAdapter() {
        super(R.layout.item_pigeon_photo_home, Lists.newArrayList());
        imgSize = (ScreenTool.getScreenWidth() - ScreenTool.dip2px(60)) / 3;
        marginB = ScreenTool.dip2px(114);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.img);
        helper.setGlideImageView(mContext, R.id.img, UserModel.getInstance().getUserData().touxiangurl);
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
