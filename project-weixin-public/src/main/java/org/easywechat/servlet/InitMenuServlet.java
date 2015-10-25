package org.easywechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxytech.main.MenuManager;
import com.zxytech.pojo.AccessToken;
import com.zxytech.util.Constants;
import com.zxytech.util.SignUtil;
import com.zxytech.util.WeixinUtil;

public class InitMenuServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    public InitMenuServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (validateToken(request)) {
                AccessToken accessToken = WeixinUtil.getAccessToken(Constants.APP_ID, Constants.APP_SERCRET);
                if (accessToken != null) {
                    System.out.println(accessToken);
                    int result = WeixinUtil.createMenu(MenuManager.getMenu2(), accessToken.getToken());
                    if (result == 0) {
                        log.info("菜单创建成功！");
                        out.print("菜单创建成功！ ["+new Date().toLocaleString()+"].");
                    } else {
                        log.error("菜单创建失败，错误码：" + result);
                        out.print("菜单创建失败，错误码："+result+" ["+new Date().toLocaleString()+"].");
                    }
                }
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

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    }
    
    //请求信息校验等
    private boolean validateToken(HttpServletRequest request) {
        String appToken = request.getParameter("appToken");
        return Constants.APP_TOKEN.equalsIgnoreCase(appToken);
    }

}
