package com.cpigeon.book.module.breeding.adapter;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.BreedEntity;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoListViewModel;
import com.cpigeon.book.widget.PopupWindowList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/2 0002.
 */

public class BreedingFootAdapter extends BaseQuickAdapter<BreedEntity, BaseViewHolder> {

    PairingInfoListViewModel mViewModel;

    public BreedingFootAdapter(PairingInfoListViewModel viewModel) {
        super(R.layout.breed_manneger_item, null);
        mViewModel = viewModel;
    }

    @Override
    protected void convert(BaseViewHolder helper, BreedEntity item) {
        TextView count = helper.getView(R.id.count);
        count.setText(String.valueOf(helper.getAdapterPosition() + 1));
        count.setBackgroundResource(R.drawable.countbackground2);
        helper.setText(R.id.man_ring_num, item.getMenFootRingNum());
        helper.setText(R.id.woman_ring_num, item.getWoFootRingNum());
        helper.setText(R.id.man_blood, StringUtil.emptyString());
        helper.setText(R.id.man_color, StringUtil.emptyString());
        helper.setText(R.id.woman_blood, StringUtil.emptyString());
        helper.setText(R.id.woman_color, StringUtil.emptyString());
        TextView man_blood = helper.getView(R.id.man_blood);
        TextView man_color = helper.getView(R.id.man_color);
        TextView woman_blood = helper.getView(R.id.woman_blood);
        TextView woman_color = helper.getView(R.id.woman_color);
        if (StringUtil.isStringValid(item.getWoPigeonPlumeName())) {
            helper.setViewVisible(R.id.woman_color, View.VISIBLE);
            helper.setText(R.id.woman_color, item.getWoPigeonPlumeName());
        }else {
            helper.setViewVisible(R.id.woman_color, View.GONE);
        }
        if (StringUtil.isStringValid(item.getMenPigeonPlumeName())) {
            helper.setViewVisible(R.id.man_color, View.VISIBLE);
            helper.setText(R.id.man_color, item.getMenPigeonPlumeName());
        }else {
            helper.setViewVisible(R.id.man_color, View.GONE);
        }
        if (StringUtil.isStringValid(item.getMenPigeonBloodName())) {
            helper.setViewVisible(R.id.man_blood, View.VISIBLE);
            helper.setText(R.id.man_blood, item.getMenPigeonBloodName());
        }else {
            helper.setViewVisible(R.id.man_blood, View.GONE);
        }
        if (StringUtil.isStringValid(item.getWoPigeonBloodName())) {
            helper.setViewVisible(R.id.woman_blood, View.VISIBLE);
            helper.setText(R.id.woman_blood,  item.getWoPigeonBloodName());
        }else {
            helper.setViewVisible(R.id.woman_blood, View.GONE);
        }

        boolean isTogether = item.isTogether();

        TextView delete = helper.getView(R.id.tvDelete);
        TextView setNotTogether = helper.getView(R.id.tvSetNotTogether);

        mViewModel.pairingId = item.getPigeonBreedID();

        delete.setOnClickListener(v -> {
            DialogUtils.createDialogWithLeft(getBaseActivity(), Utils.getString(R.string.text_hint_is_delete_pairing_info)
                    ,sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        getBaseActivity().setProgressVisible(true);
                        mViewModel.delectPairingInfo();
                    });
        });

        setNotTogether.setOnClickListener(v -> {
            DialogUtils.createDialogWithLeft(getBaseActivity(), Utils.getString(R.string.text_hint_set_not_together)
                    ,sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        getBaseActivity().setProgressVisible(true);
                        mViewModel.setPigeonTogether();
                    });
        });

        if(isTogether){
            delete.setVisibility(View.GONE);
            setNotTogether.setVisibility(View.VISIBLE);
        }else {
            delete.setVisibility(View.VISIBLE);
            setNotTogether.setVisibility(View.GONE);
        }
    }
}
