package org.easywechat.msg;

import org.easywechat.util.MessageBuilder;

public final class TextRespMsg extends BaseRespMsg {

    private StringBuilder contentBuilder;

    //构造方法
    public TextRespMsg() {
        contentBuilder = new StringBuilder();
    }

    
    public TextRespMsg(String content) {
        setContent(content);
    }
    

    public String getContent() {
        return contentBuilder.toString();
    }

    public void setContent(String content) {
        contentBuilder = new StringBuilder(content);
    }

    
    //===============其他方法=================
    
    
    public TextRespMsg add(String text) {
        contentBuilder.append(text);
        return this;
    }

    //添加换行格式符号
    public TextRespMsg addln() {
        return add("\n");
    }

    public TextRespMsg addln(String text) {
        contentBuilder.append(text);
        return addln();
    }

    //添加图片网址等链接格式符号
    public TextRespMsg addLink(String text, String url) {
        contentBuilder.append("<a href=\"").append(url).append("\">")
                .append(text).append("</a>");
        return this;
    }

    @Override
    public String toXml() {
        MessageBuilder mb = new MessageBuilder(super.toXml());
        mb.addData("Content", contentBuilder.toString());
        mb.addData("MsgType", RespMsgType.TEXT);
        mb.surroundWith("xml");
        return mb.toString();
    }

    @Override
    public String toString() {
        return "TextMsg [content=" + getContent() + "]";
    }

}
