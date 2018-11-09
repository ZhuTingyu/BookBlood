package com.cpigeon.book.module.basepigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.LogUtil;
import com.base.util.utility.ToastUtils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breeding.adapter.BreedingFootAdapter;
import com.cpigeon.book.module.breeding.viewmodel.PairingInfoListViewModel;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.util.RecyclerViewUtils;
import com.cpigeon.book.widget.FiltrateListView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/9/10.
 */

public class BaseFootListFragment extends BaseBookFragment {
    protected XRecyclerView mRecyclerView;
    protected TextView mTvOk;
    protected View view_placeholder;
    protected BasePigeonListAdapter mAdapter;
    protected BreedingFootAdapter mPairingInfoListAdapter;
    protected SelectTypeViewModel mSelectTypeViewModel;

    protected BreedPigeonListModel mBreedPigeonListModel;
    protected SearchFragmentParentActivity mActivity;

    protected FiltrateListView mFiltrate;
    protected DrawerLayout mDrawerLayout;
    protected PairingInfoListViewModel mPairingInfoListViewModel;
    public static final String TYPEID = "TYPEID";//鸽子类型（8为种鸽，9为赛鸽，不传则全部查询）
    public static final String BLOODID = "BLOODID";//血统id （1,2）
    public static final String SEXID = "SEXID";//性别id （1,2）
    public static final String YEAR = "YEAR";//出壳年份 （1,2）
    public static final String YEARS = "YEARS";//交配年份
    public static final String STATEID = "STATEID";//状态id （1,2）
    public static final String EYEID = "EYEID";//眼沙（1,2）
    public static final String FOOTNUM = "FOOTNUM";//足环号码
    public static final String BITMATCH = "BITMATCH";//是否返回赛绩（1，返回）
    public static final String BITBREED = "BITBREED";//是否有父母（1存在，2.不存在，其他全查）
    public static final String PIGEONIDSTR = "PIGEONIDSTR";//在列表中排除的鸽子(1,2)
    public static final String BITSHARE = "BITSHARE";//是否是在共享厅（1：存在，2，不存在，其他全查）
    public static final String BITMOTTO = "BITMOTTO";//是不是铭鸽（1：是，2：正在申请 ，3，不是，其他全查）


