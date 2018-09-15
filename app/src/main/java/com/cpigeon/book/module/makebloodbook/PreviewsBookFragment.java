package com.cpigeon.book.module.makebloodbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.cpigeon.book.widget.BookRootLayout;
import com.cpigeon.book.widget.family.FamilyTreeView;

/**
 * Created by Zhu TingYu on 2018/9/10.
 */

public class PreviewsBookFragment extends BaseBookFragment {

    private static final int CODE_CHOOSE_TEMPLATE = 0x123;

    private LinearLayout mLlImage;
    private ImageView mImgHead;
    private FamilyTreeView mFamilyTreeView;
    private FamilyTreeView mPrintFamilyTreeView;
    private LinearLayout mLlTextV;
    private RelativeLayout mLlTextH;
    private CheckBox mCheckbox;
    private TextView mTvOk;
    private PhotoView mImageView;

    private int bookType = SelectTemplateFragment.TYPE_H;

    private ImageView mImgPrintHead;
    private TextView mTvPrintNumber;
    private RelativeLayout mLlPrintTextV;
    private LinearLayout mLlPrintTextH;



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
            SelectTemplateFragment.start(getBaseActivity(), bookType ,CODE_CHOOSE_TEMPLATE);
            return false;
        });

        mLlImage = findViewById(R.id.llImage);
        mImgHead = findViewById(R.id.imgHead);
        mFamilyTreeView = findViewById(R.id.familyTreeView);
        mPrintFamilyTreeView = findViewById(R.id.printFamilyTreeView);
        mLlTextV = findViewById(R.id.llTextV);
        mLlTextH = findViewById(R.id.llTextH);
        mCheckbox = findViewById(R.id.checkbox);
        mTvOk = findViewById(R.id.tvOk);
        mImageView = findViewById(R.id.img);


        mImgPrintHead = findViewById(R.id.imgPrintHead);
        mTvPrintNumber = findViewById(R.id.tvPrintNumber);
        mLlPrintTextV = findViewById(R.id.llPrintTextV);
        mLlPrintTextH = findViewById(R.id.llPrintTextH);

        mCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mLlImage.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            if (isChecked){
                mImgPrintHead.setVisibility(View.VISIBLE);
            }else {
                mImgPrintHead.setVisibility(View.GONE);
            }
        });

        mTvOk.setOnClickListener(v -> {
            getBookView();
        });

        mImageView.setOnViewTapListener((view1, x, y) -> {
            mImageView.setVisibility(View.GONE);
        });

        mCheckbox.setChecked(true);
    }

    public void getBookView() {
        try {
            RelativeLayout view = findViewById(R.id.rlImage);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;

        if(requestCode == CODE_CHOOSE_TEMPLATE){
            int type = data.getIntExtra(IntentBuilder.KEY_DATA, bookType);
            bookType = type;

            if(bookType == SelectTemplateFragment.TYPE_H){
                mFamilyTreeView.setHorizontal(true);
                mFamilyTreeView.setTypeMove(FamilyTreeView.TYPE_IS_CAN_MOVE_H);
                mFamilyTreeView.initView();

                mPrintFamilyTreeView.setHorizontal(true);
                mPrintFamilyTreeView.setTypeMove(FamilyTreeView.TYPE_IS_CAN_MOVE_H);
                mPrintFamilyTreeView.setShowLine(false);
                mPrintFamilyTreeView.initView();

                mLlPrintTextH.setVisibility(View.VISIBLE);
                mLlPrintTextV.setVisibility(View.GONE);

            }else if(bookType == SelectTemplateFragment.TYPE_V){
                mFamilyTreeView.setHorizontal(false);
                mFamilyTreeView.setTypeMove(FamilyTreeView.TYPE_IS_CAN_MOVE_V);
                mFamilyTreeView.initView();

                mPrintFamilyTreeView.setHorizontal(false);
                mPrintFamilyTreeView.setTypeMove(FamilyTreeView.TYPE_IS_CAN_MOVE_V);
                mPrintFamilyTreeView.setShowLine(true);
                mPrintFamilyTreeView.initView();

                mLlPrintTextH.setVisibility(View.GONE);
                mLlPrintTextV.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void finish() {
        if(mImageView.getVisibility() == View.VISIBLE){
            mImageView.setVisibility(View.GONE);
        }else {
            super.finish();
        }
    }
}