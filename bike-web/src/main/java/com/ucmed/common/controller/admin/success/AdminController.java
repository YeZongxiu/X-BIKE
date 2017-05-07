package com.ucmed.common.controller.admin.success;

import com.ucmed.common.net.XBIKE;
import com.ucmed.common.util.GetDistanceUtil;
import com.ucmed.common.util.ResultUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping(method = RequestMethod.GET, value = "getParkInfo.htm")
    public String getParkInfo(HttpServletRequest request, ModelMap map) {
        JSONObject result = new JSONObject();
        String apiName = "api.bike.admin.get.park";
        JSONObject params = new JSONObject();
        String session_id = (String) request.getSession().getAttribute("session_id");
        result = XBIKE.getInstance().requestActionParams(apiName, params, session_id).optJSONObject("return_params");
        GetDistanceUtil.tranToBaiDu(result, "parking_list");
        map.put("result", result);
        return "admin/screen/parking/info";
    }

    @RequestMapping(method = RequestMethod.GET, value = "getForbid.htm")
    public String getForbid(HttpServletRequest request, ModelMap map) {
        JSONObject result = new JSONObject();
        String apiName = "api.bike.admin.forbid.space";
        JSONObject params = new JSONObject();
        String session_id = (String) request.getSession().getAttribute("session_id");
        result = XBIKE.getInstance().requestActionParams(apiName, params, session_id).optJSONObject("return_params");
        GetDistanceUtil.tranToBaiDu(result, "cannotParking_list");
        map.put("result", result);
        return "admin/screen/forbid/space";
    }

    @RequestMapping(method = RequestMethod.POST, value = "editForbid.htm")
    public void addForbid(HttpServletRequest request, ModelMap map, HttpServletResponse response) throws IOException  {
        JSONObject result = new JSONObject();
        String apiName = "api.bike.admin.edit.forbid";
        JSONObject params = new JSONObject();
        params.put("id", request.getParameter("id"));
        params.put("longitude", request.getParameter("longitude"));
        params.put("latitude", request.getParameter("latitude"));
        params.put("radius", request.getParameter("radius"));
        params.put("start_time", request.getParameter("start_time").replace("T", " "));
        params.put("end_time", request.getParameter("end_time").replace("T", " "));
        GetDistanceUtil.toGaoDe(params);
        String session_id = (String) request.getSession().getAttribute("session_id");
        result = XBIKE.getInstance().requestActionParams(apiName, params, session_id).optJSONObject("return_params");
        ResultUtil.writeResult(response, result.toString());
    }

    @RequestMapping(method = RequestMethod.POST, value = "addParkInfo.htm")
    public void addParkInfo(HttpServletRequest request, ModelMap map, HttpServletResponse response) throws IOException  {
        JSONObject result = new JSONObject();
        String apiName = "api.bike.add.parking";
        JSONObject params = new JSONObject();
        params.put("id", request.getParameter("id"));
        params.put("longitude", request.getParameter("longitude"));
        params.put("latitude", request.getParameter("latitude"));
        params.put("number", request.getParameter("number"));
        GetDistanceUtil.toGaoDe(params);
        String session_id = (String) request.getSession().getAttribute("session_id");
        result = XBIKE.getInstance().requestActionParams(apiName, params, session_id).optJSONObject("return_params");
        ResultUtil.writeResult(response, result.toString());
    }
}
