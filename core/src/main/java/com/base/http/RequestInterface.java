package com.base.http;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Zhu TingYu on 2017/12/15.
 */

public interface RequestInterface {

    @POST
    Call<ResponseBody> post(
            @Url String url,
            @Query("uid") String uid,
            @Query("timestamp") String time,
            @Query("sign") String sign,
            @Body RequestBody requestBody);

    @Headers("Cache-Control: public, max-age=3600")
    @POST
    Call<ResponseBody> postHaveCache (
            @Url String url,
            @Query("uid") String uid,
            @Query("timestamp") String time,
            @Query("sign") String sign,
            @Body RequestBody requestBody);

}


