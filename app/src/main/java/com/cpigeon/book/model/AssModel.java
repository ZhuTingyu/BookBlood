package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.AssEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class AssModel {
    public static Observable<ApiResponse<List<AssEntity>>> getAssList() {
        return RequestData.<ApiResponse<List<AssEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<AssEntity>>>() {
                }.getType())
                .url(R.string.get_ass_list)
                .request();
    }
}
