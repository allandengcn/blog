package cn.allandeng.blog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* @ClassName MD5Utils
* @Date: 2020/2/5 14:17
* @Description: MD5工具类
* @Author: Allan Deng
* @Version: 1.0
**/
public class MD5Utils {

    /**
     * @Title code
     * @Author Allan Deng
     * @Description MD5加密
     * @Date 14:18 2020/2/5
     * @Param [str]
     * @return java.lang.String
     **/
    public static String code(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void main(String[] args) {
        System.out.println(code("Allandeng1997322..."));
    }
}
