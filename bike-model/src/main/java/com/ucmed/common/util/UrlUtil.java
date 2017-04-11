package com.ucmed.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by ucmed on 2017/3/15.
 */
public class UrlUtil{
    /**
     * 获取请求完整URL
     * @param request
     * @return
     */
    private static Logger LOG = Logger.getLogger(UrlUtil.class);

    public static String getRequestURL(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String url="";
        try {
            url= request.getContextPath()      //项目名称
                    + request.getServletPath()      //请求页面或其他地址
                    + "?" + (request.getQueryString()); //参数

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return url;
    }

    /**
     * 编码转换
     * @param str
     * @return
     */
    public static String getParams(String str){
        try {
            if(StringUtils.isEmpty(str)){
                str="";
            }else if(str.equals(new String(str.getBytes("ISO-8859-1"), "ISO-8859-1"))){
                LOG.debug("ISO-8859-1");
                str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
            }/*else if(str.equals(new String(str.getBytes("gbk"), "gbk"))){
				LOG.debug("gbk");
				str = new String(str.getBytes("gbk"), "UTF-8");
			}*/
        } catch (UnsupportedEncodingException e) {
            LOG.info("", e);
        }
        return str;
    }
}

