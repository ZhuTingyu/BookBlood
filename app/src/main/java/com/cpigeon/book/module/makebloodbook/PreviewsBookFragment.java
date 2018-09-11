package com.cpigeon.book.module.makebloodbook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.RxUtils;
import com.base.util.utility.ImageUtils;
import com.base.widget.photoview.PhotoView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.widget.family.FamilyTreeView;

/**
 * Created by Zhu TingYu on 2018/9/10.
 */

public class PreviewsBookFragment extends BaseBookFragment {

    private LinearLayout mLlImage;
    private ImageView mImgHead;
    private FamilyTreeView mFamilyTreeView;
    private LinearLayout mLlTextV;
    private RelativeLayout mLlTextH;
    private CheckBox mCheckbox;
    private TextView mTvOk;
    private PhotoView mImageView;

    public static void start(Activity activity, String footNumber) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, footNumber)
                .startParentActivity(activity, PreviewsBookFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview_book, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setToolbarRight(R.string.text_choose_template, item -> {
            mFamilyTreeView.setHorizontal(false);
            mFamilyTreeView.setTypeMove(FamilyTreeView.TYPE_IS_CAN_MOVE_V);
            mFamilyTreeView.initView();
            return false;
        });

        mLlImage = findViewById(R.id.llImage);
        mImgHead = findViewById(R.id.imgHead);
        mFamilyTreeView = findViewById(R.id.familyTreeView);
        mLlTextV = findViewById(R.id.llTextV);
        mLlTextH = findViewById(R.id.llTextH);
        mCheckbox = findViewById(R.id.checkbox);
        mTvOk = findViewById(R.id.tvOk);
        mImageView = findViewById(R.id.img);

        mCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mLlImage.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        mTvOk.setOnClickListener(v -> {
            getBookView();
        });

        mCheckbox.setChecked(true);
    }

    public void getBookView() {
        try {
            View view = findViewById(R.id.rlImage);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(2480, 3508);
            view.setLayoutParams(layoutParams);
            composite.add(RxUtils.delayed(100, aLong -> {
                Bitmap bitmap = ImageUtils.view2Bitmap(view);
                ImageUtils.saveImageToGallery(getBaseActivity(), bitmap);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageBitmap(bitmap);
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
