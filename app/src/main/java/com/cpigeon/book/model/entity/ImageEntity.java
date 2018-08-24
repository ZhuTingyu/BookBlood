package com.cpigeon.book.model.entity;

import com.base.util.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/24.
 */

public class ImageEntity {
    /**
     * imgurl : 图片地址
     */

    private String imgurl;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public static List<String> getUrls(List<ImageEntity> list) {
        List<String> imgs = Lists.newArrayList();
        for (ImageEntity entity : list) {
            imgs.add(entity.getImgurl());
        }
        return imgs;
    }
}
