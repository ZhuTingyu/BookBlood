package com.cpigeon.book.event;

/**
 * Created by Zhu TingYu on 2018/11/12.
 */

public class WXPayEvent {

    int code;

    public WXPayEvent(int code){
        this.code = code;
    }

    public boolean isSueecse(){
        return code == 0;
    }
}
