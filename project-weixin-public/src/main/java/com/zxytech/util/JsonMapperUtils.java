package com.zxytech.util;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;

import com.zxytech.pojo.Button;

/**
 * Json格式数据转换
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class JsonMapperUtils {

    private final static ObjectMapper jacksonMapper = new ObjectMapper();
    
    public static void main(String[] args) {
        Button button1=new Button();
        button1.setName("mingzi");
        String jsonStr=toJson(button1);
        
        Button button2 = fromJson(Button.class, jsonStr);
        
        Assert.assertNotNull(button2);
    }
    

    public static <T> T fromJson(Class<T> clazz, String jsonStr) {
        T jsonObj = null;
        if (jsonStr != null && !"".equals(jsonStr)) {
            try {
                jsonObj = jacksonMapper.readValue(jsonStr, clazz);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return jsonObj;
    }

    public static String toJson(Object obj) {
        if (obj != null) {
            try {
                return jacksonMapper.writeValueAsString(obj);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

}
