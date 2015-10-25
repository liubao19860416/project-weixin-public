package org.easywechat.msg;

import org.easywechat.util.MessageBuilder;

public class ImageRespMsg extends BaseRespMsg {

    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public ImageRespMsg() {
    }

    public ImageRespMsg(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toXml() {
        MessageBuilder mb = new MessageBuilder(super.toXml());
        mb.addData("MsgType", RespMsgType.IMAGE);
        mb.append("<Image>\n");
        mb.addData("MediaId", mediaId);
        mb.append("</Image>\n");
        mb.surroundWith("xml");
        return mb.toString();
    }

    @Override
    public String toString() {
        return "ImageMsg [mediaId=" + mediaId + "]";
    }

}
