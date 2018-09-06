package com.base.entity;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/3/5.
 */

public abstract class ExpendEntity{
   public  abstract <T extends RaceEntity> List<T> getRace();
}
