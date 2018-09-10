package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.ToastUtils;
import com.base.widget.BottomSheetAdapter;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonEntryViewModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.viewmodel.ImgUploadViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 图片上传
 * Created by Administrator on 2018/8/29.
 */

public class ImgUploadFragment extends BaseBookFragment {


    public static final int CODE_SELECT_COUNTY = 0x0012;
    @BindView(R.id.imgview)
    ImageView imgview;
    @BindView(R.id.ll_label)
    LineInputView llLabel;
    @BindView(R.id.llz)
    LineInputListLayout mLlRoot;

    @BindView(R.id.boxViewRemark)
    InputBoxView boxViewRemark;

    @BindView(R.id.tv_next_step)
    TextView tv_next_step;


    private SelectTypeViewModel mSelectTypeViewModel;
    private BreedPigeonEntryViewModel mBreedPigeonEntryViewModel;
    private ImgUploadViewModel mImgUploadViewModel;

    private ImgTypeEntity mImgTypeEntity;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, ImgUploadFragment.class);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mViewModel = new FootAddMultiViewModel(getBaseActivity());
        mSelectTypeViewModel = new SelectTypeViewModel();
        mBreedPigeonEntryViewModel = new BreedPigeonEntryViewModel();
        mImgUploadViewModel = new ImgUploadViewModel();
        initViewModels(mSelectTypeViewModel, mBreedPigeonEntryViewModel, mImgUploadViewModel);
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

        setTitle("上传图片");

        mImgTypeEntity = (ImgTypeEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_TYPE);

        composite.add(RxUtils.delayed(50, aLong -> {
            mLlRoot.setLineInputViewState(false);
        }));


        bindUi(RxUtils.textChanges(llLabel.getEditText()), mImgUploadViewModel.setImgLabel());//图片标签

        mSelectTypeViewModel.getSelectType_ImgType();
        initData();
    }

    private void initData() {
        Glide.with(getBaseActivity()).load(mImgTypeEntity.getImgPath()).into(imgview);
    }


    @Override
    protected void initObserve() {
        super.initObserve();


        mImgUploadViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tv_next_step, aBoolean);
        });


        mSelectTypeViewModel.mSelectType_ImgType.observe(this, datas -> {
            mBreedPigeonEntryViewModel.mSelectTypes_ImgType = datas;
        });

    }

    @OnClick({R.id.ll_label, R.id.tv_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_label:

                if (!Lists.isEmpty(mBreedPigeonEntryViewModel.mSelectTypes_ImgType)) {

                    if (mBreedPigeonEntryViewModel.mSelectTypes_ImgType.size() >= 6) {

                        PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_ImgType), 0, new OptionPicker.OnOptionPickListener() {
                            @Override
                            public void onOptionPicked(int index, String item) {
                                mBreedPigeonEntryViewModel.imgTypeId = mBreedPigeonEntryViewModel.mSelectTypes_ImgType.get(index).getTypeID();
                                mBreedPigeonEntryViewModel.imgTypeStr = mBreedPigeonEntryViewModel.mSelectTypes_ImgType.get(index).getTypeName();
                                llLabel.setContent(mBreedPigeonEntryViewModel.mSelectTypes_ImgType.get(index).getTypeName());
                                mBreedPigeonEntryViewModel.isCanCommit();
                            }
                        });

                    } else {
                        BottomSheetAdapter.createBottomSheet(getBaseActivity()
                                , SelectTypeEntity.getTypeNames(mBreedPigeonEntryViewModel.mSelectTypes_ImgType), p -> {
                                    mBreedPigeonEntryViewModel.imgTypeId = mBreedPigeonEntryViewModel.mSelectTypes_ImgType.get(p).getTypeID();
                                    mBreedPigeonEntryViewModel.imgTypeStr = mBreedPigeonEntryViewModel.mSelectTypes_ImgType.get(p).getTypeName();
                                    llLabel.setContent(mBreedPigeonEntryViewModel.mSelectTypes_ImgType.get(p).getTypeName());
                                    mBreedPigeonEntryViewModel.isCanCommit();
                                });
                    }
                }

                break;
            case R.id.tv_next_step:

                if (mBreedPigeonEntryViewModel.imgTypeId.isEmpty() || mBreedPigeonEntryViewModel.imgTypeId.length() == 0) {
                    ToastUtils.showLong(getBaseActivity(), "请选择标签后继续");
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra(IntentBuilder.KEY_TYPE, new ImgTypeEntity.Builder()
                        .imgTypeId(mBreedPigeonEntryViewModel.imgTypeId)
                        .imgType(mBreedPigeonEntryViewModel.imgTypeStr)
                        .imgPath(mImgTypeEntity.getImgPath())
                        .imgRemark(boxViewRemark.getText())
                        .build());
                getBaseActivity().setResult(Activity.RESULT_OK, intent);
                getBaseActivity().finish();

                break;
        }
    }
}
