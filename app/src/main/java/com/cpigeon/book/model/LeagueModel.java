package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.LeagueDetailsEntity;
import com.cpigeon.book.model.entity.LeagueStatEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/9/25.
 */

public class LeagueModel {
    public static Observable<ApiResponse<LeagueStatEntity>> getLeagueStatEntity(String foodNumber) {
        return RequestData.<ApiResponse<LeagueStatEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<LeagueStatEntity>>() {
                }.getType())
                    .url(R.string.get_pigeon_league_stat)
                .addBody("footnum", foodNumber)//足环id
                .request();
    }

    public static Observable<ApiResponse<List<LeagueDetailsEntity>>> getLeagueDetails(String pigeonId,String foodId) {
        return RequestData.<ApiResponse<List<LeagueDetailsEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<LeagueDetailsEntity>>>() {
                }.getType())
                .url(R.string.get_pigeon_league_details)
                .addBody("pigeonid", pigeonId)//足环id
                .addBody("footid", foodId)//足环id
                .request();
    }
}
