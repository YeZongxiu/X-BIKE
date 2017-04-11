package com.ucmed.common.config;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class RedirectConstants {
	private static final Logger LOG = Logger.getLogger(RedirectConstants.class);

	public static String REDIRECT_URL = null;
	public static String CHANGZHENG_ID = null;
	public static String THEAPIURL = null;
	public static String HUATUO3_URL = null;
	public static String HUATUO2_URL = null;
	public static String HTTPKEY = null;
	public static String REPORT_URL = null;
	static {
		Properties prop = new Properties();
		InputStream in;
		try {
			in = RedirectConstants.class.getResourceAsStream("/configure.properties");
			prop.load(in);
			REDIRECT_URL = prop.getProperty("redirect.url");
			CHANGZHENG_ID = prop.getProperty("changzheng.id");
			HUATUO2_URL = prop.getProperty("huatuo2.url");
			HUATUO3_URL = prop.getProperty("huatuo3.url");
			THEAPIURL = prop.getProperty("the.api.url");
			HTTPKEY = prop.getProperty("httpKey");
			REPORT_URL = prop.getProperty("report.url");
		} catch(Exception e) {
			LOG.info("", e);
		}
	}
}
