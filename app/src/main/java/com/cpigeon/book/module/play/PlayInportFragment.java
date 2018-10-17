package com.cpigeon.book.module.play;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PlayInportListEntity;
import com.cpigeon.book.module.play.adapter.PlayInportAdapter;
import com.cpigeon.book.module.play.viewmodel.PlayInportViewModel;
import com.cpigeon.book.widget.mydialog.CustomAlertDialog2;
import com.cpigeon.book.widget.mydialog.HintDialog;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 比赛导入
 * Created by Administrator on 2018/9/3.
 */

public class PlayInportFragment extends BaseBookFragment {


    private PlayInportViewModel mPlayInportViewModel;

    @BindView(R.id.list)
    XRecyclerView mRecyclerView;
    @BindView(R.id.tvOk)
    TextView tvOk;

    private boolean isChooseAll = false;

    private PlayInportAdapter mAdapter;

    private CustomAlertDialog2 mCustomAlertDialog2;
    private ProgressBar progressBar;
    Timer timer;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PlayInportFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPlayInportViewModel = new PlayInportViewModel();
        initViewModels(mPlayInportViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("选择赛绩");

        setToolbarRight("全选", item -> {

            if (isChooseAll) {
                setToolbarRight("全选");
                isChooseAll = false;
                mAdapter.isChooseAll(isChooseAll);
            } else {
                setToolbarRight("取消全选");
                isChooseAll = true;
                mAdapter.isChooseAll(isChooseAll);
            }

            return true;
        });

        tvOk.setText("确定导入");


        mCustomAlertDialog2 = HintDialog.shootHintInputPlayDialog(getBaseActivity(), progressBar);
        ImageView img_gif = mCustomAlertDialog2.getWindow().getDecorView().findViewById(R.id.img_gif);
        progressBar = mCustomAlertDialog2.getWindow().getDecorView().findViewById(R.id.progressPlace);
        progressBar.setMax(2000);

        String uri = "android.resource://" + getBaseActivity().getPackageName() + "/" + R.drawable.input_play_gif;

        Glide.with(getBaseActivity()).load(uri).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_gif);

        tvOk.setOnClickListener(v -> {
//            input_play_gif

        });

        mAdapter = new PlayInportAdapter();

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setNewData(Lists.newArrayList(new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity()));

        mAdapter.setChooseVisible(true);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                mAdapter.setMultiSelectItem(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });




        mCustomAlertDialog2.show();
        timer = new Timer();
        RxUtils.runOnNewThread(o -> {
            timer.schedule(new TimerTask() {
                int tag = 0;
                @Override
                public void run() {
                    getBaseActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tag >= 2000) {
                                //退出计时器
                                timer.cancel();
                                mCustomAlertDialog2.dismiss();

                            }
                            progressBar.setProgress(tag);
                            tag += 10;
                        }
                    });
                }
            }, 0, 1 * 10);
        });

    }


}
