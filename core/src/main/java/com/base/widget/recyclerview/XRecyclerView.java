package com.base.widget.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.base.http.R;
import com.base.util.utility.FloatUtil;
import com.base.util.system.ScreenTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by Zhu TingYu on 2018/2/8.
 */

public class XRecyclerView extends FrameLayout {

    protected int mXRecyclerViewMainLayout;
    protected boolean mClipToPadding;
    protected int mPadding;
    protected int mPaddingLeft;
    protected int mPaddingTop;
    protected int mPaddingRight;
    protected int mPaddingBottom;
    protected int mScrollbarStyle;
    protected int mEmptyId;
    protected int mProgressId;

    protected SwipeRefreshLayout swipeRefreshLayout;
    protected View progressView;
    protected View emptyView;
    protected RecyclerView recyclerView;

    protected RecyclerView.OnScrollListener mExternalOnScrollListener;
    protected RecyclerView.OnScrollListener mInternalOnScrollListener;


    public XRecyclerView(@NonNull Context context) {
        super(context);
        initView();
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.superrecyclerview);
        try{
            mXRecyclerViewMainLayout = array.getResourceId(R.styleable.superrecyclerview_mainLayoutId, R.layout.layout_progress_recyclerview);
            mClipToPadding = array.getBoolean(R.styleable.superrecyclerview_recyclerClipToPadding, false);
            mPadding = (int) array.getDimension(R.styleable.superrecyclerview_recyclerPadding, -1.0f);
            mPaddingLeft = (int) array.getDimension(R.styleable.superrecyclerview_recyclerPaddingLeft, 0.0f);
            mPaddingTop = (int) array.getDimension(R.styleable.superrecyclerview_recyclerPaddingTop, 0.0f);
            mPaddingRight = (int) array.getDimension(R.styleable.superrecyclerview_recyclerPaddingRight, 0.0f);
            mPaddingBottom = (int) array.getDimension(R.styleable.superrecyclerview_recyclerPaddingBottom, 0.0f);
            mScrollbarStyle = array.getInt(R.styleable.superrecyclerview_scrollbarStyle, 0);
            mEmptyId = array.getResourceId(R.styleable.superrecyclerview_layout_empty, R.layout.layout_more_progress);
            mProgressId = array.getResourceId(R.styleable.superrecyclerview_layout_progress, R.layout.recyclerview_loading_layout);
        }finally {
            array.recycle();
        }
    }

    private void initView() {
        if(isInEditMode()){
            return;
        }
        View v = LayoutInflater.from(getContext()).inflate(mXRecyclerViewMainLayout, this);
        swipeRefreshLayout = v.findViewById(R.id.ptr_layout);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.green_light, R.color.orange_light,
                R.color.blue_light, R.color.red_light);
        if(mProgressId != 0){
            progressView = inflate(getContext(), mProgressId, null);
        }

        if(mEmptyId != 0){
            emptyView = inflate(getContext(), mEmptyId, null);
        }

        initRecyclerView(v);
    }

    private void initRecyclerView(View view) {
        View mRecyclerView = view.findViewById(R.id.inList);
        if(mRecyclerView instanceof RecyclerView){
            this.recyclerView = (RecyclerView) mRecyclerView;
        }else throw new IllegalArgumentException("SuperRecyclerView works with a RecyclerView!");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mInternalOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(mExternalOnScrollListener != null){
                    mExternalOnScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(mExternalOnScrollListener != null){
                    mExternalOnScrollListener.onScrolled(recyclerView, dx, dy);
                }
            }
        };
        recyclerView.addOnScrollListener(mInternalOnScrollListener);
        if (!FloatUtil.compareFloats(mPadding, -1.0f)) {
            recyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
        } else {
            recyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        }

        if (mScrollbarStyle != -1) {
            recyclerView.setScrollBarStyle(mScrollbarStyle);
        }
    }

    private void setAdapterInternal(RecyclerView.Adapter adapter, boolean compatibleWithPrevious
            ,boolean removeAndRecycleExistingViews){
        if(compatibleWithPrevious){
            recyclerView.swapAdapter(adapter, removeAndRecycleExistingViews);
        }else recyclerView.setAdapter(adapter);

        if(adapter instanceof BaseQuickAdapter){
            BaseQuickAdapter quickAdapter = (BaseQuickAdapter) adapter;
            quickAdapter.setEnableLoadMore(false);
            //quickAdapter.setEmptyView(progressView);
            quickAdapter.setLoadMoreView(new CustomLoadMoreView());
            quickAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    quickAdapter.loadMoreComplete();
                }
            });
        }

        swipeRefreshLayout.setRefreshing(false);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        setAdapterInternal(adapter, false, true);
    }

    public void swapAdapter(RecyclerView.Adapter adapter, boolean removeAndRecycleExistingViews) {
        setAdapterInternal(adapter, true, removeAndRecycleExistingViews);
    }

    public void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    public void setEnabled(boolean enabled) {
        swipeRefreshLayout.setEnabled(enabled);
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Set the listener when refresh is triggered and enable the SwipeRefreshLayout
     */
    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(listener);
    }



    public void setOnScrollListener(RecyclerView.OnScrollListener mExternalOnScrollListener) {
        this.mExternalOnScrollListener = mExternalOnScrollListener;
    }

    /**
     * @return the recycler adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return recyclerView.getAdapter();
    }

    /**
     * recyclerView add line
     */

    public void addItemDecorationLine(){
        addItemDecorationLine(R.color.color_line, ScreenTool.dip2px(0.5f), 0);
    }

    public void addItemDecorationLine(int margin){
        addItemDecorationLine(R.color.color_line, ScreenTool.dip2px(0.5f), margin);
    }

    protected void addItemDecorationLine( @ColorRes int color, int size, int margin){
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(recyclerView.getContext())
                .margin(ScreenTool.dip2px(margin))
                .colorResId(color).size(size).build());
    }

    public void setLoadMore(boolean isEnd) {
        if (getAdapter() instanceof BaseQuickAdapter) {
            BaseQuickAdapter quickAdapter = (BaseQuickAdapter) getAdapter();
            if (isEnd) quickAdapter.loadMoreEnd();
            else
                quickAdapter.loadMoreComplete();
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setListPadding(float left, float top, float right, float bottom){
        recyclerView.setPadding(ScreenTool.dip2px(left), ScreenTool.dip2px(top),ScreenTool.dip2px(right),ScreenTool.dip2px(bottom));
    }
}
