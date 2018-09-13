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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getListmsg() {
        return listmsg;
    }

    public void setListmsg(String listmsg) {
        this.listmsg = listmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toJsonString() {
        return GsonUtil.toJson(this);
    }
}
