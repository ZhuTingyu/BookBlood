package com.cpigeon.book.module.photo;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.StringUtil;
import com.base.util.utility.ToastUtils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.util.BitmapUtils;

import java.io.File;

import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 图片上传上传
 * Created by Administrator on 2018/9/19 0019.
 */

public class SnapshotImgUploadFragment extends BaseImgUploadFragment {


    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mSelectTypeViewModel.mSelectType_ImgType.observe(this, datas -> {
            try {
                setProgressVisible(false);

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
                } else {
                    mImgUploadViewModel.imgTypeId = mImgUploadViewModel.mSelectTypes_ImgType.get(0).getTypeID();
                    mImgUploadViewModel.imgTypeStr = mImgUploadViewModel.mSelectTypes_ImgType.get(0).getTypeName();
                }

                llLabel.setContent(mImgUploadViewModel.imgTypeStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @OnClick({R.id.ll_label, R.id.tv_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_label:

                if (StringUtil.isStringValid(mImgUploadViewModel.mImgTypeEntity.getImgTypeSpecified())) {
                    return;
                }

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
                    mSelectTypeViewModel.getSelectType_ImgType();
                    return;
                }

                setProgressVisible(true);
                //设置图片水印
                Bitmap mBitmap = BitmapFactory.decodeFile(mImgUploadViewModel.mImgTypeEntity.getImgPath());
                String savePath = getBaseActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + File.separator + System.currentTimeMillis() + ".jpeg";

                ImageView mImageView = new ImageView(getBaseActivity());
                mImageView.setImageResource(R.mipmap.watermark);

                Bitmap wBitmap = BitmapUtils.createBitmapCenter(mBitmap, BitmapUtils.convertViewToBitmap(mImageView));
                BitmapUtils.saveJPGE_After(getBaseActivity(), wBitmap, savePath, 100);

                mImgUploadViewModel.mImgTypeEntity.setImgPath(savePath);

                RxUtils.delayed(1000, aLong -> {
                    mImgUploadViewModel.getTXGP_PigeonPhoto_AddData();
                });

                break;
        }
    }
}
