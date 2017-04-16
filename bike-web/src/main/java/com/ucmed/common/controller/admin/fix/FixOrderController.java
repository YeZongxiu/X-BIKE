package com.ucmed.common.controller.admin.fix;

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
@RequestMapping("/admin")
public class FixOrderController {
    @RequestMapping(method = RequestMethod.GET, value = "getFixList.htm")
    public String menu(HttpServletRequest request, ModelMap map) {
        Long pageNo = StringUtil.getValueFromRequestLong(request, "pageNo", 1L);
        if (pageNo <= 0){
            pageNo =1l;
        }
        Long pageSize = StringUtil.getValueFromRequestLong(request, "pageSize",
                10L);
        JSONObject result = new JSONObject();
        String apiName = "api.admin.get.fix.list";
        JSONObject params = new JSONObject();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        String session_id = (String) request.getSession().getAttribute("session_id");
        result = XBIKE.getInstance().requestActionParams(apiName, params, session_id).optJSONObject("return_params");
        map.put("result", result);
        if (pageNo > result.optLong("page_count")){
            pageNo = result.optLong("page_count");
        }
        map.put("page", getPage(pageNo, result.optLong("page_count")));
        map.put("pageSize", pageSize);
        return "admin/screen/fix/list";
    }

    private String getPage(Long pageNo, Long pageCount){
        StringBuffer buffer = new StringBuffer();
        if (pageCount <= 5){
            for (int i = 1; i<= pageCount; i++){
                if (pageNo == i) {
                    buffer.append("<li class='active' id='active'>");
                } else {
                    buffer.append("<li>");
                }
                buffer.append("<a onclick='getList(this)'>" + i + "</a>");
                buffer.append("</li>");
            }
        } else if (pageNo >= pageCount - 5){
            for (Long i = pageCount - 5; i<= pageCount; i++){
                if (pageNo == i) {
                    buffer.append("<li class='active' id='active'>");
                } else {
                    buffer.append("<li>");
                }
                buffer.append("<a onclick='getList(this)'>" + i + "</a>");
                buffer.append("</li>");
            }
        } else {
            buffer.append("<li><a onclick='getList(this)'>" + (pageNo - 1) + "</a></li>");
            buffer.append("<li><a onclick='getList(this)' class='active' id='active'>" + pageNo + "</a></li>");
            buffer.append("<li><a onclick='getList(this)'>" + (pageNo + 1) + "</a></li>");
            buffer.append("<li><a>...</a></li>");
            buffer.append("<li><a onclick='getList(this)'>" + (pageCount - 1) + "</a></li>");
            buffer.append("<li><a onclick='getList(this)'>" + pageCount + "</a></li>");
        }
        return buffer.toString();
    }
}
