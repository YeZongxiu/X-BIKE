package com.ucmed.common.api.fix;

import com.ucmed.common.api.bike.GetParkingBikeApi;
import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.fix.FixOrderModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.fix.FixOrderService;
import com.ucmed.common.service.parking.ParkingSpaceService;
import com.ucmed.common.util.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzyk88 on 2017/3/28.
 */
public class GetBikeFixParkingApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(GetParkingBikeApi.class);
    private FixOrderService fixOrderService;

    public void setFixOrderService(FixOrderService fixOrderService) {
        this.fixOrderService = fixOrderService;
    }

    @Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        if (params == null || params.isEmpty()){
            return errorResult(result, "失败，参数错误。", "错误：参数为空。");
        }
        String longitude = params.optString("longitude");
        String latitude = params.optString("latitude");
        if (null == longitude || null == latitude){
            return errorResult(result, "位置信息错误。", "经纬度为空");
        }
        JSONObject model = fixOrderService.getBikeFixList(Double.valueOf(longitude), Double.valueOf(latitude));
        result.put("longitude", longitude);
        result.put("latitude", latitude);
        if (model == null) {
            return errorResult(result, "暂无需要维修车辆。", "暂无需要维修车辆");
        }
        result.put("fix_info", model);
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "获取报修车辆位置成功");
        return result;
    }

    private JSONObject errorResult(JSONObject res, String retInfo, String logString) {
        JSONObject result = res;
        if (result == null) {
            result = new JSONObject();
        }
        result.put("ret_code", 1);
        result.put("ret_info", retInfo);
        LOG.warn(logString);
        return result;
    }
}
