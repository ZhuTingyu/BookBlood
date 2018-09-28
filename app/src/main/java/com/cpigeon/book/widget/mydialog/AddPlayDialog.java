package com.cpigeon.book.widget.mydialog;

import android.app.Dialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.base.util.utility.ToastUtils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.play.PlayInportFragment;
import com.cpigeon.book.module.select.PlayOrgLoftFragment;
import com.cpigeon.book.module.select.SelectAssFragment;
import com.cpigeon.book.widget.LineInputView;

/**
 * Created by Administrator on 2018/9/3.
 */

public class AddPlayDialog extends CustomBaseBottomDialog {


    private ImageView btn_cancel;
    private TextView btn_sure;
    private LineInputView ll_foot;
    private LineInputView ll_org;
    private LineInputView unitName;

    @Override
    public int setContentView() {
        return R.layout.dialog_impot_play;
    }

    @Override
    public void initView(Dialog dialog) {

        //取消
        btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(v -> {
            this.dismiss();
        });
        //确定
        btn_sure = dialog.findViewById(R.id.btn_sure);

        btn_sure.setOnClickListener(v -> {
            if (!StringUtil.isStringValid(unitName.getContent())) {
                ToastUtils.showLong(dialog.getContext(), "请选择组织");
                return;
            }

            this.dismiss();
            //导入赛绩
            PlayInportFragment.start(getActivity());
        });

        //足环号
        ll_foot = dialog.findViewById(R.id.ll_foot);
        //组织类型
        ll_org = dialog.findViewById(R.id.ll_org);

        ll_org.setOnClickListener(v -> {
            String[] chooseWays = getResources().getStringArray(R.array.array_org);
            BottomSheetAdapter.createBottomSheet(getActivity(), Lists.newArrayList(chooseWays), p -> {
                String way = chooseWays[p];
                if (Utils.getString(R.string.title_select_ass).equals(way)) {
                    //选择协会
                    SearchFragmentParentActivity.start(getActivity(), SelectAssFragment.class, BreedPigeonDetailsFragment.CODE_ORGANIZE, null);
                    ll_org.getEditText().setText("协会");
                } else if (Utils.getString(R.string.title_select_loft).equals(way)) {
                    //选择公棚
                    SearchFragmentParentActivity.start(getActivity(), PlayOrgLoftFragment.class, BreedPigeonDetailsFragment.CODE_LOFT, null);
                    ll_org.getEditText().setText("公棚");
                }
            });
        });

        //单位名称
        unitName = dialog.findViewById(R.id.ll_unit_name);
    }

    public void setFoot(String foot) {
        ll_foot.setContent(foot);
    }

    public void setllUnitName(String ll_unit_name) {
        unitName.setContent(ll_unit_name);
    }


}
