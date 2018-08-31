package com.cpigeon.book.module.menu.service.adpter;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.veiwholder.ServiceViewHolder;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class RenewAdapter extends BaseQuickAdapter<String, ServiceViewHolder> {

    public RenewAdapter() {
        super(R.layout.item_service_open, Lists.newArrayList());
    }

    @Override
    protected void convert(ServiceViewHolder helper, String item) {
        helper.bindData(ServiceViewHolder.TYPE_OPEN, v -> {

        });
    }
}
