package com.cpigeon.book.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.view.ViewGroup;

import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.base.widget.MarqueeTextView;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.LeagueDetailsEntity;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/4/20.
 */

public class LeagueMarkerView extends MarkerView {

    List<LeagueDetailsEntity> data;

    private MarqueeTextView mTvProjectName;
    private MarqueeTextView mTvRank;
    private MarqueeTextView mTvSpeed;
    private MarqueeTextView mTvFirstSpeed;


    double position;

    public LeagueMarkerView(Context context, List<LeagueDetailsEntity> data) {
        super(context, R.layout.item_league_marker_layout);
        this.data = data;


        mTvProjectName = findViewById(R.id.tvProjectName);
        mTvRank = findViewById(R.id.tvRank);
        mTvSpeed = findViewById(R.id.tvSpeed);
        mTvFirstSpeed = findViewById(R.id.tvFirstSpeed);

    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        position = e.getX();
        if (e.getX() == 0) {
            setLayoutParams(new LayoutParams(ScreenTool.dip2px(1), ScreenTool.dip2px(1)));
        } else {
            setLayoutParams(new LayoutParams(ScreenTool.dip2px(240), ViewGroup.LayoutParams.WRAP_CONTENT));
            LeagueDetailsEntity entity = data.get((int) e.getX() - 1);
            mTvProjectName.setText(entity.getMatchName());
            mTvRank.setText(entity.getMatchNumber());
            mTvSpeed.setText(Utils.getString(R.string.text_speed_content,"0"));
            mTvFirstSpeed.setText("第一:"+"0");
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2) * 1f, -getHeight() - 10);
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        if(position == 0){
            setLayoutParams(new LayoutParams(ScreenTool.dip2px(1), ScreenTool.dip2px(1)));
        }else {
            setLayoutParams(new LayoutParams(ScreenTool.dip2px(111), ViewGroup.LayoutParams.WRAP_CONTENT));
            super.draw(canvas, posX, posY);
        }
    }
}
