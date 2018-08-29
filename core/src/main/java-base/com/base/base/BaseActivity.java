package com.base.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.base.http.R;
import com.base.util.BarUtils;
import com.base.util.Utils;
import com.base.util.system.AppManager;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.StringUtil;
import com.base.widget.LoadingView;
import com.luck.picture.lib.permissions.RxPermissions;

import java.lang.ref.WeakReference;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/1/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static final String IS_BAR_IMMERSIVE = "IS_BAR_IMMERSIVE";

    protected Toolbar toolbar;
    private View stateBar;
    private View imgTitle;
    BaseActivity baseActivity;
    private WeakReference<AppCompatActivity> weakReference;
    public SweetAlertDialog errorDialog;

    protected LoadingView progressView;
    protected ViewGroup rootView;

    protected BaseViewModel viewModel;

    private TextView title;

    private RxPermissions mRxPermission;
    private boolean isBarImmersive;
    protected final CompositeDisposable composite = new CompositeDisposable();


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isBarImmersive = getBaseActivity().getIntent().getBooleanExtra(IS_BAR_IMMERSIVE, true);
        if(isBarImmersive){
            BarUtils.setNavBarImmersive(this);
            BarUtils.setStatusBarAllAlpha(this);
        }
        weakReference = new WeakReference<AppCompatActivity>(this);
        AppManager.getAppManager().addActivity(weakReference);
        baseActivity = this;
    }





    @Override
    protected void onDestroy() {
        composite.clear();
        if (viewModel != null) {
            viewModel.onDestroy();
        }

        super.onDestroy();

        //hl
        if (errorDialog != null && errorDialog.isShowing()) {
            errorDialog.dismiss();
        }
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

    protected <T extends BaseViewModel> T getViewModel(Class<T> viewModelClass) {
        return ViewModelProviders.of(getBaseActivity()).get(viewModelClass);
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        rootView = (ViewGroup) getWindow().getDecorView();
        initView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        rootView = (ViewGroup) getWindow().getDecorView();
        initView();
    }

    public void setToolbarColor(@ColorRes int resId) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(Utils.getColor(resId));
        }
        if (stateBar != null) {
            stateBar.setBackgroundColor(Utils.getColor(resId));
            BarUtils.setStatusBarLightMode(baseActivity, true);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        stateBar = findViewById(R.id.stateBar);
        imgTitle = findViewById(R.id.imgTitle);
        setToolbar();
        initProgressLayout();
    }

    private void initProgressLayout() {
        if (progressView == null) {
            progressView = new LoadingView(this);
        }
        setProgressVisible(false);
        rootView.addView(progressView);
    }

    /**
     * 加载框
     *
     * @param isVisible 是否显示
     */

    public void setProgressVisible(boolean isVisible) {
        if (progressView != null) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            progressView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    isVisible ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    private void setToolbar() {
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            title = findViewById(R.id.toolbar_title);
        }

    }

    public void error(String message) {

        if (!StringUtil.isStringValid(message)) {
            return;
        }

        //保证界面只有一个错误提示
        if (errorDialog == null || !errorDialog.isShowing()) {
            errorDialog = DialogUtils.createErrorDialog(baseActivity, message);
        }
    }


    public void error(int code, String error) {
        setProgressVisible(false);
        error(error);
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

    /**
     * 设置顶部标题
     *
     * @param titleString
     */


    public void setTitle(String titleString) {
        if (title != null) {
            title.setText(titleString);
        }
    }

    public void setTitle(@StringRes int resId) {
        if (title != null) {
            title.setText(getString(resId));
        }
    }

    public void setImageTitle() {
        if (imgTitle != null) {
            imgTitle.setVisibility(View.VISIBLE);
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
    }

    public RxPermissions getRxPermission() {
        if (mRxPermission == null)
            mRxPermission = new RxPermissions(getBaseActivity());
        return mRxPermission;
    }

    /**
     * 检查权限
     * permission 获取方式： Manifest.permission.CAMERA
     */
    protected void checkAppPermission(String permission, Consumer<? super Boolean> onNext) {
        bindUi(getRxPermission().request(permission), onNext);
    }

    public <T> void bindUi(Observable<T> observable, Consumer<? super T> onNext) {
        composite.add(observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, throwable -> {
                            error(throwable.getMessage());
                        }
                ));
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    /**
     * 隐藏顶部通知栏
     */

    protected void hideTop() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int flags;
        int curApiVersion = android.os.Build.VERSION.SDK_INT;
        if (curApiVersion >= Build.VERSION_CODES.KITKAT) {
            flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;

        } else {
            flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

    /**
     * 设置toolbar没有左边按钮
     */

    protected void setToolbarNotBack() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(null);
            toolbar.setNavigationOnClickListener(null);
        }
    }

}
