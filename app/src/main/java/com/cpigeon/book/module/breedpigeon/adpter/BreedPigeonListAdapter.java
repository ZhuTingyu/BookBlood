package com.cpigeon.book.module.breedpigeon.adpter;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonListAdapter;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.widget.PopupWindowList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class BreedPigeonListAdapter extends BasePigeonListAdapter {
    private float mRawX;
    private float mRawY;
    private OnDeleteListener onDeleteListener;

    public BreedPigeonListAdapter(OnDeleteListener onDeleteListener) {
        super(R.layout.item_breed_pigeon_list, null);
        this.onDeleteListener=onDeleteListener;
    }

    public BreedPigeonListAdapter(int layoutResId, List<PigeonEntity> data) {
        super(layoutResId, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {

        ImageView imgSex = helper.getView(R.id.imgSex);
        TextView color = helper.getView(R.id.tvColor);
        TextView blood = helper.getView(R.id.blood);
        defultParams(color,R.drawable.textcircledefult);
        defultParams(blood,R.drawable.textcircledefult);
        if (!item.getPigeonPlumeName().trim().equals(""))
        {
            setParams(color,R.drawable.textcirclecolor);
        }
        if (!item.getPigeonBloodName().trim().equals(""))
        {
            setParams(blood,R.drawable.textcircleblood);
        }

        helper.setText(R.id.tvColor, "  "+item.getPigeonPlumeName()+"  ");
        helper.setText(R.id.blood,"  "+item.getPigeonBloodName()+"  ");
        helper.setText(R.id.state,item.getStateName());
        helper.setText(R.id.tvTime, item.getFootRingNum());
        ImageView imagehead=(ImageView)helper.getView(R.id.imgHead);
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
    public void  setParams(TextView tv,int Resource)
    {
        tv.setPadding(5,2,5,2);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tv.getLayoutParams();
        params.height=48;
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
