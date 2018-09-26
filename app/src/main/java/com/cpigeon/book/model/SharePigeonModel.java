package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/9/26.
 */

public class SharePigeonModel {
    public static Observable<ApiResponse<List<PigeonEntity>>> getSharePigeon(
            int pi,//页面
            int ps,//每页条数
            String type,//是否是我的共享（1:我的共享鸽子，2：其他为所有共享鸽子，不传则返回所有共享鸽子）
            String blood,//血统（1,2）
            String sex,//性别（1,2）
            String eye,//眼沙（1,2）
            String footNum//足环号
    ) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
                .url(R.string.get_share_pigeons)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .addBody("type", type)
                .addBody("blood", blood)
                .addBody("sex", sex)
                .addBody("eye", eye)
                .addBody("footnum", footNum)
                .request();
    }
}
