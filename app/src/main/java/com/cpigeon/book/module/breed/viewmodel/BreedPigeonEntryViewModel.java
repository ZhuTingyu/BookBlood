package com.cpigeon.book.module.breed.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.Lists;
import com.cpigeon.book.model.BreedPigeonModel;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 种鸽录入
 * Created by Administrator on 2018/8/28.
 */

public class BreedPigeonEntryViewModel extends BaseViewModel {


    public void addBreedPigeonEntry() {
        submitRequestThrowError(BreedPigeonModel.getTXGP_Pigeon_Add(countryId,
                foot,
                footVice,
                sourceId,
                footFather,
                footMother,
                pigeonName,
                sexId,
                featherColor,
                eyeSandId,
                theirShellsDate,
                lineageId,
                stateId,
                phototypeid,
                setImageMap()), r -> {

            if (r.isOk()) {
                hintDialog(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    //图片
    public List<String> images = Lists.newArrayList();
    public String phototypeid;


    //国家
    public String countryId = "1";//国家id

    //足环号
    public String foot;

    //副环
    public String footVice;

    //来源
    public List<SelectTypeEntity> mSelectTypes_Source;
    public String sourceId;

    //父足环
    public String footFather;

    //母足环
    public String footMother;

    //鸽名
    public String pigeonName;

    //性别
    public List<SelectTypeEntity> mSelectTypes_Sex;
    public String sexId;

    //羽色
    public List<SelectTypeEntity> mSelectTypes_FeatherColor;
    public String featherColor;

    //眼砂
    public List<SelectTypeEntity> mSelectTypes_EyeSand;
    public String eyeSandId;

    //出壳日期
    public String theirShellsDate;

    //血统
    public List<SelectTypeEntity> mSelectTypes_Lineage;
    public String lineageId;

    //状态
    public List<SelectTypeEntity> mSelectTypes_State;
    public String stateId;

    //图片类型
    public List<SelectTypeEntity> mSelectTypes_ImgType;
    public String imgTypeStr = "";
    public String imgTypeId = "";


    public HashMap<String, String> setImageMap() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0, len = images.size(); i < len; i++) {
            map.put("photo", images.get(i));
        }
        return map;
    }

}
