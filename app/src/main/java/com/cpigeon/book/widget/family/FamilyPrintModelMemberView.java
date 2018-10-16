package com.cpigeon.book.widget.family;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.glide.GlideUtil;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonPlayEntity;
import com.cpigeon.book.widget.ShadowRelativeLayout;

/**
 * Created by Zhu TingYu on 2018/6/21.
 */

public class FamilyPrintModelMemberView extends FamilyMember {

    ShadowRelativeLayout rlShadow;
    int generationPoint;
    int generationsOrder;
    int rootW;
    int rootH;
    int shadowColor;
    boolean isHorizontal;
    private PigeonEntity mPigeonEntity;


    private ShadowRelativeLayout mRlShadow;
    private NestedScrollView mScrollViewInfo;
    private LinearLayout mRlInMemberInfo;
    private ImageView mImgHead;
    private TextView mTvFootNumber;
    private TextView mTvBlood;
    private TextView mTvColor;
    private RecyclerView mList;
    private LinearLayout mLlInfo;

    public FamilyPrintModelMemberView(Context context, int generationPoint, int generationsOrder, boolean isHorizontal) {
        super(context);
        this.generationPoint = generationPoint;
        this.generationsOrder = generationsOrder;
        this.isHorizontal = isHorizontal;
        initView(context);
    }

    public FamilyPrintModelMemberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FamilyPrintModelMemberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view;
        if (isHorizontal) {
            view = LayoutInflater.from(context).inflate(R.layout.view_family_print_model_member_layout, this);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.view_family_print_model_v_member_layout, this);
            mLlInfo = view.findViewById(R.id.llInfo);
        }

        mRlShadow = view.findViewById(R.id.rlShadow);
        mScrollViewInfo = view.findViewById(R.id.scrollViewInfo);
        mRlInMemberInfo = view.findViewById(R.id.rlInMemberInfo);
        mImgHead = view.findViewById(R.id.imgHead);
        mTvFootNumber = view.findViewById(R.id.tvFootNumber);
        mTvBlood = view.findViewById(R.id.tvBlood);
        mTvColor = view.findViewById(R.id.tvColor);
        mList = view.findViewById(R.id.list);
        rlShadow = findViewById(R.id.rlShadow);

        /*if (generationPoint == 0) {
            mScrollViewInfo.setBackgroundResource(R.drawable.shape_bg_family_member_black);
        } else {
            if (generationsOrder % 2 == 0) {
                mScrollViewInfo.setBackgroundResource(R.drawable.shape_bg_family_member_blue);
            } else {
                mScrollViewInfo.setBackgroundResource(R.drawable.shape_bg_family_member_r ed);
            }
        }*/

        int size_206 = 412;
        int size_155 = 310;
        int size_226 = 452;
        int size_265 = 530;
        int size_400 = 800;
        int size_500 = 1000;
        int size_366 = 732;
        int size_178 = 356;
        int size_128 = 256;
        int shadowSize = 15;

        int imgW = 0;
        int imgH = 0;
        shadowColor = R.color.color_text_hint;
        if (generationPoint == 0) {
            if (isHorizontal) {
                rootW = size_226;
                rootH = size_500;
            } else {
                rootW = size_500;
                rootH = size_226;
                imgW = size_206;
                imgH = size_206;
            }
            shadowColor = R.color.color_text_hint;
        } else if (generationPoint == 1) {
            if (isHorizontal) {
                rootW = size_226;
                rootH = size_500;
            } else {
                rootW = size_400;
                rootH = size_226;
                imgW = size_155;
                imgH = size_206;
            }
            getShadowColor();
        } else if (generationPoint == 2) {
            if (isHorizontal) {
                rootW = size_226;
                rootH = size_366;
            } else {
                rootW = size_265;
                rootH = size_226;
            }
            getShadowColor();
        } else if (generationPoint == 3) {
            if (isHorizontal) {
                rootW = size_226;
                rootH = size_178;
            } else {
                rootW = size_128;
                rootH = size_226;
            }
            getShadowColor();
        }
        rlShadow.addShadow(shadowColor, shadowSize);

        LayoutParams shadowP;
        RelativeLayout.LayoutParams infoP;
        infoP = new RelativeLayout.LayoutParams(rootW, rootH);
        shadowP = new LayoutParams(rootW + ScreenTool.dip2px(shadowSize)
                , rootH + ScreenTool.dip2px(shadowSize));

        if(isHorizontal){
            mScrollViewInfo.setLayoutParams(infoP);
            rlShadow.setLayoutParams(shadowP);
        }else {
            LinearLayout.LayoutParams imgP = new LinearLayout.LayoutParams(imgW, imgH);
            mImgHead.setLayoutParams(imgP);
            rlShadow.setLayoutParams(shadowP);
            mLlInfo.setLayoutParams(infoP);
        }

        if (generationPoint == 0) {

        } else if (generationPoint == 1) {

        } else if (generationPoint == 2) {
            mImgHead.setVisibility(GONE);
        } else if (generationPoint == 3) {
            mImgHead.setVisibility(GONE);
            mTvBlood.setVisibility(GONE);
            mTvColor.setVisibility(GONE);
        }

    }

    public void bindData(PigeonEntity entity) {
        if(!StringUtil.isStringValid(entity.getPigeonID())){
            return;
        }
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        MatchAdapter matchAdapter = new MatchAdapter();
        mList.setAdapter(matchAdapter);
        matchAdapter.setNewData(entity.getMatchData());

        mTvFootNumber.setText(entity.getFootRingNum());
        mTvBlood.setText(entity.getPigeonBloodName());
        mTvColor.setText(entity.getPigeonPlumeName());
        GlideUtil.setGlideImageView(getContext(),entity.getCoverPhotoUrl(),mImgHead);

    }

    public RelativeLayout getRlMemberInfo() {
        return rlShadow;
    }

    @ColorRes
    public int getShadowColor() {
        if (generationsOrder % 2 == 0) {
            return R.color.color_book_male;
        } else {
            return R.color.color_book_female;
        }
    }

    @Override
    public View getInfoView() {
        return mRlShadow;
    }

    @Override
    public void setCanAdd() {

    }

    public interface OnMemberClickListener {
        void add(int x, int y);

        void showInfo(PigeonEntity entity);
    }

    private OnMemberClickListener mOnMemberClickListener;

}

class MatchAdapter extends BaseQuickAdapter<PigeonPlayEntity, BaseViewHolder> {
    public MatchAdapter() {
        super(R.layout.item_text_in_print_book, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonPlayEntity item) {
        TextView textView = (TextView) helper.itemView;

        if(StringUtil.isStringValid(item.getMatchInfo())){
            textView.setText(item.getMatchInfo());
        }else {
            String div = "  ";
            StringBuilder sb =  new StringBuilder();
            if(StringUtil.isStringValid(item.getMatchTime())){
                long time = TimeUtil.parse(item.getMatchTime(), TimeUtil.FORMAT_YYYYMMDDHHMMSS);
                sb.append(TimeUtil.format(time, TimeUtil.FORMAT_YYYYMMDD));
                sb.append(div);
            }

            sb.append(item.getMatchName());
            sb.append(div);
            sb.append(item.getMatchNumber());
            textView.setText(sb.toString());
        }

    }
}
