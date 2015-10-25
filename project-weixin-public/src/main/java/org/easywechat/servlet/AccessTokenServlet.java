package org.easywechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zxytech.pojo.AccessToken;
import com.zxytech.util.Constants;
import com.zxytech.util.DatetimeUtils;
import com.zxytech.util.WeixinUtil;
import com.zxytech.vo.AccessTokenVO;

/**
 * 获取token测试使用
 */
//@WebServlet("/AccessTokenServlet")
public class AccessTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AccessTokenServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        AccessTokenVO accessTokenVO = WeixinUtil.getAccessTokenVO(Constants.APP_ID,
                Constants.APP_SERCRET);
        
        StringBuilder sb=new StringBuilder();
        // 返回token相关信息
        sb.append("<div align='left' style='color: red;border: 1px;border-color: black;'>")
            .append("【The AccessToken 是】")
            .append(accessTokenVO.getToken())
            .append("<br/>")
            .append("【创建时间】")
            .append(DatetimeUtils.formatTimestamp(accessTokenVO.getCreatedDatetime()))
            .append("<br/>")
            .append("【过期时间】")
            .append(DatetimeUtils.formatTimestamp(accessTokenVO.getExperiedDatetime()))
            .append("<br/>")
            .append("</div>")
            .append("<br/><br/>");
        writer.write(sb.toString());
        writer.flush();
        writer.close();
        response.flushBuffer();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
