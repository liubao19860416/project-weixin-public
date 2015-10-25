package com.zxytech.pojo;

/**
 * 普通按钮View菜单实体bean
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class CommonViewButton extends Button {
    private String type;
    private String url;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}