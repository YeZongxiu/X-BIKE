package com.ucmed.common.controller.wechat;

import com.ucmed.common.net.XBIKE;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.GetDistanceUtil;
import com.ucmed.common.util.HttpXmlUtil;
import com.ucmed.common.util.ResultUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ucmed on 2017/3/28.
 */
@Controller
@RequestMapping("/wechat/parking.htm")
public class ParkingSpaceController {
    private Logger log = Logger.getLogger(ParkingSpaceController.class);
    @Autowired
    private MemcachedClient memcachedClient;

    /**
     * 获取用户当前位置
     *
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getUserInfo(HttpServletRequest request, ModelMap map) {
        JSONObject config = HttpXmlUtil.getSignture(memcachedClient, request);
        map.put("config", config);
        return "wechat/screen/fix/user_info";
    }

    /**
     * 获取离用户当前位置最近的车辆
     *
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, params = "action=getFixBike")
    public String getParking(HttpServletRequest request, ModelMap map) {
        String apiName = "api.bike.admin.get.fix";
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        if (null == longitude || null == latitude){
            longitude = Constants.HZ_LONGITUDE;
            latitude = Constants.HZ_LATITUDE;
        }
        String change = request.getParameter("change");
        JSONObject params = new JSONObject();
        if (null == change || "ture".equals(change)){
            JSONObject baiduGPS = GetDistanceUtil.getBaiduGPS(Double.valueOf(longitude), Double.valueOf(latitude));
            params.put("longitude", baiduGPS.optString("x"));
            params.put("latitude", baiduGPS.optString("y"));
        }else {
            params.put("longitude", longitude);
            params.put("latitude", latitude);
        }
        JSONObject result = XBIKE.getInstance().requestActionParams(apiName, params, null);
        map.put("result", result.optJSONObject("return_params"));
        return "wechat/screen/fix/fix_bike";
    }
    /**
     * 修改报修状态，并获取下一辆报修车辆位置
     *
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "action=changeStatus")
    public void changeStatus(HttpServletRequest request, ModelMap map, HttpServletResponse response) throws IOException {
        String changeStatusApi = "api.bike.admin.change.fix.status";
        String id = request.getParameter("id");
        String status = request.getParameter("status");
        JSONObject params = new JSONObject();
        params.put("id", id);
        params.put("status", status);
        JSONObject result = XBIKE.getInstance().requestActionParams(changeStatusApi, params, null);
        ResultUtil.writeResult(response, result.optJSONObject("return_params").toString());
    }
}
