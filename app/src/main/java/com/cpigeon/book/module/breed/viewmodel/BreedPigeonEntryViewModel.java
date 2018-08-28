package com.cpigeon.book.module.breed.viewmodel;

import com.base.base.BaseViewModel;
import com.base.util.Lists;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 种鸽录入
 * Created by Administrator on 2018/8/28.
 */

public class BreedPigeonEntryViewModel extends BaseViewModel {

    public List<String> images = Lists.newArrayList();

    public List<SelectTypeEntity> mSelectTypes_Sex;
    public String sexId ;


    public List<SelectTypeEntity> mSelectTypes_FeatherColor;
    public String featherColorId ;


    public List<SelectTypeEntity> mSelectTypes_EyeSand;
    public String eyeSandId ;


    public HashMap<String,String> setImageMap() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0,len = images.size(); i < len; i++) {
            map.put("pic"+ i, images.get(i));
        }
        return map;
    }

}
