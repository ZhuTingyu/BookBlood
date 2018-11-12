package com.cpigeon.book.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.base.util.utility.LogUtil;
import com.cpigeon.book.event.WXPayEvent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


/**
 * 微信支付的回调的Activity
 * Created by Administrator on 2017/7/11.
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    public static final String APP_ID = "wxfa9493e635225d92";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.print("baseReq = [" + baseReq.toString() + "]");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.print("BaseResp{" +
                "errCode=" + baseResp.errCode +
                ", errStr='" + baseResp.errStr + '\'' +
                ", transaction='" + baseResp.transaction + '\'' +
                ", openId='" + baseResp.openId + '\'' +
                '}');
//        CommonUitls.getInstance().onWxPay(this, baseResp.errCode);
//        EventBus.getDefault().post(new WXPayResultEvent(baseResp.errCode));
//        EventBus.getDefault().post(new GXTUserInfoEvent());
        EventBus.getDefault().post(new WXPayEvent());
        this.finish();
    }
}
