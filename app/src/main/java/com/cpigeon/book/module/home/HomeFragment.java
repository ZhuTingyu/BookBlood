package com.cpigeon.book.module.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.system.AppManager;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.adpter.HomeAdapter;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.foot.BreedPigeonFragment;
import com.cpigeon.book.module.login.LoginActivity;
import com.cpigeon.book.module.login.viewmodel.LoginViewModel;
import com.cpigeon.book.service.SingleLoginService;

/**
 * Created by Zhu TingYu on 2018/7/10.
 */

public class HomeFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    HomeAdapter mAdapter;
    Button acBtn;
    Button ac_btns2;
    Button ac_btns3;

    private LoginViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new LoginViewModel();
        initViewModel(mViewModel);

        setToolbarNotBack();
        mRecyclerView = findViewById(R.id.list);
        acBtn = findViewById(R.id.ac_btns);
        ac_btns2 = findViewById(R.id.ac_btns2);
        ac_btns3 = findViewById(R.id.ac_btns3);

        acBtn.setOnClickListener(v -> {
            BreedPigeonFragment.start(getActivity());
        });


        //hl  退出登录
        mViewModel.outMsg.observe(this, s -> {
            SingleLoginService.stopService();
            UserModel.getInstance().cleanUserInfo();
            getBaseActivity().errorDialog = DialogUtils.createSuccessDialog(getActivity(), s, false, dialog -> {
                dialog.dismiss();
                //结束所有页面，跳转到登录页
                AppManager.getAppManager().killAllToLoginActivity(LoginActivity.class);
            });
        });

        ac_btns2.setOnClickListener(v -> {
            mViewModel.outLogin();
        });

        ac_btns3.setOnClickListener(v -> {
            //用户信息
            InfoDetailsFragment.start(getActivity());
        });

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new HomeAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView.getRecyclerView());
        composite.add(RxUtils.delayed(50, aLong -> {
            mAdapter.setEmptyText("测试空");
            mAdapter.setNewData(Lists.newArrayList());
        }));
    }
}
