package com.cangzhitao.jbf.core.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;


public class IPUtil {

	public static String getRequestIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For") == null ? request
				.getHeader("x-forwarded-for") : request
				.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null) {
			return "";
		}
		String[] ips = ip.split(",");
		return ips[0];
	}

	/**
	 * 判断是否是IP地址
	 * @param str
	 * @return
	 */
	public static boolean isIPAdress(String ip) {
		/*Pattern pattern = Pattern.compile( "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$" );
		return pattern.matcher(ip).matches();*/
		return Util.isIpAddress(ip);
	}

	private static DbConfig config = null;
	
	private static DbSearcher searcher = null;
	
	private static String dbFile = IPUtil.class.getResource("/ip2region.db").toString().substring(5); 
	static {
		try {
			config = new DbConfig();
			searcher = new DbSearcher(config, dbFile);
		} catch (DbMakerConfigException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 中国|0|广东|广州|移动
	 * @param ip
	 * @return
	 */
	public static String getIpRegion(String ip) {
        DataBlock block = null;
		try {
			block = searcher.btreeSearch(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return block.getRegion();
	}
	
}
