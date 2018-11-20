package com.cpigeon.book.module.score;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonScoreItemEntity;
import com.cpigeon.book.util.MathUtil;
import com.cpigeon.book.widget.StarRatingView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/11/20.
 */

public class ScoreAdapter extends BaseQuickAdapter<PigeonScoreItemEntity, BaseViewHolder> {

    public ScoreAdapter() {
        super(R.layout.item_pigeon_score, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonScoreItemEntity item) {
        TextView mTvTitle;
        TextView mTvScore;
        StarRatingView mSrvScore;

        mTvTitle = helper.getView(R.id.tvTitle);
        mTvScore = helper.getView(R.id.tvScore);
        mSrvScore = helper.getView(R.id.srvScore);

        mTvTitle.setText(item.getItems());
        mTvScore.setText(MathUtil.doubleformat(item.getPscore(), 1));

        int rant = (int) ((item.getTscore() / item.getPscore()) * 10);

        mSrvScore.setRate(rant);

        mSrvScore.setOnRateChangeListener(rate -> {
            double score = (item.getTscore() / 10f) * rate;
            item.setPscore(score);

            notifyItemChanged(helper.getAdapterPosition());
            if (mOnAllScoreGetListener != null) {
                double result = 0;
                for (PigeonScoreItemEntity entity : getData()) {
                    result = result + entity.getPscore();
                }

                mOnAllScoreGetListener.allScore(result);
            }

            if (mOnItemScoreGetListener != null) {
                mOnItemScoreGetListener.itemScore(helper.getAdapterPosition(), item);
            }
        });
    }

    public interface OnAllScoreGetListener {
        void allScore(double allScore);
    }

    public interface OnItemScoreGetListener {
        void itemScore(int position, PigeonScoreItemEntity pigeonScoreItemEntity);
    }

    private OnItemScoreGetListener mOnItemScoreGetListener;
    private OnAllScoreGetListener mOnAllScoreGetListener;

    void setOnAllScoreGetListener(OnAllScoreGetListener onAllScoreGeListener) {
        mOnAllScoreGetListener = onAllScoreGeListener;
    }

    public void setOnItemScoreGetListener(OnItemScoreGetListener onItemScoreGetListener) {
        mOnItemScoreGetListener = onItemScoreGetListener;
    }
}
