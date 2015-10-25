package com.zxytech.message.req;

/**
 * 音频格式请求数据实体
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class VoiceReqMessage extends BaseReqMessage {
    private String MediaId;
    private String Format;

    public String getMediaId() {
        return this.MediaId;
    }

    public void setMediaId(String mediaId) {
        this.MediaId = mediaId;
    }

    public String getFormat() {
        return this.Format;
    }

    public void setFormat(String format) {
        this.Format = format;
    }

}