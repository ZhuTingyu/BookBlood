package com.cpigeon.book.main;

import android.app.Dialog;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.base.base.BaseDialogFragment;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.module.basepigeon.InputPigeonFragment;
import com.cpigeon.book.module.breedpigeon.InputBreedInBookFragment;
import com.cpigeon.book.module.foot.FootAdminAddMultipleFragment;
import com.cpigeon.book.module.foot.FootAdminSingleFragment;
import com.cpigeon.book.module.menu.smalltools.SmallToolsHomeFragment;
import com.cpigeon.book.module.photo.SelectFootToPhotoFragment;
import com.cpigeon.book.module.trainpigeon.NewTrainPigeonFragment;
import com.cpigeon.book.widget.SimpleTitleView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/11/21.
 */

public class MainMenuDialogFragment extends BaseDialogFragment {

    SpringForce spring;

    @Override
    protected int getLayoutRes() {
        return R.layout.pop_mian_home;
    }

    protected void initLayout(Window window, WindowManager.LayoutParams lp) {
        window.setWindowAnimations(R.style.AnimBottomDialog);
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    protected void initView(Dialog dialog) {
        List<Integer> ids = Lists.newArrayList(R.id.simpleText1, R.id.simpleText2, R.id.simpleText3
                , R.id.simpleText4, R.id.simpleText5, R.id.simpleText6);
        List<View> views = Lists.newArrayList();
        for (int i = 0; i < ids.size(); i++) {
            SimpleTitleView simpleTitleView = dialog.findViewById(ids.get(i));
            views.add(simpleTitleView);
        }

        ImageView close = dialog.findViewById(R.id.imgClose);
        SimpleTitleView simpleText1 = dialog.findViewById(R.id.simpleText1);
        SimpleTitleView simpleText2 = dialog.findViewById(R.id.simpleText2);
        SimpleTitleView simpleText3 = dialog.findViewById(R.id.simpleText3);
        SimpleTitleView simpleText4 = dialog.findViewById(R.id.simpleText4);
        SimpleTitleView simpleText5 = dialog.findViewById(R.id.simpleText5);
        SimpleTitleView simpleText6 = dialog.findViewById(R.id.simpleText6);
        close.setOnClickListener(v -> {
            dismiss();
        });


        String[] chooseWays = getResources().getStringArray(R.array.array_choose_input_foot_number);
        simpleText1.setOnClickListener(v -> {
            //足环录入
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                if (chooseWays[p].equals(Utils.getString(R.string.text_one_foot_input))) {
                    FootAdminSingleFragment.start(getBaseActivity());
                } else {
                    FootAdminAddMultipleFragment.start(getBaseActivity());
                }
                dismiss();
            });
        });

        simpleText2.setOnClickListener(v -> {
            //种鸽录入
            dismiss();
//            BreedPigeonEntryFragment.start(getBaseActivity());
            InputBreedInBookFragment.start(getBaseActivity());
        });

        simpleText3.setOnClickListener(v -> {
            //赛鸽录入
            dismiss();
            InputPigeonFragment.start(getBaseActivity(), null, null, null, null, null, 0);
        });

        simpleText4.setOnClickListener(v -> {
            //赛鸽路训
            dismiss();
            NewTrainPigeonFragment.start(getBaseActivity());
        });

        simpleText5.setOnClickListener(v -> {
            //爱鸽拍照
            dismiss();
            SelectFootToPhotoFragment.start(getBaseActivity());
        });

        simpleText6.setOnClickListener(v -> {
            //赛鸽工具
            dismiss();
            SmallToolsHomeFragment.start(getBaseActivity());
        });

        spring = new SpringForce(0)
                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_MEDIUM);
        for (int i = 0; i < views.size(); i++) {
            addAnimation(views.get(i), 300 + (100 * i));
        }
    }

    private void addAnimation(View view, int time) {
        view.setVisibility(View.GONE);
        final SpringAnimation anim = new SpringAnimation(view, SpringAnimation.TRANSLATION_Y).setSpring(spring);
        anim.setStartValue(500);
        composite.add(RxUtils.delayed(time, aLong -> {
            view.setVisibility(View.VISIBLE);
            anim.cancel();
            anim.start();
        }));
    }
}
