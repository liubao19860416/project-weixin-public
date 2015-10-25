package weixin.test;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.zxytech.jdbc.DBCPUtil;
import com.zxytech.jdbc.MyDBAssist;
import com.zxytech.jdbc.resulthandler.BeanListResultSetHandler;
import com.zxytech.jdbc.resulthandler.BeanResultSetHandler;
import com.zxytech.util.DatetimeUtils;
import com.zxytech.vo.AccessTokenVO;

/**
 * 数据库操作帮助类
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class MyDBAssistTest {

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }

    @Test
    public void testQueryAll() {
        MyDBAssist dba = new MyDBAssist();
        String sql = "select t.*,TIMESTAMPADD(SECOND,t.expiresIn,t.createdDatetime) as experiedDatetime  from t_access_token t";
        Object[] pramas = new String[0];
        List<AccessTokenVO> list = (List<AccessTokenVO>) dba.queryAll(sql,
                new BeanListResultSetHandler(AccessTokenVO.class), pramas);
        for (AccessTokenVO token : list) {
            System.out.println(token);
        }
    }

    @Test
    public void testQueryOne() {
        MyDBAssist dba = new MyDBAssist();
        String sql = "select t.*,TIMESTAMPADD(SECOND,t.expiresIn,t.createdDatetime) as experiedDatetime  from t_access_token t order by t.createdDatetime desc limit 1";
        Object[] pramas = new String[] {};
        AccessTokenVO accessToken = (AccessTokenVO) dba.queryOne(sql,
                new BeanResultSetHandler(AccessTokenVO.class), pramas);
        System.out.println(accessToken);
    }

    @Test
    public void testInsert() {
        MyDBAssist dba = new MyDBAssist();
        String token="aufiuae24iofpaui09as0fi02342393224e";
        Long expiresIn=7200L;
        String sql = "insert into t_access_token(token,expiresIn) values('"+token+"','"+expiresIn+"')";
//        String sql = "insert into t_access_token(token,expiresIn) values(?,?)";
        Object[] pramas =  new Object[] {token,expiresIn};
        int result = dba.insertDB(sql, pramas);
        Assert.assertEquals(1, result);
    }

}
