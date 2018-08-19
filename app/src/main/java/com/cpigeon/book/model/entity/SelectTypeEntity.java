package com.cpigeon.book.model.entity;

import com.base.util.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/19.
 */

public class SelectTypeEntity {

    /**
     * TypeName : 特比环
     * TypeID : 4
     */

    private String TypeName;
    private String TypeID;

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getTypeID() {
        return TypeID;
    }

    public void setTypeID(String TypeID) {
        this.TypeID = TypeID;
    }

    public static List<String> getTypeNames(List<SelectTypeEntity> data){
        List<String> names = Lists.newArrayList();
        for (SelectTypeEntity entity : data) {
            names.add(entity.getTypeName());
        }
        return names;
    }
 }
