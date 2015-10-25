package weixin.test;

import java.util.Calendar;

import org.junit.Test;

import com.zxytech.util.DatetimeUtils;

public class TestMethod {

    @Test
    public void testStringFormt() throws Exception {
        System.out.println(String.format("jdbc:mysql://%s:%d/%s%n", "localhost",
                8080, "grape20"));
        System.out.println("ok...");
    }

    @Test
    public void testName1() throws Exception {
        System.out.println("cityname".toUpperCase());
        System.out.println(DatetimeUtils.currentTimestamp().getTime());
        Calendar calendar = Calendar.getInstance();
        // calendar.setTime(DatetimeUtils.currentTimestamp());
        int i = calendar.get(Calendar.YEAR);
        System.out.println(i);
        i = calendar.get(Calendar.MONTH) + 1;
        System.out.println(i);
        i = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(i);

        System.out.println("手机-18611478781".substring(3));

        System.out.println(DatetimeUtils.weekPlus(
                DatetimeUtils.currentTimestamp(), -1));

    }

}
