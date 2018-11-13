package com.base.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.base.http.R;
import com.base.util.IntentBuilder;
import com.base.util.utility.StringUtil;

import java.util.Map;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class BaseWebViewActivity extends BaseActivity {

    protected WebView webView;
    protected WebSettings webSettings;
    String url;
    String title;
    ProgressBar progressBar;
    Map<String, String> mHeaderMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_layout);
        initView();
    }

    public static void start(Activity activity, String url){
        IntentBuilder.Builder(activity, BaseWebViewActivity.class)
                .putExtra(IntentBuilder.KEY_DATA, url)
                .startActivity();
    }

    public void initView() {

        String temp = getIntent().getStringExtra(IntentBuilder.KEY_DATA);

        if (StringUtil.isStringValid(temp)) {
            url = temp;
        }

        title = getIntent().getStringExtra(IntentBuilder.KEY_TITLE);

        if (StringUtil.isStringValid(title)) {
            setTitle(title);
        }


        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.pb_progressbar);

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar != null) {
                    if (newProgress == 100) {
                        progressBar.setVisibility(View.GONE);
                        loadJsFinish();
                    } else {
                        if (View.GONE == progressBar.getVisibility()) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        progressBar.setProgress(newProgress);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setPluginState(WebSettings.PluginState.ON);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                if (title != null) {
                    BaseWebViewActivity.this.setTitle(title);
                } else {
                    BaseWebViewActivity.this.setTitle(view.getTitle());
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);


                //如果不需要其他对点击链接事件的处理返回true，否则返回false

                return true;

            }
        });
        webView.loadUrl(url);

    }

    protected void loadWebByHtml(String url) {
        webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            /*webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);*/
            webView.destroy();
            webView = null;
        }

        super.onDestroy();
    }

    protected void loadJsFinish() {

    }

}
