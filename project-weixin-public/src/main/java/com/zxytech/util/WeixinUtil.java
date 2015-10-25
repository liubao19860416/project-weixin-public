package com.zxytech.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zxytech.dao.AccessTokenDao;
import com.zxytech.pojo.AccessToken;
import com.zxytech.pojo.Menu;
import com.zxytech.vo.AccessTokenVO;

/**
 * 微信工具类:穿件菜单,请求token校验
 * 
 * @author Liubao
 * @2015年7月11日
 * 
 */
public class WeixinUtil {

    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

    public static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    //测试方法
    public static void main(String[] args) {
        String requestUrl = Constants.JUHE_WEATHER_HTTPURL;
        String requestMethod = Constants.GET;
        String outputStr = null;
        requestUrl=requestUrl.replaceAll("CITYNAME", "上海");
        JSONObject jsonObject = httpRequestNormal(requestUrl, requestMethod, outputStr);
        System.out.println(jsonObject);
        
        String resultStr =""; 
        JSONObject jsonObjectSK = jsonObject.getJSONObject("result").getJSONObject("sk");
        resultStr=jsonObjectSK.getString("wind_direction");
        System.out.println(resultStr);
        resultStr=jsonObjectSK.getString("wind_strength");
        System.out.println(resultStr);
        JSONObject jsonObjectToday = jsonObject.getJSONObject("result").getJSONObject("today");
        resultStr=jsonObjectToday.getString("temperature");
        System.out.println(resultStr);
        resultStr=jsonObjectToday.getString("weather");
        System.out.println(resultStr);
        resultStr=jsonObjectToday.getString("wind");
        System.out.println(resultStr);
        resultStr=jsonObjectToday.getString("week");
        System.out.println(resultStr);
        resultStr=jsonObjectToday.getString("dressing_advice");
        System.out.println(resultStr);
        resultStr = jsonObjectToday.getString("week");
        System.out.println(resultStr);
    }
    
    
    /**
     * 将请求数据封装成JSONObject对象存储,供普通的http请求使用,返回数据格式为json
     */
    public static JSONObject httpRequestNormal(String requestUrl,String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn =  (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            if(null!=inputStream){
                inputStream.close();
            }
            inputStream = null;

            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }finally{
            
        }
        return jsonObject;
    }
    
    
    /**
     * 将请求数据封装成JSONObject对象存储,供微信服务https调用使用,返回json数据
     */
    public static JSONObject httpsRequest(String requestUrl,String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            
            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            // jsonObject = JSONObject.fromObject(buffer.toString());
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }finally{
            
        }
        return jsonObject;
    }

    /**
     * 获取请求校验token的url信息
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = new AccessToken();
        
        AccessTokenVO accessTokenVO = AccessTokenDao.getActivedAccessToken();
        
        if(accessTokenVO!=null
                &&accessTokenVO.getExperiedDatetime().after(DatetimeUtils.currentTimestamp())){
            //直接返回
            try {
                BeanUtilsBean.getInstance().copyProperties(accessToken, accessTokenVO);
                System.out.println("从数据库中读取access_token:"+JSON.toJSONString(accessToken.getToken()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }else{
            String requestUrl = access_token_url.replace("APPID", appid).replace(
                    "APPSECRET", appsecret);
            JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
            
            if (jsonObject != null) {
                try {
                    //重新生成accessToken
                    String token=jsonObject.getString("access_token");
                    Long expiresIn=jsonObject.getLongValue("expires_in");
                    accessToken.setToken(token);
                    accessToken.setExpiresIn(expiresIn);
                    //持久化到数据库
                    int result = AccessTokenDao.insertAccessToken(token, expiresIn);
                    if(result!=1){
                        System.out.println("保存access_token到数据库失败!!!"+JSON.toJSONString(token));
                    }else{
                        System.out.println("保存access_token到数据库成功!!!"+JSON.toJSONString(token));
                    }
                } catch (JSONException e) {
                    accessToken = null;
                    log.error("获取token失败 errcode:{} errmsg:{}",
                            Integer.valueOf(jsonObject.getIntValue("errcode")),
                            jsonObject.getString("errmsg"));
                }
            }
            
        }
        
        return accessToken;
    }
    
    
    /**
     * 获取请求校验token的url信息
     */
    public static AccessTokenVO getAccessTokenVO(String appid, String appsecret) {
        AccessTokenVO accessTokenVO = AccessTokenDao.getActivedAccessToken();
        
        if(accessTokenVO==null
                ||accessTokenVO.getExperiedDatetime().before(DatetimeUtils.currentTimestamp())){
            String requestUrl = access_token_url.replace("APPID", appid).replace(
                    "APPSECRET", appsecret);
            JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
            
            if (jsonObject != null) {
                try {
                    //重新生成accessToken
                    String token=jsonObject.getString("access_token");
                    Long expiresIn=jsonObject.getLongValue("expires_in");
                    
                    AccessToken accessToken=new AccessToken();
                    accessToken.setToken(token);
                    accessToken.setExpiresIn(expiresIn);
                    //持久化到数据库
                    int result = AccessTokenDao.insertAccessToken(token, expiresIn);
                    if(result!=1){
                        System.out.println("保存access_token到数据库失败!!!"+JSON.toJSONString(token));
                    }else{
                        System.out.println("保存access_token到数据库成功!!!"+JSON.toJSONString(token));
                        //重新获一遍取即可
                        accessTokenVO = AccessTokenDao.getActivedAccessToken();
                    }
                } catch (JSONException e) {
                    accessTokenVO = null;
                    log.error("获取token失败 errcode:{} errmsg:{}",
                            Integer.valueOf(jsonObject.getIntValue("errcode")),
                            jsonObject.getString("errmsg"));
                }
            }
        }
        
        return accessTokenVO;
    }

    /**
     * 创建菜单
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // String jsonMenu = JSONObject.fromObject(menu).toString();
        String jsonMenu = JSONObject.toJSONString(menu);
        System.out.println(jsonMenu);
        JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);
        //正常返回errcode=0
        if ((jsonObject != null) && (jsonObject.getIntValue("errcode") != 0)) {
            result = jsonObject.getIntValue("errcode");
            log.error("创建菜单失败 errcode:{} errmsg:{}",
                    Integer.valueOf(jsonObject.getIntValue("errcode")),
                    jsonObject.getString("errmsg"));
        }
        return result;
    }
}