package com.base.base.adpter;


import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.base.application.BaseApplication;
import com.base.base.BaseActivity;
import com.base.http.R;
import com.base.util.utility.StringUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.tools.StringUtils;


import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/11.
 */

public abstract class BaseQuickAdapter<T, K extends BaseViewHolder> extends com.chad.library.adapter.base.BaseQuickAdapter<T, K> {

    String emptyText;

    public BaseQuickAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }


    @Override
    public void setNewData(List<T> data) {

        if (data == null || data.isEmpty()) {
            if (!getEmptyViewText().isEmpty()) {
                this.setEmptyView();
            }
        }
        super.setNewData(data);
    }

    public void setEmptyView() {
        if (getEmptyViewImage() == 0) {
            setSelfEmptyView(getEmptyViewText());
        } else {
           setSelfEmptyView(StringUtil.isStringValid(getEmptyViewText()) ?
                   getEmptyViewText() : emptyText  ,getEmptyViewImage());
        }
    }

    public void setLoadMore(boolean isEnd) {
        if (isEnd) {
            this.loadMoreEnd();
        }
        else this.loadMoreComplete();
    }

    public void setSelfEmptyView(String message, @DrawableRes int resId){
        View view = View.inflate(BaseApplication.getAppContext(), R.layout.list_empty_layout,null);
        TextView textView = view.findViewById(R.id.empty);
        textView.setTextColor(BaseApplication.getAppContext().getResources().getColor(R.color.colorPrimary));
        textView.setText(message);
        AppCompatImageView imageView = view.findViewById(R.id.icon);
        imageView.setImageResource(resId);
        setEmptyView(view);
    }

    public void setSelfEmptyView(String message){
        View view = View.inflate(BaseApplication.getAppContext(), R.layout.list_empty_layout,null);
        TextView textView = view.findViewById(R.id.title);
        textView.setTextColor(BaseApplication.getAppContext().getResources().getColor(R.color.colorPrimary));
        textView.setText(message);
        setEmptyView(view);
    }

    protected String getEmptyViewText(){
        return StringUtil.isStringValid(emptyText) ? emptyText : StringUtil.emptyString();
    }


    protected @DrawableRes int getEmptyViewImage(){
        return 0;
    }

    protected int getColor(@ColorRes int resId) {
        return mContext.getResources().getColor(resId);
    }

    protected float getDimension(@DimenRes int resId) {
        return mContext.getResources().getDimension(resId);
    }

    protected BaseActivity getBaseActivity(){
        return (BaseActivity) mContext;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }
}
