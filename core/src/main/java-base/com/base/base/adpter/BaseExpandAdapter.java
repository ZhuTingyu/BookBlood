package com.base.base.adpter;

import com.base.base.BaseViewHolder;
import com.base.entity.ExpendEntity;
import com.base.entity.RaceEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/3/5.
 */

public abstract class BaseExpandAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_ORG = 1;
    public static final int TYPE_RACE = 2;
    int currentPosition = -1;

    public BaseExpandAdapter(List<MultiItemEntity> data) {
        super(data);
    }

    public interface OnOrgItemClickListener{
        void onclick(int position);
    }

    private OnOrgItemClickListener onOrgItemClickListener;

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_ORG:
                helper.itemView.setOnClickListener(view -> {
                    int position = helper.getAdapterPosition();

                    if(currentPosition == -1){//当前没有展开项
                        //展开当前项保存当前位置
                        currentPosition = position;
                        OrgItem orgItem = (OrgItem)getItem(currentPosition);
                        int dataSize = orgItem.getOrgInfo().getRace().size();
                        //点击的项没有展开项是可以调用该接口
                        if(dataSize == 0){
                            if(onOrgItemClickListener != null){
                                onOrgItemClickListener.onclick(position);
                            }
                        }else {
                            expand(position);
                        }
                    }else {
                        OrgItem orgItem = (OrgItem)getItem(currentPosition);
                        int dataSize = orgItem.getOrgInfo().getRace().size();
                        if(currentPosition == position){ // 点击已经展开的项
                            //关闭当前项 清空位置
                            collapse(position);
                            currentPosition = -1;
                        }else if(currentPosition > position){ // 点击已经展开的项目下面的项
                            //关闭展开项，打开点击项目，保存位置
                            collapse(currentPosition);
                            expand(position);
                            currentPosition = position;
                        }else {//点击已经展开的项目上面面的项
                            collapse(currentPosition);
                            int expandPosition = position - dataSize;
                            expand(expandPosition);
                            currentPosition = expandPosition;
                        }
                    }
                });
                initOrg(helper, item);
                break;
            case TYPE_RACE:
                initRace(helper, item);
                break;
        }
    }

    public abstract void initOrg(BaseViewHolder helper, MultiItemEntity item);

    public abstract void initRace(BaseViewHolder helper, MultiItemEntity item);

    public  List<MultiItemEntity> get(List<ExpendEntity> data) {
        List<MultiItemEntity> result = new ArrayList<>();
        if (data == null)
            return result;
        OrgItem orgItem;
        RaceItem raceItem;
        if (data.size() > 0) {
            for (int i = 0, len = data.size(); i < len; i++) {
                ExpendEntity orginfo = data.get(i);
                orgItem = new OrgItem(orginfo);
                if (orginfo.getRace() != null)
                    for (int j = 0, len2 = orginfo.getRace().size(); j < len2; j++) {
                        RaceEntity race = orginfo.getRace().get(j);
                        raceItem = new RaceItem(race);
                        orgItem.addSubItem(raceItem);
                    }

                result.add(orgItem);
            }
        }
        return result;
    }

    public class OrgItem extends AbstractExpandableItem<RaceItem> implements MultiItemEntity {

        ExpendEntity orgInfo;

        public OrgItem(ExpendEntity orgInfo) {
            this.orgInfo = orgInfo;
        }

        @Override
        public int getItemType() {
            return TYPE_ORG;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        public ExpendEntity getOrgInfo() {
            return orgInfo;
        }

    }

    public class RaceItem implements MultiItemEntity {
        public RaceEntity getRace() {
            return race;
        }

        RaceEntity race;

        public RaceItem(RaceEntity race) {
            this.race = race;
        }

        @Override
        public int getItemType() {
            return TYPE_RACE;
        }
    }

    public void setOnOrgItemClickListener(OnOrgItemClickListener onOrgItemClickListener) {
        this.onOrgItemClickListener = onOrgItemClickListener;
    }
}
