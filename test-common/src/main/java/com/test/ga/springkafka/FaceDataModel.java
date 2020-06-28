package com.test.ga.springkafka;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class FaceDataModel {

    private String function;

    private String ip;

    private String insertTime;

    private List<ImageDataModel> data;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public List<ImageDataModel> getData() {
        return data;
    }

    public void setData(List<ImageDataModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
