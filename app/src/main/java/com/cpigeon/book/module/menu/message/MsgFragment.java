package com.cpigeon.book.module.menu.message;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.NoticeMsgInfoEntity;
import com.cpigeon.book.module.menu.viewmodel.AnnouncementNoticeViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class MsgFragment extends BaseBookFragment {

    @BindView(R.id.img_notice)
    ImageView imgNotice;
    @BindView(R.id.tv_time_notice)
    TextView tvTimeNotice;
    @BindView(R.id.tv_notice_content)
    TextView tvNoticeContent;
    @BindView(R.id.img_msg)
    ImageView imgMsg;
    @BindView(R.id.tv_time_msg)
    TextView tvTimeMsg;
    @BindView(R.id.tv_msg_content)
    TextView tvMsgContent;
    private AnnouncementNoticeViewModel mAnnouncementNoticeViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, MsgFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAnnouncementNoticeViewModel = new AnnouncementNoticeViewModel();
        initViewModels(mAnnouncementNoticeViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(getString(R.string.str_msg));
        setProgressVisible(true);
        mAnnouncementNoticeViewModel.getTXGP_GetMSGInfoData();

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mAnnouncementNoticeViewModel.mNoticeMsgInfoData.observe(this, datas -> {
            setProgressVisible(false);

            NoticeMsgInfoEntity noticeInfo = datas.get(0);
            if (noticeInfo.getState().equals("0")) {
                //有未读消息
                imgNotice.setVisibility(View.VISIBLE);
            } else {
                imgNotice.setVisibility(View.GONE);
            }

            tvTimeNotice.setText(noticeInfo.getDatetime());
            tvNoticeContent.setText(noticeInfo.getTitle());

            NoticeMsgInfoEntity msgInfo = datas.get(1);

            if (msgInfo.getState().equals("0")) {
                //有未读消息
                imgMsg.setVisibility(View.VISIBLE);
            } else {
                imgMsg.setVisibility(View.GONE);
            }

            tvTimeMsg.setText(msgInfo.getDatetime());
            tvMsgContent.setText(msgInfo.getTitle());

        });
    }

    @OnClick({R.id.ll_notice, R.id.ll_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_notice:
                //公告通知
                AnnouncementNoticeFragment.start(getBaseActivity());
                break;
            case R.id.ll_msg:
                //鸽友消息
                PigeonFriendMsgFragment.start(getBaseActivity());
                break;
        }
    }
}
