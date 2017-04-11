package com.ucmed.common.config;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CommonConstants {
	private static final Logger LOG = Logger.getLogger(CommonConstants.class);
	public static String HOSPITAL_NAME = null;
	public static String WECHAT_THEME_COLOR = null;
	public static String WECHAT_THEME_COLOR_OLD = null;
	public static String ABSOLUTE_URL = null;
	static {
		Properties prop = new Properties();
		InputStream in;
		try {
			in = CommonConstants.class.getResourceAsStream("/configure.properties");
			prop.load(in);
			HOSPITAL_NAME = prop.getProperty("HOSPITAL.NAME");
			WECHAT_THEME_COLOR = prop.getProperty("WECHAT.THEME.COLOR");
			WECHAT_THEME_COLOR_OLD = prop.getProperty("WECHAT.THEME.COLOR.OLD");
			ABSOLUTE_URL = prop.getProperty("ABSOLUTE.URL");
		} catch(Exception e) {
			LOG.info("", e);
		}
	}
}
