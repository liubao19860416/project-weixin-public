package com.zxytech.main;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxytech.pojo.AccessToken;
import com.zxytech.pojo.Button;
import com.zxytech.pojo.CommonClickButton;
import com.zxytech.pojo.CommonViewButton;
import com.zxytech.pojo.ComplexButton;
import com.zxytech.pojo.Menu;
import com.zxytech.util.Constants;
import com.zxytech.util.WeixinUtil;
/**
 * 用户菜单信息管理
 *
 */
public class MenuManager {
    
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);
    
    /**
     * 手动创建用户菜单信息方法入口
     */
    @Test
    public void testCreateMenu01() throws Exception {
        AccessToken accessToken = WeixinUtil.getAccessToken(Constants.APP_ID, Constants.APP_SERCRET);
        if (accessToken != null) {
            System.out.println(accessToken);
            int result = WeixinUtil.createMenu(getMenu2(), accessToken.getToken());
            if (result == 0) {
                log.info("菜单创建成功！");
            } else {
                log.error("菜单创建失败，错误码：" + result);
            }
        }
    }
    
//    @Test
    public void testCreateMenu02() throws Exception {
        String accessToken="_blPrijuq9Df2fEtyGgTg1O-BMJMHDh0Wzb2Z9wPUJ7yrgDCK3tgZ3YnsMDAcYmnXjijUr1vqUr83DJWw4lQEJ5KF2OQ6xiRvOn8t6jaikM";
        int result = WeixinUtil.createMenu(getMenu2(), accessToken);
        if (result == 0) {
            log.info("菜单创建成功！");
        } else {
            log.error("菜单创建失败，错误码：" + result);
        }
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(java.net.URLEncoder.encode("http://wasabili.aliapp.com/oauth","UTF-8"));
    }

    
    public static Menu getMenu2() throws UnsupportedEncodingException {
        
        //普通click菜单
        CommonClickButton btn11 = new CommonClickButton();
        btn11.setName("【笑话列表查询】");
        btn11.setType("click");
        btn11.setKey("CLICK_KEY_11");

        //click菜单
        CommonClickButton btn12 = new CommonClickButton();
        btn12.setName("【历史上的今天】");
        btn12.setType("click");
        btn12.setKey("CLICK_KEY_12");
        
        //click菜单
        CommonClickButton btn13 = new CommonClickButton();
        btn13.setName("【菜谱推荐】");
        btn13.setType("click");
        btn13.setKey("CLICK_KEY_13");
        
        //view菜单
        CommonViewButton btn21 = new CommonViewButton();
        btn21.setName("【测试论坛】");
        btn21.setType("view");
        btn21.setUrl("http://wasabili0.duapp.com/");
        
        CommonViewButton btn22 = new CommonViewButton();
        btn22.setName("【微信浏览器1】");
        btn22.setType("view");
        btn22.setUrl("http://wasabili.aliapp.com/weixin2?dispatcher=1");
        
        CommonViewButton btn23 = new CommonViewButton();
        btn23.setName("【微信浏览器2】");
        btn23.setType("view");
        btn23.setUrl("http://wasabili.aliapp.com/weixin2?dispatcher=1&#wechat_webview_type=1");
        
        //click菜单
        CommonClickButton btn24 = new CommonClickButton();
        btn24.setName("【获取二维码】");
        btn24.setType("click");
        btn24.setKey("CLICK_KEY_24");
        
        //view菜单
        CommonViewButton btn31 = new CommonViewButton();
        btn31.setName("【请柬主页】");
        btn31.setType("view");
//        btn31.setUrl("http://wasabili0.duapp.com/emailIndex");
        btn31.setUrl("http://t.cn/RL6CSXL");
//        btn31.setUrl("http://www.e1v.cn/4c4");
//        btn31.setUrl("http://qqurl.com/yHc");
//        btn31.setUrl("http://wasabili.aliapp.com/getToken");
        
        CommonClickButton btn32 = new CommonClickButton();
        btn32.setName("【获取AT2】");
        btn32.setType("click");
        btn32.setKey("CLICK_KEY_32");
        
        CommonViewButton btn33 = new CommonViewButton();
        btn33.setName("【用户授权】");
        btn33.setType("view");
        btn33.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constants.APP_ID+"&redirect_uri="
                + 
//                "http://wasabili.aliapp.com/oauthServlet"
        java.net.URLEncoder.encode("http://wasabili.aliapp.com/oauth","UTF-8")
                + "&response_type=code&scope=snsapi_userinfo&state=12#wechat_redirect");
        
        CommonViewButton btn34 = new CommonViewButton();
        btn34.setName("【初始化菜单】");
        btn34.setType("view");
        btn34.setUrl("http://wasabili.aliapp.com/initMenu?appToken="+Constants.APP_TOKEN);

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("小艺平台");
        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13 });
        
        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("小艺视频");
        mainBtn2.setSub_button(new Button[] { btn21,btn22,btn23,btn24});
        
        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("正在开发");
        mainBtn3.setSub_button(new Button[] { btn31,btn32,btn33,btn34});
        
        //创建最外层菜单按钮
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1,mainBtn2,mainBtn3 });

        return menu;
        
 }
    /**
     * 创建所有的菜单信息
     */
    private static Menu getMenu() {
        
        //普通子菜单信息
        CommonClickButton btn11 = new CommonClickButton();
        btn11.setName("天气预报");
        btn11.setType("click");
        btn11.setKey("11");

        CommonClickButton btn12 = new CommonClickButton();
        btn12.setName("公交查询");
        btn12.setType("click");
        btn12.setKey("12");

        CommonClickButton btn13 = new CommonClickButton();
        btn13.setName("周边搜索");
        btn13.setType("click");
        btn13.setKey("13");

        CommonClickButton btn14 = new CommonClickButton();
        btn14.setName("历史上的今天");
        btn14.setType("click");
        btn14.setKey("14");

        CommonClickButton btn21 = new CommonClickButton();
        btn21.setName("歌曲点播");
        btn21.setType("click");
        btn21.setKey("21");

        CommonClickButton btn22 = new CommonClickButton();
        btn22.setName("经典游戏");
        btn22.setType("click");
        btn22.setKey("22");

        CommonClickButton btn23 = new CommonClickButton();
        btn23.setName("美女电台");
        btn23.setType("click");
        btn23.setKey("23");

        CommonClickButton btn24 = new CommonClickButton();
        btn24.setName("人脸识别");
        btn24.setType("click");
        btn24.setKey("24");

        CommonClickButton btn25 = new CommonClickButton();
        btn25.setName("聊天唠嗑");
        btn25.setType("click");
        btn25.setKey("25");

        CommonClickButton btn31 = new CommonClickButton();
        btn31.setName("Q友圈");
        btn31.setType("click");
        btn31.setKey("31");

        CommonClickButton btn32 = new CommonClickButton();
        btn32.setName("电影排行榜");
        btn32.setType("click");
        btn32.setKey("32");

        CommonClickButton btn33 = new CommonClickButton();
        btn33.setName("幽默笑话");
        btn33.setType("click");
        btn33.setKey("33");

        //创建主菜单信息
        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("生活助手");
        mainBtn1.setSub_button(new CommonClickButton[] { btn11, btn12, btn13, btn14 });

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("休闲驿站");
        mainBtn2.setSub_button(new CommonClickButton[] { btn21, btn22, btn23, btn24,
                btn25 });

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_button(new CommonClickButton[] { btn31, btn32, btn33 });

        //创建最外层菜单按钮
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

        return menu;
    }
}