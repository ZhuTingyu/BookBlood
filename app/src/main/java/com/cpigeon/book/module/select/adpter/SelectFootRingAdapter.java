package com.cpigeon.book.module.select.adpter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;

/**
 * Created by Zhu TingYu on 2018/10/13.
 */

public class SelectFootRingAdapter extends BaseQuickAdapter<PigeonEntity, BaseViewHolder> {

    private boolean mIsCanSetDeath;
    private String SexId;

    public SelectFootRingAdapter(boolean isCanSetDeath,String sexId) {
        super(R.layout.choose_layout, null);
        this.SexId=sexId;
        mIsCanSetDeath = isCanSetDeath;
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {
        ImageView imgSex = helper.getView(R.id.imgSex);
        TextView mPigeonType = helper.getView(R.id.zl);
        TextView blood = helper.getView(R.id.blood);
        TextView color = helper.getView(R.id.tvColor);
        defultParams(mPigeonType, R.drawable.textcircledefult);
        defultParams(blood, R.drawable.textcircledefult);
        defultParams(color, R.drawable.textcircledefult);
        mPigeonType.setTextColor(mContext.getResources().getColor(R.color.white));
        try {
            switch (item.getTypeID()) {
                case PigeonEntity.ID_BREED_PIGEON:
                    //种鸽
                    setParams(mPigeonType, R.drawable.textcirclebreed);
                    break;
                case PigeonEntity.ID_MATCH_PIGEON:
                    //赛鸽
                    setParams(mPigeonType, R.drawable.textcirclematch);
                    break;
                default:
                    setParams(mPigeonType, R.drawable.textcirclechild);
            }
        } catch (Exception e) {
            defultParams(color, R.drawable.textcircledefult);
        }

        if (!item.getPigeonPlumeName().trim().equals("")) {
            setParams(color, R.drawable.textcirclecolor);
        }
        if (!item.getPigeonBloodName().trim().equals("")) {
            setParams(blood, R.drawable.textcircleblood);
        }
        helper.setText(R.id.zl, " " + item.getTypeName() + " ");
        helper.setText(R.id.tvColor, " " + item.getPigeonPlumeName() + " ");
        helper.setText(R.id.blood, " " + item.getPigeonBloodName() + " ");
        helper.setText(R.id.state, item.getStateName());
        helper.setText(R.id.tvTime, item.getFootRingNum());

        Glide.with(mContext)
                .load(item.getCoverPhotoUrl())
                .placeholder(R.drawable.ic_img_default)
                .into((ImageView) helper.getView(R.id.imgHead));


        if (Utils.getString(R.string.text_male_a).equals(item.getPigeonSexName())) {
            imgSex.setImageResource(R.mipmap.ic_male);
        } else if (Utils.getString(R.string.text_female_a).equals(item.getPigeonSexName())) {
            imgSex.setImageResource(R.mipmap.ic_female);
        } else {
            imgSex.setImageResource(R.mipmap.ic_sex_no);
        }

        helper.itemView.setOnClickListener(v -> {

            String sexString = SexId.contains(PigeonEntity.ID_MALE)
                    ? Utils.getString(R.string.text_father) : Utils.getString(R.string.text_mother);

            DialogUtils.createDialogWithLeft(getBaseActivity()
                    , Utils.getString(R.string.text_hint_set_parent_pigeon, sexString), sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        IntentBuilder.Builder()
                                .putExtra(IntentBuilder.KEY_DATA, item)
                                .finishForResult(getBaseActivity());
                    });

        });

    }

    public void setParams(TextView tv, int Resource) {
        tv.setPadding(5, 2, 5, 2);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
        params.height = 48;
        params.setMargins(0, 0, 10, 0);
        tv.setBackgroundResource(Resource);
        tv.setLayoutParams(params);
    }

    public void defultParams(TextView tv, int Resource) {
        tv.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
        params.height = 50;
        params.setMargins(0, 0, 0, 0);
        tv.setBackgroundResource(Resource);
        tv.setLayoutParams(params);
    }
}
