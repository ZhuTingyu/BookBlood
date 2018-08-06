package com.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.base.base.BaseActivity;
import com.base.base.BaseViewModel;
import com.base.http.R;
import com.base.util.IntentBuilder;
import com.base.util.utility.ToastUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


public abstract class BaseFragment extends Fragment {
    private View parentView;

    private BaseActivity baseActivity;

    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;

    //标志位 fragment是否可见
    protected boolean isVisible;


    protected SweetAlertDialog mLoadingSweetAlertDialog;

    protected Toolbar toolbar;

    protected final CompositeDisposable composite = new CompositeDisposable();


    protected TextView titleView;

    protected boolean isBack = true;

    protected Context context;

    protected BaseViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.baseActivity = (BaseActivity) context;
    }

    protected <T extends BaseViewModel> T getViewModel(Class<T> viewModelClass) {
        return ViewModelProviders.of(getBaseActivity()).get(viewModelClass);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.baseActivity = null;
    }

    protected void initViewModel(BaseViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.setBaseActivity(getBaseActivity());
        viewModel.getError().observe(this, restErrorInfo -> {
            if (restErrorInfo != null) {
                error(restErrorInfo.code, restErrorInfo.message);
            }
        });
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (getArguments() != null) {
            isBack = getArguments().getBoolean(IntentBuilder.KEY_BOOLEAN);
        }
        toolbar = view.findViewById(R.id.toolbar);
        titleView = view.findViewById(R.id.toolbar_title);
        if (toolbar == null) {
            toolbar = getActivity().findViewById(R.id.toolbar);
            titleView = getActivity().findViewById(R.id.toolbar_title);
        }
        if (toolbar != null && isBack) {
            toolbar.setNavigationOnClickListener(v -> {
                getActivity().finish();
            });
        }
        super.onViewCreated(view, savedInstanceState);
    }

    protected void setToolbarNotBack() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(null);
            toolbar.setNavigationOnClickListener(null);
        }
    }

    public void setTitle(@StringRes int resId) {
        if (null != toolbar) {
            if (titleView != null) {
                titleView.setText(getString(resId));
            }
        }
    }

    public void setTitle(String title) {
        if (null != toolbar) {
            if (titleView != null) {
                titleView.setText(title);
            }
        }
    }

    protected void setToolbarRight(@StringRes int resId, MenuItem.OnMenuItemClickListener listener) {
        if (toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.getMenu().add(getString(resId))
                    .setOnMenuItemClickListener(listener)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    protected void setToolbarRight(String string, MenuItem.OnMenuItemClickListener listener) {
        if (toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.getMenu().add(string)
                    .setOnMenuItemClickListener(listener)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    protected void onVisible() {

        lazyLoad();
    }

    protected void lazyLoad() {
    }


    protected void onInvisible() {
    }


    protected void loadData() {
    }


    protected void error(String message) {
        setProgressVisible(false);
        baseActivity.error(message);
    }

    public void error(int code, String error) {
        baseActivity.error(code, error);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        composite.clear();
        if (viewModel != null) {
            viewModel.onDestroy();
            viewModel = null;
        }
    }

    protected <T extends View> T findViewById(@NonNull View view, @IdRes int resId) {
        T t = null;
        if (view != null)
            t = (T) view.findViewById(resId);
        if (t == null) {
            throw new IllegalArgumentException("view 0x" + Integer.toHexString(resId)
                    + " doesn't exist");
        }
        return t;
    }

    protected <T extends View> T findViewById(@IdRes int resId) {
        T t = null;
        if (getView() != null)
            t = getView().findViewById(resId);
        if (t == null) {
            throw new IllegalArgumentException("view 0x" + Integer.toHexString(resId)
                    + " doesn't exist");
        }
        return t;
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    protected void addItemDecorationLine(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(recyclerView.getContext())
                .colorResId(R.color.color_line).size(1).build());
    }

    protected <T> void bindUi(Observable<T> observable, Consumer<? super T> onNext) {
        composite.add(observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, throwable -> {
                            ToastUtils.showLong(getActivity(), throwable.getMessage());
                        }
                ));
    }

    //加载框显示隐藏
    public void setProgressVisible(boolean isVisible) {
        if (baseActivity != null) {
            baseActivity.setProgressVisible(isVisible);
        }
    }

    protected void finish() {
        getBaseActivity().finish();
    }

    // 隐藏软键盘
    protected void hideSoftInput(IBinder token) {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(token,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //edittext默认不显示软键盘，只有edittext被点击时，软键盘才弹出
    protected void hideSoftInput() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}