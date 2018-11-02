package com.cpigeon.book.module.breeding.adapter;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.BreedEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/11/2 0002.
 */

public class BreedingFootAdapter extends BaseQuickAdapter<BreedEntity, BaseViewHolder> {

    public BreedingFootAdapter(int layoutResId, List<BreedEntity> data) {
        super(layoutResId, data);
    }
    public void  setParams(TextView tv, int Resource)
    {
        tv.setPadding(5,2,5,2);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tv.getLayoutParams();
        params.height=60;
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

    @Override
    protected void convert(BaseViewHolder helper, BreedEntity item) {
        helper.setText(R.id.man_ring_num,item.getMenFootRingNum());
        helper.setText(R.id.woman_ring_num,item.getWoFootRingNum());
        helper.setText(R.id.man_blood,item.getMenPigeonBloodName());
       helper.setText(R.id.man_color,item.getMenPigeonPlumeName());
        helper.setText(R.id.woman_blood,item.getMenPigeonBloodName());
        helper.setText(R.id.man_color,item.getWoPigeonPlumeName());
        TextView man_blood = helper.getView(R.id.man_blood);
        TextView man_color = helper.getView(R.id.man_color);;
        TextView woman_blood = helper.getView(R.id.woman_blood);
        TextView woman_color = helper.getView(R.id.woman_color);;
        defultParams(man_blood,R.drawable.textcircledefult);
        defultParams(man_color,R.drawable.textcircledefult);
        defultParams(woman_color,R.drawable.textcircledefult);
        defultParams(woman_blood,R.drawable.textcircledefult);
        if (!item.getWoPigeonPlumeName().trim().equals(""))
        {
            setParams(woman_color,R.drawable.textcirclecolor);
        }
        if (!item.getMenPigeonPlumeName().trim().equals(""))
        {
            setParams(man_color,R.drawable.textcirclecolor);
        }
        if (!item.getMenPigeonBloodName().trim().equals(""))
        {
            setParams(man_blood,R.drawable.textcircleblood);
        }
        if (!item.getWoPigeonBloodName().trim().equals(""))
        {
            setParams(woman_blood,R.drawable.textcircleblood);
        }

    }
}
