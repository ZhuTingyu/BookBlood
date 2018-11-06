package com.cpigeon.book.module.breeding.adapter;

import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.utility.StringUtil;
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
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();
        params.setMargins(10,0,0,0);
        params.height=48;
        tv.setBackgroundResource(Resource);
        tv.setLayoutParams(params);
    }
    public void  defultParams(TextView tv,int Resource)
    {
        tv.setPadding(0,0,0,0);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();
        params.setMargins(0,0,0,0);
        params.height=60;
        tv.setBackgroundResource(Resource);
        tv.setLayoutParams(params);
    }
    @Override
    protected void convert(BaseViewHolder helper, BreedEntity item) {
        TextView count=helper.getView(R.id.count);
        count.setText(helper.getPosition()+1+"");
        count.setBackgroundResource(R.drawable.countbackground2);
        helper.setText(R.id.man_ring_num,item.getMenFootRingNum());
        helper.setText(R.id.woman_ring_num,item.getWoFootRingNum());
        helper.setText(R.id.man_blood, StringUtil.emptyString());
        helper.setText(R.id.man_color,StringUtil.emptyString());
        helper.setText(R.id.woman_blood,StringUtil.emptyString());
        helper.setText(R.id.woman_color,StringUtil.emptyString());
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
            helper.setText(R.id.woman_color," "+item.getWoPigeonPlumeName()+" ");
            setParams(woman_color,R.drawable.textcirclecolor);
        }
        if (!item.getMenPigeonPlumeName().trim().equals(""))
        { helper.setText(R.id.man_color," "+item.getMenPigeonPlumeName()+" ");
            setParams(man_color,R.drawable.textcirclecolor);
        }
        if (!item.getMenPigeonBloodName().trim().equals(""))
        {
            helper.setText(R.id.man_blood," "+item.getMenPigeonBloodName()+" ");
            setParams(man_blood,R.drawable.textcircleblood);
        }
        if (!item.getWoPigeonBloodName().trim().equals(""))
        {
            helper.setText(R.id.woman_blood," "+item.getWoPigeonBloodName()+" ");
            setParams(woman_blood,R.drawable.textcircleblood);
        }
        Log.d("songshuaishuai", "convert: wo  "+woman_blood.getText().toString());
        Log.d("songshuaishuai", "convert: men "+woman_color.getText().toString());
    }
}
