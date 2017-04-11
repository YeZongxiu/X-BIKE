package com.ucmed.common.util;

import com.ucmed.common.dao.user.UserSessionMapper;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by wzyk88 on 2017/4/8.
 */
public class HttpXmlUtil {
    private final static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";
    private final static String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&";

    public static String post(String url, JSONObject params) {
    DefaultHttpClient httpclient = new DefaultHttpClient();
    String body = null;
    HttpPost post = postForm(url, params);
    body = invoke(httpclient, post);
    httpclient.getConnectionManager().shutdown();
    return body;
}

    public static String get(String url) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;
        HttpGet get = new HttpGet(url);
        body = invoke(httpclient, get);
        httpclient.getConnectionManager().shutdown();
        return body;
    }

    private static String invoke(DefaultHttpClient httpclient,
                                 HttpUriRequest httpost) {
        HttpResponse response = sendRequest(httpclient, httpost);
        String body = paseResponse(response);
        return body;
    }

    private static String paseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    private static HttpResponse sendRequest(DefaultHttpClient httpclient,
                                            HttpUriRequest httpost) {
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpPost postForm(String url, JSONObject params) {

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Iterator it = params.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            nvps.add(new BasicNameValuePair(key, params.getString(key)));
        }

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }

    public static String SHA1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getToken(MemcachedClient memcachedClient) {
        String token = null;
        try {
            token = memcachedClient.get("token");
        } catch (Exception e){
        }
        if (token == null){
            String xml = get(GET_TOKEN_URL + "appid=" + Constants.APP_ID + "&secret=" + Constants.APP_SECRET);
            JSONObject result = JSONObject.fromObject(xml);
            token = result.optString("access_token");
            try {
                memcachedClient.set("token", 100 * 60, token);
            }catch (Exception e){
            }
        }
        return token;
    }

    public static String getTicket(MemcachedClient memcachedClient){
        String ticket = null;
        try {
            ticket = memcachedClient.get("ticket");
        } catch (Exception e){
        }
        if (ticket == null){
            String xml = get(GET_TICKET_URL + "access_token=" + getToken(memcachedClient));
            JSONObject result = JSONObject.fromObject(xml);
            ticket = result.optString("ticket");
            try {
                memcachedClient.set("ticket", 100 * 60, ticket);
            }catch (Exception e){
            }
        }
        return ticket;
    }

    public static JSONObject getSignture(MemcachedClient memcachedClient, HttpServletRequest request){
        JSONObject config = new JSONObject();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
        //String basePath = "http://yezongxiu.ngrok.cc" + request.getContextPath() + request.getServletPath();
        String nonceStr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String str =  "jsapi_ticket=" + getTicket(memcachedClient) +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + basePath;
        String signature = SHA1(str);
        config.put("appId", Constants.APP_ID);
        config.put("timestamp", timestamp);
        config.put("nonceStr", nonceStr);
        config.put("signature", signature);
        return config;
    }

}