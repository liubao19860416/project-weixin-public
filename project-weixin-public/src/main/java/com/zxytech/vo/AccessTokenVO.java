package com.zxytech.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.zxytech.pojo.AccessToken;

/**
 * Token相关信息实体bean
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class AccessTokenVO implements Serializable {
    
    private static final long serialVersionUID = 6847283217271544752L;
    
    private long id;
    private String token;
    private long expiresIn;
    private long usersAppId;
    private Timestamp createdDatetime;
    private Timestamp experiedDatetime;
    
    public long getUsersAppId() {
        return usersAppId;
    }

    public void setUsersAppId(long usersAppId) {
        this.usersAppId = usersAppId;
    }

    public Timestamp getExperiedDatetime() {
        return experiedDatetime;
    }

    public void setExperiedDatetime(Timestamp experiedDatetime) {
        this.experiedDatetime = experiedDatetime;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}