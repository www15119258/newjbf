package com.cangzhitao.jbf.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

	private volatile static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
	
	public static String getProperty(String prop, String key) {
		Properties properties = loadProperties(prop);
		if(properties==null) {
			return null;
		}
		return properties.getProperty(key);
	}
	
	public static String getProperty(String prop, String key, String defaultValue) {
		Properties properties = loadProperties(prop);
		if(properties==null) {
			return defaultValue;
		}
		return properties.getProperty(key, defaultValue);
	}
	
	public static Properties loadProperties(String prop) {
		return loadProperties(null, prop);
	}
	
	public static Properties loadProperties(String[] activeProfiles, String prop) {
		Properties properties = propertiesMap.get(prop);
		if(properties!=null) {
			return properties;
		}
		synchronized (PropertiesUtil.class) {
			if(properties==null) {
				properties = new Properties();
				InputStream infile1 = null;
				InputStream infile2 = null;
				try {
					ClassLoader cl = Thread.currentThread().getContextClassLoader();
					infile1 = cl.getResourceAsStream(prop+".properties");
					if(infile1!=null) {
						properties.load(infile1);
					}
					if(activeProfiles==null) {
						try {
							activeProfiles = SpringUtil.getApplicationContext().getEnvironment().getActiveProfiles();
						} catch (Exception e) {
						}
					}
					String active = "";
					if(activeProfiles!=null&&activeProfiles.length>0) {
						active = "-"+activeProfiles[0];
						infile2 = cl.getResourceAsStream(prop+active+".properties");
						if(infile2!=null) {
							Properties p2 = new Properties();
							p2.load(infile2);
							properties.putAll(p2);
						}
					}
				} catch (Exception e) {
				} finally {
					if(infile1!=null) {
						try {
							infile1.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(infile2!=null) {
						try {
							infile2.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				propertiesMap.put(prop, properties);
			}
		}
		return properties;
	}
	
}
