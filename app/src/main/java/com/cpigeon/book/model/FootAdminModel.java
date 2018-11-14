package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.model.entity.FootRingStatEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/7.
 */

public class FootAdminModel {


    //hl 添加足环（单个）

    public static Observable<ApiResponse<Object>> getTXGP_FootRing_Add(String countryId, String foot, String money, String footType, String footSource, String remark) {

        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.foot_add_single)
                .addBody("codeid", countryId)
                .addBody("numstr", foot)//足环号码
                .addBody("codeid", countryId)//国家id
                .addBody("money", money)//足环金额
                .addBody("typeid", footType)// 足环类型
                .addBody("source", footSource)// 足环来源
                .addBody("reamrk", remark)// 备注
                .request();
    }

    //hl 修改足环（单个）
    public static Observable<ApiResponse<Object>> getTXGP_FootRing_Edit(String footId, String codeid, String foot, String money, String footType, String footSource, String remark,String wofootid ,String wopigeonid,String menfootid,String menpigeonid,String usenum,String pigeonid) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.foot_edit_single)

                .addBody("usenum", usenum)
                .addBody("pigeonid", pigeonid)
                .addBody("wofootid", wofootid)//母足环id
                .addBody("wopigeonid", wopigeonid)//公足环id
                .addBody("menfootid", menfootid)//母足环id
                .addBody("menpigeonid", menpigeonid)//公足环id
                .addBody("footid", footId)//足环id
                .addBody("codeid", codeid)//足环id
                .addBody("numstr", foot)//足环号码
                .addBody("money", money)//足环金额
                .addBody("typeid", footType)// 足环类型
                .addBody("source", footSource)// 足环来源
                .addBody("remark", remark)// 备注
                .request();
    }

    //hl 删除足环（单个）
    public static Observable<ApiResponse<Object>> getTXGP_FootRing_Delete(String delFootId) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.foot_del_single)
                .addBody("footid", delFootId)//足环id
                .request();
    }


    //hl 获取单个足环详细
    public static Observable<ApiResponse<FootEntity>> getTXGP_FootRing_Select(String footId) {
        return RequestData.<ApiResponse<FootEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<FootEntity>>() {
                }.getType())
                .url(R.string.foot_details_single)
                .addBody("footid", footId)
                .request();
    }


    //hl 添加足环号段
    public static Observable<ApiResponse<String>> addMultiFoot(
            String startFoot
            , String count
            , String typeId
            , String source
            , String cityCode
            , String money
            , String remark
    ) {
        return RequestData.<ApiResponse<String>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.foot_add_segment)
                .addBody("fromNum", startFoot)//开始足环号
                .addBody("count", count)//终止足环号
                .addBody("typeid", typeId)//类型
                .addBody("source", source)//来源
                .addBody("codeid", cityCode)//来源
                .addBody("money", money)//足环金额
                .addBody("remark", remark)//备注
                .request();
    }

    //hl 得到各种类型的足环个数
    public static Observable<ApiResponse<List<FootEntity>>> getTXGP_FootRing_SelectKeyAll(int pi, int ps, String year, String typeid, String stateid, String key) {
        return RequestData.<ApiResponse<List<FootEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<FootEntity>>>() {
                }.getType())
                .url(R.string.foot_list_all)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .addBody("yearstr", year)
                .addBody("typeidstr", typeid)
                .addBody("stateidstr", stateid)
                .addBody("key", key)
                .request();
    }

    public static Observable<ApiResponse<String>> deleteFoots(String sFootId, String eFootId
            , String sFootNumber, String eFootNumber) {
        return RequestData.<ApiResponse<String>>build()
                .setToJsonType(new TypeToken<ApiResponse<String>>() {
                }.getType())
                .url(R.string.delete_foots)
                .addBody("fromnumid", sFootId)
                .addBody("endnumid", eFootId)
                .addBody("fromnum", sFootNumber)
                .addBody("endnum", eFootNumber)
                .request();
    }

    public static Observable<ApiResponse<String>> modifyMultiFoot(
            String sFoodId
            , String eFoodId
            , String sFoodNumber
            , String eFoodNumber
            , String typeId
            , String source
            , String codeId
            , String money
            , String remark
    ) {
        return RequestData.<ApiResponse<String>>build()
                .setToJsonType(new TypeToken<ApiResponse<String>>() {
                }.getType())
                .url(R.string.modify_foots)
                .addBody("fromnumid", sFoodId)
                .addBody("endnumid", eFoodId)
                .addBody("fromnum", sFoodNumber)
                .addBody("endnum", eFoodNumber)
                .addBody("typeid", typeId)
                .addBody("source", source)
                .addBody("codeid", codeId)
                .addBody("money", money)
                .addBody("remark", remark)
                .request();
    }


    public static Observable<ApiResponse<FootRingStatEntity>> getFootRingStat() {
        return RequestData.<ApiResponse<FootRingStatEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<FootRingStatEntity>>() {
                }.getType())
                .url(R.string.get_foot_stat)
                .request();
    }

    public static Observable<ApiResponse<List<FootEntity>>> getFootList(
            int pi,
            int ps,
            String footNumber,
            String sexId,
            String staterId
    ) {
        return RequestData.<ApiResponse<List<FootEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<FootEntity>>>() {
                }.getType())
                .url(R.string.get_foot_list)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(20))
                .addBody("footnum", footNumber)
                .addBody("sexidstr", sexId)
                .addBody("stateid", staterId)
                .request();
    }
    public static Observable<ApiResponse<List<PigeonEntity>>> getFootList2(
            int pi,
            int ps,
            String footNumber,
            String sexId,
            String staterId
    ) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
                .url(R.string.pigeon_breed_sift)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(20))
                .addBody("footnum",footNumber)
                .addBody("sexid", sexId)
                .addBody("stateid", staterId)
                .request();
    }
}
