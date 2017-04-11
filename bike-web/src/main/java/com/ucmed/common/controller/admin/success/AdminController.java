package com.ucmed.common.controller.admin.success;

import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.net.XBIKE;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.ResultUtil;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping(method = RequestMethod.GET, value = "getParkInfo.htm")
    public String menu(HttpServletRequest request, ModelMap map) {
        JSONObject result = new JSONObject();
        String apiName = "api.bike.admin.get.park";
        JSONObject params = new JSONObject();
        String session_id = (String) request.getSession().getAttribute("session_id");
        result = XBIKE.getInstance().requestActionParams(apiName, params, session_id).optJSONObject("return_params");
        map.put("result", result);
        return "admin/screen/parking/info";
    }
}
