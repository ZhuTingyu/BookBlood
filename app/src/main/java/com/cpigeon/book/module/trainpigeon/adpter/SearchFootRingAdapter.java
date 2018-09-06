package com.cpigeon.book.module.trainpigeon.adpter;

import android.view.View;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class SearchFootRingAdapter extends BaseQuickAdapter<String, BaseViewHolder>{
    public SearchFootRingAdapter() {
        super(R.layout.item_new_train_add_pigeon, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.getView(R.id.imgAdd).setVisibility(View.GONE);
        helper.setText(R.id.tvFootNumber,"2018-22-1234567");
        helper.setText(R.id.tvColor, "白色");
        helper.setText(R.id.tvBlood, "詹森");
    }
}
