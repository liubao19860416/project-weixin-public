package org.easywechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.easywechat.msg.BaseRespMsg;
import org.easywechat.msg.req.BaseReq;
import org.easywechat.msg.req.BaseReqEvent;
import org.easywechat.msg.req.BaseReqMsg;
import org.easywechat.msg.req.ImageReqMsg;
import org.easywechat.msg.req.LinkReqMsg;
import org.easywechat.msg.req.LocationReqEvent;
import org.easywechat.msg.req.LocationReqMsg;
import org.easywechat.msg.req.MenuReqEvent;
import org.easywechat.msg.req.QrCodeReqEvent;
import org.easywechat.msg.req.ReqEventType;
import org.easywechat.msg.req.ReqMsgType;
import org.easywechat.msg.req.TextReqMsg;
import org.easywechat.msg.req.VideoReqMsg;
import org.easywechat.msg.req.VoiceReqMsg;
import org.easywechat.util.MessageUtil;

import com.zxytech.util.Constants;
import com.zxytech.util.SignUtil;

/**
 * 处理GET和POST请求和转发,抽象类
 * 
 * @author Liubao
 * @2015年7月11日
 * 
 */
public abstract class WeixinServlet extends HttpServlet {

    private static final long serialVersionUID = -2219853756098148261L;

