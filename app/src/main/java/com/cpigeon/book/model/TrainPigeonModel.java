package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PigeonEntity;
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

    public static Observable<ApiResponse<List<PigeonEntity>>> getAllPigeons(int pi
            , int ps
            , String typeId
            , String blood
            , String sex
            , String year
            , String state
            , String footNumber
    ) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
                .url(R.string.get_all_pigeon_list)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .addBody("typeid", typeId)
                .addBody("blood", blood)
                .addBody("sex", sex)
                .addBody("year", year)
                .addBody("State", state)
                .addBody("footnum", footNumber)
                .request();
    }
}
