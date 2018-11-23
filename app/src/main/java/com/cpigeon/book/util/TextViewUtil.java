package com.cpigeon.book.util;

import android.graphics.Color;
import android.widget.TextView;

import com.base.base.BaseActivity;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.widget.LineInputView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/9.
 */

public class TextViewUtil {
    public static void DialogShowNullMsg(BaseActivity activity, LineInputView... lineInputViews) {
        List<LineInputView> list = Lists.newArrayList(lineInputViews);
        for (LineInputView s : list) {
            if (!StringUtil.isStringValid(s.getContent())) {
                StringBuffer Msg = new StringBuffer();
                Msg.append("请输入");
                Msg.append(s.getTitle()); Msg.append("!");
                DialogUtils.createHintDialog(activity, Msg.toString());
                return;
            }
        }
    }
    public static void setEnabled(TextView textView, boolean isEnabled) {
        textView.setEnabled(isEnabled);
        if (isEnabled) {
            textView.setTextColor(Color.WHITE);
        } else {
            textView.setTextColor(Utils.getColor(R.color.color_text_can_click));
        }
    }

    public static void setCancle(TextView textView) {
        textView.setBackgroundResource(R.drawable.selector_bg_cancel_btn);
    }
}
