//package com.base.util;
//
//import android.app.Activity;
//import android.text.TextUtils;
//
//import com.alipay.sdk.app.PayTask;
//import com.base.http.R;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//import io.reactivex.Observable;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//
//
///**
// * Created by Zhu TingYu on 2018/2/26.
// */
//
//public class AlipayManager {
//    public static Disposable pay(Activity activity, String orderInfo, Consumer<String> consumer){
//        return Observable.<String>create(subscriber -> {
//            PayTask alipay = new PayTask(activity);
//            String result = alipay.pay(orderInfo, true);
//            AliPayResult aliPayResult = new AliPayResult(result);
//            String callBack;
//            if ("9000".equals(aliPayResult.getResultStatus())) {
//                callBack = activity.getString(R.string.message_pay_success);
//            } else if ("8000".equals(aliPayResult.getResultStatus())) {
//                callBack = activity.getString(R.string.resultcode_alipay_ERROR_4000);
//            } else if ("4000".equals(aliPayResult.getResultStatus())) {
//                callBack = activity.getString(R.string.resultcode_alipay_ERROR_4000);
//            } else if ("6001".equals(aliPayResult.getResultStatus())) {
//                callBack = activity.getString(R.string.resultcode_alipay_ERROR_6001);
//            } else if ("6002".equals(aliPayResult.getResultStatus())) {
//                callBack = activity.getString(R.string.resultcode_alipay_ERROR_6002);
//            } else {
//                callBack = activity.getString(R.string.resultcode_alipay_ERROR_4000);
//            }
//            subscriber.onNext(callBack);
//            subscriber.onComplete();
//        }).subscribe(consumer);
//    }
//}
//
//class AliPayResult {
//    private String resultStatus;
//    private String result;
//    private String memo;
//
//    public AliPayResult(String rawResult) {
//
//        if (TextUtils.isEmpty(rawResult))
//            return;
//
//        String[] resultParams = rawResult.split(";");
//        for (String resultParam : resultParams) {
//            if (resultParam.startsWith("resultStatus")) {
//                resultStatus = gatValue(resultParam, "resultStatus");
//            }
//            if (resultParam.startsWith("result")) {
//                result = gatValue(resultParam, "result");
//            }
//            if (resultParam.startsWith("memo")) {
//                memo = gatValue(resultParam, "memo");
//            }
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "resultStatus={" + resultStatus + "};memo={" + memo
//                + "};result={" + result + "}";
//    }
//
//    private String gatValue(String content, String key) {
//        String prefix = key + "={";
//        return content.substring(content.indexOf(prefix) + prefix.length(),
//                content.lastIndexOf("}"));
//    }
//
//    /**
//     * @return the resultStatus
//     */
//    public String getResultStatus() {
//        return resultStatus;
//    }
//
//    /**
//     * @return the memo
//     */
//    public String getMemo() {
//        return memo;
//    }
//
//    /**
//     * @return the result
//     */
//    public String getResult() {
//        return result;
//    }
//
//    public String getTradeNumber(){
//        JSONObject res = null;
//        String tradeNumber = "";
//        try {
//            res = new JSONObject(getResult());
//            res = res.getJSONObject("alipay_trade_app_pay_response");
//            tradeNumber = res.getString("trade_no");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return tradeNumber;
//    }
//}
//
