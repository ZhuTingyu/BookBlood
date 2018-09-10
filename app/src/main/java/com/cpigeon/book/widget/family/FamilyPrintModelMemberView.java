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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
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
    private BreedPigeonEntity mPigeonEntity;


    private ShadowRelativeLayout mRlShadow;
    private NestedScrollView mScrollViewInfo;
    private LinearLayout mRlInMemberInfo;
    private ImageView mImgHead;
    private TextView mTvFootNumber;
    private TextView mTvBlood;
    private TextView mTvColor;
    private RecyclerView mList;

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
        View view = LayoutInflater.from(context).inflate(R.layout.view_family_print_model_member_layout, this);


        mRlShadow = view.findViewById(R.id.rlShadow);
        mScrollViewInfo = view.findViewById(R.id.scrollViewInfo);
        mRlInMemberInfo = view.findViewById(R.id.rlInMemberInfo);
        mImgHead = view.findViewById(R.id.imgHead);
        mTvFootNumber = view.findViewById(R.id.tvFootNumber);
        mTvBlood = view.findViewById(R.id.tvBlood);
        mTvColor = view.findViewById(R.id.tvColor);
        mList = view.findViewById(R.id.list);


        int size_226 = 552;
        int size_500 = 1000;
        int size_366 = 732;
        int size_178 = 356;
        int shadowSize = 20;
        rlShadow = findViewById(R.id.rlShadow);

        shadowColor = R.color.color_text_hint;
        rootW = size_226;
        if (generationPoint == 0) {
            rootH = size_500;
            shadowColor = R.color.color_text_hint;
        } else if (generationPoint == 1) {
            rootH = size_500;
            getShadowColor();
        } else if (generationPoint == 2) {
            rootH = size_366;
            getShadowColor();
        } else if (generationPoint == 3) {
            rootH = size_178;
            getShadowColor();
        }
        rlShadow.addShadow(shadowColor);

        LayoutParams shadowP;
        RelativeLayout.LayoutParams infoP;
        if (isHorizontal) {
            shadowP = new LayoutParams(rootW + shadowSize, rootH + shadowSize);
            infoP = new RelativeLayout.LayoutParams(rootW, rootH);
        } else {
            shadowP = new LayoutParams(rootH + shadowSize, rootW + shadowSize);
            infoP = new RelativeLayout.LayoutParams(rootH, rootW);
        }
        mScrollViewInfo.setLayoutParams(infoP);
        rlShadow.setLayoutParams(shadowP);

    }

    public void bindData(BreedPigeonEntity entity) {

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        MatchAdapter matchAdapter = new MatchAdapter();
        mList.setAdapter(matchAdapter);
        matchAdapter.setNewData(Lists.newArrayList("", ""));

        if (generationPoint == 0) {

        } else if (generationPoint == 1) {

        } else if (generationPoint == 2) {
            mImgHead.setVisibility(GONE);
        } else if (generationPoint == 3) {
            mImgHead.setVisibility(GONE);
        }
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

        void showInfo(BreedPigeonEntity entity);
    }

    private OnMemberClickListener mOnMemberClickListener;

}

class MatchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MatchAdapter() {
        super(R.layout.item_text_in_print_book, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView textView = (TextView) helper.itemView;
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 28);

    }
}
