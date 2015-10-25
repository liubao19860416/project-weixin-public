package com.zxytech.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.Set;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import com.alibaba.fastjson.JSON;

/**
 * 定义的常量值信息
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public abstract class Constants {
    
    private Constants() {
    }
    
    public static final String APPKEY_JUHE = "";
    
    //消息请求
    //天气查询
    public static final String JUHE_WEATHER_HTTPURL = "http://v.juhe.cn/weather/index?format=2&cityname=CITYNAME&key=a030ee6be0182e68e8be03824a66406c"+APPKEY_JUHE;;
    //手机归属地查询
    public static final String JUHE_PHONENUMBER_HTTPURL = "http://apis.juhe.cn/mobile/get?phone=PHONENUMBER&key=7aaf4baf6cb6d52e39efb01f72d0e787"+APPKEY_JUHE;;
    //商品简单比价查询接口
    public static final String JUHE_GOODNAME_HTTPURL = "http://api2.juheapi.com/mmb/search/simple?keyword=GOODNAME&key=ffca0c54db339d65a817cbece8dd7d34"+APPKEY_JUHE;;
    //QQ吉凶运程预测
    public static final String JUHE_QQNUMBER_HTTPURL = "http://japi.juhe.cn/qqevaluate/qq?qq=QQNUMBER&key=1c464d2cc30823c24892c0e233b12b8a"+APPKEY_JUHE;
    //商品菜谱查询接口
    public static final String JUHE_COOKMENU_HTTPURL = "http://apis.juhe.cn/cook/query?key=&menu=COOKMENU&rn=30&pn=0&key=f9719088eed06cd59af7b3df22fffcff"+APPKEY_JUHE;
    
    //银行卡信息查询
    public static final String JUHE_BANKCARD_HTTPURL = "http://v.juhe.cn/bankcard/query?card=BANKCARD&key="+APPKEY_JUHE;
    //身份证信息查询
    public static final String JUHE_IDNUMBER_HTTPURL = "http://apis.juhe.cn/idcard/index?cardno=IDNUMBER&key="+APPKEY_JUHE;
    //公交路线信息查询
    public static final String JUHE_BUSLINE_HTTPURL = "http://api.juheapi.com/bus/line?city=BUSLINE_CITY&q=BUSLINE_NUM&key="+APPKEY_JUHE;
    //英语单词翻译查询接口
    public static final String JUHE_CHECKWORD_HTTPURL = "http://japi.juhe.cn/youdao/dictionary.from?word=CHECKWORD&only=&key="+APPKEY_JUHE;
    
    //笑话列表查询,CLIKC事件,时间戳（10位)
//    public static final String JUHE_LAUGHSTORY_HTTPURL = "http://japi.juhe.cn/joke/content/list.from?page=1&pagesize=20&sort=asc&time=TIMEBEGIN&key=34488a1d2798c93b0b0311004a586433"+APPKEY_JUHE;
    public static final String JUHE_LAUGHSTORY_HTTPURL = "http://japi.juhe.cn/joke/content/text.from?page=1&pagesize=20&key=34488a1d2798c93b0b0311004a586433"+APPKEY_JUHE;
    //历史上的今天,CLIKC事件
    public static final String JUHE_TODAYINHIS_HTTPURL = "http://api.juheapi.com/japi/toh?v=1.0&month=MONTH&day=DAY&key=ea479e62326316bcd3919d847bbabe3a"+APPKEY_JUHE;
    
    
    //百度api坐标转换\搜索圆形区域检索
    public static final String BAIDU_QUERY_REGIN_HTTPURL = "http://api.map.baidu.com/place/v2/search?query=QUERY&location=LOCATIONX,LOCATIONY&radius=2000&output=json&ak=2ypeiW3drvccDTAArRkYjMtd"+APPKEY_JUHE;
    public static final String BAIDU_UID_HTTPURL = "http://api.map.baidu.com/place/v2/detail?uid=UID&output=json&scope=2&ak=2ypeiW3drvccDTAArRkYjMtd"+APPKEY_JUHE;
   
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final int ARACLE_MAXSIZE = 5;

    //测试帐号liubao手机号
    public static final String APP_ID = "wx1772dcd877ef5da5";
    public static final String APP_SERCRET = "3d68306fe922125be82f189eb77058b2";
    
    //曲阳艺人
//    public static final String APP_ID = "wx118da1735da60886";
//    public static final String APP_SERCRET = "a297ef67d5506a61e6542b3c1a3f20c7";
    public static final String APP_TOKEN = "wasabili"; 
    public static final String PROPCONFIG_FILENAME_PREFIX = "/"; // 文件路径名
    public static final String PROPCONFIG_FILENAME_SUFFIX = ".properties"; // 属性文件后缀名
    public static final String PROPCONFIG_FILENAME = "messages"; // 编号通配符

    //一些字符常量
    public static final String CODE_WILDCARD = "*"; // 编号通配符
    public static final String HTML_WRAP_CHARACTER = "<br/>"; // HTML换行符
    public static final String TEXT_WRAP_CHARACTER = "\n"; // 文本消息换行符
    public static final String TEXT_POINT_CHN = "。"; // 文本消息句号
    public static final String TEXT_POINT_EN = "."; // 文本消息句号
    public static final String TEXT_COMMA_CHN = "，"; // 文本消息逗号
    public static final String TEXT_COMMA_EN = ","; // 文本消息逗号
    public static final String TEXT_SEMICOLON_EN = ";"; // 文本消息逗号
    public static final String TEXT_COLON_EN = ":"; // 文本消息冒号
    public static final int KEYWORDS_ARRAYL_ENGTH =10;

    public static final HashMap<Integer, String> weekMap = new HashMap<>();
    public static final Map<String,String> keyWordsMap;// =new HashMap<>(KEYWORDS_ARRAYL_ENGTH);
    public static final String[] keyWordsArray ;//=new String[KEYWORDS_ARRAYL_ENGTH];
    public static final List<String> keyWordsList;//=new ArrayList<String>(KEYWORDS_ARRAYL_ENGTH);
    
    //菜单信息
    public static final String[] menuArray;
    
    public static final Map<String,String> propMap;// =new HashMap<>();
    
    private static final Map<Class<? extends Enum<?>>, Enum<?>[]> mapEnumTypes = new HashMap<>();
    
    public static void main(String[] args) {
        
        Enum<?> enum1 = getEnumByValue(UserCouponStatus.class, "已过期");
        System.out.println(enum1.toString());
        
        System.out.println(JSON.toJSONString(keyWordsMap));
        System.out.println(JSON.toJSONString(keyWordsArray));
        System.out.println(JSON.toJSONString(keyWordsList));
        
        System.out.println(propMap);
        
    }
    
    static {
        String keyWord0=" ";
        String keyWord1="？";
        String keyWord2="?";
        String keyWord3="在吗";
        String keyWord4="你好";
        String keyWord5="您好";
        
        String keyWord6="天气6";
        String keyWord7="天气7";
        String keyWord8="";
        String keyWord9="";
        
        //初始化Map信息
        keyWordsMap=new LinkedHashMap<>(KEYWORDS_ARRAYL_ENGTH);
        keyWordsMap.put("0", keyWord0);
        keyWordsMap.put("1", keyWord1);
        keyWordsMap.put("2", keyWord2);
        keyWordsMap.put("3", keyWord3);
        keyWordsMap.put("4", keyWord4);
        keyWordsMap.put("5", keyWord5);
        keyWordsMap.put("6", keyWord6);
        keyWordsMap.put("7", keyWord7);
        keyWordsMap.put("9", keyWord9);
        keyWordsMap.put("8", keyWord8);
        
        //同步的Map
        //Collections.synchronizedMap(keyWordsMap);
        
        //KEY不区分大小写的Map
        @SuppressWarnings({ "unchecked", "unused" })
        Map<String,String> caseInsensitiveMap = new CaseInsensitiveMap(KEYWORDS_ARRAYL_ENGTH);
        //caseInsensitiveMap = new CaseInsensitiveMap(keyWordsMap);
        
        //Map转换为Collection
        Collection<String> values = keyWordsMap.values();
        
        //Collection转换为数组
        keyWordsArray=values.toArray(new String[0]);
        
        //转换为List
        //keyWordsList = Arrays.asList(keyWordsArray);
        keyWordsList = new ArrayList<String>(keyWordsMap.values());
        
        //List转换为数组:方式1
        //keyWordsArray=keyWordsList.toArray(new String[0]);
        
        //Collection转换为List:方式
        //keyWordsList = new ArrayList<String>(keyWordsMap.values());
        
        //数组转换为List:方式1
        //Collections.addAll(keyWordsList, keyWordsArray);
        //数组转换为List:方式2
        //keyWordsList = Arrays.asList(keyWordsArray);
        //数组转换为List:方式3
        //keyWordsList=new ArrayList<String>(KEYWORDS_ARRAYL_ENGTH);
        //CollectionUtils.addAll(keyWordsList, keyWordsArray);
        
        //排序
        Arrays.sort(keyWordsArray);
        Collections.sort(keyWordsList);
        
        //菜单信息
        menuArray=new String[]{"菜谱","红烧肉","排骨汤","烧烤","烧鱼","烧鸡","鸡排","猪蹄"};
        
    }

    //初始化枚举Map静态常量
    static {
        mapEnumTypes.put(OrderStatus.class,
                OrderStatus.class.getEnumConstants());
        mapEnumTypes.put(UserCouponStatus.class,
                UserCouponStatus.class.getEnumConstants());

        weekMap.put(0, "星期日");
        weekMap.put(1, "星期一");
        weekMap.put(2, "星期二");
        weekMap.put(3, "星期三");
        weekMap.put(4, "星期四");
        weekMap.put(5, "星期五");
        weekMap.put(6, "星期六");
        
        //初始化配置文件信息
        propMap=initPropConfig();
        
        //输出信息
        for (Entry<String, String> entry : propMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("propMap[key="+key+",value="+value+"]");
        }
        
    }
    
    
    // 用户保养券状态--1 已生成(未激活),2 已发放(已激活),3 已使用 ,4 已结算,5 已关联,6 已取消,7 已过期
    public static enum UserCouponStatus {
        GENERATED(1, "已生成"), 
        ISSUED(2, "已发放"), 
        USED(3, "已使用"), 
        SETTLED(4, "已结算"), 
        ASSOCIATED(5, "已关联"), 
        CANCELED(6, "已取消"), 
        EXPIRED(7, "已过期"),
        NA(0, "-"); // N/A 未知

        private int key;
        private String value;

        private UserCouponStatus(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 返回对应的key的方法
         */
        public static int getKey(UserCouponStatus couponStatus) {
            int key = 0;
            UserCouponStatus[] values = UserCouponStatus.values();
            for (UserCouponStatus us : values) {
                if (couponStatus.getValue().equalsIgnoreCase(us.getValue())) {
                    key = us.getKey();
                }
            }
            return key;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }

    public static enum OrderStatus {
        UNCONFIRMED(1, "未确认", 1), 
        CONFIRMED(2, "已确认", 2), 
        REFUSED(3, "已拒绝", 5), 
        FINISHED(9, "已完工", 3), 
        KMUPDATED(11, "已更新公里数", 4), 
        CANCELED(99, "已取消", 6), 
        COMMENTED(97, "已评论", 12), 
        EXPIRED(96, "已过期", 13), 
        VIEWREFUSED(95,"拒绝已查看", 14), 
        ERROR(404, "有错误", 404);
        
        private String key;
        private int code;
        private int sort;

        OrderStatus(int code, String key, int sort) {
            this.key = key;
            this.code = code;
            this.sort = sort;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return getKey();
        }

        public static OrderStatus getKey(int code) {
            OrderStatus[] os = OrderStatus.values();
            for (int i = 0; i < os.length; i++) {
                if (os[i].getCode() == code) {
                    return os[i];
                }
            }
            return OrderStatus.ERROR;
        }
    }

    public static Enum<?> getEnumByValue(Class<? extends Enum<?>> enumClass,
            String value) {

        if (enumClass == null || !enumClass.isEnum()) {
            throw new IllegalArgumentException("Argument enumClass is null!!");
        }

        if (value == null || "".equals(value)) {
            return null;
        }

        for (Class<? extends Enum<?>> eclass : mapEnumTypes.keySet()) {
            if (eclass.equals(enumClass)) {
                for (Enum<?> e : mapEnumTypes.get(eclass)) {
                    if (e.toString().equals(value)) {
                        return e;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Enum class \""
                + enumClass.getName() + "\"" + " with value \"" + value + "\""
                + " is not supported!!");
    }
    
    /**
     * 初始化读取配置文件信息
     * @return 
     */
    @SuppressWarnings("unused")
    private static Map<String, String> initPropConfig() {
        
        Map<String, String> resultMap=new HashMap<>();

        try {
            
            //方式3,必须不能添加绝对路径分隔符和文件后缀名
            PropertyResourceBundle rb = (PropertyResourceBundle) PropertyResourceBundle
                    .getBundle(PROPCONFIG_FILENAME,Locale.CHINA); 
            Set<String> keySet = rb.keySet();
            for (String key : keySet) {
                String value = rb.getString(key);
                resultMap.put(key, value);
                System.out.println("ResourceBundle[key="+key+",value="+value+"]");
            }
            
            //方式2,必须添加文件后缀名,绝对路径分隔符可有可无,推荐使用
            InputStream in2 = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(PROPCONFIG_FILENAME+PROPCONFIG_FILENAME_SUFFIX);
            
            //方式1,需要路径信息,得到一个输入流,必须添加绝对路径分隔符和文件后缀名,建议使用方式2
            InputStream fis = Constants.class.getResourceAsStream(PROPCONFIG_FILENAME_PREFIX
                    +PROPCONFIG_FILENAME+PROPCONFIG_FILENAME_SUFFIX);
            
            Properties p = new Properties();
            p.load(fis);
            Set<Entry<Object,Object>> entrySet = p.entrySet();
            for (Entry<Object, Object> entry : entrySet) {
                String key = MyStringUtils.getStringValue(entry.getKey());
                String value = MyStringUtils.getStringValue(entry.getValue());
                resultMap.put(key, value);
                //System.out.println("PROP[key="+key+",value="+value+"]");
            }
        } catch (FileNotFoundException e) {
            // 如果这个异常是致命的(比如数据库连不了)，可以抛出这个错误
            throw new ExceptionInInitializerError();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return resultMap;
    }
    

}
