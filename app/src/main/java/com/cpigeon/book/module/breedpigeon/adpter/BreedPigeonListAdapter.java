package com.cpigeon.book.module.breedpigeon.adpter;

import android.widget.ImageView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.BreedPigeonEntity;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

//public class BreedPigeonListAdapter extends BaseQuickAdapter<String, BreedPigeonListViewHolder> {
//
//    public BreedPigeonListAdapter() {
//        super(R.layout.item_breed_pigeon_list, Lists.newArrayList());
//    }
//
//    @Override
//    protected void convert(BreedPigeonListViewHolder helper, String item) {
//        helper.bindData(item);
//    }
//}

public class BreedPigeonListAdapter extends BaseQuickAdapter<BreedPigeonEntity, BaseViewHolder> {

    public BreedPigeonListAdapter() {
        super(R.layout.item_breed_pigeon_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, BreedPigeonEntity item) {

        ImageView imgSex = helper.getView(R.id.imgSex);

        helper.setText(R.id.tvColor, item.getPigeonPlumeName());

        helper.setText(R.id.tvTime, item.getFootRingNum());

        Glide.with(mContext)
                .load(item.getCoverPhotoUrl())
                .placeholder(R.drawable.ic_img_default)
                .into((ImageView) helper.getView(R.id.imgHead));


        if (item.getPigeonSexName().equals("雌")) {
            imgSex.setImageResource(R.mipmap.ic_female);
        } else if (item.getPigeonSexName().equals("雄")) {
            imgSex.setImageResource(R.mipmap.ic_male);
        } else {
            imgSex.setImageResource(R.mipmap.ic_sex_no);
        }

    }
}
