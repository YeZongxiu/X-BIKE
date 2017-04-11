package com.ucmed.common.net;

import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class HttpConnectionManager {

	private static HttpParams httpParams;
	private static ClientConnectionManager connectionManager;

	private static Properties props = null;
	static {
		try {
			if(props == null) {
				Resource resource = new ClassPathResource("/configure.properties");
				props = PropertiesLoaderUtils.loadProperties(resource);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = Integer.parseInt(props.getProperty(
			"MAX_TOTAL_CONNECTIONS", "400"));
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = Integer.parseInt(props.getProperty("WAIT_TIMEOUT",
			"60000"));
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = Integer.parseInt(props.getProperty(
			"MAX_ROUTE_CONNECTIONS", "400"));
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = Integer.parseInt(props.getProperty("CONNECT_TIMEOUT",
			"10000"));;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = Integer.parseInt(props.getProperty("READ_TIMEOUT",
			"40000"));;

	static {
		httpParams = new BasicHttpParams();
		// 设置最大连接数
		ConnManagerParams.setMaxTotalConnections(httpParams, MAX_TOTAL_CONNECTIONS);
		// 设置获取连接的最大等待时间
		ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
		// 设置每个路由最大连接数
		ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
		// 设置连接超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
		// 设置读取超时时间
		HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);

		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		connectionManager = new ThreadSafeClientConnManager(httpParams, registry);
	}

	public static HttpClient getHttpClient() {
		return new DefaultHttpClient(connectionManager, httpParams);
	}

}
