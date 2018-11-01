package com.cpigeon.book.module.basepigeon;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;

import java.util.List;

/**
 * 所有鸽子列表  base  适配器
 * Created by Administrator on 2018/9/21 0021.
 */

public class BasePigeonListAdapter extends BaseQuickAdapter<PigeonEntity, BaseViewHolder> {
    protected  OnDeleteListener onDeleteListener;
    public void BasePigeonListAdapter(OnDeleteListener onDeleteListener )
    {
        this.onDeleteListener=onDeleteListener;
    }

    public BasePigeonListAdapter(int layoutResId, List<PigeonEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {

    }
    public void  setParams(TextView tv, int Resource)
    {
        tv.setPadding(5,2,5,2);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tv.getLayoutParams();
        params.height=60;
        params.setMargins(0,0,10,0);
        tv.setBackgroundResource(Resource);
        tv.setLayoutParams(params);
    }
    public void  defultParams(TextView tv,int Resource)
    {
        tv.setPadding(0,0,0,0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tv.getLayoutParams();
        params.height=60;
        params.setMargins(0,0,0,0);
        tv.setBackgroundResource(Resource);
        tv.setLayoutParams(params);
    }
}
