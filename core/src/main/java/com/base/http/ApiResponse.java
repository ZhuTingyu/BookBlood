package com.base.http;


import com.base.util.http.GsonUtil;

import java.io.Serializable;

/**
 * 网络数据返回结构
 * @param <T>
 */


public class ApiResponse<T> implements Serializable{

    /**
     * status : false
     * errorCode : 20002
     * msg :
     * data : null
     */

    public boolean status;
    public int errorCode;
    public String msg;
    public String listmsg; //列表数据为空
    public T data;
    public boolean isOk(){
        return errorCode == 0 ;
    }

    public String toJsonString() {
        return GsonUtil.toJson(this);
    }
}