    //接收GET请求
    @SuppressWarnings("deprecation")
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        //添加跳转测试页面
        String dispatcher = request.getParameter("dispatcher");
        String wechat_webview_type = request.getParameter("wechat_webview_type");
        if(StringUtils.isNotBlank(dispatcher)&&"1".equals(dispatcher)){
//            response.sendRedirect("dispatcher.html");
            response.sendRedirect("dispatcher.jsp");
//            request.getRequestDispatcher("dispatcher.jsp").forward(request, response);
            return;
        }
        
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (validateRequest(request)) {
                out.print(request.getParameter("echostr"));
            } else {
                out.print("WeixinServlet==>Token message is not right at time ["+new Date().toLocaleString()+"].");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null!=out){
                out.close();
                out = null;
            }
        }
    }
    
    //接收POST请求
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (!validateRequest(request)) {
            return;
        }
        
        //处理POST请求
        String resp = processRequest(request);

        PrintWriter out = response.getWriter();
        out.print(resp);
        out.close();
    }
    
    //请求信息校验等
    private boolean validateRequest(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        return SignUtil.checkSignature(Constants.APP_TOKEN, signature, timestamp, nonce);
    }

    //处理POST请求
    private String processRequest(HttpServletRequest request) {
        Map<String, String> reqMap = MessageUtil.parseXml(request);
        String fromUserName = reqMap.get("FromUserName");
        String toUserName = reqMap.get("ToUserName");
        String msgType = reqMap.get("MsgType");
        
        // 要返回给用户的消息实体
        BaseRespMsg msg = null;

        // 事件推送
        if (msgType.equals(ReqMsgType.EVENT)) {
            String eventType = reqMap.get("Event");
            String ticket = reqMap.get("Ticket");
            if (ticket != null) {
                String eventKey = reqMap.get("EventKey");
                QrCodeReqEvent event = new QrCodeReqEvent(eventKey, ticket);
                buildBasicEvent(reqMap, event);
                //二维码事件
                msg = handleQrCodeEvent(event);
            }else if (eventType.equals(ReqEventType.SUBSCRIBE)) {
                BaseReqEvent event = new BaseReqEvent();
                buildBasicEvent(reqMap, event);
                //订阅事件
                msg = handleSubscribe(event);
            } else if (eventType.equals(ReqEventType.UNSUBSCRIBE)) {
                BaseReqEvent event = new BaseReqEvent();
                buildBasicEvent(reqMap, event);
                //取消订阅事件
                msg = handleUnsubscribe(event);
            } else if (eventType.equals(ReqEventType.CLICK)) {
                String eventKey = reqMap.get("EventKey");
                MenuReqEvent event = new MenuReqEvent(eventKey);
                buildBasicEvent(reqMap, event);
                //点击菜单拉取消息时的事件推送
                msg = handleMenuClickEvent(event);
            } else if (eventType.equals(ReqEventType.VIEW)) {
                String eventKey = reqMap.get("EventKey");
                MenuReqEvent event = new MenuReqEvent(eventKey);
                buildBasicEvent(reqMap, event);
                //点击菜单跳转链接时的事件推送
                msg = handleMenuViewEvent(event);
            } else if (eventType.equals(ReqEventType.LOCATION)) {
                double latitude = Double.parseDouble(reqMap.get("Latitude"));
                double longitude = Double.parseDouble(reqMap.get("Longitude"));
                double precision = Double.parseDouble(reqMap.get("Precision"));
                LocationReqEvent event = new LocationReqEvent(latitude, longitude,
                        precision);
                buildBasicEvent(reqMap, event);
                //上报地理位置事件
                msg = handleLocationEvent(event);
            }
        } else {
            // 接受普通消息
            if (msgType.equals(ReqMsgType.TEXT)) {
                String content = reqMap.get("Content");
                TextReqMsg textReqMsg = new TextReqMsg(content);
                buildBasicReqMsg(reqMap, textReqMsg);
                // 文本消息
                msg = handleTextMsg(textReqMsg);
            } else if (msgType.equals(ReqMsgType.IMAGE)) {
                String picUrl = reqMap.get("PicUrl");
                String mediaId = reqMap.get("MediaId");
                ImageReqMsg imageReqMsg = new ImageReqMsg(picUrl, mediaId);
                buildBasicReqMsg(reqMap, imageReqMsg);
                // 图片消息
                msg = handleImageMsg(imageReqMsg);
            } else if (msgType.equals(ReqMsgType.VOICE)) {
                String format = reqMap.get("Format");
                String mediaId = reqMap.get("MediaId");
                String recognition = reqMap.get("Recognition");
                VoiceReqMsg voiceReqMsg = new VoiceReqMsg(mediaId, format,
                        recognition);
                buildBasicReqMsg(reqMap, voiceReqMsg);
                // 音频消息
                msg = handleVoiceMsg(voiceReqMsg);
            } else if (msgType.equals(ReqMsgType.SHORTVIDEO)) {
                String thumbMediaId = reqMap.get("ThumbMediaId");
                String mediaId = reqMap.get("MediaId");
                VideoReqMsg videoReqMsg = new VideoReqMsg(mediaId, thumbMediaId);
                buildBasicReqMsg(reqMap, videoReqMsg);
                // 视频消息    
                msg = handleVideoMsg(videoReqMsg);
            }  else if (msgType.equals(ReqMsgType.VIDEO)) {
                String thumbMediaId = reqMap.get("ThumbMediaId");
                String mediaId = reqMap.get("MediaId");
                VideoReqMsg videoReqMsg = new VideoReqMsg(mediaId, thumbMediaId);
                buildBasicReqMsg(reqMap, videoReqMsg);
                // 视频消息    
                msg = handleVideoMsg(videoReqMsg);
            } else if (msgType.equals(ReqMsgType.LOCATION)) {
                double locationX = Double.parseDouble(reqMap.get("Location_X"));
                double locationY = Double.parseDouble(reqMap.get("Location_Y"));
                int scale = Integer.parseInt(reqMap.get("Scale"));
                String label = reqMap.get("Label");
                LocationReqMsg locationReqMsg = new LocationReqMsg(locationX,
                        locationY, scale, label);
                buildBasicReqMsg(reqMap, locationReqMsg);
                // 地理位置消息
                msg = handleLocationMsg(locationReqMsg);
            } else if (msgType.equals(ReqMsgType.LINK)) {
                String title = reqMap.get("Title");
                String description = reqMap.get("Description");
                String url = reqMap.get("Url");
                LinkReqMsg linkReqMsg = new LinkReqMsg(title, description, url);
                buildBasicReqMsg(reqMap, linkReqMsg);
                // 链接消息
                msg = handleLinkMsg(linkReqMsg);
            }
        }

        if (msg == null) {
            return "";
        }

        msg.setFromUserName(toUserName);
        msg.setToUserName(fromUserName);
        
        //返回xml格式字符串
        return msg.toXml();
    }
    

    //=======================事件推送消息和普通消息request请求实体封装实现部分============================
    
    //处理请求消息到请求实体中
    private void buildBasicReqMsg(Map<String, String> reqMap, BaseReqMsg reqMsg) {
        addBasicReqParams(reqMap, reqMsg);
        reqMsg.setMsgId(reqMap.get("MsgId"));
    }

    //处理请求事件到请求实体中
    private void buildBasicEvent(Map<String, String> reqMap, BaseReqEvent event) {
        addBasicReqParams(reqMap, event);
        event.setEvent(reqMap.get("Event"));
    }

    /**
     * 最终调用的封装request请求信息到对应的请求实体中去的方法
     * 设置4个默认的属性参数信息
     */
    private void addBasicReqParams(Map<String, String> reqMap, BaseReq req) {
        req.setMsgType(reqMap.get("MsgType"));
        req.setFromUserName(reqMap.get("FromUserName"));
        req.setToUserName(reqMap.get("ToUserName"));
        req.setCreateTime(Long.parseLong(reqMap.get("CreateTime")));
    }
    
    
    //=======================事件推送消息和普通消息默认处理实现部分,可以被子类复写的方法============================
    
    /**
     * 如果不复写该方法,不返回任何消息
     */
    //事件推送消息默认处理实现 TODO 
    protected BaseRespMsg handleDefaultEvent(BaseReqEvent event) {
        return null;
    }
    
    //普通消息默认处理实现 
    protected BaseRespMsg handleDefaultMsg(BaseReqMsg msg) {
        return null;
    }
    
    
    
    
    //=======================普通消息处理部分,重点被子类复写的方法============================

    //子类实现
    protected BaseRespMsg handleTextMsg(TextReqMsg msg) {
        return null;
    }

    //子类实现
    protected BaseRespMsg handleImageMsg(ImageReqMsg msg) {
        return null;
    }

    //子类实现
    protected BaseRespMsg handleVoiceMsg(VoiceReqMsg msg) {
        return null;
    }

    //子类实现
    protected BaseRespMsg handleVideoMsg(VideoReqMsg msg) {
        return null;
    }

    //子类实现
    protected BaseRespMsg handleLocationMsg(LocationReqMsg msg) {
        return null;
    }

    //子类实现
    protected BaseRespMsg handleLinkMsg(LinkReqMsg msg) {
        return null;
    }
    
    
    //=======================事件推送消息处理部分,重点被子类复写的方法============================

    //子类实现
    protected BaseRespMsg handleQrCodeEvent(QrCodeReqEvent event) {
        return null;
    }

    //子类实现
    protected BaseRespMsg handleSubscribe(BaseReqEvent event) {
        return null;
    }

    //子类实现
    protected BaseRespMsg handleUnsubscribe(BaseReqEvent event) {
        return null;
    }
    
    //子类实现
    protected BaseRespMsg handleMenuClickEvent(MenuReqEvent event) {
        return null;
    }

    //子类实现
    protected BaseRespMsg handleMenuViewEvent(MenuReqEvent event) {
        return null;
    }

    //子类实现
    protected BaseRespMsg handleLocationEvent(LocationReqEvent event) {
        return null;
    }
    
    
}
