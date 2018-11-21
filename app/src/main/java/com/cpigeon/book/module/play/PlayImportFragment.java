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
import com.base.util.dialog.DialogUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PlayImportListEntity;
import com.cpigeon.book.module.play.adapter.PlayImportAdapter;
import com.cpigeon.book.module.play.viewmodel.PlayImportViewModel;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.mydialog.CustomAlertDialog2;
import com.cpigeon.book.widget.mydialog.HintDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 比赛导入
 * Created by Administrator on 2018/9/3.
 */

public class PlayImportFragment extends BaseBookFragment {


    private PlayImportViewModel mPlayInportViewModel;

    @BindView(R.id.list)
    XRecyclerView mRecyclerView;
    @BindView(R.id.tvOk)
    TextView tvOk;

    private boolean isChooseAll = false;

    private PlayImportAdapter mAdapter;

    private CustomAlertDialog2 mCustomAlertDialog2;
    private ProgressBar progressBar;
    Timer timer;

    public static void start(Activity activity, PigeonEntity pigeonEntity, String orgType, String orgName, String orgId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_FOOT_NUMBER, pigeonEntity)
                .putExtra(IntentBuilder.KEY_DATA, orgType)
                .putExtra(IntentBuilder.KEY_DATA_2, orgName)
                .putExtra(IntentBuilder.KEY_DATA_3, orgId)
                .startParentActivity(activity, PlayImportFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPlayInportViewModel = new PlayImportViewModel();
        initViewModels(mPlayInportViewModel);
        mPlayInportViewModel.mPigeonEntity = (PigeonEntity) getIntent().getSerializableExtra(IntentBuilder.KEY_FOOT_NUMBER);
        mPlayInportViewModel.organizeType = getIntent().getStringExtra(IntentBuilder.KEY_DATA);
        mPlayInportViewModel.organizeName = getIntent().getStringExtra(IntentBuilder.KEY_DATA_2);
        mPlayInportViewModel.organizeId = getIntent().getStringExtra(IntentBuilder.KEY_DATA_3);
        mPlayInportViewModel.houseNumber = UserModel.getInstance().getUserData().pigeonHouseEntity.getPigeonHomeID();
        mPlayInportViewModel.matchNumber = UserModel.getInstance().getUserData().pigeonHouseEntity.getPigeonMatchNum();
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
            mAdapter.getSelectedEntity();
            setProgressVisible(true);
            mPlayInportViewModel.inputLivePlayData();
        });

        mAdapter = new PlayImportAdapter();

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setChooseVisible(true);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                mAdapter.setMultiSelectItem(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            setProgressVisible(true);
            mPlayInportViewModel.getLivePlay();
        });

        tvOk.setOnClickListener(v -> {
            mPlayInportViewModel.mPlayData = mAdapter.getSelectedEntity();
            setProgressVisible(true);
            mPlayInportViewModel.inputLivePlayData();
        });

        setProgressVisible(true);
        mPlayInportViewModel.getLivePlay();

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

    @Override
    protected void initObserve() {
        mPlayInportViewModel.mPlayListData.observe(this, playImportListEntities -> {
            setProgressVisible(false);
            mRecyclerView.setRefreshing(false);
            mAdapter.setNewData(playImportListEntities);
        });

        mPlayInportViewModel.mPlayInporttData.observe(this, s -> {
            setProgressVisible(false);
            EventBus.getDefault().post(EventBusService.PIGEON_PLAY_LIST_REFRESH);
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                finish();
            });
        });
    }
}
