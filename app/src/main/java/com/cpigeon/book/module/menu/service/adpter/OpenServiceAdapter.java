package com.cpigeon.book.module.menu.service.adpter;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.ServiceEntity;
import com.cpigeon.book.module.menu.service.ChoosePayWayDialog;
import com.cpigeon.book.veiwholder.ServiceViewHolder;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class OpenServiceAdapter extends BaseQuickAdapter<ServiceEntity, ServiceViewHolder> {

   private String type;
   private boolean mIsOpen;

    public OpenServiceAdapter(String type, boolean isOpen) {
        super(R.layout.item_service_open, Lists.newArrayList());
        this.type = type;
        this.mIsOpen = isOpen;
    }

    @Override
    protected void convert(ServiceViewHolder helper, ServiceEntity item) {


        /*SpannableStringBuilder span = new SpannableStringBuilder("享用所有功能，不受限制，只要:");
        SpannableString odlMoney = new SpannableString("188");
        odlMoney.setSpan(new StrikethroughSpan(), 0, odlMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString newMoney = new SpannableString("188");
        newMoney.setSpan(new ForegroundColorSpan(Color.RED), 0, newMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.append(newMoney);
        span.append("不要");
        span.append(odlMoney);
        */


        helper.bindData(type, item , v -> {
            ChoosePayWayDialog.show(item, mIsOpen ,getBaseActivity().getSupportFragmentManager());
        });
    }
}