    private Class cls;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) context;

        mSelectTypeViewModel = new SelectTypeViewModel();
        mBreedPigeonListModel = new BreedPigeonListModel();

        initViewModels(mSelectTypeViewModel, mBreedPigeonListModel);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @LayoutRes
    protected int getLayoutRes() {
        return R.layout.xreclyview_with_bottom_btn;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = findViewById(R.id.list);
        try {
            mTvOk = findViewById(R.id.tvOk);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            view_placeholder = findViewById(R.id.view_placeholder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mActivity.setSearchHint(R.string.text_input_foot_number_search);

        if(mTvOk != null){
            mTvOk.setVisibility(View.GONE);
        }

        if(view_placeholder != null){
            view_placeholder.setVisibility(View.GONE);
        }

        initParameter();//初始化请求的参数

        mRecyclerView.setRefreshListener(() -> {
            setProgressVisible(true);
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            mBreedPigeonListModel.pi = 1;
            mBreedPigeonListModel.getPigeonList();
        });

        mActivity.setSearchClickListener(v -> {
            //搜索
            Bundle bundle = new Bundle();
            try {
                bundle.putString(BaseFootListFragment.TYPEID, BaseFootListFragment.this.getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.TYPEID));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.BLOODID, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BLOODID));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.SEXID, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.SEXID));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.YEAR, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.YEAR));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.STATEID, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.STATEID));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.EYEID, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.EYEID));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.FOOTNUM, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.FOOTNUM));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.BITMATCH, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITMATCH));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.BITBREED, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITBREED));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.PIGEONIDSTR, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.PIGEONIDSTR));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.BITSHARE, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITSHARE));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bundle.putString(BaseFootListFragment.BITMOTTO, getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITMOTTO));
            } catch (Exception e) {
                e.printStackTrace();
            }

            BaseSearchActivity.start(getBaseActivity(), cls, bundle);

        });

        mAdapter = new BreedPigeonListAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                mBreedPigeonListModel.id = PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        });

        initData();

        mRecyclerView.addItemDecorationLineHaveMargin();
        mRecyclerView.setAdapter(mAdapter);
        initBreedData();
        mAdapter.setOnLoadMoreListener(() -> {
            setProgressVisible(true);
            mBreedPigeonListModel.pi++;
            mBreedPigeonListModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());


        setProgressVisible(true);
        mBreedPigeonListModel.getPigeonList();

        mDrawerLayout = mActivity.getDrawerLayout();
        mFiltrate = mActivity.getFiltrate();

        if (mDrawerLayout == null || mFiltrate == null) {
            return;
        }

        setToolbarRightImage(R.drawable.svg_filtrate, item -> {
            if (mDrawerLayout != null) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
            return false;
        });

        mFiltrate.setOnSureClickListener(selectItems -> {
            LogUtil.print(selectItems);
            mDrawerLayout.closeDrawer(Gravity.RIGHT);

            setProgressVisible(true);
            mBreedPigeonListModel.pi = 1;
            mBreedPigeonListModel.isSearch = false;
            mAdapter.cleanList();

            //年份
            List<SelectTypeEntity> mSelectTypeYear = selectItems.get(0);
            mBreedPigeonListModel.year = SelectTypeEntity.getTypeName(mSelectTypeYear);

            //性别
            List<SelectTypeEntity> mSelectTypeSex = selectItems.get(1);
            mBreedPigeonListModel.sexid = SelectTypeEntity.getTypeIds(mSelectTypeSex);

            //状态
            List<SelectTypeEntity> mSelectTypeStatus = selectItems.get(2);
            mBreedPigeonListModel.stateid = SelectTypeEntity.getTypeIds(mSelectTypeStatus);

            //血统
            List<SelectTypeEntity> mSelectTypeLineage = selectItems.get(3);
            mBreedPigeonListModel.bloodid = SelectTypeEntity.getTypeIds(mSelectTypeLineage);

            mBreedPigeonListModel.getPigeonList();
            mBreedPigeonListModel.getPigeonCount();

        });

        mSelectTypeViewModel.setSelectType(SelectTypeViewModel.TYPE_SEX, SelectTypeViewModel.STATE_STATE, SelectTypeViewModel.TYPE_PIGEON_BLOOD);
        mSelectTypeViewModel.getSelectTypes();
    }

    //初始化请求的参数
    private void initParameter() {
        try {
            mBreedPigeonListModel.typeid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.TYPEID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bloodid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BLOODID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.sexid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.SEXID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.year = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.YEAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.stateid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.STATEID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.eyeid = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.EYEID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.searchStr = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.FOOTNUM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bitmatch = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITMATCH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bitbreed = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITBREED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.pigeonidStr = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.PIGEONIDSTR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bitshare = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITSHARE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mBreedPigeonListModel.bitMotto = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.BITMOTTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mPairingInfoListViewModel.year = getBaseActivity().getIntent().getStringExtra(BaseFootListFragment.YEARS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initData() {

    }

    protected void initBreedData() {

    }

    //跳转的搜索页
    protected void setStartSearchActvity(Class cls) {
        this.cls = cls;
    }

    @Override
    protected void initObserve() {
        mSelectTypeViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            List<String> titles = Lists.newArrayList(Utils.getString(R.string.text_sex)
                    , Utils.getString(R.string.text_pigeon_status), Utils.getString(R.string.text_pigeon_blood));

            if (mFiltrate != null) {
                mFiltrate.setData(true, selectTypeEntities, titles, mSelectTypeViewModel.whichIds);
            }
        });

        mBreedPigeonListModel.mPigeonListData.observe(this, datas -> {
            if (datas.isEmpty() || datas.size() == 0) {

            } else {
                if (mAdapter.getHeaderViewsCount() == 0) {
                    initHeadView();
                }
            }

            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, datas);

            setProgressVisible(false);
        });

        mBreedPigeonListModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });
        mBreedPigeonListModel.listDeleteMessage.observe(this, s -> {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            mBreedPigeonListModel.pi = 1;
            mBreedPigeonListModel.getPigeonList();
            ToastUtils.showLong(getBaseActivity(), s);
        });
    }

    protected void initHeadView() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

