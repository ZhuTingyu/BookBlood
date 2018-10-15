package com.cpigeon.book.module.select.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.PigeonPublicModel;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/15.
 */

public class SetPigeonDeathViewModel extends BaseViewModel {
    public List<SelectTypeEntity> mDeathReason;

    public String pigeonId;
    public String footId;
    public String stateId;
    public String deathId;

    public FootEntity mFootEntity;

    public void setPigeonDeath() {
        submitRequestThrowError(PigeonPublicModel.modifyPigeonStatue(mFootEntity.getPigeonID()
                , mFootEntity.getFootRingID(), stateId, deathId), r -> {
            if (r.isOk()) {
                normalResult.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }

    public void isCanCommit(){
        isCanCommit(deathId);
    }
}
