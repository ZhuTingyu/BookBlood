package com.cpigeon.book.module.play.adapter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Lists;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonListAdapter;
import com.cpigeon.book.util.PigeonPublicUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 赛鸽管理  足环列表  适配器
 * Created by Administrator on 2018/9/21 0021.
 */

public class PlayFootListAdapter extends BasePigeonListAdapter {

    public PlayFootListAdapter() {
        super(R.layout.item_play_foot, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {
        super.convert(helper, item);

        //头像
        Glide.with(mContext)
                .load(item.getCoverPhotoUrl())
                .placeholder(R.drawable.ic_img_default)
                .into((CircleImageView) helper.getView(R.id.imgHead));//鸽子照片

        //设置性别
        PigeonPublicUtil.setPigeonSexImg(item.getPigeonSexName(), helper.getView(R.id.img_sex));

        helper.setText(R.id.tv_foot, item.getFootRingNum());

        helper.setText(R.id.tv_lineage, item.getPigeonBloodName());//血统

        //鸽子状态
        TextView tv_state = helper.getView(R.id.tv_state);
        tv_state.setText(item.getStateName());
        if (item.getStateName().equals(mContext.getString(R.string.string_die))) {
            tv_state.setTextColor(getColor(R.color.general_text_hint_color));
        } else {
            tv_state.setTextColor(getColor(R.color.general_text_color));
        }
    }
}
