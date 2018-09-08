package com.cpigeon.book.module.pigeonhouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.idcard.IdCardCameraActivity;
import com.base.idcard.IdCardNInfoEntity;
import com.base.idcard.IdCardPInfoEntity;
import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.cache.CacheUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.file.FileUtils;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.home.viewmodel.IdCertificationViewModel;
import com.cpigeon.book.widget.LineInputView;

import java.io.File;

import butterknife.BindView;

/**
 * hl 鸽舍 身份认证
 * <p>
 * Created by Administrator on 2018/8/6.
 */

public class IdCertificationFragment extends BaseBookFragment {

    private TextView mTvOk;
    private ImageView mImgIdN;
    private ImageView mImgIdP;
    private LineInputView mLvName;
    private LineInputView mLvNumber;
    private LineInputView mLvSex;
    private LineInputView mLvFamily;
    private LineInputView mLvAddress;
    private LineInputView mLvAuthority;
    private LineInputView mLvIndate;

    @BindView(R.id.ll_top_hint)
    LinearLayout ll_top_hint;

    IdCertificationViewModel mViewModel;

    boolean mIsEdit;


    public static void start(Activity activity, boolean isEdit) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_BOOLEAN, isEdit)
                .startParentActivity(activity, IdCertificationFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new IdCertificationViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_id_certification, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(R.string.title_id_card);

        mIsEdit = getBaseActivity().getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, true);

        mTvOk = findViewById(R.id.tvOk);
        mImgIdN = findViewById(R.id.imgIdN);
        mImgIdP = findViewById(R.id.imgIdP);
        mLvName = findViewById(R.id.lvName);
        mLvNumber = findViewById(R.id.lvNumber);
        mLvSex = findViewById(R.id.lvSex);
        mLvFamily = findViewById(R.id.lvFamily);
        mLvAddress = findViewById(R.id.lvAddress);
        mLvAuthority = findViewById(R.id.lvAuthority);
        mLvIndate = findViewById(R.id.lvIndate);


        ViewGroup.LayoutParams mParams = mLvAddress.getLlContent().getLayoutParams();
        mParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        mLvAddress.getLlContent().setLayoutParams(mParams);
        mLvAddress.getEditText().setSingleLine(false);

        bindUi(RxUtils.textChanges(mLvName.getEditText()), mViewModel.setName());
        bindUi(RxUtils.textChanges(mLvNumber.getEditText()), mViewModel.setCardNumber());
        bindUi(RxUtils.textChanges(mLvSex.getEditText()), mViewModel.setSex());
        bindUi(RxUtils.textChanges(mLvFamily.getEditText()), mViewModel.setNation());
        bindUi(RxUtils.textChanges(mLvAddress.getEditText()), mViewModel.setAddress());
        bindUi(RxUtils.textChanges(mLvAuthority.getEditText()), mViewModel.setAuthority());
        bindUi(RxUtils.textChanges(mLvIndate.getEditText()), mViewModel.setInDate());

        mImgIdP.setOnClickListener(v -> {
            IdCardCameraActivity.start(getBaseActivity(), IdCardCameraActivity.TYPE_P);
        });

        mImgIdN.setOnClickListener(v -> {
            IdCardCameraActivity.start(getBaseActivity(), IdCardCameraActivity.TYPE_N);
        });

        mTvOk.setOnClickListener(v -> {
            mViewModel.setUserIdCard();
        });

        if (!mIsEdit) {
            mViewModel.getUserIdCard();
        }
    }

    @Override
    protected void initObserve() {
        mViewModel.isCanCommit.observe(this, aBoolean -> {
            mTvOk.setEnabled(aBoolean);
        });

        mViewModel.normalResult.observe(this, s -> {
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                getBaseActivity().setResult(Activity.RESULT_OK);
                finish();
            });
        });

        mViewModel.mUserIdCardLiveData.observe(this, userIdCardEntity -> {
            if (userIdCardEntity == null) {
                return;
            }

            mTvOk.setText("重新认证身份");
            ll_top_hint.setVisibility(View.INVISIBLE);

            mLvName.setContent(userIdCardEntity.getXingming());
            mLvNumber.setContent(userIdCardEntity.getSfzhm());
            mLvSex.setContent(userIdCardEntity.getXingbie());
            mLvFamily.setContent(userIdCardEntity.getMinzu());
            mLvAddress.setContent(userIdCardEntity.getZhuzhi());
            mLvAuthority.setContent(userIdCardEntity.getQfjg());
            mLvIndate.setContent(userIdCardEntity.getYouxiaoqi());

            File idCardP = FileUtils.byte2File(userIdCardEntity.getSfzzm()
                    , CacheUtils.getInstance().getIdCardPath(true));

            File idCardN = FileUtils.byte2File(userIdCardEntity.getSfzbm()
                    , CacheUtils.getInstance().getIdCardPath(false));

            Glide.with(getBaseActivity()).load(idCardP).into(mImgIdP);
            Glide.with(getBaseActivity()).load(idCardN).into(mImgIdN);

            mViewModel.idCardP = idCardP.getPath();
            mViewModel.idCardN = idCardN.getPath();

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IdCardCameraActivity.CODE_ID_CARD_P) {
                //身份证正面
                IdCardPInfoEntity pInfoEntity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mLvName.setContent(pInfoEntity.name);
                mLvNumber.setContent(pInfoEntity.id);
                mLvSex.setContent(pInfoEntity.sex);
                mLvFamily.setContent(pInfoEntity.nation);
                mLvAddress.setContent(pInfoEntity.address);
                Glide.with(getBaseActivity()).load(pInfoEntity.frontimage).into(mImgIdP);
                mViewModel.idCardP = CacheUtils.getInstance().getString(IdCardCameraActivity.IMAGE_P_PATH);
            } else {
                //身份证反面
                IdCardNInfoEntity nInfoEntity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                mLvAuthority.setContent(nInfoEntity.authority);
                mLvIndate.setContent(nInfoEntity.valid_date);
                Glide.with(getBaseActivity()).load(nInfoEntity.backimage).into(mImgIdN);
                mViewModel.idCardN = CacheUtils.getInstance().getString(IdCardCameraActivity.IMAGE_N_PATH);
            }
        }

    }
}
