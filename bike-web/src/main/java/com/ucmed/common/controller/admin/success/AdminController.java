package com.ucmed.common.controller.admin.success;

import com.ucmed.common.model.user.UserModel;
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
    @RequestMapping(method = RequestMethod.GET, value = "menu.htm")
    public String menu(HttpServletRequest request, ModelMap map) {
        return "admin/layout/menu";
    }
}
