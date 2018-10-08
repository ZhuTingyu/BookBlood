package com.cpigeon.book.widget.mydialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.cpigeon.book.R;
import com.umeng.socialize.UMShareListener;

import java.io.File;


/**
 * Created by Administrator on 2017/1/7.
 * 分享Fragment
 */

public class LocalShareDialogFragment extends DialogFragment {


    private ImageButton imgbtn_wx, imgbtn_pyq, imgbtn_qq, imgbtn_qqz;//分享按钮
    private Button btn_cancel;//取消

    private Bitmap mBitmap;

    public UMShareListener umShareListener;


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgbtn_wx:
                    //微信分享
                    share2WX();

                    break;
                case R.id.imgbtn_pyq:
                    //微信朋友圈
                    share2WX_pyq();
                    break;
                case R.id.imgbtn_qq:
                    //QQ
                    share2QQ();
                    break;

                case R.id.imgbtn_qqz:
                    //QQ空间
//                    share2QQ_Z();

                    break;
                case R.id.btn_cancel:
                    dismiss();
                    break;
            }
        }
    };

    private String shareTitle = "中鸽助手";
    private String shareContent = "中鸽助手分享";

    private String TAG = "ShareDialogFragment";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.layout_share_dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        assert window != null;
        window.setWindowAnimations(R.style.AnimBottomDialog);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
//        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 2 / 5;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        initView(dialog);

        return dialog;
    }

    private void initView(Dialog dialog) {
        imgbtn_wx = (ImageButton) dialog.findViewById(R.id.imgbtn_wx);
        imgbtn_pyq = (ImageButton) dialog.findViewById(R.id.imgbtn_pyq);
        imgbtn_qq = (ImageButton) dialog.findViewById(R.id.imgbtn_qq);
        imgbtn_qqz = (ImageButton) dialog.findViewById(R.id.imgbtn_qqz);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        imgbtn_pyq.setVisibility(View.GONE);
        imgbtn_qqz.setVisibility(View.GONE);

        imgbtn_wx.setOnClickListener(clickListener);
        imgbtn_pyq.setOnClickListener(clickListener);
        imgbtn_qq.setOnClickListener(clickListener);
        imgbtn_qqz.setOnClickListener(clickListener);
        btn_cancel.setOnClickListener(clickListener);
    }

    //本地分享
    private boolean isUM = true;
    private String localFilePath = "";

    public void setIsUM(boolean isUMShare) {
        this.isUM = isUMShare;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public void share2QQ() {

        try {
            Intent qqIntent = new Intent(Intent.ACTION_SEND);
            qqIntent.setPackage("com.tencent.mobileqq");
            qqIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
            File file = new File(localFilePath);
            System.out.println("file " + file.exists());
            qqIntent.putExtra(Intent.EXTRA_STREAM, getImageContentUri(file));
            qqIntent.setType("*/*");
            startActivity(qqIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void share2QQ_Z() {
        try {
            Intent qqIntent = new Intent(Intent.ACTION_SEND);
            qqIntent.setPackage("com.tencent.mobileqq");
            ComponentName comp = new ComponentName("com.qzone", "com.qzonex.module.maxvideo.activity.QzonePublishVideoActivity");
            qqIntent.setComponent(comp);
            File file = new File(localFilePath);
            System.out.println("file " + file.exists());
            qqIntent.putExtra(Intent.EXTRA_STREAM, getImageContentUri(file));
            qqIntent.setType("*/*");
            startActivity(qqIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void share2WX() {
        try {
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            Intent shareIntent = new Intent();
            shareIntent.setComponent(comp);
            shareIntent.setAction(Intent.ACTION_SEND);
            File file = new File(localFilePath);
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageContentUri(file));
            shareIntent.setType("*/*");
            startActivity(Intent.createChooser(shareIntent, "分享"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void share2WX_pyq() {
        try {
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            Intent shareIntent = new Intent();
            shareIntent.setComponent(comp);
            shareIntent.setAction(Intent.ACTION_SEND);
            File file = new File(localFilePath);
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageContentUri(file));
            shareIntent.setType("*/*");
            startActivity(Intent.createChooser(shareIntent, "分享"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换 content:// uri
     *
     * @param imageFile
     * @return
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


}
