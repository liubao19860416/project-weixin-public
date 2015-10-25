package com.zxytech.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * 此接口的实例管理使用哪一个 X509 证书来验证远端的安全套接字。决定是根据信任的证书授权、证书撤消列表、在线状态检查或其他方式做出的。
 * 
 * @author Liubao
 * @2015年6月30日
 * 
 */
public class MyX509TrustManager implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] arg0, String arg1)
            throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] arg0, String arg1)
            throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}