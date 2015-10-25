package com.zxytech.dao;

import java.util.List;

import com.zxytech.jdbc.DBCPUtil;
import com.zxytech.jdbc.MyDBAssist;
import com.zxytech.jdbc.resulthandler.BeanListResultSetHandler;
import com.zxytech.jdbc.resulthandler.BeanResultSetHandler;
import com.zxytech.vo.AccessTokenVO;

public class AccessTokenDao {
    
    private AccessTokenDao() {
        super();
    }

    /**
     * 获取方法
     */
    public static AccessTokenVO getActivedAccessToken() {
        MyDBAssist dba = new MyDBAssist();
        String sql = "select t.*,TIMESTAMPADD(SECOND,t.expiresIn,t.createdDatetime) as experiedDatetime  from t_access_token t order by t.createdDatetime desc limit 1";
        Object[] pramas = new String[] {};
        AccessTokenVO accessToken = (AccessTokenVO) dba.queryOne(sql,
                new BeanResultSetHandler(AccessTokenVO.class), pramas);
        System.out.println(accessToken);
        dba=null;
        return accessToken;
    }

    /**
     * 插入方法
     */
    public static int insertAccessToken(String token,Long expiresIn) {
        MyDBAssist dba = new MyDBAssist();
        String sql = "insert into t_access_token(token,expiresIn,usersAppId) values('"
                + token + "','" + expiresIn + "','1')";
        // String sql = "insert into t_access_token(token,expiresIn) values(?,?)";
        Object[] pramas = new Object[] { token, expiresIn };
        int result = dba.insertDB(sql, pramas);
        return result;
    }
    
    public static List<AccessTokenVO>  getAccessTokenList() {
        MyDBAssist dba = new MyDBAssist();
        String sql = "select t.*,TIMESTAMPADD(SECOND,t.expiresIn,t.createdDatetime) as experiedDatetime  from t_access_token t";
        Object[] pramas = new String[0];
        List<AccessTokenVO> list = (List<AccessTokenVO>) dba.queryAll(sql,
                new BeanListResultSetHandler(AccessTokenVO.class), pramas);
        for (AccessTokenVO token : list) {
            System.out.println(token);
        }
        return list;
    }
    
}
