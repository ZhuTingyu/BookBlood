package com.cpigeon.book.module.menu.service.adpter;

import android.text.SpannableStringBuilder;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.module.menu.service.PayOpenServiceDialog;
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

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("123144444444444");

        helper.bindData(ServiceViewHolder.TYPE_OPEN, stringBuilder, v -> {
            PayOpenServiceDialog.show("", getBaseActivity().getSupportFragmentManager());
        });
    }
}
