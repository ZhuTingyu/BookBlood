package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/8/8.
 */

public class LogbookEntity {


    /**
     * UserOperateId : 55778
     * UserId : 5
     * UserOperateIP : 192.168.0.43
     * UserOperateType : 登录
     * UserOperateDatetime : 2018-08-03 16:19:58
     * Remark : Android APP登录
     * OperateTable :
     * OperateId :
     * OperateTag :
     */

    private int UserOperateId;
    private int UserId;
    private String UserOperateIP;
    private String UserOperateType;
    private String UserOperateDatetime;
    private String Remark;
    private String OperateTable;
    private String OperateId;
    private String OperateTag;

    public int getUserOperateId() {
        return UserOperateId;
    }

    public void setUserOperateId(int UserOperateId) {
        this.UserOperateId = UserOperateId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getUserOperateIP() {
        return UserOperateIP;
    }

    public void setUserOperateIP(String UserOperateIP) {
        this.UserOperateIP = UserOperateIP;
    }

    public String getUserOperateType() {
        return UserOperateType;
    }

    public void setUserOperateType(String UserOperateType) {
        this.UserOperateType = UserOperateType;
    }

    public String getUserOperateDatetime() {
        return UserOperateDatetime;
    }

    public void setUserOperateDatetime(String UserOperateDatetime) {
        this.UserOperateDatetime = UserOperateDatetime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getOperateTable() {
        return OperateTable;
    }

    public void setOperateTable(String OperateTable) {
        this.OperateTable = OperateTable;
    }

    public String getOperateId() {
        return OperateId;
    }

    public void setOperateId(String OperateId) {
        this.OperateId = OperateId;
    }

    public String getOperateTag() {
        return OperateTag;
    }

    public void setOperateTag(String OperateTag) {
        this.OperateTag = OperateTag;
    }
}
