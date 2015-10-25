package org.easywechat.msg.req;

import org.easywechat.util.MessageBuilder;

public class BaseReqEvent extends BaseReq {

    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public BaseReqEvent() {
        setMsgType(ReqMsgType.EVENT);
    }

    @Override
    public String toXml() {
        MessageBuilder mb = new MessageBuilder(super.toXml());
        mb.addData("MsgType", ReqMsgType.EVENT);
        mb.addData("Event", event);
        return mb.toString();
    }

}
