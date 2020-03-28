package com.jgkj.utils.token.utils.des;

import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * MD5 加密
 * Created by jugekeji on 2017/9/11.
 */

public class HmacMd5 {

    /**
     * MD5 加密 ，返回 16 位的字符串
     *
     * @param key
     * @param data
     * @return MD5 加密 16 位的字符串
     */
    public static String getHmacMd5(byte[] key, byte[] data) {
        try {
            String HEX = "0123456789abcdef";
            SecretKeySpec keySpec = new SecretKeySpec(key, "HmacMD5");
            Mac mac = Mac.getInstance(keySpec.getAlgorithm());
            mac.init(keySpec);
            byte[] bytes = mac.doFinal(data);
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
                sb.append(HEX.charAt((b >> 4) & 0x0f));
                // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
                sb.append(HEX.charAt(b & 0x0f));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * md5加密，不可解密
     *
     * @param plainText
     * @return
     */
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
