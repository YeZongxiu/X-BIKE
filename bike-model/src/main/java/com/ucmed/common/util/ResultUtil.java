package com.ucmed.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ResultUtil {

    /**
     * 向结果页面发送消息。
     * @param response
     * @param status
     * @param returnUrl
     * @param memo
     */
    public static void sendMessageToResultPage(HttpServletResponse response, boolean status,
                                               String returnUrl, String memo) throws IOException {

        String resultUrl = "/admin/result.htm";
        StringBuilder sb = new StringBuilder();
        if(!resultUrl.contains("?"))
            sb.append("?");
        sb.append("returnUrl=");
        // CharsetUtil.iso_8859_1ToUTF_8
        sb.append(URLEncoder.encode(UrlUtil.getParams(returnUrl), "UTF-8"));
        sb.append("&status=");
        sb.append(status);
        sb.append("&memo=");
        sb.append(URLEncoder.encode(new String(Base64.encodeBase64(memo.getBytes())), "UTF-8"));
        response.sendRedirect(resultUrl + sb.toString());
    }

    /**
     * 向结果页面发送消息。
     * @param response
     * @param status
     * @param returnUrl
     */
    public static void sendMessageToResultPage(HttpServletResponse response, boolean status,
                                               String returnUrl) throws IOException {

        String resultUrl = "/admin/result.htm?";
        StringBuilder sb = new StringBuilder();
        sb.append("returnUrl=");
        sb.append(URLEncoder.encode(UrlUtil.getParams(returnUrl), "UTF-8"));
        sb.append("&status=");
        sb.append(status);
        sb.append("&memo=");
        sb.append("11");
        sb.append("&right=");
        sb.append("1");
        response.sendRedirect(resultUrl + sb.toString());
    }

    public static void writeResult(HttpServletResponse response, String res) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.write(res);
        writer.flush();
        writer.close();
    }

    public static void writeResult1(HttpServletResponse response, String res ,String res1) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.write(res);
        writer.write(res1);
        writer.flush();
        writer.close();
    }
}