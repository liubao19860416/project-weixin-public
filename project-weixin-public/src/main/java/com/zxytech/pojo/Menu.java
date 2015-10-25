package com.zxytech.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 菜单封装实体bean
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class Menu {
    private Button[] button;

    public Button[] getButton() {
        return this.button;
    }

    public void setButton(Button[] button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}