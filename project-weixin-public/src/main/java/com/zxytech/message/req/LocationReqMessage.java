package com.zxytech.message.req;

/**
 * 位置请求信息数据封装实体
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class LocationReqMessage extends BaseReqMessage {
    private String Location_X;
    private String Location_Y;
    private String Scale;
    private String Label;

    public String getLocation_X() {
        return this.Location_X;
    }

    public void setLocation_X(String location_X) {
        this.Location_X = location_X;
    }

    public String getLocation_Y() {
        return this.Location_Y;
    }

    public void setLocation_Y(String location_Y) {
        this.Location_Y = location_Y;
    }

    public String getScale() {
        return this.Scale;
    }

    public void setScale(String scale) {
        this.Scale = scale;
    }

    public String getLabel() {
        return this.Label;
    }

    public void setLabel(String label) {
        this.Label = label;
    }

}