package com.cpigeon.book.module.photo.adpter;

import android.support.v7.widget.RecyclerView;
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
    int rootCW;
    int marginB;


    public PigeonPhotoHomeAdapter() {
        super(R.layout.item_pigeon_photo_home, Lists.newArrayList());
        imgSize = (ScreenTool.getScreenWidth() - 60) / 4;
        rootCW = imgSize + ScreenTool.dip2px(20);
        marginB = ScreenTool.dip2px(10);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RelativeLayout root = helper.getView(R.id.rlRoot);
        ImageView imageView = helper.getView(R.id.img);

        int rootW;
        int rootH;

        helper.setGlideImageView(mContext, R.id.img,UserModel.getInstance().getUserData().touxiangurl);

        if(helper.getAdapterPosition() % 3 == 1){
            rootW = rootCW;
            rootH = imgSize;
        }else {
            rootW = imgSize;
            rootH = imgSize;
        }

        RecyclerView.LayoutParams rootP = new RecyclerView.LayoutParams(rootW, rootH);
        rootP.setMargins(0, 0, 0, marginB);
        RelativeLayout.LayoutParams imgP = new RelativeLayout.LayoutParams(imgSize, imgSize);
        root.setLayoutParams(rootP);
        imageView.setLayoutParams(imgP);

    }
}
