package com.cpigeon.book.util;

/**
 * Created by Zhu TingYu on 2017/12/7.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Base64;

import com.base.base.BaseActivity;
import com.cpigeon.book.R;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;

public class SendWX {
    public String APP_ID = "";
    public static String STATE = "EShopApp";
    private Context context;
    private IWXAPI api;

    public SendWX(BaseActivity context) {
        this.context = context;
        this.APP_ID = getAppId(context);
        api = WXAPIFactory.createWXAPI(context, APP_ID);
    }

    public SendWX(Context context) {
        this.context = context;
        this.APP_ID = getAppId(context);
        api = WXAPIFactory.createWXAPI(context, APP_ID);
    }

    public IWXAPI getWxApi() {
        return api;
    }


    public static String getAppId(Context context) {
        try {
            return new String(Base64.decode(context.getResources().getString(R.string.weixin_appid), Base64.DEFAULT), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String getAppSecret(Context context) {
        try {
            return new String(Base64.decode(context.getResources().getString(R.string.weixin_appid), Base64.DEFAULT), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public Boolean isBinding() {
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        api.registerApp(APP_ID);
        return api.isWXAppInstalled();
    }

    public boolean isInstalled() {
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        return api.isWXAppInstalled();
    }


    public void payWeiXin(PayReq payReq) {
        if (context == null) {
            return;
        }
        if (payReq == null) {
            if (context instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) context;
            }
            return;
        }
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        if (!api.isWXAppInstalled()) {
            if (context instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) context;
            }
            createDialog();
            return;
        }
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!isPaySupported) {
            if (context instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) context;
            }
            createDialogNotPay();
            return;
        }
        api.sendReq(payReq);
    }

    /*private void sendWeb(boolean isTimelineCb, String webpageUrl, String title, String desc, Bitmap thumb) {
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        api.registerApp(APP_ID);
        if (!api.isWXAppInstalled()) {
            if(context instanceof  BaseActivity){
                BaseActivity baseActivity=(BaseActivity)context;
                baseActivity.setProgressVisible(false);
            }
            createDialog();
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webpageUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc;
        if (thumb != null) {
            msg.setThumbImage(thumb);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage" + System.currentTimeMillis();
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }*/


    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("当前没有下载微信，请下载安装");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public void createDialogNotPay() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("当前版本不支持微信功能，请下载安装");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
