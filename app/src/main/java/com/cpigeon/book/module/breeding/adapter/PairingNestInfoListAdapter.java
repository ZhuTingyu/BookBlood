package com.cpigeon.book.module.breeding.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.StringUtil;
import com.base.util.utility.ToastUtils;
import com.base.widget.BottomSheetAdapter;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.cpigeon.book.module.breeding.OffspringChooseFragment;
import com.cpigeon.book.module.breeding.PairingNestAddFragment;
import com.cpigeon.book.module.breeding.viewmodel.PairingNestViewModel;
import com.cpigeon.book.util.MathUtil;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputView;

import java.time.format.TextStyle;
import java.util.Date;

/**
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestInfoListAdapter extends BaseQuickAdapter<PairingNestInfoEntity, BaseViewHolder> {


//    private LineInputView ll_nest_num;//窝次
//    private LineInputView ll_pairing_time;//配对时间
//    private LineInputView ll_lay_eggs;//产蛋信息
//    private LineInputView ll_lay_eggs_time;//产蛋时间
//    private LineInputView ll_fertilized_egg;//受精蛋
//    private LineInputView ll_fertilized_egg_no;//无精蛋
//    private LineInputView ll_hatches_info;//出壳信息
//    private LineInputView ll_hatches_time;//出壳时间
//    private LineInputView ll_hatches_num;//出壳个数
//    private LineInputView ll_offspring_info;//子代信息
//    private RecyclerView rv_offspring_info;//子代信息 列表

    private PairingNestViewModel mPairingNestViewModel;
//    private OffspringInfoAdapter mOffspringInfoAdapter;


    public int addChildPosition = 0;

    public PairingNestInfoListAdapter(PairingNestViewModel mPairingNestViewModel) {
        super(R.layout.item_pairing_nest_info, Lists.newArrayList());
        this.mPairingNestViewModel = mPairingNestViewModel;
    }

    @Override
    protected void convert(BaseViewHolder helper, PairingNestInfoEntity item) {


        LineInputView ll_nest_num = helper.getView(R.id.ll_nest_num);
//        ll_nest_num.setTitle("第" + MathUtil.toChinese(String.valueOf(Integer.valueOf(item.getLayNum()) + 1)) + "窝");
        ll_nest_num.setTitle("第" + MathUtil.toChinese(String.valueOf(helper.getPosition() + 1)) + "窝");

        LineInputView ll_pairing_time = helper.getView(R.id.ll_pairing_time);
        ll_pairing_time.setContent(item.getPigeonBreedTime());

        LineInputView ll_lay_eggs = helper.getView(R.id.ll_lay_eggs);//产蛋信息
        LineInputView ll_lay_eggs_time = helper.getView(R.id.ll_lay_eggs_time);//产蛋时间
        LineInputView ll_fertilized_egg = helper.getView(R.id.ll_fertilized_egg);//受精蛋
        LineInputView ll_fertilized_egg_no = helper.getView(R.id.ll_fertilized_egg_no);//无精蛋


        ll_lay_eggs_time.setContent("");
        ll_fertilized_egg.setContent("");
        ll_fertilized_egg_no.setContent("");


        if (!StringUtil.isStringValid(item.getLayEggTime()) || item.getLayEggTime().equals(mContext.getString(R.string.text_default))) {
            ll_lay_eggs_time.setContent("");
        } else {
            ll_lay_eggs_time.setContent(item.getLayEggTime());
        }

        ll_fertilized_egg.setContent(item.getInseEggCount() + "枚");
        ll_fertilized_egg_no.setContent(item.getFertEggCount() + "枚");


        if ((!StringUtil.isStringValid(item.getLayEggTime()) || item.getLayEggTime().equals(mContext.getString(R.string.text_default))) &&//产蛋时间

                (!StringUtil.isStringValid(item.getInseEggCount()) ||

                        Integer.valueOf(item.getInseEggCount()) == 0) && //受精蛋

                (!StringUtil.isStringValid(item.getFertEggCount()) ||

                        Integer.valueOf(item.getFertEggCount()) == 0)  //无精蛋

                ) {
            //未产蛋
            ll_lay_eggs.setContent(mContext.getString(R.string.string_lay_eggs_no));

            ll_lay_eggs_time.setVisibility(View.GONE);
            ll_fertilized_egg.setVisibility(View.GONE);
            ll_fertilized_egg_no.setVisibility(View.GONE);

        } else {
            //已产蛋
            ll_lay_eggs.setContent(mContext.getString(R.string.string_lay_eggs_yes));

            ll_lay_eggs_time.setVisibility(View.VISIBLE);
            ll_fertilized_egg.setVisibility(View.VISIBLE);
            ll_fertilized_egg_no.setVisibility(View.VISIBLE);
        }


        LineInputView ll_hatches_info = helper.getView(R.id.ll_hatches_info);//出壳信息
        LineInputView ll_hatches_time = helper.getView(R.id.ll_hatches_time);//出壳时间
        LineInputView ll_hatches_num = helper.getView(R.id.ll_hatches_num);//出壳个数

        if (!StringUtil.isStringValid(item.getOutShellTime()) || item.getOutShellTime().equals(mContext.getString(R.string.text_default))) {
            ll_hatches_time.setContent("");
        } else {
            ll_hatches_time.setContent(item.getOutShellTime());
        }

        ll_hatches_num.setContent(item.getOutShellCount() + "枚");
        if ((StringUtil.isStringValid(item.getOutShellTime()) && !item.getOutShellTime().equals(mContext.getString(R.string.text_default)))
                || Integer.valueOf(item.getOutShellCount()) > 0) {
            // 已出壳
            ll_hatches_info.setContent(mContext.getString(R.string.string_hatches_info_yes));

            ll_hatches_time.setVisibility(View.VISIBLE);
            ll_hatches_num.setVisibility(View.VISIBLE);
        } else {
            // 未出壳
            ll_hatches_info.setContent(mContext.getString(R.string.string_hatches_info_no));

            ll_hatches_time.setVisibility(View.GONE);
            ll_hatches_num.setVisibility(View.GONE);
        }

        LineInputView ll_offspring_info = helper.getView(R.id.ll_offspring_info);//子代信息

        ll_offspring_info.setContent(mContext.getString(R.string.text_add));

        OffspringInfoAdapter mOffspringInfoAdapter = new OffspringInfoAdapter();

        RecyclerView rv_offspring_info = helper.getView(R.id.rv_offspring_info);//子代信息
        rv_offspring_info.setLayoutManager(new LinearLayoutManager(mContext));
        rv_offspring_info.setAdapter(mOffspringInfoAdapter);
        mOffspringInfoAdapter.bindToRecyclerView(rv_offspring_info);
        mOffspringInfoAdapter.setNewData(item.getPigeonList());


        ll_nest_num.setOnClickListener(v -> {
            setOnClick(ll_nest_num, item, mOffspringInfoAdapter);//删除
        });

        ll_pairing_time.setOnClickListener(v -> {
            setOnClick(ll_pairing_time, item, mOffspringInfoAdapter);//配对时间
        });

        ll_lay_eggs.setOnClickListener(v -> {
//            setOnClick(ll_lay_eggs, item, mOffspringInfoAdapter);

            // 产蛋信息
            String[] chooseWays = getBaseActivity().getResources().getStringArray(R.array.array_lay_eggs);
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                String way = chooseWays[p];
                if (way.equals(Utils.getString(R.string.string_lay_eggs_yes))) {
                    //已产蛋
                    ll_lay_eggs.setContent(mContext.getString(R.string.string_lay_eggs_yes));

                    ll_lay_eggs_time.setVisibility(View.VISIBLE);
                    ll_fertilized_egg.setVisibility(View.VISIBLE);
                    ll_fertilized_egg_no.setVisibility(View.VISIBLE);

                } else if (way.equals(Utils.getString(R.string.string_lay_eggs_no))) {
                    //未产蛋
                    ll_lay_eggs.setContent(mContext.getString(R.string.string_lay_eggs_no));

                    ll_lay_eggs_time.setVisibility(View.GONE);
                    ll_fertilized_egg.setVisibility(View.GONE);
                    ll_fertilized_egg_no.setVisibility(View.GONE);
                }
            });

        });

        ll_lay_eggs_time.setOnClickListener(v -> {
            setOnClick(ll_lay_eggs_time, item, mOffspringInfoAdapter);//产蛋时间
        });


        ll_fertilized_egg.setOnClickListener(v -> {
            setOnClick(ll_fertilized_egg, item, mOffspringInfoAdapter);//产蛋 受精蛋
        });

        ll_fertilized_egg_no.setOnClickListener(v -> {
            setOnClick(ll_fertilized_egg_no, item, mOffspringInfoAdapter);//产蛋 无精蛋
        });

        ll_hatches_info.setOnClickListener(v -> {
//            setOnClick(ll_hatches_info, item, mOffspringInfoAdapter);//出壳信息
            //出壳信息
            String[] chooseWays2 = getBaseActivity().getResources().getStringArray(R.array.array_hatches_info);
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays2), p -> {
                String way = chooseWays2[p];
                if (way.equals(Utils.getString(R.string.string_hatches_info_yes))) {
                    //已出壳
                    ll_hatches_info.setContent(mContext.getString(R.string.string_hatches_info_yes));

                    ll_hatches_time.setVisibility(View.VISIBLE);
                    ll_hatches_num.setVisibility(View.VISIBLE);
                } else if (way.equals(Utils.getString(R.string.string_hatches_info_no))) {
                    //未出壳
                    ll_hatches_info.setContent(mContext.getString(R.string.string_hatches_info_no));

                    ll_hatches_time.setVisibility(View.GONE);
                    ll_hatches_num.setVisibility(View.GONE);
                }
            });


        });


        ll_hatches_time.setOnClickListener(v -> {
            setOnClick(ll_hatches_time, item, mOffspringInfoAdapter);//出壳时间
        });

        ll_hatches_num.setOnClickListener(v -> {
            setOnClick(ll_hatches_num, item, mOffspringInfoAdapter);//出壳个数
        });

        ll_offspring_info.setOnClickListener(v -> {
            addChildPosition = helper.getPosition();
            setOnClick(ll_offspring_info, item, mOffspringInfoAdapter);//子代信息
        });

        mOffspringInfoAdapter.setOffspringChildViewClick((position, imgClose, tvContent, item1) -> {

            imgClose.setOnClickListener(v -> {
                mOffspringInfoAdapter.remove(position);
                mPairingNestViewModel.mPairingNestInfoEntity = item;
                mPairingNestViewModel.mOffspringInfoAdapter = mOffspringInfoAdapter;
                getBaseActivity().setProgressVisible(true);
                mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();
            });

            tvContent.setOnClickListener(v -> {
                ToastUtils.showLong(getBaseActivity(), "详情 -->" + position);
            });
        });
    }


    private BaseInputDialog mInputDialog;

    public void setOnClick(View view, PairingNestInfoEntity item, OffspringInfoAdapter mOffspringInfoAdapter) {
        mPairingNestViewModel.mPairingNestInfoEntity = item;
        mPairingNestViewModel.mOffspringInfoAdapter = mOffspringInfoAdapter;

        switch (view.getId()) {
            case R.id.ll_nest_num:
                //删除
                getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), mContext.getString(R.string.text_delete_warning_hint), sweetAlertDialog -> {
                    //确定
                    sweetAlertDialog.dismiss();
                    getBaseActivity().setProgressVisible(true);
                    mPairingNestViewModel.getTXGP_PigeonBreedNest_DeleteData();
                }, sweetAlertDialog -> {
                    //取消
                    sweetAlertDialog.dismiss();
                });

                break;
            case R.id.ll_pairing_time:
                //配对时间
                PickerUtil.showTimePicker(getBaseActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    mPairingNestViewModel.mPairingNestInfoEntity.setPigeonBreedTime(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();
                });
                break;
            case R.id.ll_lay_eggs:
                //产蛋信息

                break;
            case R.id.ll_lay_eggs_time:
                //产蛋时间
                PickerUtil.showTimePicker(getBaseActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    mPairingNestViewModel.mPairingNestInfoEntity.setLayEggTime(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();
                });
                break;
            case R.id.ll_fertilized_egg:
                //产蛋 受精蛋
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_fertilized_egg, InputType.TYPE_CLASS_NUMBER, content -> {
                            mInputDialog.hide();
                            mPairingNestViewModel.mPairingNestInfoEntity.setInseEggCount(content);
                            mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();

                        }, null);

                break;
            case R.id.ll_fertilized_egg_no:
                //产蛋 无精蛋
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_fertilized_egg_no, InputType.TYPE_CLASS_NUMBER, content -> {
                            mInputDialog.hide();
                            mPairingNestViewModel.mPairingNestInfoEntity.setFertEggCount(content);
                            mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();

                        }, null);

                break;
            case R.id.ll_hatches_info:
                //出壳信息

                break;
            case R.id.ll_hatches_time:
                //出壳时间
                PickerUtil.showTimePicker(getBaseActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    mPairingNestViewModel.mPairingNestInfoEntity.setOutShellTime(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();
                });
                break;
            case R.id.ll_hatches_num:
                //出壳个数
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_hatches_num, InputType.TYPE_CLASS_NUMBER, content -> {
                            mInputDialog.hide();
                            mPairingNestViewModel.mPairingNestInfoEntity.setOutShellCount(content);
                            mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();

                        }, null);
                break;
            case R.id.ll_offspring_info:
                //子代信息
                OffspringChooseFragment.start(getBaseActivity(), PairingNestAddFragment.requestCode);
                break;
        }
    }
}
