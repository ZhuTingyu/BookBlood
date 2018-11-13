package com.cpigeon.book.module.homingpigeon.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

/**
 * Created by Administrator on 2018/10/9 0009.
 */

public class MyHomingPigeonAdapter extends BasePigeonListAdapter {
    private float mRawX;
    private float mRawY;
    private OnDeleteListener onDeleteListener;
    private LinearLayoutListener linearLayoutListener;
    private boolean isInBreed = false;

    public MyHomingPigeonAdapter() {
        super(R.layout.item_breed_pigeon_list, null);
    }

    public MyHomingPigeonAdapter(OnDeleteListener onDeleteListener, LinearLayoutListener linearLayoutListener) {
        super(R.layout.item_breed_pigeon_list, null);
        this.onDeleteListener = onDeleteListener;
        this.linearLayoutListener = linearLayoutListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {
        ImageView imgSex = helper.getView(R.id.imgSex);
        TextView mPigeonType = helper.getView(R.id.zl);
        TextView blood = helper.getView(R.id.blood);
        TextView color = helper.getView(R.id.tvColor);
        helper.setText(R.id.tvColor, StringUtil.emptyString());
        helper.setText(R.id.blood, StringUtil.emptyString());
        helper.setText(R.id.zl, " " + item.getTypeName() + " ");
        helper.setText(R.id.state, item.getStateName());
        helper.setText(R.id.tvTime, item.getFootRingNum());
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
            helper.setText(R.id.tvColor, " " + item.getPigeonPlumeName() + " ");
        }
        if (!item.getPigeonBloodName().trim().equals("")) {
            setParams(blood, R.drawable.textcircleblood);
            helper.setText(R.id.blood, " " + item.getPigeonBloodName() + " ");
        }
        helper.setText(R.id.zl, " " + item.getTypeName() + " ");
        helper.setText(R.id.state, item.getStateName());
        helper.setText(R.id.tvTime, item.getFootRingNum());
        ImageView imagehead = (ImageView) helper.getView(R.id.imgHead);
        Glide.with(mContext)
                .load(item.getCoverPhotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_img_default)
                .error(R.drawable.ic_img_default)
                .dontAnimate()
                .into(imagehead);


        if (Utils.getString(R.string.text_male_a).equals(item.getPigeonSexName())) {
            imgSex.setImageResource(R.mipmap.ic_male);
        } else if (Utils.getString(R.string.text_female_a).equals(item.getPigeonSexName())) {
            imgSex.setImageResource(R.mipmap.ic_female);
        } else {
            imgSex.setImageResource(R.mipmap.ic_sex_no);
        }

        SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) helper.itemView;

        if (onDeleteListener == null) {
            swipeMenuLayout.setSwipeEnable(false);
        } else {
            TextView delete = helper.getView(R.id.tvDelete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getBaseActivity().errorDialog != null && getBaseActivity().errorDialog.isShowing()) {
                        getBaseActivity().errorDialog.dismiss();
                    }

                    String hintStr = "确认删除足环号为" + item.getFootRingNum() + "的信鸽吗？";
                    getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), hintStr, sweetAlertDialog -> {
                        if (onDeleteListener != null) {
                            onDeleteListener.delete(item.getPigeonID());
                        }
                        sweetAlertDialog.dismiss();
                    }, sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                    });
                }
            });
        }

        LinearLayout linearLayout = helper.getView(R.id.llay);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayoutListener != null) {
                    linearLayoutListener.click(helper.getAdapterPosition() - getHeaderLayoutCount());
                }

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.click(MyHomingPigeonAdapter.this, v, helper.getAdapterPosition() - getHeaderLayoutCount());
                }

            }
        });

        TextView tvTogetherState = helper.getView(R.id.tvTogetherState);
        if(isInBreed){
            tvTogetherState.setVisibility(View.VISIBLE);
            tvTogetherState.setText("(分居状态)");
        }else {
            tvTogetherState.setVisibility(View.GONE);
        }

    }

    public OnDeleteListener getOnDeleteListener() {
        return onDeleteListener;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public LinearLayoutListener getLinearLayoutListener() {
        return linearLayoutListener;
    }

    public void setLinearLayoutListener(LinearLayoutListener linearLayoutListener) {
        this.linearLayoutListener = linearLayoutListener;
    }

    public interface OnItemClickListener {
        void click(MyHomingPigeonAdapter myAdapter, View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnInItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setInBreed(boolean inBreed) {
        isInBreed = inBreed;
    }
}
