package com.base.widget.guideview;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Zhu TingYu on 2018/11/23.
 */

public abstract class BaseComponent implements Component {
    protected Guide guide;
    protected int xOffset = 0;
    protected int yOffset = 0;
    protected int guideLocation = Component.ANCHOR_BOTTOM;
    protected int viewLocation = Component.FIT_CENTER;
    protected String guideHint;

    @Override
    public abstract View getView(LayoutInflater inflater);

    @Override
    public int getAnchor() {
        return guideLocation;
    }

    @Override
    public int getFitPosition() {
        return viewLocation;
    }

    @Override
    public int getXOffset() {
        return xOffset;
    }

    @Override
    public int getYOffset() {
        return yOffset;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public void setGuideLocation(int guideLocation) {
        this.guideLocation = guideLocation;
    }

    public void setViewLocation(int viewLocation) {
        this.viewLocation = viewLocation;
    }

    public String getGuideHint() {
        return guideHint;
    }

    public void setGuideHint(String guideHint) {
        this.guideHint = guideHint;
    }


}
