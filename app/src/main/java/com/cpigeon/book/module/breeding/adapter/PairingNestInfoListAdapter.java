package com.cpigeon.book.module.breeding.adapter;

import android.view.View;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.StringUtil;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.cpigeon.book.util.MathUtil;
import com.cpigeon.book.widget.LineInputView;

/**
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestInfoListAdapter extends BaseQuickAdapter<PairingNestInfoEntity, BaseViewHolder> {


    private LineInputView ll_nest_num;//窝次
    private LineInputView ll_pairing_time;//配对时间
    private LineInputView ll_lay_eggs;//产蛋信息
    private LineInputView ll_lay_eggs_time;//产蛋时间
    private LineInputView ll_fertilized_egg;//受精蛋
    private LineInputView ll_fertilized_egg_no;//无精蛋
    private LineInputView ll_hatches_info;//出壳信息
    private LineInputView ll_hatches_time;//出壳时间
    private LineInputView ll_hatches_num;//出壳个数
    private LineInputView ll_offspring_info;//子代信息
    private XRecyclerView rv_offspring_info;//子代信息 列表

    public PairingNestInfoListAdapter() {
        super(R.layout.item_pairing_nest_info, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PairingNestInfoEntity item) {


        ll_nest_num = helper.getView(R.id.ll_nest_num);
        ll_nest_num.setContent("第" + MathUtil.toChinese(item.getLayNum()) + "窝");
        ll_nest_num.setOnClickListener(v -> {

            //删除
            getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), mContext.getString(R.string.text_delete_warning_hint), sweetAlertDialog -> {
                //确定
                sweetAlertDialog.dismiss();


            }, sweetAlertDialog -> {
                //取消
                sweetAlertDialog.dismiss();
            });
        });

        ll_pairing_time = helper.getView(R.id.ll_pairing_time);
        ll_pairing_time.setContent(item.getPigeonBreedTime());

        ll_lay_eggs = helper.getView(R.id.ll_lay_eggs);//产蛋信息
        ll_lay_eggs_time = helper.getView(R.id.ll_lay_eggs_time);//产蛋时间
        ll_fertilized_egg = helper.getView(R.id.ll_fertilized_egg);//受精蛋
        ll_fertilized_egg_no = helper.getView(R.id.ll_fertilized_egg_no);//无精蛋


        if (!StringUtil.isStringValid(item.getLayEggTime()) &&//产蛋时间

                (!StringUtil.isStringValid(item.getInseEggCount()) ||

                        Integer.valueOf(item.getInseEggCount()) == 0) && //受精蛋

                (!StringUtil.isStringValid(item.getFertEggCount()) ||

                        Integer.valueOf(item.getFertEggCount()) == 0)  //无精蛋

                ) {
            //未产蛋
            ll_lay_eggs.setContent(mContext.getString(R.string.string_lay_eggs_no));
            ll_lay_eggs_time.setContent("");
            ll_fertilized_egg.setContent("");
            ll_fertilized_egg_no.setContent("");

        } else {
            //已产蛋
            ll_lay_eggs.setContent(mContext.getString(R.string.string_lay_eggs_yes));

            ll_lay_eggs_time.setContent(item.getLayEggTime());
            ll_fertilized_egg.setContent(item.getInseEggCount() + "枚");
            ll_fertilized_egg_no.setContent(item.getFertEggCount() + "枚");
        }


        ll_hatches_info = helper.getView(R.id.ll_hatches_info);//出壳信息
        ll_hatches_time = helper.getView(R.id.ll_hatches_time);//出壳时间
        ll_hatches_num = helper.getView(R.id.ll_hatches_num);//出壳个数

        ll_hatches_time.setContent(item.getOutShellTime());
        ll_hatches_num.setContent(item.getOutShellCount() + "枚");
        if (StringUtil.isStringValid(item.getOutShellTime())) {
            // 已出壳
            ll_hatches_info.setContent(mContext.getString(R.string.string_hatches_info_yes));
        } else {
            // 未出壳
            ll_hatches_info.setContent(mContext.getString(R.string.string_hatches_info_no));
        }

        ll_offspring_info = helper.getView(R.id.ll_offspring_info);//子代信息

        if (item.getPigeonList().size() == 0) {
            //未挂环
            ll_offspring_info.setContent(mContext.getString(R.string.text_status_not_set_foot_ring));
        } else {
            //已挂环
            ll_offspring_info.setContent(mContext.getString(R.string.text_status_set_foot_ring));
        }


        OffspringInfoAdapter mOffspringInfoAdapter = new OffspringInfoAdapter();

        rv_offspring_info = helper.getView(R.id.rv_offspring_info);//子代信息
        rv_offspring_info.setAdapter(mOffspringInfoAdapter);

        mOffspringInfoAdapter.setNewData(item.getPigeonList());

    }
}
