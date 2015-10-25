package com.zxytech.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Token相关信息实体bean
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class AccessToken implements Serializable {
    
    private static final long serialVersionUID = -20763648677531974L;
    
    private String token;
    private long expiresIn;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}