package com.cpigeon.book.module.homingpigeon.adapter;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonListAdapter;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.widget.PopupWindowList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/9 0009.
 */

public class MyHomingPigeonAdapter extends BasePigeonListAdapter {
    private float mRawX;
    private float mRawY;
    private OnDeleteListener onDeleteListener;
    public MyHomingPigeonAdapter()
    {
        super(R.layout.item_breed_pigeon_list, null);
    }
    public MyHomingPigeonAdapter(OnDeleteListener onDeleteListener) {
        super(R.layout.item_breed_pigeon_list, null);
        this.onDeleteListener=onDeleteListener;

    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {
        List<String> popupMenuItemList = new ArrayList<>();
        popupMenuItemList.add("删除");
        popupMenuItemList.add("取消");
        ImageView imgSex = helper.getView(R.id.imgSex);
        TextView mPigeonType = helper.getView(R.id.zl);
        TextView blood = helper.getView(R.id.blood);
        TextView color = helper.getView(R.id.tvColor);
        defultParams(mPigeonType,R.drawable.textcircledefult);
        defultParams(blood,R.drawable.textcircledefult);
        defultParams(color,R.drawable.textcircledefult);
        mPigeonType.setTextColor(mContext.getResources().getColor(R.color.white));
        try {
            switch (item.getTypeID()) {
                case PigeonEntity.ID_BREED_PIGEON:
                    //种鸽
                    setParams(mPigeonType,R.drawable.textcirclebreed);
                    break;
                case PigeonEntity.ID_MATCH_PIGEON:
                    //赛鸽
                    setParams(mPigeonType,R.drawable.textcirclematch);
                    break;
                default:
                    setParams(mPigeonType,R.drawable.textcirclechild);
            }
        } catch (Exception e) {
            defultParams(color,R.drawable.textcircledefult);
        }

        if (!item.getPigeonPlumeName().trim().equals(""))
        {
          setParams(color,R.drawable.textcirclecolor);
        }
        if (!item.getPigeonBloodName().trim().equals(""))
        {
            setParams(blood,R.drawable.textcircleblood);
        }
        helper.setText(R.id.zl, " "+item.getTypeName()+" ");
        helper.setText(R.id.tvColor, " "+item.getPigeonPlumeName()+" ");
        helper.setText(R.id.blood," "+item.getPigeonBloodName()+" ");
        helper.setText(R.id.state,item.getStateName());
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
        LinearLayout linearLayout =helper.getView(R.id.llay);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mRawX = event.getRawX();
                mRawY = event.getRawY();
                return false;
            }
        });
        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                PopupList popupList = new PopupList(getBaseActivity().getBaseContext());
//                popupList.showPopupListWindow(getBaseActivity().getRootView(), helper.getAdapterPosition(), mRawX, mRawY, popupMenuItemList, new PopupList.PopupListListener() {
//                    @Override
//                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
//                        return true;
//                    }
//
//
//                    @Override
//                    public void onPopupListClick(View contextView, int contextPosition, int position) {
//                       if (position==0)
//                       {
//
//
//                if (getBaseActivity().errorDialog != null && getBaseActivity().errorDialog.isShowing()) {
//                    getBaseActivity().errorDialog.dismiss();
//                }
//
//                String hintStr = "确认删除足环号为"+item.getFootRingNum()+"的信鸽吗？";
//                getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), hintStr, sweetAlertDialog -> {
//                    if(onDeleteListener!=null) {
//                        onDeleteListener.delete(item.getPigeonID());
//                    }
//                    sweetAlertDialog.dismiss();
//                }, sweetAlertDialog -> {
//                    sweetAlertDialog.dismiss();
//                });
//                       }
//                    }
//                });
                final PopupWindowList mPopupWindowList = new PopupWindowList(getBaseActivity());;
                List<String> dataList = new ArrayList<>();

                dataList.add("删除");
                dataList.add("取消");
                mPopupWindowList.setPopupWindowWidth(180);
                mPopupWindowList.setDIVIDER(getBaseActivity().getResources().getDrawable(R.drawable.popupwindowbackground));
                mPopupWindowList.setLocation((int)mRawX,(int)mRawY);
                mPopupWindowList.setAnchorView(v);
                mPopupWindowList.setItemData(dataList);
                mPopupWindowList.setModal(true);
                mPopupWindowList.show();
                mPopupWindowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e(TAG, "click position="+position);
                        if(position==0)
                        {

                if (getBaseActivity().errorDialog != null && getBaseActivity().errorDialog.isShowing()) {
                    getBaseActivity().errorDialog.dismiss();
                }

                String hintStr = "确认删除足环号为"+item.getFootRingNum()+"的信鸽吗？";
                getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), hintStr, sweetAlertDialog -> {
                    if(onDeleteListener!=null) {
                        onDeleteListener.delete(item.getPigeonID());
                    }
                    sweetAlertDialog.dismiss();
                }, sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                });
                        }
                        mPopupWindowList.hide();
                    }
                });
                return false;
            }

        });



    }


}
