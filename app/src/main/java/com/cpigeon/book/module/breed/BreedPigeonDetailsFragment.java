package com.cpigeon.book.module.breed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.photo.PigeonPhotoHomeActivity;
import com.cpigeon.book.module.play.AddPlayFragment;
import com.cpigeon.book.widget.mydialog.ImportPlayDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 种鸽详情
 * Created by Administrator on 2018/8/29.
 */

public class BreedPigeonDetailsFragment extends BaseBookFragment {

    @BindView(R.id.tv_foot)
    TextView tvFoot;
    @BindView(R.id.img_sex)
    ImageView imgSex;
    @BindView(R.id.tv_foot_vice)
    TextView tvFootVice;
    @BindView(R.id.ll_foot_vice)
    LinearLayout llFootVice;
    @BindView(R.id.tv_lineage)
    TextView tvLineage;
    @BindView(R.id.ll_lineage)
    LinearLayout llLineage;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.ll_state)
    LinearLayout llState;
    @BindView(R.id.tv_eye_sand)
    TextView tvEyeSand;
    @BindView(R.id.ll_eye_sand)
    LinearLayout llEyeSand;
    @BindView(R.id.tv_feather_color)
    TextView tvFeatherColor;
    @BindView(R.id.ll_feather_color)
    LinearLayout llFeatherColor;
    @BindView(R.id.ll_info1)
    LinearLayout llInfo1;
    @BindView(R.id.tv_their_shells_date)
    TextView tvTheirShellsDate;
    @BindView(R.id.ll_their_shells_date)
    LinearLayout llTheirShellsDate;
    @BindView(R.id.tv_foot_source)
    TextView tvFootSource;
    @BindView(R.id.ll_foot_source)
    LinearLayout llFootSource;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.ll_score)
    LinearLayout llScore;
    Unbinder unbinder;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breed_pigeon_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("详情");

        setToolbarRight("成长记录", item -> {

            return true;
        });
    }


    @OnClick({R.id.img_pigeon, R.id.tv_foot, R.id.img_sex, R.id.ll_foot_vice, R.id.ll_lineage, R.id.ll_state, R.id.ll_eye_sand, R.id.ll_feather_color, R.id.ll_their_shells_date, R.id.ll_foot_source, R.id.ll_score
            , R.id.tv_make_book, R.id.tv_lineage_analysis, R.id.tv_lineage_roots, R.id.tv_breed_info
            , R.id.img_play_import, R.id.img_play_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_pigeon:
                //信鸽相册
                PigeonPhotoHomeActivity.start(getBaseActivity());
                break;

            case R.id.tv_foot:
                //足环号
                List<String> foots = new ArrayList<>();
                InputSingleFootDialog dialog = new InputSingleFootDialog();
                dialog.setFoots(foots);
                dialog.setOnFootStringFinishListener(foot -> {
                    tvFoot.setText(foot);
                });
                dialog.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.img_sex:
                //性别

                break;
            case R.id.ll_foot_vice:
                //副环
                List<String> foots2 = new ArrayList<>();
                InputSingleFootDialog dialog2 = new InputSingleFootDialog();
                dialog2.setFoots(foots2);
                dialog2.setOnFootStringFinishListener(foot -> {
                    tvFootVice.setText(foot);
                });
                dialog2.show(getBaseActivity().getSupportFragmentManager());
                break;
            case R.id.ll_lineage:
                //血统

                break;
            case R.id.ll_state:
                //状态

                break;
            case R.id.ll_eye_sand:
                //眼砂

                break;
            case R.id.ll_feather_color:
                //羽色

                break;
            case R.id.ll_their_shells_date:
                //出壳日期

                break;
            case R.id.ll_foot_source:
                //来源

                break;
            case R.id.ll_score:
                //评分

                break;

            case R.id.tv_make_book:
                //血统书制作

                break;

            case R.id.tv_lineage_analysis:
                //血统分析

                break;
            case R.id.tv_lineage_roots:
                //血统寻根

                break;

            case R.id.tv_breed_info:
                //繁育信息

                break;
            case R.id.img_play_import:
                //赛绩导入

                ImportPlayDialog mImportPlayDialog = new ImportPlayDialog(getBaseActivity());
                mImportPlayDialog.show();

                break;
            case R.id.img_play_add:
                //手动添加赛绩
                AddPlayFragment.start(getBaseActivity());
                break;
        }
    }
}
