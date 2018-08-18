package com.base.http;

import com.base.util.Utils;
import com.base.util.utility.LogUtil;

import java.io.File;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zhu TingYu on 2017/12/15.
 */

public class RxRequest {
    public static Observable<String> getRxHttp(RequestUtil rxRequest) {
        return Observable.<String>create(observableEmitter -> {
            Map<String, String> body = rxRequest.getBodyParameter();
            Map<String, String> imageBody = rxRequest.getImageFileParameter();
            Map<String, String> videoBody = rxRequest.getVideoFileParameter();

            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            for (String key : body.keySet()) {
                builder.addFormDataPart(key, getStringValue(body.get(key)));
            }

            for (String key : imageBody.keySet()) {
                File imgF = new File(imageBody.get(key));
                builder.addPart(MultipartBody.Part.createFormData(key, imgF.getName(),
                        RequestBody.create(MediaType.parse("image/*"), imgF)));
            }

            for (String key : videoBody.keySet()) {
                File videoF = new File(videoBody.get(key));
                builder.addPart(MultipartBody.Part.createFormData("video", videoF.getName(),
                        RequestBody.create(MediaType.parse("video/*"), videoF)));
            }

            if (body.isEmpty()) {
                builder.addFormDataPart("timestamp", "");
            }

            Call<ResponseBody> call;

            if (!rxRequest.isCache) {
                call = rxRequest.getRequestInterface().post(rxRequest.getUrl(), String.valueOf(System.currentTimeMillis() / 1000), rxRequest.getSign(), builder.build());

            } else {
                call = rxRequest.getRequestInterface().postHaveCache(rxRequest.getUrl(), String.valueOf(System.currentTimeMillis() / 1000), rxRequest.getSign(), builder.build());
            }


            try {
                Response<ResponseBody> response = call.execute();

                String responseStr = response.body().string();
                LogUtil.print("请求接口返回: " + responseStr);

                if (response.code() == 200) {
                    observableEmitter.onNext(responseStr);
                } else {
                    ApiResponse apiResponse = new ApiResponse();
                    apiResponse.errorCode = -1;
                    apiResponse.msg = Utils.getString(R.string.message_net_error);
                    observableEmitter.onNext(apiResponse.toJsonString());
                }
            } catch (Exception e) {

                if(e instanceof SocketTimeoutException){
                    ApiResponse apiResponse = new ApiResponse();
                    apiResponse.errorCode = -1;
                    apiResponse.msg = Utils.getString(R.string.message_net_error);
                    observableEmitter.onNext(apiResponse.toJsonString());
                }

                e.printStackTrace();
            }

        });
    }


    public static String getStringValue(String s) {
        return s == null ? "" : s;
    }
}
