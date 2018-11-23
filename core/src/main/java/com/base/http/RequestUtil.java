package com.base.http;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.base.application.BaseApplication;
import com.base.util.EncryptionTool;
import com.base.util.Lists;
import com.base.util.http.GsonUtil;
import com.base.util.http.NetworkUtils;
import com.base.util.utility.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zhu TingYu on 2017/12/15.
 */

public class RequestUtil<T> {

    private static final int DEFAULT_TIMEOUT = 5;

    protected Map<String, String> bodyParameter = new HashMap<>();
    private Map<String, String> headParameter = new HashMap<>();
    private Map<String, String> imageFileParameter = new HashMap<>();
    private Map<String, String> videoFileParameter = new HashMap<>();
    private Map<String, String> normalFileParameter = new HashMap<>();

    private Type toJsonType;

    private String url;
    private String baseUrl;
    private String headUrl;
    private String uid;
    private String signString;

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;


    private RequestInterface requestInterface;

    static OkHttpClient.Builder builder;

    boolean isCache = false;

    protected static <T> RequestUtil<T> builder() {
        RequestUtil<T> requestUtil = new RequestUtil<>();
        builder = new OkHttpClient.Builder();
        return requestUtil;
    }

    /**
     * 添加网络请求表单
     *
     * @param key
     * @param value
     * @return
     */

    public RequestUtil<T> addBody(String key, String value) {
        bodyParameter.put(key, value);
        return this;
    }

    /**
     * 添加图片文件参数
     *
     * @param key
     * @param filePath
     * @return
     */

    public RequestUtil<T> addImageFileBody(String key, String filePath) {
        imageFileParameter.put(key, filePath);
        return this;
    }


    public RequestUtil<T> addImageFileBodys(Map<String, String> body) {
        Iterator<Map.Entry<String, String>> entries = body.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            imageFileParameter.put(entry.getKey(), entry.getValue());
        }
        return this;
    }


    /**
     * 添加视频文件参数
     *
     * @param key
     * @param filePath
     * @return
     */

    public RequestUtil<T> addVideoFileBody(String key, String filePath) {
        videoFileParameter.put(key, filePath);
        return this;
    }

    /**
     * 添加普通文件参数
     *
     * @param key
     * @param filePath
     * @return
     */

    public RequestUtil<T> addNormalFileBody(String key, String filePath) {
        normalFileParameter.put(key, filePath);
        return this;
    }

    /**
     * 添加请求头
     *
     * @param key
     * @param value
     * @return
     */

    public RequestUtil<T> addHead(String key, String value) {
        headParameter.put(key, value);
        return this;
    }

    /**
     * 设置项目头链接 例（/CAdminAPI/V1/）
     *
     * @param headUrl
     * @return
     */

    public RequestUtil<T> headUrl(String headUrl) {
        this.headUrl = headUrl;
        return this;
    }

    /**
     * 设置接口 例（GetCircleMessageList）
     *
     * @param url
     * @return
     */

    public RequestUtil<T> url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置接口
     *
     * @param resId
     * @return
     */

    public RequestUtil<T> url(@StringRes int resId) {
        this.url = BaseApplication.getAppContext().getString(resId);
        return this;
    }

    /**
     * 设置返回数据中data的数据类型
     *
     * @param toJsonType
     * @return
     */

    public RequestUtil<T> setToJsonType(Type toJsonType) {
        this.toJsonType = toJsonType;
        return this;
    }

    public RequestUtil<T> addList(Map<String, String> body) {
        Iterator<Map.Entry<String, String>> entries = body.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            bodyParameter.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 设置服务器链接
     *
     * @param baseUrl
     * @return
     */

    public RequestUtil<T> setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;

    }

    public RequestUtil<T> setCacheFile() {
        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "httpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        builder.cache(cache);
        isCache = true;
        return this;

    }

    public String getSignString() {
        return signString;
    }

    public Observable<T> request() {
        LogUtil.print(Lists.newArrayList(bodyParameter, imageFileParameter, videoFileParameter));
        LogUtil.print(getRequestUrl() + url);
        builder.addInterceptor(new BaseInterceptor(headParameter))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        if (isCache) {
            builder.addNetworkInterceptor(new CacheInterceptor());
        }


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        okHttpClient = builder.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl(getRequestUrl())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        requestInterface = retrofit.create(RequestInterface.class);

        Observable<T> observable = RxRequest.getRxHttp(this)
                .map(s -> {
                    try {
                        return GsonUtil.fromJson(s, toJsonType);
                    } catch (Exception e) {
                        return JSON.parseObject(s, toJsonType);
                    }
                });

        observable = observable.map(e -> {
            if (e instanceof ApiResponse) {
                ApiResponse responseJson = (ApiResponse) e;
//                LogUtil.print(responseJson.toJsonString());
            }
            return e;
        });
        return observable;

    }


    RequestInterface getRequestInterface() {
        return requestInterface;
    }

    String getRequestUrl() {
        return baseUrl + headUrl;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getBodyParameter() {
        return bodyParameter;
    }

    public Map<String, String> getImageFileParameter() {
        return imageFileParameter;
    }

    public Map<String, String> getVideoFileParameter() {
        return videoFileParameter;
    }

    public Map<String, String> getNormalFileParameter() {
        return normalFileParameter;
    }

    public void setSignString(String signString) {
        this.signString = signString;
    }

    public String getSign() {
        Map<String, Object> map = new TreeMap<>();
        map.put("get_timestamp", System.currentTimeMillis() / 1000);
        if (getBodyParameter() != null && getBodyParameter().size() > 0) {
            for (String key : getBodyParameter().keySet()) {
                if (!map.containsKey("post_" + key) && getBodyParameter().get(key) != null && !TextUtils.isEmpty(getBodyParameter().get(key).toString())) {
                    map.put("post_" + key, getBodyParameter().get(key).toString());
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            stringBuilder.append(key + "=" + map.get(key) + "&");
        }
        stringBuilder.append("key=" + signString);
        String result = stringBuilder.toString();
        LogUtil.print("签名：" + result);
        result = EncryptionTool.MD5_32(result);
        return result;
    }
}

class CacheInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        // 有网络时 设置缓存超时时间10秒钟
        int maxAge = 10;
        // 无网络时，设置超时为1个小时
        int maxStale = 60 * 60;
        Request request = chain.request();
        if (NetworkUtils.isConnected()) {
            //有网络时只从网络获取
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        } else {
            //无网络时只从缓存中读取
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        okhttp3.Response response = chain.proceed(request);
        if (NetworkUtils.isConnected()) {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
