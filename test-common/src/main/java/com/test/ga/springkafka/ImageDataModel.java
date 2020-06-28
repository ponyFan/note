package com.test.ga.springkafka;

import com.alibaba.fastjson.JSON;

public class ImageDataModel {

    private String insertTime;

    private String image;

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
