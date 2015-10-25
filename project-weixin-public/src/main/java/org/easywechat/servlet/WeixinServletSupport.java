package org.easywechat.servlet;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.easywechat.msg.Article;
import org.easywechat.msg.BaseRespMsg;
import org.easywechat.msg.ImageRespMsg;
import org.easywechat.msg.NewsRespMsg;
import org.easywechat.msg.TextRespMsg;
import org.easywechat.msg.req.BaseReqEvent;
import org.easywechat.msg.req.BaseReqMsg;
import org.easywechat.msg.req.ImageReqMsg;
import org.easywechat.msg.req.LinkReqMsg;
import org.easywechat.msg.req.LocationReqEvent;
import org.easywechat.msg.req.LocationReqMsg;
import org.easywechat.msg.req.MenuReqEvent;
import org.easywechat.msg.req.QrCodeReqEvent;
import org.easywechat.msg.req.TextReqMsg;
import org.easywechat.msg.req.VideoReqMsg;
import org.easywechat.msg.req.VoiceReqMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxytech.pojo.AccessToken;
import com.zxytech.util.Constants;
import com.zxytech.util.DatetimeUtils;
import com.zxytech.util.MessageUtil;
import com.zxytech.util.MyStringUtils;
import com.zxytech.util.WeixinUtil;
import com.zxytech.vo.AccessTokenVO;

/**
 * 定义对应的解析方法,其中处理POST请求和转发的部分在父类中已经实现
 * 
 * 事件推送消息和普通消息不同的处理方式,具体的实现逻辑处理
 * 
 * @author Liubao
 * @2015年7月11日
 * 
 */
@SuppressWarnings("unused")
public class WeixinServletSupport extends WeixinServlet {

    private static final long serialVersionUID = -5725395050509984825L;
    
    private Logger logger=LoggerFactory.getLogger(WeixinServletSupport.class);
    
    //===========================消息相关私有方法==================================
    
