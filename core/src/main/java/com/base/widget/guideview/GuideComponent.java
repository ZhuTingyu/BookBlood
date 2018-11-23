package com.base.widget.guideview;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.http.R;


/**
 * Created by Zhu TingYu on 2018/6/6.
 */

public class GuideComponent extends BaseComponent {

    private TextView textView;
    private TextView tvOk;
    @Override
    public View getView(LayoutInflater inflater) {
        LinearLayout ll;
        if (guideLocation == Component.ANCHOR_BOTTOM) {
            ll = (LinearLayout) inflater.inflate(R.layout.view_defalut_arrow_up_guide_layout, null);
        } else {
            ll = (LinearLayout) inflater.inflate(R.layout.view_defalut_arrow_down_guide_layout, null);
        }

        textView = ll.findViewById(R.id.text);
        textView.setText(guideHint);

        tvOk = ll.findViewById(R.id.tvOk);
        tvOk.setOnClickListener(v -> {
            if(guide != null){
                guide.dismiss();
            }
        });

        return ll;
    }

}
