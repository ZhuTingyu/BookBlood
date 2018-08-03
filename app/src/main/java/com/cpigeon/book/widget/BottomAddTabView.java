package com.cpigeon.book.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.util.Lists;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/7/10.
 */

public class BottomAddTabView extends LinearLayout {


    private SimpleTitleView simple1;
    private SimpleTitleView simple2;
    private SimpleTitleView simple3;
    private SimpleTitleView simple4;
    private ImageView imgAdd;

    private OnAddClickListener addClickListener;

    SimpleTitleView currentSimpleTitleView;

    List<SimpleTitleView> simpleTitleViews = Lists.newArrayList();

    ViewPager viewPager;

    private int currentPosition = 0;

    public BottomAddTabView(Context context) {
        this(context, null);
    }

    public BottomAddTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomAddTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_bottom_add_tab, this, true);

        simple1 = view.findViewById(R.id.simple1);
        simple2 = view.findViewById(R.id.simple2);
        simple3 = view.findViewById(R.id.simple3);
        simple4 = view.findViewById(R.id.simple4);
        imgAdd = view.findViewById(R.id.imgAdd);

        simpleTitleViews.add(simple1);
        simpleTitleViews.add(simple2);
        simpleTitleViews.add(simple3);
        simpleTitleViews.add(simple4);

        imgAdd.setOnClickListener(v -> {
            if(addClickListener != null){
                addClickListener.click();
            }
        });

        for (int i = 0; i < simpleTitleViews.size(); i++) {
            int finalI = i;
            simpleTitleViews.get(i).setOnClickListener(v -> {
                viewPager.setCurrentItem(finalI);
            });
        }

    }

    public void switchTab(int tabPosition){
        if(currentSimpleTitleView != null){
            currentSimpleTitleView.setPress(false);
        }
        simpleTitleViews.get(tabPosition).setPress(true);
        currentSimpleTitleView = simpleTitleViews.get(tabPosition);
    }

    public void bindViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void setAddClickListener(OnAddClickListener addClickListener) {
        this.addClickListener = addClickListener;
    }

    public interface OnAddClickListener{
        void click();
    }
}
