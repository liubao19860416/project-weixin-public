package com.zxytech.message.resp;

/**
 * 文本响应数据信息封装实体
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class TextRespMessage extends BaseRespMessage {
    private String Content;

    public String getContent() {
        return this.Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }
}