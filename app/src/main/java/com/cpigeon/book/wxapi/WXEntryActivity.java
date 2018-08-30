package com.cpigeon.book.wxapi;

import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * 微信分享回调Activity
 */
public class WXEntryActivity extends WXCallbackActivity {
//    private String TAG = "umeng_tool";
//    private SweetAlertDialog dialog;

//    @Override
//    public void onResp(BaseResp resp) {
//        Log.d("umeng_tool", "onResp: " + resp.errCode);
//
//        switch (resp.getType()) {
//
//            case ConstantsAPI.COMMAND_PAY_BY_WX:
//                //支付回调
//                switch (resp.errCode) {
//                    case 0:
//
//                        dialog = CommonUitls.showSweetDialog(this, "支付成功!");
//                        Log.d(TAG, "onResp: 支付成功!");
//                        break;
//                    case -2:
//                        dialog = CommonUitls.showSweetDialog(this, "支付取消!");
//                        Log.d(TAG, "onResp: 支付取消!");
//                        break;
//                    case -1:
//                        dialog = CommonUitls.showSweetDialog(this, "支付失败!");
//                        Log.d(TAG, "onResp: 支付失败!");
//                        break;
//                    default:
//                        CommonUitls.showSweetDialog(this, "支付出错!");
//                        Log.d(TAG, "onResp: 支付出错!");
//                        break;
//                }
//                break;
//
//            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
//
//                switch (resp.errCode) {
//                    case 0:
//                        dialog = CommonUitls.showSweetDialog(this, "分享成功!");
//                        Log.d(TAG, "onResp: 分享成功!");
//                        break;
//                    case -2:
//                        dialog = CommonUitls.showSweetDialog(this, "分享取消!");
//                        Log.d(TAG, "onResp: 分享取消!");
//                        break;
//                    case -1:
//                        dialog = CommonUitls.showSweetDialog(this, "分享失败!");
//                        Log.d(TAG, "onResp: 分享失败!");
//                        break;
//                    default:
//                        dialog = CommonUitls.showSweetDialog(this, "分享出错!");
//                        Log.d(TAG, "onResp: 分享出错!");
//                        break;
//                }
//                break;
//            default:
//        }
//
//        super.onResp(resp);//一定要加super，实现我们的方法，否则不能回调
//
//    }
}
