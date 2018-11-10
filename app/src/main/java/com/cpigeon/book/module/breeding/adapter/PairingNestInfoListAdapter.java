package com.cpigeon.book.module.breeding.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.dialog.DialogUtils;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.cpigeon.book.module.breeding.OffspringChooseFragment;
import com.cpigeon.book.module.breeding.PairingNestAddFragment;
import com.cpigeon.book.module.breeding.viewmodel.PairingNestViewModel;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.util.MathUtil;
import com.cpigeon.book.widget.LineInputView;

import java.util.Date;

/**
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestInfoListAdapter extends BaseQuickAdapter<PairingNestInfoEntity, BaseViewHolder> {

    private PairingNestViewModel mPairingNestViewModel;

    public int addChildPosition = 0;

    public PairingNestInfoListAdapter(PairingNestViewModel mPairingNestViewModel) {
        super(R.layout.item_pairing_nest_info, Lists.newArrayList());
        this.mPairingNestViewModel = mPairingNestViewModel;
    }

    @Override
    protected void convert(BaseViewHolder helper, PairingNestInfoEntity item) {
        boolean llHatchesInfoTag = false;
        boolean llLayEggsTag = false;

        LineInputView ll_nest_num = helper.getView(R.id.ll_nest_num);
        ll_nest_num.setTitle("第" + MathUtil.toChinese(String.valueOf(helper.getPosition() + 1)) + "窝");

        LineInputView ll_pairing_time = helper.getView(R.id.ll_pairing_time);
        ll_pairing_time.setContent(item.getPigeonBreedTime());

        LinearLayout ll_lay_eggs = helper.getView(R.id.ll_lay_eggs);//产蛋信息
        LineInputView ll_lay_eggs_time = helper.getView(R.id.ll_lay_eggs_time);//产蛋时间
        LineInputView ll_fertilized_egg = helper.getView(R.id.ll_fertilized_egg);//受精蛋
        LineInputView ll_fertilized_egg_no = helper.getView(R.id.ll_fertilized_egg_no);//无精蛋
        LineInputView ll_hatches_info = helper.getView(R.id.ll_hatches_info);//出壳信息


        ImageView img_right_lay_eggs = helper.getView(R.id.img_right_lay_eggs);
        ImageView img_giving = helper.getView(R.id.img_giving);
        LinearLayout ll_lay_eggs_z = helper.getView(R.id.ll_lay_eggs_z);//
        LinearLayout ll_fertilized_giving = helper.getView(R.id.ll_fertilized_giving);//
        LinearLayout ll_hatches_info_z = helper.getView(R.id.ll_hatches_info_z);//

        LineInputView ll_hatches_time = helper.getView(R.id.ll_hatches_time);//出壳时间
        LineInputView ll_hatches_num = helper.getView(R.id.ll_hatches_num);//出壳个数
        TextView tv_giving_name = helper.getView(R.id.tv_giving_name);


        ImageView hatches_info_img = ll_hatches_info.getImgRight();


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
            img_right_lay_eggs.setRotation(0);
            ll_lay_eggs_z.setVisibility(View.GONE);

            llLayEggsTag = false;

        } else {
            //已产蛋

            img_right_lay_eggs.setRotation(90);
            ll_lay_eggs_z.setVisibility(View.VISIBLE);

            llLayEggsTag = true;
        }

        //是否赠送
        if (StringUtil.isStringValid(item.getGivePrson())) {
            img_giving.setImageResource(R.mipmap.giving_yes);
            tv_giving_name.setText(item.getGivePrson());
        } else {
            tv_giving_name.setText("");
            img_giving.setImageResource(R.mipmap.giving_no);
        }

        if (!StringUtil.isStringValid(item.getOutShellTime()) || item.getOutShellTime().equals(mContext.getString(R.string.text_default))) {
            ll_hatches_time.setContent("");
        } else {
            ll_hatches_time.setContent(item.getOutShellTime());
        }

        ll_hatches_num.setContent(item.getOutShellCount() + "枚");
        if ((StringUtil.isStringValid(item.getOutShellTime()) && !item.getOutShellTime().equals(mContext.getString(R.string.text_default)))
                || Integer.valueOf(item.getOutShellCount()) > 0) {
            // 已出壳
            llHatchesInfoTag = true;

            hatches_info_img.setRotation(90);
            ll_hatches_info_z.setVisibility(View.VISIBLE);
        } else {
            // 未出壳
            llHatchesInfoTag = false;

            hatches_info_img.setRotation(0);
            ll_hatches_info_z.setVisibility(View.GONE);
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

        boolean finalLlLayEggsTag = llLayEggsTag;
        ll_lay_eggs.setOnClickListener(new View.OnClickListener() {

            boolean llLayEggsTags = finalLlLayEggsTag;

            @Override
            public void onClick(View v) {
                if (llLayEggsTags) {
                    //未产蛋
                    llLayEggsTags = false;

                    img_right_lay_eggs.setRotation(0);
                    ll_lay_eggs_z.setVisibility(View.GONE);

                } else {
                    //已产蛋
                    llLayEggsTags = true;

                    img_right_lay_eggs.setRotation(90);
                    ll_lay_eggs_z.setVisibility(View.VISIBLE);
                }
            }
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

        //给送给别人
        ll_fertilized_giving.setOnClickListener(v -> {
            mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                    , R.string.tv_hatches_giving_name, tv_giving_name.getText().toString(), InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                        mInputDialog.hide();
                        tv_giving_name.setText(content);

                        if (StringUtil.isStringValid(content)) {
                            img_giving.setImageResource(R.mipmap.giving_yes);
                        } else {
                            img_giving.setImageResource(R.mipmap.giving_no);
                        }
                        mPairingNestViewModel.mPairingNestInfoEntity = item;

                        mPairingNestViewModel.mPairingNestInfoEntity.setGivePrson(content);
                        mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();

                    }, null);
        });

        boolean finalLlHatchesInfoTag = llHatchesInfoTag;
        ll_hatches_info.setOnClickListener(new View.OnClickListener() {

            boolean llHatchesInfoTags = finalLlHatchesInfoTag;

            @Override
            public void onClick(View v) {
                //出壳信息
                if (llHatchesInfoTags) {
                    //未出壳
                    llHatchesInfoTags = false;

                    hatches_info_img.setRotation(0);
                    ll_hatches_info_z.setVisibility(View.GONE);

                } else {
                    //已出壳
                    llHatchesInfoTags = true;
                    hatches_info_img.setRotation(90);
                    ll_hatches_info_z.setVisibility(View.VISIBLE);

                }
            }
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

                BreedPigeonDetailsFragment.start(getBaseActivity(),
                        mOffspringInfoAdapter.getData().get(position).getPigeonID(),
                        mOffspringInfoAdapter.getData().get(position).getFootRingID());
                //                ToastUtils.showLong(getBaseActivity(), "详情 -->" + position);
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
                PickerUtil.showTimeYMD(getBaseActivity(), new Date().getTime(), (year, monthOfYear, dayOfMonth) -> {
                    mPairingNestViewModel.mPairingNestInfoEntity.setPigeonBreedTime(year + "-" + (monthOfYear) + "-" + dayOfMonth);
                    mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();
                });
                break;
            case R.id.ll_lay_eggs:
                //产蛋信息

                break;
            case R.id.ll_lay_eggs_time:
                //产蛋时间
                PickerUtil.showTimeYMD(getBaseActivity(), new Date().getTime(), (year, monthOfYear, dayOfMonth) -> {
                    mPairingNestViewModel.mPairingNestInfoEntity.setLayEggTime(year + "-" + (monthOfYear) + "-" + dayOfMonth);
                    mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();
                });
                break;
            case R.id.ll_fertilized_egg:
                //产蛋 受精蛋
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_fertilized_egg, EditorInfo.TYPE_CLASS_NUMBER, content -> {
                            mInputDialog.hide();
                            mPairingNestViewModel.mPairingNestInfoEntity.setInseEggCount(content);
                            mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();

                        }, null);

                break;
            case R.id.ll_fertilized_egg_no:
                //产蛋 无精蛋
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_fertilized_egg_no, EditorInfo.TYPE_CLASS_NUMBER, content -> {
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
                PickerUtil.showTimeYMD(getBaseActivity(), new Date().getTime(), (year, monthOfYear, dayOfMonth) -> {
                    mPairingNestViewModel.mPairingNestInfoEntity.setOutShellTime(year + "-" + (monthOfYear) + "-" + dayOfMonth);
                    mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();
                });
                break;
            case R.id.ll_hatches_num:
                //出壳个数
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_hatches_num, EditorInfo.TYPE_CLASS_NUMBER, content -> {
                            mInputDialog.hide();
                            mPairingNestViewModel.mPairingNestInfoEntity.setOutShellCount(content);
                            mPairingNestViewModel.getTXGP_PigeonBreedNest_UpdateData();

                        }, null);
                break;
            case R.id.ll_offspring_info:
                //子代信息
                OffspringChooseFragment.start(getBaseActivity()
                        , item.getPigeonBreedNestID()
                        , mPairingNestViewModel.mPairingInfoEntity
                        , PairingNestAddFragment.requestCode);
                break;
        }
    }
}
