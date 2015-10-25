package com.zxytech.message.req;

/**
 * 文本请求信息封装实体
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class TextReqMessage extends BaseReqMessage {
    private String Content;

    public String getContent() {
        return this.Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

}