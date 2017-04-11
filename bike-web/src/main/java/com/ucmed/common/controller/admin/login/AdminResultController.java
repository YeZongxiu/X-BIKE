/**
 * 
 */
package com.ucmed.common.controller.admin.login;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author John Lee
 */
@Controller
@RequestMapping("/admin/result.htm")
public class AdminResultController {

    @RequestMapping(method = RequestMethod.GET)
    public String result(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException {
        String status = request.getParameter("status");
        String memo = request.getParameter("memo");
        String returnUrl = request.getParameter("returnUrl");
        String right = request.getParameter("right");

        map.put("returnUrl", returnUrl);
        map.put("status", "true".equalsIgnoreCase(status) ? "操作成功" : "操作失败");
        if(memo!=null){
            map.put("memo", new String(Base64.decodeBase64(memo.getBytes())));
        }       
        map.put("right", right);
        return "admin/screen/result";
    }
}
