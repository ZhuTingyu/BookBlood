package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.base.base.BaseMapFragment;
import com.base.util.FragmentUtils;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.LocationFormatUtils;
import com.base.util.Utils;
import com.base.util.db.AppDatabase;
import com.base.util.dialog.DialogUtils;
import com.base.util.map.MapMarkerManager;
import com.base.util.system.AppManager;
import com.base.util.utility.StringUtil;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.UpdateTrainEvent;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.login.LoginActivity;
import com.cpigeon.book.module.pigeonhouse.InputLocationFragment;
import com.cpigeon.book.module.select.SelectLocationByMapFragment;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainPigeonListAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.NewTrainPigeonViewModel;
import com.cpigeon.book.service.SingleLoginService;
import com.cpigeon.book.util.MathUtil;
import com.cpigeon.book.widget.LineInputView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class NewTrainPigeonFragment extends BaseBookFragment {

    private static final int CODE_SELECT_PIGEONS = 0x123;

    private LineInputView mLvName;
    private ImageView mImgAdd;
    private RecyclerView mList;
    private NewTrainPigeonListAdapter mAdapter;
    private TextView mTvOk;
    NewTrainPigeonViewModel mViewModel;


    public static void start(Activity activity) {
        IntentBuilder.Builder().startParentActivity(activity, NewTrainPigeonFragment.class);
    }

    public static void start(Activity activity, TrainEntity trainEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, trainEntity)
                .startParentActivity(activity, NewTrainPigeonFragment.class);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new NewTrainPigeonViewModel(getBaseActivity());
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_train_pigeon, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mViewModel.mTrainEntity == null) {
            setTitle(R.string.text_new_train_pigeon);
        } else {
            setTitle(Utils.getString(R.string.text_train_again_content, mViewModel.mTrainEntity.getPigeonTrainName()));
        }

        mLvName = findViewById(R.id.lvName);
        mImgAdd = findViewById(R.id.imgAdd);
        mList = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);

        mImgAdd.setOnClickListener(v -> {
            NewTrainAddPigeonFragment.start(getBaseActivity()
                    , (ArrayList<PigeonEntity>) mAdapter.getData(), CODE_SELECT_PIGEONS);
        });

        mTvOk.setOnClickListener(v -> {
            setProgressVisible(true);
            if (mViewModel.mTrainEntity == null) {
                mViewModel.name = mLvName.getContent();
                mViewModel.newTrainPigeon();
            } else {
                mViewModel.trainAgain();
            }

        });

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        addItemDecorationLine(mList);
        mAdapter = new NewTrainPigeonListAdapter();
        mAdapter.setEmptyText(Utils.getString(R.string.text_not_choose_pigeon));
        mList.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newArrayList());
        getBaseActivity().setOnActivityFinishListener(() -> {
            AppDatabase.getInstance(getBaseActivity()).delete(AppDatabase.getInstance(getBaseActivity()).DbEntityDao()
                    .getDataByUserAndType(UserModel.getInstance().getUserId()
                            , AppDatabase.TYPE_SELECT_PIGEON_TO_TRAINING));
            return false;
        });

        if (mViewModel.mTrainEntity != null) {
            setProgressVisible(true);
            mViewModel.getTrainDetails();
        } else {
            mAdapter.setOnDeleteListener(position -> {
                mAdapter.remove(position);
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == CODE_SELECT_PIGEONS) {
            List<PigeonEntity> pigeonEntities = (List<PigeonEntity>) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            mAdapter.setNewData(pigeonEntities);
            mViewModel.mPigeonEntities = pigeonEntities;
        }
    }

    @Override
    protected void initObserve() {

        mViewModel.mDataTrain.observe(this, trainEntity -> {
            setProgressVisible(false);
            mLvName.setCanEdit(false);
            mLvName.setRightText(trainEntity.getPigeonTrainName());

            mImgAdd.setVisibility(View.GONE);
            mAdapter.setNewData(trainEntity.getFootRingList());
        });

        mViewModel.normalResult.observe(this, s -> {
            setProgressVisible(false);
            EventBus.getDefault().post(new UpdateTrainEvent());
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                finish();
            });
        });
    }
}
