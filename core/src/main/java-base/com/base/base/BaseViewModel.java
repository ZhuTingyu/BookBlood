package com.base.base;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.base.BaseFragment;
import com.base.entity.RestErrorInfo;
import com.base.entity.RestHintInfo;
import com.base.http.HttpErrorException;
import com.base.http.R;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

;

/**
 * 6 on 2016/3/15.
 */
public class BaseViewModel extends ViewModel {

    private Object baseActivity;
    protected final CompositeDisposable subscription = new CompositeDisposable();
    protected MutableLiveData<RestErrorInfo> error = new MutableLiveData<>();
    public MutableLiveData<String> listEmptyMessage = new MutableLiveData<>();
    public MutableLiveData<String> normalResult = new MutableLiveData<>();
    public MutableLiveData<Boolean> isCanCommit = new MutableLiveData<>();

    //                showHintClosePage.setValue(new RestHintInfo.Builder().message(r.msg).build());
    public MutableLiveData<RestHintInfo> showHintClosePage = new MutableLiveData<>();//弹出成功提示框

    public BaseViewModel() {
    }

    protected void newBaseModel() {

    }

    public BaseActivity getActivity() {
        if (null != baseActivity && baseActivity instanceof BaseActivity) {
            return (BaseActivity) baseActivity;
        } else if (null != baseActivity && baseActivity instanceof BaseFragment) {
            return ((BaseFragment) baseActivity).getBaseActivity();
        }
        return null;
    }

    public Object getBaseActivity() {
        return baseActivity;
    }

    public void setBaseActivity(Object baseActivity) {
        this.baseActivity = baseActivity;
        newBaseModel();
    }

    public LiveData<RestErrorInfo> getError() {
        return error;
    }


    public LiveData<RestHintInfo> getHintClosePage() {
        return showHintClosePage;
    }

    public <T> void submitRequest(Observable<T> observable, final Consumer<? super T> onNext, final Consumer<Throwable> onError, final Action onComplete) {
        if (null != baseActivity && baseActivity instanceof AppCompatActivity) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete));
        } else if (null != baseActivity && baseActivity instanceof BaseFragment) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete));

        } else {
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete);
        }
    }

    public <T> void submitRequest(Observable<T> observable, final Consumer<? super T> onNext, final Consumer<Throwable> onError) {
        if (null != baseActivity && baseActivity instanceof BaseActivity) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError));
        } else if (null != baseActivity && baseActivity instanceof BaseFragment) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError));

        } else {
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError);
        }
    }

    public <T> void submitRequestThrowError(Observable<T> observable, final Consumer<? super T> onNext) {
        if (null != baseActivity && baseActivity instanceof BaseActivity) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, throwable -> error.setValue(getError(throwable))));
        } else if (null != baseActivity && baseActivity instanceof BaseFragment) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, throwable -> error.setValue(getError(throwable))));

        } else {
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, throwable -> error.setValue(getError(throwable)));
        }
    }

    public <T> void submitRequestThrowError(Observable<T> observable, final Consumer<? super T> onNext, final Action onComplete) {
        if (null != baseActivity && baseActivity instanceof BaseActivity) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, throwable -> error.setValue(getError(throwable)), onComplete));
        } else if (null != baseActivity && baseActivity instanceof BaseFragment) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, throwable -> error.setValue(getError(throwable)), onComplete));

        } else {
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, throwable -> error.setValue(getError(throwable)), onComplete);
        }
    }

    public <T> void submitRequest(Observable<T> observable, final Consumer<? super T> onNext) {
        if (null != baseActivity && baseActivity instanceof BaseActivity) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext));
        } else if (null != baseActivity && baseActivity instanceof BaseFragment) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext));

        } else {
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
        }
    }

    public <T> void bindUi(Observable<T> observable, Consumer<? super T> onNext, Consumer<Throwable> onError) {
        subscription.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError));
    }

    public <T> void bindUi(Observable<T> observable, Consumer<? super T> onNext) {
        subscription.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, throwable -> {
        }));
    }

    public void onDestroy() {
        baseActivity = null;
        subscription.clear();
    }

    public RestErrorInfo getError(Throwable throwable) {

        //hl
        if (throwable instanceof ConnectException) {
            return new RestErrorInfo(Utils.getString(R.string.text_net_error));
        }

        if (throwable instanceof SocketTimeoutException) {
            return new RestErrorInfo("网路有点不稳定\n请检查网速");
        }

        if (throwable instanceof HttpErrorException) {
            return new RestErrorInfo(((HttpErrorException) throwable).getResponseJson());
        }

        if (throwable != null) {
            return new RestErrorInfo(throwable.getMessage());
        }

        return new RestErrorInfo("");
    }

    public String getString(@StringRes int r) {
        if (getActivity() != null) {
            return getActivity().getString(r);
        }
        return "";
    }

    public RestErrorInfo getErrorString(@StringRes int r) {
        if (getActivity() != null) {
            return new RestErrorInfo(getActivity().getString(r));
        }
        return new RestErrorInfo("");
    }

    public RestErrorInfo getErrorString(String r) {
        return new RestErrorInfo(r);
    }

    public String getString(@StringRes int r, Object... formatArgs) {
        if (getActivity() != null) {
            return getActivity().getString(r, formatArgs);
        }
        return "";
    }

    protected void error(@StringRes int resId) {
        error.setValue(getErrorString(resId));
    }


    protected void error(String resId) {
        error.setValue(getErrorString(resId));
    }


    protected void hintDialog(String resId) {
        showHintClosePage.setValue(new RestHintInfo.Builder().message(resId).build());
    }


    protected void setListEmptyMessage(String message) {
        listEmptyMessage.setValue(message);
    }

    public MutableLiveData<String> getListEmptyMessage() {
        return listEmptyMessage;
    }

    public void isCanCommit(String... strings) {
        List<String> list = Lists.newArrayList(strings);
        for (String s : list) {
            if (!StringUtil.isStringValid(s)) {
                isCanCommit.setValue(false);
                return;
            }
        }
        isCanCommit.setValue(true);
    }
}