    //回应用户发送的文本消息 TODO
    private BaseRespMsg onText(String content) {
        BaseRespMsg msg= null;
        //关键字自动回复
        if(Constants.keyWordsList.contains(content)){
            StringBuilder stringBuilder=new StringBuilder();
            //根据关键字,自动回复以下信息
            String currentTimestampStr = DatetimeUtils.currentTimestampStr();
            stringBuilder.append("你好,当前时间为【"+currentTimestampStr+"】,请问有什么可以帮助您的吗?")
                         .append(Constants.TEXT_WRAP_CHARACTER)
                         .append("回复【天气-城市名称】,查询对应城市的天气情况;")
                         .append(Constants.TEXT_WRAP_CHARACTER)
                         .append("回复【手机-号码】,查询对应手机号码归属地信息;")
                         .append(Constants.TEXT_WRAP_CHARACTER)
                         .append("回复【商品-商品名称】,商品比价信息查询;")
                         .append(Constants.TEXT_WRAP_CHARACTER)
                         .append("回复【QQ-QQ号码】,QQ吉凶运程预测;")
                         .append(Constants.TEXT_WRAP_CHARACTER)
                         .append("回复【其他?】,正在努力开发中!!!")
                         .append(Constants.TEXT_WRAP_CHARACTER);
            
            msg=new TextRespMsg(stringBuilder.toString());
        }else if("生日快乐".equals(content)
                ||"Happy Birthday".equals(content)
                ||"Miss U".equals(content)
                ||"Miss You".equals(content)
                ||"想你了".equals(content)
                ||"么么哒".equals(content)){
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("你好,"+content+" ,这是表情纷发【"+content+"】测试页面")
                         .append(Constants.TEXT_WRAP_CHARACTER);
            
            msg=new TextRespMsg(stringBuilder.toString());
        } else if(MessageUtil.isQqFace(content)){
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("你好,原样返回发送的    QQ表情【"+content+"】")
                         .append(Constants.TEXT_WRAP_CHARACTER);
            
            msg=new TextRespMsg(stringBuilder.toString());
        } else if(content.startsWith("天气")||content.startsWith("天气")){
            //全国天气预报信息查询自动回复,格式为天气-城市名
            String requestUrl = Constants.JUHE_WEATHER_HTTPURL;
            String requestMethod = Constants.GET;
            String outputStr = null;
            String[] splits = content.split("-");
            if(splits==null||splits.length==0){
                splits=content.split("—");
            }
            String cityName=splits[1];
            
//            String cityName=content.substring(3);
            
            requestUrl=requestUrl.replaceAll("CITYNAME", cityName);
            JSONObject jsonObject = WeixinUtil.httpRequestNormal(requestUrl, requestMethod, outputStr);
            StringBuilder stringBuilder=new StringBuilder();
            //处理天气信息,整理成制定格式进行返回,类型为TextRespMsg
            String resultStr =""; 
            logger.warn(JSON.toJSONString(jsonObject));
            JSONObject jsonObjectSK = jsonObject.getJSONObject("result").getJSONObject("sk");
            JSONObject jsonObjectToday = jsonObject.getJSONObject("result").getJSONObject("today");
            logger.warn(JSON.toJSONString(jsonObjectSK));
            logger.warn(JSON.toJSONString(jsonObjectToday));
            
            resultStr=jsonObjectToday.getString("city");
            stringBuilder.append("  城     市:"+resultStr).append(Constants.TEXT_WRAP_CHARACTER);
            resultStr=jsonObjectToday.getString("weather");
            stringBuilder.append("  天     气:"+resultStr).append(Constants.TEXT_WRAP_CHARACTER);
            resultStr=jsonObjectToday.getString("temperature");
            stringBuilder.append("  温     度:"+resultStr).append(Constants.TEXT_WRAP_CHARACTER);
            resultStr=jsonObjectToday.getString("wind");
            stringBuilder.append("  风     向:"+resultStr);
            resultStr=jsonObjectSK.getString("wind_strength");
            stringBuilder.append(Constants.PROPCONFIG_FILENAME_PREFIX+resultStr).append(Constants.TEXT_WRAP_CHARACTER);
            resultStr=jsonObjectToday.getString("dressing_advice");
            stringBuilder.append("穿衣指数:"+resultStr).append(Constants.TEXT_WRAP_CHARACTER);
            
            msg=new TextRespMsg(stringBuilder.toString());
        }else if(content.startsWith("手机")||content.startsWith("手机")){
            //全国手机号码归属地信息查询自动回复,格式为手机-号码
            String requestUrl = Constants.JUHE_PHONENUMBER_HTTPURL;
            String requestMethod = Constants.GET;
            String outputStr = null;
//            String[] splits = content.split("-");
//            if(splits==null||splits.length==0){
//                splits=content.split("—");
//            }
//            String phoneNum=splits[1];
            String phoneNum=content.substring(3);
            
            requestUrl=requestUrl.replaceAll("PHONENUMBER", phoneNum);
            JSONObject jsonObject = WeixinUtil.httpRequestNormal(requestUrl, requestMethod, outputStr);
            
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            
            StringBuilder stringBuilder=new StringBuilder();
            //处理手机号码信息,整理成制定格式进行返回,类型为TextRespMsg
            String resultStr =""; 
            stringBuilder.append("  号     码:"+phoneNum)
                         .append(Constants.TEXT_WRAP_CHARACTER);
            resultStr=jsonResult.getString("province");
            stringBuilder.append("  城     市:"+resultStr)
                         .append(Constants.PROPCONFIG_FILENAME_PREFIX);
            resultStr=jsonResult.getString("city");
            stringBuilder.append(resultStr)
                         .append(Constants.TEXT_WRAP_CHARACTER);
            resultStr=jsonResult.getString("company");
            stringBuilder.append("  网     络:"+resultStr)
                         .append(Constants.TEXT_WRAP_CHARACTER);
            resultStr=jsonResult.getString("card");
            stringBuilder.append("   套    餐:"+resultStr)
                         .append(Constants.TEXT_POINT_CHN)
                         .append(Constants.TEXT_WRAP_CHARACTER);
            
            msg=new TextRespMsg(stringBuilder.toString());
        }else if(content.startsWith("商品")||content.startsWith("商品")){
            //商品简单比价查询自动回复,格式为商品-商品名称
            String requestUrl = Constants.JUHE_GOODNAME_HTTPURL;
            String requestMethod = Constants.GET;
            String outputStr = null;
//            String[] splits = content.split("-");
//            if(splits==null||splits.length==0){
//                splits=content.split("—");
//            }
//            String goodName=splits[1];
            String goodName=content.substring(3);
            
            requestUrl=requestUrl.replaceAll("GOODNAME", goodName);
            JSONObject jsonObject = WeixinUtil.httpRequestNormal(requestUrl, requestMethod, outputStr);
            
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("【商品名称:"+goodName+"】")
                         .append(Constants.TEXT_WRAP_CHARACTER);
            
            JSONArray jsonArray = jsonObject.getJSONArray("result");
//            for (int i = 0; i < jsonArray.size(); i++) {
//            }
            int i=0;
            for (Object object : jsonArray) {
                JSONObject jsonObj = jsonArray.getJSONObject(i++);
                String siteName = jsonObj.getString("siteName");
                Double spprice = jsonObj.getDoubleValue("spprice");
                String sppic =jsonObj.getString("sppic");
                String spurl = jsonObj.getString("spurl");
                stringBuilder.append(siteName+"\t")
                             .append(Constants.TEXT_COLON_EN)
                             .append(spprice+"元;")
                             .append(Constants.TEXT_WRAP_CHARACTER);
            }
            
            msg=new TextRespMsg(stringBuilder.toString());   
        }else if(content.startsWith("QQ-")||content.startsWith("QQ—")){
            //QQ吉凶运程预测自动回复,格式为QQ-QQ号码
            String requestUrl = Constants.JUHE_QQNUMBER_HTTPURL;
            String requestMethod = Constants.GET;
            String outputStr = null;
//            String[] splits = content.split("-");
//            if(splits==null||splits.length==0){
//                splits=content.split("—");
//            }
//            String QQNumber=splits[1];
            
            String QQNumber=content.substring(3);
            
            requestUrl=requestUrl.replaceAll("QQNUMBER", QQNumber);
            JSONObject jsonObject = WeixinUtil.httpRequestNormal(requestUrl, requestMethod, outputStr);
            
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("【QQ号码:"+QQNumber+"】").append(Constants.TEXT_WRAP_CHARACTER);
            
            String resultStr =""; 
            JSONObject jsonResult = jsonObject.getJSONObject("result").getJSONObject("data");
            resultStr=jsonResult.getString("conclusion");
            stringBuilder.append("吉凶预测:"+resultStr)
                         .append(Constants.TEXT_WRAP_CHARACTER);
            resultStr=jsonResult.getString("analysis");
            stringBuilder.append("运势预测:"+resultStr)
                         .append(Constants.TEXT_WRAP_CHARACTER);
            msg=new TextRespMsg(stringBuilder.toString());   
        } else {
            // 默认自动回复文本信息
            StringBuilder stringBuilder = new StringBuilder();
            String currentTimestampStr = DatetimeUtils.currentTimestampStr();
            stringBuilder.append("你好,当前时间为【" + currentTimestampStr+ "】,请问有什么可以帮助您的吗?")
                    .append(Constants.TEXT_WRAP_CHARACTER)
                    .append(Constants.TEXT_WRAP_CHARACTER)
                    .append("“曲阳艺人网”，意在打造“曲阳雕刻之乡”最大的“雕刻信息大数据平台平台”。")
                    .append(Constants.TEXT_WRAP_CHARACTER)
                    .append("您将在这里查询到曲阳本地所有的雕刻企业、图文印刷店面、雕刻工具销售店铺、物流企业!")
                    .append(Constants.TEXT_WRAP_CHARACTER)
                    .append("意见、建议及合作：quyangyirenwang@163.com")
                    .append(Constants.TEXT_WRAP_CHARACTER)
                    .append("微信公众号：quyangyirenwang")
                    .append(Constants.TEXT_WRAP_CHARACTER);
            msg = new TextRespMsg(stringBuilder.toString());
        }
        return msg;
    }

