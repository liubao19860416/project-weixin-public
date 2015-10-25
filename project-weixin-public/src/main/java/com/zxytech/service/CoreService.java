package com.zxytech.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zxytech.message.resp.Article;
import com.zxytech.message.resp.NewsRespMessage;
import com.zxytech.message.resp.TextRespMessage;
import com.zxytech.util.MessageUtil;
import com.zxytech.util.MyStringUtils;

/**
 * 核心业务处理操作类
 * 
 * @author Liubao
 * @2015年6月30日
 * 
 */
public class CoreService {
    
    public static void main(String[] args) {
        System.out.println(emoji(128697));
    }
    
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            String respContent = "请求处理异常，请稍候尝试！";
            //封装数据到map中,方便后面的ui数据进行获取等操作
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            
            //获取基本数据信息
            String fromUserName = MyStringUtils.getStringValue(requestMap.get("FromUserName"));
            String toUserName = MyStringUtils.getStringValue(requestMap.get("ToUserName"));
            String msgType = MyStringUtils.getStringValue(requestMap.get("MsgType"));
            
            TextRespMessage textMessage = new TextRespMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType("text");
            textMessage.setFuncFlag(0);

            //文本消息处理
            if (msgType.equals("text")) {
                String content = (String) requestMap.get("Content");
                NewsRespMessage newsMessage = new NewsRespMessage();
                newsMessage.setToUserName(fromUserName);
                newsMessage.setFromUserName(toUserName);
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setMsgType("news");
                newsMessage.setFuncFlag(0);
                List<Article> articleList = new ArrayList<Article>();
                if ("1".equals(content)) {
                    Article article = new Article();
                    article.setTitle("微信公众帐号开发教程Java版");
                    article.setDescription("柳峰，80后，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列教程，也希望借此机会认识更多同行！");
                    article.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
                    article.setUrl("http://blog.csdn.net/lyq8479");
                    articleList.add(article);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                } else if ("2".equals(content)) {
                    Article article = new Article();
                    article.setTitle("微信公众帐号开发教程Java版");
                    article.setDescription("柳峰，80后，"
                            + emoji(128697)
                            + "，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");
                    article.setPicUrl("");
                    article.setUrl("http://blog.csdn.net/lyq8479");
                    articleList.add(article);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                } else if ("3".equals(content)) {
                    Article article1 = new Article();
                    article1.setTitle("微信公众帐号开发教程\n引言");
                    article1.setDescription("");
                    article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
                    article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");
                    Article article2 = new Article();
                    article2.setTitle("第2篇\n微信公众帐号的类型");
                    article2.setDescription("");
                    article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyqjpg");
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");
                    Article article3 = new Article();
                    article3.setTitle("第3篇\n开发模式启用及接口配置");
                    article3.setDescription("");
                    article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyqjpg");
                    article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");

                    articleList.add(article1);
                    articleList.add(article2);
                    articleList.add(article3);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                } else if ("4".equals(content)) {
                    Article article1 = new Article();
                    article1.setTitle("微信公众帐号开发教程Java版");
                    article1.setDescription("");
                    article1.setPicUrl("");
                    article1.setUrl("http://blog.csdn.net/lyq8479");

                    Article article2 = new Article();
                    article2.setTitle("第4篇\n消息及消息处理工具的封装");
                    article2.setDescription("");
                    article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyqjpg");
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");

                    Article article3 = new Article();
                    article3.setTitle("第5篇\n各种消息的接收与响应");
                    article3.setDescription("");
                    article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyqjpg");
                    article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");

                    Article article4 = new Article();
                    article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");
                    article4.setDescription("");
                    article4.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyqjpg");
                    article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");

                    articleList.add(article1);
                    articleList.add(article2);
                    articleList.add(article3);
                    articleList.add(article4);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                } else if ("5".equals(content)) {
                    Article article1 = new Article();
                    article1.setTitle("第7篇\n文本消息中换行符的使用");
                    article1.setDescription("");
                    article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
                    article1.setUrl("http://blog.csdn.net/lyq8479/article/details/9141467");

                    Article article2 = new Article();
                    article2.setTitle("第8篇\n文本消息中使用网页超链接");
                    article2.setDescription("");
                    article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyqjpg");
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/9157455");

                    Article article3 = new Article();
                    article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");
                    article3.setDescription("");
                    article3.setPicUrl("");
                    article3.setUrl("http://blog.csdn.net/lyq8479");

                    articleList.add(article1);
                    articleList.add(article2);
                    articleList.add(article3);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                }
             //其他消息类型处理
            } else if (msgType.equals("image")) {
                respContent = "您发送的是图片消息！";
                //TODO 
                
                
            } else if (msgType.equals("location")) {
                respContent = "您发送的是地理位置消息！";
                //TODO
                
                
            } else if (msgType.equals("link")) {
                respContent = "您发送的是链接消息！";
                // TODO 
                
                
            } else if (msgType.equals("voice")) {
                respContent = "您发送的是音频消息！";
                
                
                //事件类型处理
            } else if (msgType.equals("event")) {
                String eventType = (String) requestMap.get("Event");
                if (eventType.equals("subscribe")) {
                    respContent = "谢谢您的关注！";
                    //TODO 
                    
                    
                } else if (!eventType.equals("unsubscribe")) {
                    if (eventType.equals("CLICK")) {
                        String eventKey = (String) requestMap.get("EventKey");
                        if (eventKey.equals("11")){
                            respContent = "天气预报菜单项被点击！";
                        }
                        else if (eventKey.equals("12")){
                            respContent = "公交查询菜单项被点击！";
                        }
                        else if (eventKey.equals("13")){
                            respContent = "周边搜索菜单项被点击！";
                        }
                        else if (eventKey.equals("14")){
                            respContent = "历史上的今天菜单项被点击！";
                        }
                        else if (eventKey.equals("21")){
                            respContent = "歌曲点播菜单项被点击！";
                        }
                        else if (eventKey.equals("22")){
                            respContent = "经典游戏菜单项被点击！";
                        }
                        else if (eventKey.equals("23")){
                            respContent = "美女电台菜单项被点击！";
                        }
                        else if (eventKey.equals("24")){
                            respContent = "人脸识别菜单项被点击！";
                        }
                        else if (eventKey.equals("25")){
                            respContent = "聊天唠嗑菜单项被点击！";
                        }
                        else if (eventKey.equals("31")){
                            respContent = "Q友圈菜单项被点击！";
                        }
                        else if (eventKey.equals("32")){
                            respContent = "电影排行榜菜单项被点击！";
                        }
                        else if (eventKey.equals("33")) {
                            respContent = "幽默笑话菜单项被点击！";
                        }
                    }
                }
            }
            
            //设置消息内容
            textMessage.setContent(respContent);
            
            //转换成sml格式字符串数据进行返回给微信服务器
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }
    
    
    /**
     * 将Unicode转换为UTF-8
     */
    private static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }
}