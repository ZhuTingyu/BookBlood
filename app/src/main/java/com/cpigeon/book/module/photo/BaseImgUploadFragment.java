package com.cpigeon.book.module.photo;

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
import com.base.util.RxUtils;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.viewmodel.ImgUploadViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/9/19 0019.
 */

public class BaseImgUploadFragment extends BaseBookFragment {


    @BindView(R.id.imgview)
    protected ImageView imgview;
    @BindView(R.id.ll_label)
    protected LineInputView llLabel;
    @BindView(R.id.llz)
    protected LineInputListLayout mLlRoot;

    @BindView(R.id.boxViewRemark)
    protected InputBoxView boxViewRemark;

    @BindView(R.id.tv_next_step)
    protected TextView tv_next_step;

    protected SelectTypeViewModel mSelectTypeViewModel;
    protected ImgUploadViewModel mImgUploadViewModel;


    public static void start(Activity activity, Class mClasee, ImgTypeEntity mImgTypeEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mImgTypeEntity)
                .startParentActivity(activity, mClasee);
    }


    public static void start(Activity activity, Class mClasee, ImgTypeEntity mImgTypeEntity, int code) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mImgTypeEntity)
                .startParentActivity(activity, mClasee, code);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSelectTypeViewModel = new SelectTypeViewModel();
        mImgUploadViewModel = new ImgUploadViewModel();
        initViewModels(mSelectTypeViewModel, mImgUploadViewModel);

       mImgUploadViewModel. mImgTypeEntity = (ImgTypeEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_img, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(getString(R.string.str_img_upload));


        composite.add(RxUtils.delayed(50, aLong -> {
            mLlRoot.setLineInputViewState(false);
        }));

        bindUi(RxUtils.textChanges(llLabel.getEditText()), mImgUploadViewModel.setImgLabel());//图片标签

        mSelectTypeViewModel.getSelectType_ImgType();
        initData();
    }

    protected void initData() {
        try {
            Glide.with(getBaseActivity()).load(mImgUploadViewModel.mImgTypeEntity.getImgPath()).into(imgview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mImgUploadViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tv_next_step, aBoolean);
        });
    }
}
