package com.cpigeon.book.widget.mydialog;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.base.util.RxUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpigeon.book.R;

/**
 * Created by Administrator on 2018/8/23.
 */

public class HintDialog {


    //点击拍照  拍摄视频
    public static CustomAlertDialog2 shootHintDialog(Activity mContext, String hintStr) {

        try {
            CustomAlertDialog2 dialog = new CustomAlertDialog2(mContext);

            LinearLayout dialogLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_hint, null);
            dialogLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            TextView tv_hint = dialogLayout.findViewById(R.id.tv_hint);
            tv_hint.setText(hintStr);

            dialog.setContentView(dialogLayout);

            //调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            RxUtils.delayed(2000, aLong -> {
                dialog.dismiss();
            });
//            toggleInput(mContext);
            return dialog;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //导入赛绩动画
    public static CustomAlertDialog2 shootHintInputPlayDialog(Activity mContext, ProgressBar progressBar) {

        try {
            CustomAlertDialog2 dialog = new CustomAlertDialog2(mContext);

            LinearLayout dialogLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_input_play, null);
            dialogLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            ImageView img_gif = dialogLayout.findViewById(R.id.img_gif);
            //input_play_gif
            Glide.with(mContext).load(R.drawable.input_play_gif).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_gif);
            dialog.setContentView(dialogLayout);
            //调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            return dialog;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
