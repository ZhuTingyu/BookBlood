package com.base.util.glide;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/12/14.
 */

public class GlideUtil {

    /**
     * 清除内存缓存.
     */
    public static void clearMemoryCache(Context mContext) {
        // This method must be called on the main thread.
        Glide.get(mContext).clearMemory();
    }

    /**
     * 清除磁盘缓存.
     */
    public static void clearDiskCache(Context mContext) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                // This method must be called on a background thread.
                Glide.get(mContext).clearDiskCache();
                return null;
            }
        };
    }


}
