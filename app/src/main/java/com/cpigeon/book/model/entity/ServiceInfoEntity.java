package com.cpigeon.book.model.entity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/29.
 */

public class ServiceInfoEntity {

    /**
     * services : [{"sname":"服务名称","sid":"服务ID"}]
     * services_no : ["未开通的服务"]
     * yu : 帐户余额
     * gb : 我的鸽币
     */

    private double yu;
    private double gb;
    private List<ServiceEntity> services;
    private List<ServiceEntity> services_no;

    public double getYu() {
        return yu;
    }

    public void setYu(double yu) {
        this.yu = yu;
    }

    public double getGb() {
        return gb;
    }

    public void setGb(double gb) {
        this.gb = gb;
    }

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }

    public List<ServiceEntity> getServices_no() {
        return services_no;
    }

    public void setServices_no(List<ServiceEntity> services_no) {
        this.services_no = services_no;
    }
}
