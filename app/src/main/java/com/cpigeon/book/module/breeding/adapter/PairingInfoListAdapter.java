package com.cpigeon.book.module.breeding.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoListAdapter extends BaseQuickAdapter<PairingInfoEntity, BaseViewHolder> {


    public PigeonEntity mPigeonEntity;

    public PairingInfoListAdapter(PigeonEntity mPigeonEntity) {
        super(R.layout.item_pairing_info, Lists.newArrayList());
        this.mPigeonEntity = mPigeonEntity;
    }

    @Override
    protected void convert(BaseViewHolder helper, PairingInfoEntity item) {

        if (mPigeonEntity.getPigeonSexName().equals("雄")) {

            helper.setText(R.id.tv_foot, item.getWoFootRingNum());
            helper.setText(R.id.tv_lineage, item.getWoPigeonBloodName());

        } else if (mPigeonEntity.getPigeonSexName().equals("雌")) {
            helper.setText(R.id.tv_foot, item.getMenFootRingNum());
            helper.setText(R.id.tv_lineage, item.getMenPigeonBloodName());
        }

        helper.setText(R.id.tv_nest_num, item.getLayNum() + "窝");

    }
}
