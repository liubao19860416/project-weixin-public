package com.zxytech.pojo;

/**
 * 复合按钮实体bean
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class ComplexButton extends Button {
    private Button[] sub_button;

    public Button[] getSub_button() {
        return this.sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }

}