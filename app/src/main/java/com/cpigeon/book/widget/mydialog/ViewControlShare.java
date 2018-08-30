package com.cpigeon.book.widget.mydialog;

import android.content.Context;
import android.util.Log;

import com.base.util.utility.ToastUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Administrator on 2017/12/27.
 */

public class ViewControlShare {

    //分享结果回调  下载
    public static UMShareListener getShareResultsDown(Context mContext, ShareDialogFragment dialogFragment, String type) {

        return new UMShareListener() {

            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d("xiaohlshare", "onResult: 0-->" + share_media);
                dialogFragment.dismiss();
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {

                Log.d("xiaohlshare", "onResult: 1-->" + share_media);

                ToastUtils.showLong(mContext, "分享成功");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Log.d("xiaohlshare", "onResult: 2-->" + throwable.getLocalizedMessage());
//                CommonUitls.showSweetDialog(mContext, "分享失败" + throwable.getLocalizedMessage());

                ToastUtils.showLong(mContext, "分享失败！可能您分享的平台没有安装。");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Log.d("xiaohlshare", "onResult: 3->" + share_media);
                ToastUtils.showLong(mContext, "取消分享");
            }
        };
    }


}
