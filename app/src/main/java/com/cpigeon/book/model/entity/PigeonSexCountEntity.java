package com.cpigeon.book.model.entity;

import com.base.util.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/17.
 */

public class PigeonSexCountEntity {
    public int ciCount;//雌鸽数量
    public int xiongCount;//雄鸽数量
    public int allCount;//全部鸽子数量
    public int youCount;//幼鸽数量 ，
    public int Count;//全部鸽子数量
    public int ZciCount;//种鸽雌鸽数量,
    public int ZxiongCount;//种鸽雄鸽数量 ，
    public int ZyouCount; //种鸽幼鸽数量 ，
    public int ZCount;//种鸽鸽子数量
    public int SciCount;//赛鸽雌鸽数量,
    public int SxiongCount; //赛鸽雄鸽数量 ，
    public int SyouCount; //赛鸽幼鸽数量 ，
    public int SCount;//赛鸽鸽子数量
    public int YciCount;//幼鸽雌鸽数量,
    public int YxiongCount; //幼鸽雄鸽数量 ，
    public int YyouCount; //幼鸽幼鸽数量 ，
    public int YCount; //幼鸽鸽子数量 }

    public int XCount;//小鸽子数量,
    public int QCount; //成年鸽数量,
    public int LCount; //老年鸽数量，
    public int CSount; //长寿鸽数量，
    public int QTCount;//其他不知年龄个数量


    public List<Integer> getBreedPigeonStat() {
        return Lists.newArrayList(ZCount, ZxiongCount, ZciCount, XCount, QCount, LCount, CSount);
    }

}
