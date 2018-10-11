package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.StringUtil;
import com.base.util.utility.ToastUtils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.ImgTypeEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 图片上传
 * Created by Administrator on 2018/8/29.
 */

public class ImgUploadFragment extends BaseImgUploadFragment {

    public static final int CODE_SELECT_COUNTY = 0x0012;


    @Override
    protected void initData() {
        super.initData();


    }

    @Override
    protected void initObserve() {
        super.initObserve();


        mSelectTypeViewModel.mSelectType_ImgType.observe(this, datas -> {
            mImgUploadViewModel.mSelectTypes_ImgType = datas;
            String type = mImgUploadViewModel.mImgTypeEntity.getImgTypeSpecified();
            if (StringUtil.isStringValid(type)) {
                llLabel.setCanEdit(false);
                llLabel.setRightImageVisible(false);
                for (SelectTypeEntity entity : datas) {
                    if (type.equals(entity.getTypeName())) {
                        mImgUploadViewModel.imgTypeId = entity.getTypeID();
                        mImgUploadViewModel.imgTypeStr = entity.getTypeName();
                        break;
                    }
                }
            }
        });
    }

    @OnClick({R.id.ll_label, R.id.tv_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_label:

                if (!Lists.isEmpty(mImgUploadViewModel.mSelectTypes_ImgType)) {

                    if (mImgUploadViewModel.mSelectTypes_ImgType.size() >= 6) {

                        PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mImgUploadViewModel.mSelectTypes_ImgType), 0, new OptionPicker.OnOptionPickListener() {
                            @Override
                            public void onOptionPicked(int index, String item) {
                                mImgUploadViewModel.imgTypeId = mImgUploadViewModel.mSelectTypes_ImgType.get(index).getTypeID();
                                mImgUploadViewModel.imgTypeStr = mImgUploadViewModel.mSelectTypes_ImgType.get(index).getTypeName();
                                llLabel.setContent(mImgUploadViewModel.mSelectTypes_ImgType.get(index).getTypeName());
                                mImgUploadViewModel.isCanCommit();
                            }
                        });

                    } else {
                        BottomSheetAdapter.createBottomSheet(getBaseActivity()
                                , SelectTypeEntity.getTypeNames(mImgUploadViewModel.mSelectTypes_ImgType), p -> {
                                    mImgUploadViewModel.imgTypeId = mImgUploadViewModel.mSelectTypes_ImgType.get(p).getTypeID();
                                    mImgUploadViewModel.imgTypeStr = mImgUploadViewModel.mSelectTypes_ImgType.get(p).getTypeName();
                                    llLabel.setContent(mImgUploadViewModel.mSelectTypes_ImgType.get(p).getTypeName());
                                    mImgUploadViewModel.isCanCommit();
                                });
                    }
                } else {
                    mSelectTypeViewModel.getSelectType_ImgType();
                }

                break;
            case R.id.tv_next_step:

                if (mImgUploadViewModel.imgTypeId.isEmpty() || mImgUploadViewModel.imgTypeId.length() == 0) {
                    ToastUtils.showLong(getBaseActivity(), "请选择标签后继续");
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra(IntentBuilder.KEY_DATA, new ImgTypeEntity.Builder()
                        .imgTypeId(mImgUploadViewModel.imgTypeId)
                        .imgType(mImgUploadViewModel.imgTypeStr)
                        .imgPath(mImgUploadViewModel.mImgTypeEntity.getImgPath())
                        .imgRemark(boxViewRemark.getText())
                        .build());
                getBaseActivity().setResult(Activity.RESULT_OK, intent);
                getBaseActivity().finish();

                break;
        }
    }
}
