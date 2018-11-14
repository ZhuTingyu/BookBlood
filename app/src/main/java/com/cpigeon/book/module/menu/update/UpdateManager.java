package com.cpigeon.book.module.menu.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.dialog.DialogUtils;
import com.base.util.system.AppManager;
import com.base.util.utility.PhoneUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.UpdateInfo;
import com.cpigeon.book.util.CpigeonConfig;
import com.cpigeon.book.util.MathUtil;
import com.cpigeon.book.util.SharedPreferencesTool;
import com.cpigeon.book.widget.mydialog.CustomAlertDialog4;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Administrator on 2016-10-20.
 */

public class UpdateManager {
    private static final String DOWNLOAD_STATE_NO = "no";
    private static final String DOWNLOAD_STATE_READY = "ready";
    private static final String DOWNLOAD_STATE_ERROR = "error";
    private static final String DOWNLOAD_STATE_CANCEL = "cancel";
    private static final String DOWNLOAD_STATE_FINISHED = "finished";

    private OnInstallAppListener onInstallAppListener;
    private OnCheckUpdateInfoListener onCheckUpdateInfoListener;
    private Context mContext;
    private ProgressDialog mProgressDialog;


    public UpdateManager(@NonNull Context context) {
        mContext = context;
    }

    public void setOnCheckUpdateInfoListener(OnCheckUpdateInfoListener onCheckUpdateInfoListener) {
        this.onCheckUpdateInfoListener = onCheckUpdateInfoListener;
    }

    public UpdateManager setOnInstallAppListener(UpdateManager.OnInstallAppListener onInstallAppListener) {
        this.onInstallAppListener = onInstallAppListener;
        return this;
    }


    /**
     * 检查App更新
     */
    public void checkUpdate(List<UpdateInfo> updateInfos) {
        if (updateInfos == null || updateInfos.size() == 0) return;
        for (UpdateInfo updateInfo : updateInfos) {
            if (mContext.getPackageName() != null && mContext.getPackageName().equals(updateInfo.getPackageName())) {
                if (updateInfo.getVerCode() > PhoneUtils.getVersionCode(mContext)) {
                    updateReady(updateInfo);
                    return;
                }
            }
        }
        if (onCheckUpdateInfoListener != null)
            onCheckUpdateInfoListener.onNotFoundUpdate();
    }

    public OnCheckUpdateInfoListener getOnCheckUpdateInfoListener() {
        return onCheckUpdateInfoListener;
    }

    /**
     * 下载准备
     */
    private LinearLayout mDialogLayout;
//    private Button btn_cancel;//右上角取消
//    private TextView tv_xbbh;//新版本号
//    private TextView update_tv1;//当前版本
//    private TextView update_tv2;//更新时间
//    private TextView update_tv3;//更新内容
//    private Button btn_ljgx;//点击更新

    //    private AlertDialog updateAlertDialog;
    private CustomAlertDialog4 updateAlertDialog;

    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_num;
    private RelativeLayout rl_btn;

    private void updateReady(final UpdateInfo updateInfo) {

        if (onCheckUpdateInfoListener != null)
            onCheckUpdateInfoListener.onHasUpdate(updateInfo);
        final String _url = updateInfo.getUrl();

        mDialogLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_update_dialog, null);


        tv_title = mDialogLayout.findViewById(R.id.tv_title);//新版本号
        tv_content = mDialogLayout.findViewById(R.id.tv_content);//内容
        tv_num = mDialogLayout.findViewById(R.id.tv_num);//大小
        rl_btn = mDialogLayout.findViewById(R.id.rl_btn);//按钮升级


        String strCode = mContext.getString(R.string.str_new_code);
        strCode = strCode.replace("%1%", updateInfo.getVerName());
        tv_title.setText(strCode);

        tv_content.setText(updateInfo.getUpdateExplain());


        getFileSize(_url, tv_num);
//        tv_num.setText() + "M");


        //点击升级
        rl_btn.setOnClickListener(v -> {
            String path = CpigeonConfig.UPDATE_SAVE_FOLDER + mContext.getPackageName() + ".apk";
            //判断当前是否已经下载了
            if (SharedPreferencesTool.Get(mContext, "download", UpdateManager.DOWNLOAD_STATE_NO, SharedPreferencesTool.SP_FILE_APPUPDATE).equals(UpdateManager.DOWNLOAD_STATE_FINISHED)) {
                //获取APK信息
                PackageManager pm = mContext.getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
                //判断是否是最新版本的APK文件
                if (info != null && info.versionCode == updateInfo.getVerCode() && info.applicationInfo.packageName.equals(updateInfo.getPackageName())) {
                    install(path);
                    return;
                }
            }
            SharedPreferencesTool.Save(mContext, "download", UpdateManager.DOWNLOAD_STATE_READY, SharedPreferencesTool.SP_FILE_APPUPDATE);
            DownLoad(_url, path);
        });

//        btn_cancel = (Button) mDialogLayout.findViewById(R.id.btn_cancel);
//        tv_xbbh = (TextView) mDialogLayout.findViewById(R.id.tv_xbbh);
//        update_tv1 = (TextView) mDialogLayout.findViewById(R.id.update_tv1);
//        update_tv2 = (TextView) mDialogLayout.findViewById(R.id.update_tv2);
//        update_tv3 = (TextView) mDialogLayout.findViewById(R.id.update_tv3);
//        update_tv3 = (TextView) mDialogLayout.findViewById(R.id.update_tv3);
//        btn_ljgx = (Button) mDialogLayout.findViewById(R.id.btn_ljgx);
//
//        tv_xbbh.setText(updateInfo.getVerName());
//        update_tv1.setText("当前版本:" + PhoneUtils.getVersionName(mContext));
//        update_tv2.setText("更新时间：" + updateInfo.getUpdateTime());
//        update_tv3.setText(updateInfo.getUpdateExplain());
//
//        //取消
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferencesTool.Save(mContext, "download", UpdateManager.DOWNLOAD_STATE_NO, SharedPreferencesTool.SP_FILE_APPUPDATE);
//                if (updateInfo.isForce()) {
//                    AppManager.getAppManager().killAllActivity();
//                }
//            }
//        });
//
//        //确定
//        btn_ljgx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String path = CpigeonConfig.UPDATE_SAVE_FOLDER + mContext.getPackageName() + ".apk";
//                //判断当前是否已经下载了
//                if (SharedPreferencesTool.Get(mContext, "download", UpdateManager.DOWNLOAD_STATE_NO, SharedPreferencesTool.SP_FILE_APPUPDATE).equals(UpdateManager.DOWNLOAD_STATE_FINISHED)) {
//                    //获取APK信息
//                    PackageManager pm = mContext.getPackageManager();
//                    PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
//                    //判断是否是最新版本的APK文件
//                    if (info != null && info.versionCode == updateInfo.getVerCode() && info.applicationInfo.packageName.equals(updateInfo.getPackageName())) {
//                        install(path);
//                        return;
//                    }
//                }
//
//                SharedPreferencesTool.Save(mContext, "download", UpdateManager.DOWNLOAD_STATE_READY, SharedPreferencesTool.SP_FILE_APPUPDATE);
//                DownLoad(_url, path);
//            }
//        });

