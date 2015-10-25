package com.zxytech.message.req;

/**
 * 图片格式数据请求实体
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class ImageReqMessage extends BaseReqMessage {
    private String PicUrl;

    public String getPicUrl() {
        return this.PicUrl;
    }

    public void setPicUrl(String picUrl) {
        this.PicUrl = picUrl;
    }

}