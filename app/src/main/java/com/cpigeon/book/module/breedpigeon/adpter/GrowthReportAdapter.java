package com.cpigeon.book.module.breedpigeon.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.Utils;
import com.base.util.glide.GlideUtil;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.GrowthReportEntity;
import com.cpigeon.book.module.photo.adpter.ImageItemDecoration;
import com.cpigeon.book.util.MathUtil;

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

        helper.setText(R.id.tvDay, String.valueOf(TimeUtil.getTimeFormat(item.getUseTime(), TimeUtil.FORMAT_dd)));
        helper.setText(R.id.tvYear, String.valueOf(TimeUtil.getTimeFormat(item.getUseTime(), TimeUtil.FORMAT_YYYYMM)));
        helper.setText(R.id.tvTitle, item.getTypeName());
        icon.setImageResource(R.mipmap.ic_report_auction);

        TextView tv1 = helper.getView(R.id.tv1);
        TextView tv2 = helper.getView(R.id.tv2);
        ImageView img = helper.getView(R.id.img);


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

            case 1://出壳
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
                helper.setText(R.id.tv1, "第" + MathUtil.toChinese(String.valueOf(item.getLayNum() + 1)) + "窝" + split +
                        item.getWeather() + split +
                        item.getTemperature() + "℃" + split +
                        item.getWeather());
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_hatches);
                break;

            case 2://挂环
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
                helper.setText(R.id.tv1, "第" + MathUtil.toChinese(String.valueOf(item.getLayNum() + 1)) + "窝" + split + item.getFootRingNum());
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_set_foot);

                break;

            case 3://拍照
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                GlideUtil.setGlideImageView(getBaseActivity(), item.getName(), img);
                img.setOnClickListener(v -> {
                    PictureSelectUtil.showImagePhoto(getBaseActivity(), img, Lists.newArrayList(item.getName())
                            , 0);
                });
                tv1.setText(item.getInfo());
                break;

            case 4://配对

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_pair);
                helper.setText(R.id.tv1, item.getFootRingNum() + split + item.getPigeonPlumeName());
                helper.setText(R.id.tv2, item.getPigeonBloodName());

                break;

            case 5://生病
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);

                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_condition);
                helper.setText(R.id.tv1, "病情名称：" + item.getName());
                helper.setText(R.id.tv2, "病情症状：" + item.getInfo());

                break;

            case 6://用药

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);

                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_drug_use);

                helper.setText(R.id.tv1, "药品名称：" + item.getName());
                String info = StringUtil.isStringValid(item.getInfo()) ? item.getInfo() : Utils.getString(R.string.text_temp_no);
                helper.setText(R.id.tv2, "使用效果：" + info);

                break;

            case 7://疫苗

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_vaccine);

                helper.setText(R.id.tv1, "疫苗名称：" + item.getName());
                helper.setText(R.id.tv2, "注射原因：" + item.getInfo());

                break;

            case 8://保健品

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_care);
                helper.setText(R.id.tv1, "用品名称：" + item.getName());

                break;
            case 9://训练

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);

                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_train);

                helper.setText(R.id.tv1, item.getMatchNumber() + "名" + split + item.getMatchCount()
                        + "羽" + split + "归巢" + item.getRetureFly() + "羽");
                helper.setText(R.id.tv2, Utils.getString(R.string.text_speed_content_1, item.getFraction()));
                break;

            case 10://比赛
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);

                helper.setImageResource(R.id.imgIcon, R.mipmap.ic_report_match);

                helper.setText(R.id.tv1, item.getMatchNumber() + "名" + split + +item.getMatchCount() + "羽" + split
                        + Utils.getString(R.string.text_speed_content_1, item.getFraction()));
                helper.setText(R.id.tv2, item.getName());

                break;
            case 11://现役在棚
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
                helper.setText(R.id.tv1, item.getInfo());

        }
        addTopAndBttomMargin(helper, 32);
    }
}
