package com.bjpowernode.crm.common.domain;

/**
 * Copyright (C), 2017-2022
 * <author>          <time>              <version>
 * 冯俊        2022/8/23 22:05    since 1.0.0
 */
public class ReturnObject {
    private String code;//成功和失败的标记 0--1
    private String message;//返回信息 登录失败原因
    private Object retData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }
}
