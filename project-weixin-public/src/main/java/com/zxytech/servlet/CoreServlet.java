package com.zxytech.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zxytech.service.CoreService;
import com.zxytech.util.Constants;
import com.zxytech.util.SignUtil;

/**
 * 核心的请求访问Servlet
 * 
 * @author Liubao
 * @2015年6月30日
 * 
 */
public class CoreServlet extends HttpServlet {
    
    private static final long serialVersionUID = -2294240735226021526L;
    //定义token
    protected static final String tooken = Constants.APP_TOKEN;

    /**
     * GET请求方式,进行服务器用户信息校验
     */
    @SuppressWarnings("deprecation")
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        
//        System.out.println(Constants.keyWordsList);
        System.out.println(Constants.propMap);
        
        PrintWriter out=null;
        try {
            out = response.getWriter();
            if (SignUtil.checkSignature(tooken,signature, timestamp, nonce)) {
                out.print(echostr);
            } else {
                out.print("CoreServlet==>Token message is not right at time ["+new Date().toLocaleString()+"].");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null!=out){
                out.close();
                out = null;
            }
        }
    }

    /**
     * PSOT方式,接受微信服务器返回的数据信息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置编码格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //核心的业务处理方法类,与servlet解耦,并返回xml格式的字符串数据
        String respMessage = CoreService.processRequest(request);

        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }
}