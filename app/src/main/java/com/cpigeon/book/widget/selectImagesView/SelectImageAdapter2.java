package com.cpigeon.book.widget.selectImagesView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.base.BaseActivity;
import com.base.base.BaseViewHolder;
import com.base.util.Lists;
import com.base.util.glide.GlideUtil;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.ImgTypeEntity;

import java.io.File;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/22.
 */

public class SelectImageAdapter2 extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int MAX_NUMBER = 1;
    public static final int MAX_NUMBER_LINE = 6;
    protected final static int TYPE_ADD = 1;

    BaseActivity mBaseActivity;
    List<ImgTypeEntity> mImgData = Lists.newArrayList();

    int rootW;

    public SelectImageAdapter2(BaseActivity activity) {
        mBaseActivity = activity;
        rootW = ScreenTool.getScreenWidth() / MAX_NUMBER_LINE;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD) {
            View view = LayoutInflater.from(mBaseActivity).inflate(R.layout.item_selecte_image_add_image, parent, false);
            return new AddViewHolder(view);
        } else {
            View view = LayoutInflater.from(mBaseActivity).inflate(R.layout.item_selecte_image_show2, parent, false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ADD) {
            AddViewHolder viewHolder = (AddViewHolder) holder;
        } else {
            ImageViewHolder viewHolder = (ImageViewHolder) holder;
            viewHolder.bindData(getImagePosition(position));
        }
    }

    private int getImagePosition(int position) {
        int imagePosition = position;
//        int imagePosition = position - 1;


        if (mImgData.size() == 1) {
            imagePosition = 0;
        } else {
            imagePosition -= 1;
        }

        return imagePosition;
    }

    @Override
    public int getItemCount() {
//        return mImgData.size() + 1;
        if (mImgData.size() < MAX_NUMBER) {
            return mImgData.size() + 1;
        } else {
            return mImgData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {

//        if (position == 0) {
//            return TYPE_ADD;
//        } else {
//            return super.getItemViewType(position);
//        }

        if (mImgData.size() == 0) {
            return TYPE_ADD;
        } else {
            return super.getItemViewType(position);
        }
    }

    protected class ImageViewHolder extends BaseViewHolder {
        RelativeLayout rlRoot;
        public ImageView icon;
        public ImageView del;
        public TextView tv_img_type;

        public ImageViewHolder(View itemView) {
            super(itemView);
            rlRoot = itemView.findViewById(R.id.rlRoot);
            icon = itemView.findViewById(R.id.img);
            icon.setVisibility(View.VISIBLE);
            del = itemView.findViewById(R.id.imgDel);
            tv_img_type = itemView.findViewById(R.id.tv_img_type);

            RecyclerView.LayoutParams rootParams = new RecyclerView.LayoutParams(rootW, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlRoot.setLayoutParams(rootParams);
        }

        void bindData(int position) {

            GlideUtil.setGlideImageViewHaveRound(mBaseActivity, mImgData.get(position).getImgPath(), icon, 5);

            tv_img_type.setText(mImgData.get(position).getImgType());

            del.setVisibility(View.VISIBLE);
            del.setOnClickListener(v -> {
                mImgData.remove(position);
                notifyDataSetChanged();
                if (mOnSelectImageClickListener != null) {
                    mOnSelectImageClickListener.onImageDelete(position);
                }
            });
        }
    }

    class AddViewHolder extends BaseViewHolder {
        RelativeLayout rlRoot;
        ImageView addBtn;

        public AddViewHolder(View itemView) {
            super(itemView);

            rlRoot = itemView.findViewById(R.id.rlRoot);
            addBtn = itemView.findViewById(R.id.imgAdd);

            addBtn.setImageResource(R.mipmap.ic_add);

            RecyclerView.LayoutParams rootParams = new RecyclerView.LayoutParams(rootW, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlRoot.setLayoutParams(rootParams);
            bindData();

        }

        public void bindData() {
            addBtn.setOnClickListener(view -> {
                if (mOnSelectImageClickListener != null) {
                    mOnSelectImageClickListener.onAddImage();
                }
            });
        }
    }

    public void addImage(List<ImgTypeEntity> urls) {
        mImgData.addAll(0, urls);
        notifyDataSetChanged();
    }

    public List<ImgTypeEntity> getImgData() {
        return mImgData;
    }

    public interface OnSelectImageClickListener {
        void onAddImage();

        void onImageDelete(int position);
    }

    private OnSelectImageClickListener mOnSelectImageClickListener;

    public void setOnSelectImageClickListener(OnSelectImageClickListener onSelectImageClickListener) {
        mOnSelectImageClickListener = onSelectImageClickListener;
    }
}
