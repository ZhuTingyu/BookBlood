package com.base.util.dialog;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.base.http.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */


public class DialogUtils {

    /**
     * 设置有取消按钮的弹框 (取消按钮监听是隐藏弹框)
     *
     * @param context
     * @param content
     * @param rightListener
     */

    public static void createDialogWithLeft(Context context, String content,
                                            SweetAlertDialog.OnSweetClickListener rightListener) {
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setCanceledOnTouchOutside(false);
        dialogPrompt.setCancelable(false);
        dialogPrompt.setTitleText("提示")
                .setCancelText(context.getString(R.string.btn_cancel))
                .setCancelClickListener(sweetAlertDialog -> {
                    dialogPrompt.dismissWithAnimation();
                })
                .setConfirmClickListener(rightListener)
                .setContentText(content)
                .setConfirmText("确定").show();
    }

    /**
     * 设置有取消按钮的弹框（自定义取消按钮弹框）
     *
     * @param context
     * @param content
     * @param leftListener
     * @param rightListener
     */

    public static void createDialogWithLeft(Context context, String content,
                                            SweetAlertDialog.OnSweetClickListener leftListener,
                                            SweetAlertDialog.OnSweetClickListener rightListener) {
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setCanceledOnTouchOutside(false);
        dialogPrompt.setCancelable(false);
        dialogPrompt.setTitleText("提示")
                .setCancelText("取消")
                .setCancelClickListener(leftListener)
                .setConfirmClickListener(rightListener)
                .setContentText(content)
                .setConfirmText("确定").show();
    }

    /**
     * 设置有取消按钮的弹框（自定义取消按钮弹框）
     *
     * @param context
     * @param content
     * @param leftListener
     * @param rightListener
     */

    public static SweetAlertDialog createDialogWithLeft2(Context context, String content, boolean isCancelable,
                                                         SweetAlertDialog.OnSweetClickListener leftListener,
                                                         SweetAlertDialog.OnSweetClickListener rightListener) {
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setCanceledOnTouchOutside(false);
        dialogPrompt.setCancelable(isCancelable);
        dialogPrompt.setTitleText("提示")
                .setCancelText("取消")
                .setCancelClickListener(leftListener)
                .setConfirmClickListener(rightListener)
                .setContentText(content)
                .setConfirmText("确定").show();

        return dialogPrompt;
    }


    /**
     * 显示错误弹框
     *
     * @param context
     * @param error
     * @return
     */

    public static SweetAlertDialog createErrorDialog(Context context, String error) {
        try {
            SweetAlertDialog dialogPrompt;
            dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            dialogPrompt.setTitleText("失败");
            dialogPrompt.setContentText(error);
            dialogPrompt.show();
            return dialogPrompt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 显示成功或错误弹框
     *
     * @param context
     * @param message
     * @return
     */
    public static SweetAlertDialog createHintDialog(Context context, String message, int alerType, boolean cancelable, SweetAlertDialog.OnSweetClickListener mConfirmClick) {
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, alerType);


        if (alerType == SweetAlertDialog.SUCCESS_TYPE) {
            dialogPrompt.setTitleText("成功");
        } else if (alerType == SweetAlertDialog.ERROR_TYPE) {
            dialogPrompt.setTitleText("失败");
        }

        dialogPrompt.setContentText(message);
        dialogPrompt.setConfirmClickListener(mConfirmClick);
        dialogPrompt.setCancelable(cancelable);
        dialogPrompt.show();
        return dialogPrompt;
    }

    /**
     * 显示成功弹框
     *
     * @param context
     * @param message
     * @return
     */

    public static SweetAlertDialog createSuccessDialog(Context context, String message, SweetAlertDialog.OnSweetClickListener mConfirmClick) {
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context);
        dialogPrompt.setTitleText("成功");
        dialogPrompt.setContentText(message);
        dialogPrompt.setConfirmClickListener(mConfirmClick);
        dialogPrompt.setCancelable(false);
        dialogPrompt.show();
        return dialogPrompt;
    }

