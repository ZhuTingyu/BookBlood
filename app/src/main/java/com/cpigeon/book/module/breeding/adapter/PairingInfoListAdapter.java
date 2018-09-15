package com.cpigeon.book.module.breeding.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;

/**
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoListAdapter extends BaseQuickAdapter<PairingInfoEntity, BaseViewHolder> {


    public BreedPigeonEntity mBreedPigeonEntity;

    public PairingInfoListAdapter(BreedPigeonEntity mBreedPigeonEntity) {
        super(R.layout.item_pairing_info, Lists.newArrayList());
        this.mBreedPigeonEntity = mBreedPigeonEntity;
    }

    @Override
    protected void convert(BaseViewHolder helper, PairingInfoEntity item) {

        if (mBreedPigeonEntity.getPigeonSexName().equals("雄")) {

            helper.setText(R.id.tv_foot, item.getWoFootRingNum());
            helper.setText(R.id.tv_lineage, item.getWoPigeonBloodName());

        } else if (mBreedPigeonEntity.getPigeonSexName().equals("雌")) {
            helper.setText(R.id.tv_foot, item.getMenFootRingNum());
            helper.setText(R.id.tv_lineage, item.getMenPigeonBloodName());
        }

        helper.setText(R.id.tv_nest_num, item.getLayNum() + "窝");

    }
}