        //Logger.i("下载");

        updateAlertDialog = new CustomAlertDialog4(mContext);

        updateAlertDialog.setContentView(mDialogLayout);
        //调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
        updateAlertDialog.setCanceledOnTouchOutside(false);
        updateAlertDialog.setCancelable(false);
        updateAlertDialog.show();
    }

    /**
     * 下载安装包
     *
     * @param url  下载路径
     * @param path 保存路径
     */
    private void DownLoad(String url, final String path) {
        Log.d("update_path", "DownLoad: " + path);
        if (onCheckUpdateInfoListener != null)
            onCheckUpdateInfoListener.onDownloadStart();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("准备下载");
        mProgressDialog.setCancelable(false);// 设置点击空白处也不能关闭该对话框
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置采用圆形进度条
        mProgressDialog.setMax(0);
        mProgressDialog.setIndeterminate(false);// 设置显示明确的进度
        mProgressDialog.setProgressNumberFormat("%1dK/%2dK");
        mProgressDialog.show();
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(path);

        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                mProgressDialog.setMessage("开始下载");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                BigDecimal b = new BigDecimal((float) current / (float) total);
                float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                if (mProgressDialog.getMax() == 100)
                    mProgressDialog.setMax((int) (total / 1024));
                mProgressDialog.setMessage("正在下载...");
                mProgressDialog.setProgress((int) (f1 * mProgressDialog.getMax()));
            }

            @Override
            public void onSuccess(File result) {
                mProgressDialog.setMessage("下载完成");
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                SharedPreferencesTool.Save(mContext, "download", UpdateManager.DOWNLOAD_STATE_FINISHED, SharedPreferencesTool.SP_FILE_APPUPDATE);
                install(path);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                DialogUtils.createHintDialog(AppManager.getAppManager().getTopActivity(), "下载失败", SweetAlertDialog.ERROR_TYPE, false, dialog -> {
                    dialog.dismiss();
                });


                SharedPreferencesTool.Save(mContext, "download", UpdateManager.DOWNLOAD_STATE_ERROR, SharedPreferencesTool.SP_FILE_APPUPDATE);
                closeAndExit();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                SharedPreferencesTool.Save(mContext, "download", UpdateManager.DOWNLOAD_STATE_CANCEL, SharedPreferencesTool.SP_FILE_APPUPDATE);
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFinished() {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    private void install(String path) {
//        CommonTool.installApp(mContext, path);
        PhoneUtils.openAPKFile(mContext, path);
        if (onInstallAppListener != null)
            onInstallAppListener.onInstallApp();
    }

    private void closeAndExit() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(startMain);
        System.exit(0);
    }

    public interface OnCheckUpdateInfoListener {
        /**
         * 开始获取更新信息
         */
        void onGetUpdateInfoStart();

        /**
         * 获取更新信息完成
         *
         * @param updateInfos
         * @return
         */
        boolean onGetUpdateInfoEnd(List<UpdateInfo> updateInfos);

        /**
         * 没有更新
         */
        void onNotFoundUpdate();

        /**
         * 有更新
         *
         * @param updateInfo
         */
        void onHasUpdate(UpdateInfo updateInfo);

        /**
         * 开始下载更新包
         */
        void onDownloadStart();
    }

    public interface OnInstallAppListener {
        void onInstallApp();
    }


    public void getFileSize(String urlString, TextView tv) {

        String url = urlString;

        new Thread(new Runnable() {
            @Override
            public void run() {

                double lenght = 0;
                try {
                    lenght = 0;
                    URL mUrl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
                    conn.setConnectTimeout(5 * 1000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept-Encoding", "identity");
                    conn.setRequestProperty("Referer", url);
                    //conn.setRequestProperty("Referer", urlString);
                    conn.setRequestProperty("Charset", "UTF-8");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.connect();
                    int responseCode = conn.getResponseCode();

                    // 判断请求是否成功处理
                    if (responseCode == 200) {
                        lenght = conn.getContentLength();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                double finalLenght = lenght;
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            tv.setText(String.valueOf(MathUtil.doubleformat(finalLenght / 1024 / 1024, 2) + "M"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            tv.setText(String.valueOf(0 + "M"));
                        }
                    }
                });
            }
        }).start();
    }
}