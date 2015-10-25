package com.zxytech.pojo;

/**
 * 普通按钮Click菜单创建实体bean
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class CommonClickButton extends Button {
    private String type;
    private String key;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}