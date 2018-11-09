package com.cpigeon.book.module.breedpigeon.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.GrowthReportEntity;
import com.cpigeon.book.module.photo.adpter.ImageItemDecoration;

/**
 * Created by Zhu TingYu on 2018/8/29.
 */

public class GrowthReportAdapter extends BaseQuickAdapter<GrowthReportEntity, BaseViewHolder> {
    private static final int TAG_IS_ADD_DECORATION = 0;
    private String split = "   ";

    ImageItemDecoration mItemDecoration;

    public GrowthReportAdapter() {
        super(R.layout.item_growth_report, Lists.newArrayList());
        mItemDecoration = new ImageItemDecoration(ScreenTool.dip2px(5), 3);
    }

    @Override
    protected void convert(BaseViewHolder helper, GrowthReportEntity item) {

        ImageView icon = helper.getView(R.id.imgIcon);
        ImageView icon2 = helper.getView(R.id.img);
        helper.setText(R.id.tvDay, String.valueOf(TimeUtil.getTimeFormat(item.getUseTime(), TimeUtil.FORMAT_dd)));
        helper.setText(R.id.tvYear, String.valueOf(TimeUtil.getTimeFormat(item.getUseTime(), TimeUtil.FORMAT_YYYYMM)));
        helper.setText(R.id.tvTitle, item.getTypeName());
         icon.setImageResource(R.mipmap.ic_report_auction);
        icon2.setVisibility(View.GONE);
        TextView tv1 = helper.getView(R.id.tv1);
        TextView tv2 = helper.getView(R.id.tv2);


        if (getData().size() == 1) {
            helper.getView(R.id.rlArrow).setVisibility(View.GONE);
        } else if (getData().size() >= 2) {
            if (helper.getAdapterPosition() == getData().size() - 1) {
                helper.getView(R.id.rlArrow).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.rlArrow).setVisibility(View.VISIBLE);
            }
        }

        switch (item.getTypeID()) {
            case 1://状态
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);

                helper.setText(R.id.tv1,item.getRemark());
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_auction);
                break;

            case 2://出壳
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                helper.setText(R.id.tv1,item.getRemark());
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_hatches);
                break;

            case 3://挂环
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                helper.setText(R.id.tv1, item.getRemark());
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_set_foot);

                break;

            case 4: //窝次信息  需要拆分字符串
            if(item.equals(StringUtil.emptyString()))
            {
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                helper.setText(R.id.tv1,"与"+item.getFootRingNum()+"于今日产下第"+item.getNumber()+"窝");
            }
            else
            {
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                helper.setText(R.id.tv1,"与"+item.getFootRingNum()+"于今日产下第"+item.getNumber()+"窝");

            }





                break;

            case 5  ://配对
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_pair);
                helper.setText(R.id.tv1, item.getRemark());
                helper.setText(R.id.tv2, item.getFootRingNum() + split + item.getPlumeName()+split+item.getBloodName());

                break;

            case 6://生病
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_condition);
                helper.setText(R.id.tv1, "病情名称：" + item.getName()+split+"病情症状：" + item.getInfo());
                helper.setText(R.id.tv2, item.getRemark());

                break;

            case 7://用药

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_drug_use);

                helper.setText(R.id.tv1, "药品名称：" + item.getName()+"|使用效果："+item.getInfo());
                helper.setText(R.id.tv2, item.getRemark());

                break;

            case 8://疫苗

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_vaccine);

                helper.setText(R.id.tv1, "疫苗名称：" + item.getName()+"|使用效果："+item.getInfo());
                helper.setText(R.id.tv2, item.getRemark());

                break;

            case 9://保健品


                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_care);
                helper.setText(R.id.tv1, "保健品名称：" + item.getName());
                helper.setText(R.id.tv2, item.getRemark());

                break;
            case 10://训练

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_train);

                helper.setText(R.id.tv1, "第"+item.getNumber() + "名" + split +"总" +item.getCount() + "羽" + split+item.getFraction()+split+item.getName());

                helper.setText(R.id.tv2, item.getRemark());
                break;

            case 11://比赛
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_match);
                helper.setText(R.id.tv1, "第"+item.getNumber() + "名" + split +"总" +item.getCount() + "羽" + split+item.getName());

                helper.setText(R.id.tv2, item.getRemark());

                break;

        }
        addTopAndBttomMargin(helper, 32);
    }
}
