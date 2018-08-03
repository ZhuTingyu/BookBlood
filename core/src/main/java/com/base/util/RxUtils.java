package com.base.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class RxUtils {

    /**
     * view 点击过后，一段时间内不能点击
     * @param view
     * @return
     */
    public static Observable<Object> click(View view) {
        return Observable.create(subscriber -> {
            view.setOnClickListener(v -> {
                v.setEnabled(false);
                v.postDelayed(() -> {
                    v.setEnabled(true);
                }, 350);
                subscriber.onNext(new Object());
            });
        });
    }

    /**
     * 在一个新线程里运行
     * @param consumer
     * @return
     */

    public static Disposable runOnNewThread(Consumer<Object> consumer) {
        return Observable.create(sub -> {
            sub.onNext(new Object());
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(consumer);
    }

    /**
     * 延时运行
     * @param milliseconds
     * @return
     */

    public static Observable<Long> delayed(int milliseconds) {
       return  Observable.timer(milliseconds, TimeUnit.MILLISECONDS);
    }

    public static Disposable delayed(int milliseconds, Consumer<Long> consumer) {
        return  Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(consumer);
    }

    /**
     * 轮询
     * @param initialDelay 延迟时间
     * @param period 轮询间隔时间
     * @return
     */

    public static Observable<Long> rollPoling(long initialDelay, long period) {
        return Observable.interval(initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 监听textview 并发送文本内容
     * @param view
     * @return
     */

    public static Observable<String> textChanges(TextView view) {
        return Observable.create(subscriber -> {
            final TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    subscriber.onNext(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };

            if (view != null) {
                view.removeTextChangedListener(watcher);
                view.addTextChangedListener(watcher);
                subscriber.onNext(view.getText().toString());
            }
        });
    }

    public static Consumer<? super Boolean> enabled(final View view) {
        return b -> {
            if (view != null)
                view.setEnabled(b);
        };
    }


}
