package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.FeedbackUpdateEvent;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.menu.viewmodel.FeedBackAddViewModel;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;
import com.cpigeon.book.widget.selectImagesView.SelectImageAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 添加意见反馈
 * Created by Administrator on 2018/8/18.
 */

public class FeedbackAddFragment extends BaseBookFragment {


    private LineInputListLayout mLlRoot;
    private LineInputView mLvPhone;
    private InputBoxView mIbContent;
    private TextView mTvOk;
    private RecyclerView list;


    private FeedBackAddViewModel mViewModel;
    SelectImageAdapter mAdapter;


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, FeedbackAddFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new FeedBackAddViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_add, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_feed_back);
        list = findViewById(R.id.list);
        mLlRoot = findViewById(R.id.llRoot);
        mLvPhone = findViewById(R.id.lvPhone);
        mIbContent = findViewById(R.id.ibContent);
        mTvOk = findViewById(R.id.tvOk);
        mLvPhone.setRightText(UserModel.getInstance().getUserData().handphone);

        bindUi(RxUtils.textChanges(mIbContent.getEditText()), mViewModel.setContent());//反馈内容 q
        bindUi(RxUtils.textChanges(mLvPhone.getEditText()), mViewModel.setPhone());//反馈内容 q
        list.setLayoutManager(new GridLayoutManager(getBaseActivity(), 6));
        mAdapter = new SelectImageAdapter(getBaseActivity());
        list.setAdapter(mAdapter);
        mAdapter.setOnSelectImageClickListener(new SelectImageAdapter.OnSelectImageClickListener() {
            @Override
            public void onAddImage() {
                String[] chooseWays = getResources().getStringArray(R.array.array_choose_photo);
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                    String way = chooseWays[p];
                    if (Utils.getString(R.string.text_open_gallery).equals(way)) {
                        PictureSelectUtil.showChooseImage(getBaseActivity(), PictureMimeType.ofImage(), SelectImageAdapter.MAX_NUMBER - mViewModel.images.size());
                    } else if (Utils.getString(R.string.text_open_camera).equals(way)) {
                        PictureSelectUtil.openCamera(getBaseActivity(), false);
                    }
                });
            }

            @Override
            public void onImageDelete(int position) {
                mViewModel.images.remove(position);
            }
        });

        mTvOk.setOnClickListener(v -> {
            setProgressVisible(true);
            mViewModel.addFadeBack();
        });
    }

    @Override
    protected void initObserve() {
        mViewModel.normalResult.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new FeedbackUpdateEvent());
                finish();
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == PictureMimeType.ofImage()) {
            List<String> imgs = Lists.newArrayList();
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            for (int i = 0; i < selectList.size(); i++) {
                imgs.add(0, selectList.get(i).getCompressPath());
            }
            mAdapter.addImage(imgs);
            mViewModel.images.addAll(imgs);
        }
    }
}
