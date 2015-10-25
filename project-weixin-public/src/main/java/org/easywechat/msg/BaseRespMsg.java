package org.easywechat.msg;

import org.easywechat.util.MessageBuilder;

public class BaseRespMsg {

    private String toUserName;
    private String fromUserName;
    private long createTime;
    private String msgType;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public BaseRespMsg() {
    }

    public String toXml() {
        // 159 = 106 + 28(ToUserName) + 15(FromUserName) + 10(CreateTime)
        MessageBuilder builder = new MessageBuilder(159);
        builder.addData("ToUserName", toUserName);
        builder.addData("FromUserName", fromUserName);
        builder.addTag("CreateTime",String.valueOf(System.currentTimeMillis() / 1000));
        return builder.toString();
    }

    @Override
    public String toString() {
        return toXml();
    }

}
