package com.zxytech.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/**
 * 服务器校验加密工具类
 * 
 * @author Liubao
 * @2015年7月11日
 * 
 */
public class SignUtil {
   
    public static boolean checkSignature(String token,String signature, String timestamp,
            String nonce) {
        if (MyStringUtils.hasNull(token, signature, timestamp, nonce)) {
            return false;
        }
        String[] arr = { token, timestamp, nonce };
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        return tmpStr != null &&tmpStr.equalsIgnoreCase(signature.toUpperCase());
    }

    private static String byteToStr(byte[] digest) {
        String strDigest = "";
        for (int i = 0; i < digest.length; i++) {
            strDigest = strDigest + byteToHexStr(digest[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte b) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4 & 0xF)];
        tempArr[1] = Digit[(b & 0xF)];
        String s = new String(tempArr);
        return s;
    }
}