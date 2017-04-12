package com.ucmed.common.controller.admin.login;

import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.net.XBIKE;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.ResultUtil;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "index.htm")
    public String a(HttpServletRequest request, ModelMap map) {
        return "admin/screen/login/index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "checkPassword.htm")
    public void checkPassword(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserModel user = userService.getUser(username);
        if(user == null) {
            result.put("ret_code", 1);
            result.put("ret_info", "用户不存在");
            ResultUtil.writeResult(response, result.toString());
            return;
        }
        if (!StringUtil.isNotBlank(user.getIsAdmin()) || !"1".equals(user.getIsAdmin())){
            result.put("ret_code", 1);
            result.put("ret_info", "用户不存在");
            ResultUtil.writeResult(response, result.toString());
            return;
        }
        String apiName = "api.bike.user.login";
        JSONObject params = new JSONObject();
        params.put("phone", username);
        params.put("password", password);
        result = XBIKE.getInstance().requestActionParams(apiName, params, null).optJSONObject("return_params");
        if (result.optInt("ret_code") == 0){
            request.getSession().setAttribute("pt_admin", result);
            request.getSession().setAttribute("session_id", result.optString("session_id"));
        }
        ResultUtil.writeResult(response, result.toString());
    }

    @RequestMapping(method = RequestMethod.GET, value = "super.htm")
    public String index(HttpServletRequest request, ModelMap map) {
        return "admin/layout/index";
    }

    private void updateUser(UserModel user) {
        user.setLastLoginTime(new Date());
        user.setLoginTime(user.getLoginTime() == null ? 0 : user.getLoginTime() + 1);
    }

    @RequestMapping(method = RequestMethod.GET, value = "loginOut.htm")
    public void loginOut(HttpServletRequest request, ModelMap map, HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();
        String apiName = "api.bike.user.login.out";
        JSONObject params = new JSONObject();
        String session_id = (String) request.getSession().getAttribute("session_id");
        result = XBIKE.getInstance().requestActionParams(apiName, params, session_id).optJSONObject("return_params");
        request.getSession().removeAttribute("session_id");
        response.sendRedirect("/login/index.htm");
    }
}