    private BaseRespMsg onImage(String mediaId, String picUrl) {
        BaseRespMsg msg=null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【 onImage 】");
        msg = new TextRespMsg(stringBuilder.toString());
        return msg;
    }

    /**
     * 开启语音识别后的方法调用
     */
    private BaseRespMsg onVoice(String mediaId, String format, String recognition) {
        BaseRespMsg msg=null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【 onVoice return 】,开启语音识别结果为:")
                    .append(Constants.TEXT_WRAP_CHARACTER)
                    .append(recognition)
                    .append(Constants.TEXT_WRAP_CHARACTER);
        msg = new TextRespMsg(stringBuilder.toString());
        return msg;
    }
    
    /**
     * 未启语音识别后的方法调用
     */
    private BaseRespMsg onVoice(String mediaId, String format) {
        BaseRespMsg msg=null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【 onVoice 】");
        msg = new TextRespMsg(stringBuilder.toString());
        return msg;
    }

    private BaseRespMsg onVideo(String mediaId, String thumbMediaId) {
        BaseRespMsg msg=null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【 onVideo 】");
        msg = new TextRespMsg(stringBuilder.toString());
        return msg;
    }

    //地理位置消息,及微信中的发送地理位置消息,回复图文消息
    private BaseRespMsg onLocation(double locationX, double locationY, int scale,
            String label) {
        BaseRespMsg msg=null;
        List<Article> articles=new ArrayList<Article>();
        
        //设置默认图文消息头信息
        Article article0=new Article();
        article0.setTitle("【 周边美食 】");
        article0.setDescription("看看周边都有什么好吃的!!!");
        article0.setPicUrl("");
        article0.setUrl("");
        articles.add(article0);
        
        //查询周边美食 , 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【 周边美食 】")
                .append(Constants.TEXT_WRAP_CHARACTER)
                .append("位  置:").append(label)
                .append(Constants.TEXT_WRAP_CHARACTER)
                .append("维  度:").append(locationX)
                .append(Constants.TEXT_WRAP_CHARACTER)
                .append("经  度:").append(locationY)
                .append(Constants.TEXT_WRAP_CHARACTER);
        
        String requestUrl = Constants.BAIDU_QUERY_REGIN_HTTPURL;
        String requestMethod = Constants.GET;
        String outputStr = null;
        requestUrl=requestUrl.replaceAll("QUERY", "美食");
        requestUrl=requestUrl.replaceAll("LOCATIONX", MyStringUtils.getStringValue(locationX));
        requestUrl=requestUrl.replaceAll("LOCATIONY", MyStringUtils.getStringValue(locationY));
        JSONObject jsonObject = WeixinUtil.httpRequestNormal(requestUrl, requestMethod, outputStr);
        
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        if(jsonArray!=null){
            for (int i = 0; i < Constants.ARACLE_MAXSIZE&&i<jsonArray.size(); i++) {
                //初始化请求URL
                String requestUrl2 = Constants.BAIDU_UID_HTTPURL;
                
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String uid =jsonObj.getString("uid");
                //根据uid回去对应的详情信息
                requestUrl2=requestUrl2.replaceAll("UID", uid);
                JSONObject jsonObject2 = WeixinUtil.httpRequestNormal(requestUrl2, requestMethod, outputStr);
                JSONObject jsonObject3 = jsonObject2.getJSONObject("result");
                String name = jsonObject3.getString("name");
                String address = jsonObject3.getString("address");
                String tag = jsonObject3.getString("tag");
                String detailUrl = jsonObject3.getJSONObject("detail_info").getString("detail_url");
                
                Article article=new Article();
                article.setTitle(name);
                article.setDescription(address);
                article.setPicUrl("暂无");
                article.setUrl(detailUrl);
                articles.add(article);
            }
            msg=new NewsRespMsg(articles);
        }else{
            msg=new TextRespMsg(stringBuilder.toString()); 
        }
        
        return msg;
    }

