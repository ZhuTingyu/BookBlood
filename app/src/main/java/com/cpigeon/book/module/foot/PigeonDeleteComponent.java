package com.cpigeon.book.module.foot;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.widget.guideview.BaseComponent;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/11/23.
 */

public class PigeonDeleteComponent extends BaseComponent{
    private TextView mText;
    private ImageView mImgKnow;

    @Override
    public View getView(LayoutInflater inflater) {
        LinearLayout ll = null;
        ll = (LinearLayout) inflater.inflate(R.layout.view_mark_pigeon_delete, null);

        mText = ll.findViewById(R.id.text);
        mImgKnow = ll.findViewById(R.id.imgKnow);

        mImgKnow.setOnClickListener(v -> {
            if(guide != null){
                guide.dismiss();
            }
        });

        return ll;
    }
}
