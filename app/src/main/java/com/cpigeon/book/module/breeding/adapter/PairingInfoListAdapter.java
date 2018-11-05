package com.cpigeon.book.module.breeding.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.BreedEntity;
import com.cpigeon.book.model.entity.PairingInfoEntity;

/**
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoListAdapter extends BaseQuickAdapter<PairingInfoEntity, BaseViewHolder> {


    public BreedEntity mBreedEntity;
    private boolean  IsMen;

    public void setIsMen(boolean ismen) {
        IsMen = ismen;
    }

    public PairingInfoListAdapter(BreedEntity mBreedEntity) {
        super(R.layout.item_pairing_info, Lists.newArrayList());
        this.mBreedEntity = mBreedEntity;
    }
    public PairingInfoListAdapter(BreedEntity mBreedEntity,boolean IsMen) {
        super(R.layout.item_pairing_info, Lists.newArrayList());
        this.IsMen=IsMen;
        this.mBreedEntity = mBreedEntity;
    }
    @Override
    protected void convert(BaseViewHolder helper, PairingInfoEntity item) {

        ImageView imgSex = helper.getView(R.id.imgSex);
        TextView color = helper.getView(R.id.tvColor);
        TextView count = helper.getView(R.id.count);
        TextView blood = helper.getView(R.id.blood);
        TextView foot = helper.getView(R.id.tvTime);
        defultParams(color,R.drawable.textcircledefult);
        defultParams(blood,R.drawable.textcircledefult);
        count.setText(helper.getPosition()+"");
        ImageView imagehead=(ImageView) helper.getView(R.id.imgHead);
        count.setBackgroundResource(R.drawable.countbackground);

        if (IsMen) {

            if (!item.getWoPigeonPlumeName().trim().equals(""))
            {

                setParams(color,R.drawable.textcirclecolor);
            }
            if (!item.getWoPigeonBloodName().trim().equals(""))
            {
                setParams(blood,R.drawable.textcircleblood);
            }
            color.setText(" "+item.getWoPigeonPlumeName()+" ");
            blood.setText(" "+item.getWoPigeonBloodName()+" ");
            foot.setText(item.getWoFootRingNum());
            imgSex.setImageResource(R.mipmap.ic_female);
            Glide.with(mContext)
                    .load(item.getWoCoverPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_img_default)
                    .error(R.drawable.ic_img_default)
                    .dontAnimate()
                    .into(imagehead);

        } else  {
            if (!item.getMenPigeonPlumeName().trim().equals(""))
            {
                setParams(color,R.drawable.textcirclecolor);
            }
            if (!item.getMenPigeonBloodName().trim().equals(""))
            {
                setParams(blood,R.drawable.textcircleblood);
            }
            color.setText(" "+item.getMenPigeonPlumeName()+" ");
            blood.setText(" "+item.getMenPigeonBloodName()+" ");
            foot.setText(item.getMenFootRingNum());
            imgSex.setImageResource(R.mipmap.ic_male);
            Glide.with(mContext)
                    .load(item.getWoCoverPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_img_default)
                    .error(R.drawable.ic_img_default)
                    .dontAnimate()
                    .into(imagehead);
        }

        helper.setText(R.id.state, item.getLayNum() + "窝");

    }
}
