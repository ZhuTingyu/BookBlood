package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.TrainEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class TrainPigeonModel {
    public static Observable<ApiResponse<List<TrainEntity>>> getTrainPigeonList(int pi, int ps) {
        return RequestData.<ApiResponse<List<TrainEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<TrainEntity>>>() {
                }.getType())
                .url(R.string.get_train_pigeon_list)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }
}
