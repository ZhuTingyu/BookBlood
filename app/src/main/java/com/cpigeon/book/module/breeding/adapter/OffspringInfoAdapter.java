package com.cpigeon.book.module.breeding.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;

import java.util.List;

/**
 * 配对信息  --》 子代信息
 * Created by Administrator on 2018/9/19 0019.
 */

public class OffspringInfoAdapter extends BaseQuickAdapter<PairingNestInfoEntity.PigeonListBean, BaseViewHolder> {


    public OffspringChildView offspringChildView;

    public OffspringInfoAdapter() {
        super(R.layout.item_offspring_info, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PairingNestInfoEntity.PigeonListBean item) {
        helper.setText(R.id.tv_content, item.getFootRingNum() + "  " + item.getPigeonPlumeName());

        try {
            offspringChildView.itemView(helper.getPosition(), helper.getView(R.id.img_close), helper.getView(R.id.tv_content), item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface OffspringChildView {
        void itemView(int position, ImageView imgClose, TextView tvContent, PairingNestInfoEntity.PigeonListBean item);
    }

    public void setOffspringChildViewClick(OffspringChildView offspringChildView) {
        this.offspringChildView = offspringChildView;
    }
}
