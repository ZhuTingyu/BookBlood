package com.cpigeon.book.module.breed.viewmodel;

import com.base.base.BaseViewModel;
import com.base.util.Lists;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/7.
 */

public class BasePigeonViewModel extends BaseViewModel {

    //图片
    public List<String> images = Lists.newArrayList();
    public String phototypeid;

    //国家
    public String countryId = "2";//国家id

    //足环号
    public String foot = "";


    //副环
    public String footVice = "";

    //来源
    public List<SelectTypeEntity> mSelectTypes_Source;
    public String sourceId = "";

    //父足环
    public String footFather = "";

    //母足环
    public String footMother = "";

    //鸽名
    public String pigeonName = "";

    //性别
    public List<SelectTypeEntity> mSelectTypes_Sex;
    public String sexId = "";

    //羽色
    public List<SelectTypeEntity> mSelectTypes_FeatherColor;
    public String featherColor = "";

    //眼砂
    public List<SelectTypeEntity> mSelectTypes_EyeSand;
    public String eyeSandId = "";

    //出壳日期
    public String theirShellsDate = "";

    //血统
    public List<SelectTypeEntity> mSelectTypes_Lineage;
    public String lineage = "";

    //状态
    public List<SelectTypeEntity> mSelectTypes_State;
    public String stateId = "";

    //图片类型
    public List<SelectTypeEntity> mSelectTypes_ImgType;
    public String imgTypeStr = "";
    public String imgTypeId = "";

}
