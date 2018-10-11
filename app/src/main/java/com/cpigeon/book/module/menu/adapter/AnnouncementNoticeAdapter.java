package com.cpigeon.book.module.menu.adapter;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AnnouncementNoticeEntity;

import java.util.List;

/**
 * 公告通知适配器
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeAdapter extends BaseQuickAdapter<AnnouncementNoticeEntity, BaseViewHolder> {

    private WebView mWebView;
    private WebSettings mWebSettings;



    public AnnouncementNoticeAdapter(List<AnnouncementNoticeEntity> data) {
        super(R.layout.item_annoucenment_notice, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, AnnouncementNoticeEntity item) {

//        TextView tv_circle = helper.getView(R.id.tv_circle);
//        tv_circle.setVisibility(View.GONE);

        helper.setTextView(R.id.tv_title, item.getTitle());
        helper.setTextView(R.id.tv_time, item.getDate());
        helper.setTextView(R.id.tv_send_address, item.getSource());



//        TextView tv_title = helper.getView(R.id.tv_title);
//
//        if (item.getIsread().equals("1")) {
//            //消息已读
//            tv_circle.setVisibility(View.GONE);
//            tv_title.setTextColor(mContext.getResources().getColor(R.color.color_999999));
//        } else {
//            //消息未读
//            tv_circle.setVisibility(View.VISIBLE);
//            tv_title.setTextColor(mContext.getResources().getColor(R.color.color_010101));
//        }

        mWebView = ((WebView) helper.getView(R.id.it_webview));
        mWebView.setOnClickListener(null);
        mWebView.setOnLongClickListener(null);
        mWebView.setWebChromeClient(null);
        mWebView.setEnabled(true);
        mWebSettings = mWebView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setTextSize(WebSettings.TextSize.NORMAL);
        //  加载、并显示HTML代码
        mWebView.loadDataWithBaseURL(null, "<a onselectstart = \"return false\">" + item.getContent() + "</a>", "text/html", "utf-8", null);
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示

    }
}