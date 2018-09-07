package com.cpigeon.book.module.play.adapter;

import android.view.View;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonPlayEntity;

/**
 * Created by Administrator on 2018/9/5.
 */

public class PlayListAdapter extends BaseQuickAdapter<PigeonPlayEntity, BaseViewHolder> {


    public PlayListAdapter() {
        super(R.layout.item_pigeon_play, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonPlayEntity item) {

        TextView tv_source = helper.getView(R.id.tv_source);
        tv_source.setVisibility(View.GONE);

        //赛事名称
        helper.setText(R.id.tv_title, item.getMatchName());
        helper.setText(R.id.tv_ullage, "空距 | " + item.getMatchInterval() + "公里");
        helper.setText(R.id.tv_time, "比赛日期 | " + item.getMatchTime());
        helper.setText(R.id.tv_paly_rank, "第" + item.getMatchNumber() + "名");//比赛名次
        helper.setText(R.id.tv_paly_scale, "总" + item.getMatchCount() + "羽");//比赛名次

        if (Integer.valueOf(item.getBitUpdate()) == 1) {
            //手动录入
            tv_source.setVisibility(View.GONE);
        } else {
            //导入赛绩
            tv_source.setVisibility(View.VISIBLE);
            tv_source.setOnClickListener(v -> {

            });
        }

    }
}
