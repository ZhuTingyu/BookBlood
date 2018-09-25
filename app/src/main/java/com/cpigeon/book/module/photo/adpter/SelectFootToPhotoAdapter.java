package com.cpigeon.book.module.photo.adpter;

import android.widget.ImageView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonListAdapter;
import com.cpigeon.book.module.photo.PigeonPhotoHomeActivity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class SelectFootToPhotoAdapter extends BasePigeonListAdapter {

    public SelectFootToPhotoAdapter() {
        super(R.layout.item_selecte_foot_to_photo, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {

        Glide.with(mContext)
                .load(item.getCoverPhotoUrl())
                .placeholder(R.drawable.ic_img_default)
                .into((ImageView) helper.getView(R.id.imgHead));

        helper.setText(R.id.tvColor, item.getPigeonPlumeName());

        helper.setText(R.id.tvFootNumber, item.getFootRingNum());
    }
}