    private BaseRespMsg onLink(String title, String description, String url) {
        BaseRespMsg msg=null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【 onLink 】");
        msg = new TextRespMsg(stringBuilder.toString());
        return msg;
    }
    
    //===========================事件相关私有方法==================================
    
    private BaseRespMsg onQrCode(String eventKey, String ticket) {
        BaseRespMsg msg=null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【 onQrCode 】");
        msg = new TextRespMsg(stringBuilder.toString());
        return msg;
    }
    

    // 回应菜单点击事件,需要菜单创建权限  
    private BaseRespMsg onMenuClick(String eventKey) {
        BaseRespMsg msg= null;
        if(StringUtils.isNotBlank(eventKey)){
            if("CLICK_KEY_11".equalsIgnoreCase(eventKey)){
                //笑话列表,点击事件
                String requestUrl = Constants.JUHE_LAUGHSTORY_HTTPURL;
                String requestMethod = Constants.GET;
                String outputStr = null;
                
//                Timestamp timeBegin = DatetimeUtils.weekPlus(DatetimeUtils.currentTimestamp(), -1);
//                requestUrl= requestUrl.replaceAll("TIMEBEGIN", MyStringUtils.getStringValue(timeBegin.getTime()));
                JSONObject jsonObject = WeixinUtil.httpRequestNormal(requestUrl, requestMethod, outputStr);
                
                //返回值信息
                List<Article> articles=new ArrayList<Article>();
                StringBuilder stringBuilder=new StringBuilder();
                
                JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
                System.out.println("获取jsonArray为:"+JSON.toJSONString(jsonArray));
                int index = Math.abs(new Random().nextInt(jsonArray.size()-Constants.ARACLE_MAXSIZE));
                
                //单个图文消息
                Article article=new Article();
                article.setTitle("【每日笑话】");
                article.setPicUrl("http://img5.dwstatic.com/xunxian/1407/268194219037/268195417823.jpg");
                article.setUrl("http://baike.haosou.com/doc/4289526-4492964.html");
                for (int i = 0; i <  Constants.ARACLE_MAXSIZE&&i<jsonArray.size()&&index<jsonArray.size(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(index++);
                    stringBuilder.append("【笑话"+(i+1)+" 】")
                                 .append(Constants.TEXT_WRAP_CHARACTER)
                                 .append(jsonObj.getString("content"))
                                 .append(Constants.TEXT_WRAP_CHARACTER);
                }
                
                article.setDescription(stringBuilder.toString());
                articles.add(article);
                
                msg=new NewsRespMsg(articles);
            }else if("CLICK_KEY_12".equalsIgnoreCase(eventKey)){
                //历史上的今天
                int currentMonth = DatetimeUtils.getCurrentMonth();
                int currentDay = DatetimeUtils.getCurrentDayOfMonth();
                
                String requestUrl = Constants.JUHE_TODAYINHIS_HTTPURL;
                String requestMethod = Constants.GET;
                String outputStr = null;
                
                requestUrl=requestUrl.replaceAll("MONTH", MyStringUtils.getStringValue(currentMonth));
                requestUrl=requestUrl.replaceAll("DAY", MyStringUtils.getStringValue(currentDay));
                JSONObject jsonObject = WeixinUtil.httpRequestNormal(requestUrl, requestMethod, outputStr);
                
                //单个图文消息
                List<Article> articles=new ArrayList<Article>();
                StringBuilder stringBuilder=new StringBuilder();
                Article article=new Article();
                article.setTitle("【历史上的今天】");
                article.setPicUrl("http://img1.gamedog.cn/2014/03/13/43-1403131154400.jpg");
                article.setUrl("http://www.todayonhistory.com/");
                
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if(jsonArray!=null){
                    int index = Math.abs(new Random().nextInt(jsonArray.size()-Constants.ARACLE_MAXSIZE));
                    for (int i = 0; i <  Constants.ARACLE_MAXSIZE&&i<jsonArray.size()&&index<jsonArray.size(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(index++);
                        String title = jsonObj.getString("title");
                        String des = jsonObj.getString("des");
                        
                        stringBuilder.append("【事件"+(i+1)+"】")
                            .append(title)
                            .append(Constants.TEXT_WRAP_CHARACTER)
                            .append(des)
                            .append(Constants.TEXT_WRAP_CHARACTER);
                    }
                }
                article.setDescription(stringBuilder.toString());
                articles.add(article);
                
                msg=new NewsRespMsg(articles);  
            }else if("CLICK_KEY_13".equalsIgnoreCase(eventKey)){
                //菜谱推荐大全
                String requestUrl = Constants.JUHE_COOKMENU_HTTPURL;
                String requestMethod = Constants.GET;
                String outputStr = null;
                //随机选择一个菜名  
                int index = new Random().nextInt(Constants.menuArray.length);
                
                //设置默认图文消息头信息
                List<Article> articles=new ArrayList<Article>();
                StringBuilder stringBuilder=new StringBuilder();
                
                requestUrl=requestUrl.replaceAll("COOKMENU",Constants.menuArray[index]);
                JSONObject jsonObject = WeixinUtil.httpRequestNormal(requestUrl, requestMethod, outputStr);
                
                JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
                 //List<String> list=JSONObject.parseArray(jsonArray.toJSONString(), String.class);
                 if(jsonArray!=null){
                     for (int i = 0; i < Constants.ARACLE_MAXSIZE&&i<jsonArray.size(); i++) {
                         JSONObject jsonObj = jsonArray.getJSONObject(i);
                         Article article=new Article();
                         article.setTitle(jsonObj.getString("title"));
                         stringBuilder.append(jsonObj.getString("tags"))
                                     .append(Constants.TEXT_WRAP_CHARACTER)
                                     .append(jsonObj.getString("imtro"))
                                     .append(Constants.TEXT_WRAP_CHARACTER)
                                     .append(jsonObj.getString("ingredients"))
                                     .append(Constants.TEXT_WRAP_CHARACTER)
                                     .append(jsonObj.getString("burden"))
                                     .append(Constants.TEXT_WRAP_CHARACTER);
                         
                         JSONArray jsonArray2 = jsonObj.getJSONArray("albums");
                         List<String> list=JSONObject.parseArray(jsonArray2.toJSONString(), String.class);
                         
                         article.setDescription(stringBuilder.toString());
                         article.setPicUrl(list.get(0));
                         article.setUrl("http://www.ichide.cn/");
                         articles.add(article);
                     }
                 }
                
                msg=new NewsRespMsg(articles);
            }else if("CLICK_KEY_32".equalsIgnoreCase(eventKey)){
                // 获取当前AccessToken事件处理
                StringBuilder stringBuilder=new StringBuilder();
                AccessTokenVO accessTokenVO = WeixinUtil.getAccessTokenVO(Constants.APP_ID,
                        Constants.APP_SERCRET);
                stringBuilder.append("【当前AccessToken值】")
                            .append(Constants.TEXT_WRAP_CHARACTER)
                            .append(accessTokenVO.getToken())
                            .append(Constants.TEXT_WRAP_CHARACTER)
                            .append("【创建时间】")
                            .append(Constants.TEXT_WRAP_CHARACTER)
                            .append(DatetimeUtils.formatTimestamp(accessTokenVO.getCreatedDatetime()))
                            .append(Constants.TEXT_WRAP_CHARACTER)
                            .append("【过期时间间隔】")
                            .append(Constants.TEXT_WRAP_CHARACTER)
                            .append(accessTokenVO.getExpiresIn())
                            .append("秒")
                            .append(Constants.TEXT_WRAP_CHARACTER)
                            .append("【过期时间】")
                            .append(Constants.TEXT_WRAP_CHARACTER)
                            .append(DatetimeUtils.formatTimestamp(accessTokenVO.getExperiedDatetime()))
                            .append(Constants.TEXT_WRAP_CHARACTER);
                
                msg=new TextRespMsg(stringBuilder.toString());  
            }else if("CLICK_KEY_24".equalsIgnoreCase(eventKey)){
                // 获取二维码图片 TODO 
//                String mediaId="UEtH9lHHMxilixj0vGoCq_v7VWf83eJWarNABknFezkUOcWHSfIphuDnlB4Wq7yJ";
                String mediaId="PuF4tbYj7hxqtH4nUbUCY9G1ocI7WZQ9eD89sp51qz0EW6BFKy_ikV5meNIN03dK";
                msg=new ImageRespMsg(mediaId);  
            }else{
                // 其他点击事件处理
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append("【正在开发中的CLICK事件!!!】")
                             .append(Constants.TEXT_WRAP_CHARACTER);
                
                msg=new TextRespMsg(stringBuilder.toString());  
            }
        }
        
        return msg;
    }
    
    //关注事件后,自动回复图文消息
    private BaseRespMsg onSubscribe() {
        //获取用户对应的信息 TODO 
        
        List<Article> articles=new ArrayList<Article>();
        
        Article article0=new Article();
        article0.setTitle("艺人传记");
        article0.setDescription("那些可歌可泣的创业实事!");
        article0.setPicUrl("http://p0.so.qhimg.com/t01826f7b284943070f.jpg");
        article0.setUrl("http://mp.weixin.qq.com/s?__biz=MzA3MDYwNzM4Nw==&mid=219008222&idx=2&sn=dff4beb5a767fde4b5790459969afa4d&scene=18#wechat_redirect");
        
        Article article1=new Article();
        article1.setTitle("网站介绍");
        article1.setDescription("欢迎光临我的微信网站!");
        article1.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/6kP5DqUUVZsUFKibAoYlDR6zDqxoKsMAWfjZg8iatWSgB2dbIVULfGa1JWnnpaAFmIzYRyHBtrU1BJokorLwjr3A/0?wx_fmt=jpeg");
        article1.setUrl("http://mp.weixin.qq.com/s?__biz=MzA3MDYwNzM4Nw==&mid=219257662&idx=1&sn=1dcaa4d6ab6d8e8ed479cb002966c852&scene=18#wechat_redirect");
        
        Article article2=new Article();
        article2.setTitle("小艺社区");
        article2.setDescription("欢迎光临我的微信网站!");
        article2.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/6kP5DqUUVZsUFKibAoYlDR6zDqxoKsMAWsVTBHmOMViaA6egrtKBJ0pNUwx26erDAsSqrwHfaRRnf2Rg4dnCHa1w/0?wx_fmt=jpeg");
        article2.setUrl("http://mp.weixin.qq.com/s?__biz=MzA3MDYwNzM4Nw==&mid=219008222&idx=1&sn=1001da11442db74fc1a58690213a45f7&scene=18#wechat_redirect");
        
        Article article3=new Article();
        article3.setTitle("小艺微店");
        article3.setDescription("欢迎光临我的微信网站!");
        article3.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/6kP5DqUUVZsUFKibAoYlDR6zDqxoKsMAWvFcbMGzXicSNbdAyxwpoBVpYvLeG2QgdXCwyNle1vClZSAD2M0Dvbrg/0?wx_fmt=jpeg");
        article3.setUrl("http://mp.weixin.qq.com/s?__biz=MzA3MDYwNzM4Nw==&mid=214892966&idx=1&sn=cf63107fb302237414064a4c95a0b801&scene=18#wechat_redirect");
        
        Article article4=new Article();
        article4.setTitle("小艺微站");
        article4.setDescription("欢迎光临我的微信网站!");
        article4.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/6kP5DqUUVZsUFKibAoYlDR6zDqxoKsMAWJad2zyruNbCjHfu3icbC263aGLFaaDuOajB09ZkByZ8icOkAoR5lbgPQ/0?wx_fmt=jpeg");
        article4.setUrl("http://mp.weixin.qq.com/s?__biz=MzA3MDYwNzM4Nw==&mid=219637610&idx=1&sn=01c91b04beab96519ef379edb7e7a826&scene=18#wechat_redirect");
        
        articles.add(article0);
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);
        articles.add(article4);
        
        BaseRespMsg msg=new NewsRespMsg(articles);
        return msg;
    }
    
    //取消订阅,不需要推送消息!
    private BaseRespMsg onUnsubscribe() {
        StringBuilder stringBuilder=new StringBuilder();
        //根据关键字,自动回复以下信息
        stringBuilder.append("【希望您以后能够再关注俺们微信号!】")
                     .append(Constants.TEXT_WRAP_CHARACTER);
        BaseRespMsg msg=new TextRespMsg(stringBuilder.toString());
        return msg;
    }
    
    private BaseRespMsg onMenuView(String eventKey) {
        BaseRespMsg msg=null;
        return null;
    }
    
    //上报地理位置事件 TODO
    private BaseRespMsg onLocation(double latitude, double longitude,double precision) {
        BaseRespMsg msg=null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("上报地理位置事件")
                .append(Constants.TEXT_WRAP_CHARACTER);
        msg = new TextRespMsg(stringBuilder.toString());

        return msg;
    }
    
    //============================================================
    
    // 默认的判断逻辑,全部放到onText中去处理
    private String handleTextWithText(String content) {
        return null;
    }
    
    @Override
    protected final BaseRespMsg handleTextMsg(TextReqMsg msg) {
        String reqText = msg.getContent();
        String respText = handleTextWithText(reqText);
        if (respText != null) {
            return new TextRespMsg(respText);
        }
        BaseRespMsg respMsg = onText(reqText);
        return responseMsgDefault(respMsg, msg);
    }

    @Override
    protected final BaseRespMsg handleImageMsg(ImageReqMsg msg) {
        BaseRespMsg respMsg = onImage(msg.getMediaId(), msg.getPicUrl());
        return responseMsgDefault(respMsg, msg);
    }

    @Override
    protected final BaseRespMsg handleVoiceMsg(VoiceReqMsg msg) {
        BaseRespMsg respMsg = onVoice(msg.getMediaId(), msg.getFormat(),msg.getRecognition());
        return responseMsgDefault(respMsg, msg);
    }

    @Override
    protected final BaseRespMsg handleVideoMsg(VideoReqMsg msg) {
        BaseRespMsg respMsg = onVideo(msg.getMediaId(), msg.getThumbMediaId());
        return responseMsgDefault(respMsg, msg);
    }

    @Override
    protected final BaseRespMsg handleLocationMsg(LocationReqMsg msg) {
        BaseRespMsg respMsg = onLocation(msg.getLocationX(), msg.getLocationY(),
                msg.getScale(), msg.getLabel());
        return responseMsgDefault(respMsg, msg);
    }
    
    @Override
    protected final BaseRespMsg handleLinkMsg(LinkReqMsg msg) {
        BaseRespMsg respMsg = onLink(msg.getTitle(),msg.getDescription(), msg.getUrl());
        return responseMsgDefault(respMsg, msg);
    }

    //============================================================
    
    @Override
    protected final BaseRespMsg handleQrCodeEvent(QrCodeReqEvent event) {
        BaseRespMsg respMsg = onQrCode(event.getEventKey(),event.getTicket());
        return responseEventDefault(respMsg, event);
    }

    @Override
    protected final BaseRespMsg handleSubscribe(BaseReqEvent event) {
        BaseRespMsg respMsg = onSubscribe();
        return responseEventDefault(respMsg, event);
    }

    @Override
    protected final BaseRespMsg handleUnsubscribe(BaseReqEvent event) {
        BaseRespMsg respMsg = onUnsubscribe();
        return responseEventDefault(respMsg, event);
    }

    @Override
    protected final BaseRespMsg handleMenuClickEvent(MenuReqEvent event) {
        BaseRespMsg respMsg = onMenuClick(event.getEventKey());
        return responseEventDefault(respMsg, event);
    }
    
    @Override
    protected final BaseRespMsg handleMenuViewEvent(MenuReqEvent event) {
        BaseRespMsg respMsg = onMenuView(event.getEventKey());
        return responseEventDefault(respMsg, event);
    }

    @Override
    protected final BaseRespMsg handleLocationEvent(LocationReqEvent event) {
        BaseRespMsg respMsg = onLocation(event.getLatitude(), event.getLongitude(),
                event.getPrecision());
        return responseEventDefault(respMsg, event);
    }
    
    
    // ==================设置默认处理方式================================


    private BaseRespMsg responseMsgDefault(BaseRespMsg respMsg, BaseReqMsg reqMsg) {
        if (respMsg != null) {
            return respMsg;
        }
        return super.handleDefaultMsg(reqMsg);
    }

    private BaseRespMsg responseEventDefault(BaseRespMsg respMsg, BaseReqEvent reqEvent) {
        if (respMsg != null) {
            return respMsg;
        }
        return super.handleDefaultEvent(reqEvent);
    }

}
