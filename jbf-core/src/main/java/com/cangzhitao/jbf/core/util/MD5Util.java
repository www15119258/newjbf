package com.cangzhitao.jbf.core.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {

	public static String encodeMD5(String encodeStr) {
		MessageDigest md;
		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(encodeStr.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			String value = new BigInteger(1, md.digest()).toString(16);
			if(value.length()<32) {
				for(int i=32-value.length();i>0;i--) {
					value = "0"+value;
				}
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodeStr;
	}
	
	public static String encodeMD5(String encodeStr, int length) {
		MessageDigest md;
		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(encodeStr.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			String value = new BigInteger(1, md.digest()).toString(16);
			return value.substring(0, length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodeStr;
	}
}
