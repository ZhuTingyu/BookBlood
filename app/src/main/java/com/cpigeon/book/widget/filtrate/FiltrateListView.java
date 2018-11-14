package com.cpigeon.book.widget.filtrate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class FiltrateListView extends RelativeLayout {

    private LinearLayout mLlRoot;
    private TextView mTvReset;
    private TextView mTvSure;
    private List<List<SelectTypeEntity>> mSelectTypeList = Lists.newArrayList();
    private List<FiltrateItemView> mFiltrateItemViews = Lists.newArrayList();
    private List<List<SelectTypeEntity>> mFiltrateSelectItems = Lists.newArrayList();

    private OnSureClickListener mOnSureClickListener;


    public FiltrateListView(Context context) {
        super(context);
        initView(context);
    }

    public FiltrateListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView(context);
    }

    public FiltrateListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView(context);
    }

    private void initAttrs(AttributeSet attrs) {
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_filtrate_list, this);

        mLlRoot = view.findViewById(R.id.llRoot);
        mTvReset = view.findViewById(R.id.tvReset);
        mTvSure = view.findViewById(R.id.tvSure);

        mTvSure.setOnClickListener(v -> {
            mFiltrateSelectItems.clear();
            if (mFiltrateItemViews != null) {
                for (int i = 0; i < mFiltrateItemViews.size(); i++) {
                    FiltrateItemAdapter adapter = mFiltrateItemViews.get(i).mAdapter;
                    mFiltrateSelectItems.add(adapter.getSelectItem());
                }

                if (mOnSureClickListener != null && !Lists.isEmpty(mFiltrateSelectItems)) {
                    mOnSureClickListener.onSure(mFiltrateSelectItems);
                }
            }
        });

        mTvReset.setOnClickListener(v -> {
            resetData();
        });

    }

    public void resetData() {
        if (mFiltrateItemViews != null) {
            for (int i = 0; i < mFiltrateItemViews.size(); i++) {
                FiltrateItemAdapter adapter = mFiltrateItemViews.get(i).mAdapter;
                adapter.resetSelect(true);
            }
        }
    }

    public void setData(boolean isHaveYear, List<SelectTypeEntity> data, List<String> titles, List<String> whichIds) {

        for (int i = 0, len = whichIds.size(); i < len; i++) {
            List<SelectTypeEntity> list = Lists.newArrayList();
            mSelectTypeList.add(list);
        }

        for (int i = 0, len = data.size(); i < len; i++) {
            SelectTypeEntity entity = data.get(i);
            String whichType = entity.getWhichType();
            int position = whichIds.indexOf(whichType);
            mSelectTypeList.get(position).add(entity);
        }

        if (isHaveYear) {
            titles.add(0, Utils.getString(R.string.text_year));
            String cYear = TimeUtil.format(System.currentTimeMillis(), TimeUtil.FORMAT_YYYY);
            int year = Integer.valueOf(cYear);
            List<SelectTypeEntity> years = Lists.newArrayList();
            for (int i = 0; i < 10; i++) {
                SelectTypeEntity yearType = new SelectTypeEntity();
                yearType.setTypeName(String.valueOf(year - i));
                yearType.setTypeID(String.valueOf(year - i));
                years.add(yearType);
            }
            years.add(SelectTypeEntity.getCustomEntity(SelectTypeViewModel.TIME));
            mSelectTypeList.add(0, years);
        }

        for (int i = 0, len = mSelectTypeList.size(); i < len; i++) {
            FiltrateItemView itemView = new FiltrateItemView(getContext());
            itemView.setTitle(titles.get(i));
            List<SelectTypeEntity> itemData = mSelectTypeList.get(i);
            SelectTypeEntity all = new SelectTypeEntity();
            all.setSelect(true);
            itemData.add(0, all);
            itemView.setData(itemData);
            mFiltrateItemViews.add(itemView);
            mLlRoot.addView(itemView);
        }


    }

    public interface OnSureClickListener {
        void onSure(List<List<SelectTypeEntity>> selectItems);

    }

    public void setOnSureClickListener(OnSureClickListener onSureClickListener) {
        mOnSureClickListener = onSureClickListener;
    }
}