    public static void createDialog(Context context, String title, String content
            , @Nullable String left, String right, @Nullable SweetAlertDialog.OnSweetClickListener leftListener, @Nullable SweetAlertDialog.OnSweetClickListener rightListener) {

        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setTitleText(title);
        dialogPrompt.setCanceledOnTouchOutside(false);
        if (left != null) {
            dialogPrompt.setCancelText(left);
        }
        dialogPrompt.setCancelClickListener(leftListener);
        if(rightListener == null){
            dialogPrompt.setConfirmClickListener(sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
            });
        }else {
            dialogPrompt.setConfirmClickListener(rightListener);
        }
        dialogPrompt.setContentText(content);
        dialogPrompt.setConfirmText(right);
        dialogPrompt.show();

    }


    public static SweetAlertDialog createDialogReturn(Context context, String content
            , @Nullable SweetAlertDialog.OnSweetClickListener leftListener, @Nullable SweetAlertDialog.OnSweetClickListener rightListener) {

        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setTitleText(context.getString(R.string.string_text_hint));
        dialogPrompt.setCancelText(context.getString(com.base.http.R.string.btn_cancel));
        dialogPrompt.setCancelClickListener(rightListener);
        dialogPrompt.setConfirmClickListener(leftListener);
        dialogPrompt.setContentText(content);
        dialogPrompt.setConfirmText(context.getString(com.base.http.R.string.btn_confirm));
        dialogPrompt.show();


        return dialogPrompt;
    }


    public static void createNotCancelableDialog(Context context, @StringRes int message, @Nullable SweetAlertDialog.OnSweetClickListener leftListener, @Nullable SweetAlertDialog.OnSweetClickListener rightListener) {
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setTitleText(context.getString(R.string.string_text_hint));
        dialogPrompt.setCancelText(context.getString(R.string.btn_cancel));
        dialogPrompt.setCancelClickListener(leftListener);
        dialogPrompt.setConfirmClickListener(rightListener);
        dialogPrompt.setContentText(context.getString(message));
        dialogPrompt.setConfirmText(context.getString(R.string.btn_confirm));
        dialogPrompt.show();
    }

    /**
     * 提示弹出框
     *
     * @param context
     * @param content
     */
    public static void createHintDialog(Context context, String content) {

        createDialog(context, context.getString(R.string.string_text_hint), content
                , null, context.getString(R.string.btn_confirm), null, null);

    }

    /**
     * 提示弹出框
     *
     * @param context
     * @param content
     */

    public static void createHintDialog(Context context, String content, SweetAlertDialog.OnSweetClickListener rightClickListener) {

        createDialog(context, context.getString(R.string.string_text_hint), content
                , null, context.getString(R.string.btn_confirm), null, rightClickListener);

    }

    /**
     * 编辑弹出框，返回输入框内容
     *
     * @param context
     * @param resId
     * @param titleString
     * @param contentString
     * @param listener
     */

    public static void createEditMessageDialog(Context context, @LayoutRes int resId, String titleString, String contentString, OnEditDialogListener listener) {
        AlertDialog dialogPrompt = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(resId, null);

        AppCompatEditText content = view.findViewById(R.id.content);
        TextView title = view.findViewById(R.id.title);

        title.setText(titleString);

        content.setText(contentString);

        TextView btnLeft = view.findViewById(R.id.btn_left);
        TextView btnRight = view.findViewById(R.id.btn_right);

        content.setText(contentString);
        content.setSelection(contentString.length());

        btnLeft.setOnClickListener(view1 -> {
            dialogPrompt.dismiss();
        });

        btnRight.setOnClickListener(v -> {
            listener.click(content.getText().toString(), dialogPrompt);
        });

        dialogPrompt.setView(view);
        dialogPrompt.show();
    }
}
